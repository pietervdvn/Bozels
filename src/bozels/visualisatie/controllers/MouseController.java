package bozels.visualisatie.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import bozels.exceptions.BozelSkillAlreadyUsedException;
import bozels.exceptions.BozelStillPlayingException;
import bozels.exceptions.NoBozelLoadedException;
import bozels.exceptions.NoBozelPlayingException;
import bozels.exceptions.NoMoreBozelsException;
import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListenerAdapter;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;

/*
 * The mousecontroller is designed for the following actions:
 * 
 * -> Clicking makes the in game bozel do its action
 * 
 * -> Dragging moves the drawing field within limits
 * -> Dragging within 2* max katapult dev charges and places the bozel
 * -> Once charged, there is no range limit
 * -> Releasing the mouse launches the bozel
 * 
 * Optional additions:
 * -> Dragging outside of range moves view
 * -> scrolling changes zoom level
 * 
 * 
 */
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class MouseController extends LevelListenerAdapter implements MouseInputListener, MouseWheelListener {

	private final PainterSettingsModel psm;
	private final Level level;

	public MouseController(Level level, PainterSettingsModel psm) {
		this.psm = psm;
		this.level = level;
		level.addListener(this);
	}
	
	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (level.getInGameBozel() != null && level.isLaunched() && !level.hasBozelUsedSkill()) {
			try {
				level.doBozelAction();
			} catch (BozelSkillAlreadyUsedException e) {
			} catch (NoBozelPlayingException e) {
			}
		}else if (level.getInGameBozel() != null && level.isLaunched()){
			level.remove(level.getInGameBozel());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(!level.isLaunched()){
			try {
				level.launch();
			} catch (NoBozelLoadedException e) {
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(level.getInGameBozel() == null){
			try {
				level.charge();
			} catch (NoMoreBozelsException e) {
			} catch (BozelStillPlayingException e) {
			}
		}
		if(!level.isLaunched()){
			try {
				level.setLaunchPosition((double) (-psm.getXTranslation() + arg0.getX())/psm.getScale(), 
						(double) (psm.getYTranslation() + arg0.getComponent().getHeight() - 
								arg0.getY())/psm.getScale());
			} catch (NoMoreBozelsException e) {
			} catch (BozelStillPlayingException e) {
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		psm.setScale(psm.getScale()-e.getWheelRotation());
	}

}
