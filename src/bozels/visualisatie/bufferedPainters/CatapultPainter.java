package bozels.visualisatie.bufferedPainters;

import static bozels.visualisatie.gameColorModel.GameColorModel.KATAPULT_DOT;
import static bozels.visualisatie.gameColorModel.GameColorModel.KATAPULT_ELASTIC;

import java.awt.Graphics2D;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListener;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.levelModel.settingsModel.SettingsListener;
import bozels.levelModel.settingsModel.SettingsModel;
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
  */
public class CatapultPainter extends BufferedPainter implements LevelListener, SettingsListener, GameColorModelListener{

	private final Level level;
	private final GameColorModel gameColorModel;
	private final PainterSettingsModel settingModel;
	
	public CatapultPainter(Level level, GameColorModel cm, PainterSettingsModel psm) {
		super(psm);
		this.level = level;
		this.gameColorModel = cm;
		this.settingModel = psm;
		level.addListener(this);
		cm.addListener(this);
	}
	
	
	@Override
	public void paintContents(Graphics2D g) {
		if(level == null || level.getKatapultLoc() == null){
			return;
		}
		g.setColor(gameColorModel.getColor(KATAPULT_DOT));
		int dotSize = settingModel.getCatapultDotSize()*settingModel.getScale();
		
		int x = (int) (level.getKatapultLoc().x*settingModel.getScale());
		int y = (int) (level.getKatapultLoc().y*settingModel.getScale());
		g.fillOval(x-dotSize/2,
				y-dotSize/2,
				dotSize, dotSize);
		if(level.getInGameBozel() != null && !level.isLaunched()){
			g.setColor(gameColorModel.getColor(KATAPULT_ELASTIC));
			Vec2 pos = level.getInGameBozel().getBody().getPosition();
			g.drawLine(x, y, (int) (pos.x*settingModel.getScale()),
					(int) (pos.y*settingModel.getScale()));
			
		}
		double katDev = level.getKatapultDev()*settingModel.getScale();
		g.drawOval((int) (x-katDev), (int) (y-katDev), (int) (katDev*2), (int) (katDev*2));
		
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
	public void onObjectsChanged(Level source) {
	}

	@Override
	public void onWorldStepped(Level source) {
	}

	@Override
	public void onSleepingStatusChanged(Level levelEventSource) {
	}

	@Override
	public void onNumberOfTargetsChanged(Level source) {
	}

	@Override
	public void onNumberOfBozelsChanged(Level source) {
		repaint();
	}


	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
	}

	@Override
	public void onBozelUsedSkill(Level level, Bozel bozel) {
	}


	@Override
	public void onGravityChanged(SettingsModel source) {
	}


	@Override
	public void onTimeSettingsChanged(SettingsModel source) {
	}


	@Override
	public void onYThresholdChanged(SettingsModel source) {
	}


	@Override
	public void onKatapultSettingsChanged(SettingsModel source) {
		repaint();
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
