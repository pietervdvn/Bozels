package bozels.visualisatie.bufferedPainters;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListener;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.physicsModel.material.Material;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.gameColorModel.GameColorModelListener;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  * This class paints only the bozels
  */
public class BozelPainter extends BufferedPainter implements LevelListener, GameColorModelListener{

	private final Level level;
	private final GameColorModel cm;
	private final PainterSettingsModel psm;
	
	public BozelPainter(Level level, GameColorModel cm, PainterSettingsModel psm) {
		super(psm);
		this.level = level;
		this.cm = cm;
		this.psm = psm;
		level.addListener(this);
		cm.addListener(this);
	}

	@Override
	public void paintContents(Graphics2D g) {
		List<GameObject> objs = level.getGameObjects();
		int scale = psm.getScale();
		g.setRenderingHint
		(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.translate(0, -psm.getYTranslation());
		//DO NOT SYNCHRONIZE ON GAMEOBJECTS
		for(GameObject obj : objs){
			if(obj.isBozel() || obj.isTarget()){
			obj.paintOn(g, scale, psm.getXTranslation(), -psm.getYTranslation(), cm, 
					psm);
			}
		}
		g.translate(0, psm.getYTranslation());
		
		
	}

	@Override
	public void onObjectsChanged(Level source) {
		repaint();
	}

	@Override
	public void onWorldStepped(Level source) {
		if(!source.isSleeping()){
			repaint();
		}
	}

	@Override
	public void onSleepingStatusChanged(Level levelEventSource) {
		repaint();
	}

	@Override
	public void onNumberOfTargetsChanged(Level source) {
	}

	@Override
	public void onNumberOfBozelsChanged(Level source) {
	}

	@Override
	public void onBozelCharged(Level source, Bozel bozel) {
		repaint();
	}

	@Override
	public void onBozelLaunched(Level source, Bozel bozel) {
		repaint();
	}

	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
		repaint();
	}

	@Override
	public void onBozelUsedSkill(Level level, Bozel bozel) {
	}

	@Override
	public void onBozelMoved(Level level, Bozel bozel) {
		repaint();
	}

	@Override
	public void onColorChanged(GameColorModel source) {
		repaint();
	}

	@Override
	public void onObjectRemoved(Level source, GameObject removed,
			Material material) {
	}

	@Override
	public void onObjectContact(Level source, GameObject obj1, GameObject obj2, int force) {
	}

}
