package bozels.visualisatie;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import bozels.gui.resourceModel.ResourceTracker;
import bozels.levelModel.core.ExplosionLogger;
import bozels.levelModel.core.Level;
import bozels.physicsModel.explosions.Explosion;
import bozels.superModel.SuperModel;
import bozels.visualisatie.bufferedPainters.BackgroundPainter;
import bozels.visualisatie.bufferedPainters.BozelPainter;
import bozels.visualisatie.bufferedPainters.BufferedPainter;
import bozels.visualisatie.bufferedPainters.BufferedPainterListener;
import bozels.visualisatie.bufferedPainters.CatapultPainter;
import bozels.visualisatie.bufferedPainters.ExplosionPainter;
import bozels.visualisatie.bufferedPainters.LevelPainter;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModelListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class LevelDrawer extends JPanel implements ExplosionLogger, BufferedPainterListener, PainterSettingsModelListener{
	private static final long serialVersionUID = 1L;
	
	private final RenderingThread rt = RenderingThread.getTheRenderingThread();
	
	private final BufferedPainter levelpaint;
	private final BufferedPainter bozelPainter;
	private final BufferedPainter catapultPaint;
	private final BufferedPainter backgroundPainter;
	
	private final List<BufferedPainter> allPainters = new ArrayList<BufferedPainter>();
	
	private final List<ExplosionPainter> explosions = new ArrayList<ExplosionPainter>();
	
	private final PainterSettingsModel psm;
	private final GameColorModel cm;
	
	public LevelDrawer(SuperModel supM){
		Level l = supM.getLevel();
		GameColorModel cm = supM.getGameColorModel();
		PainterSettingsModel psm = supM.getPainterSettingsModel();
		ResourceTracker tracker = supM.getResourceModel();
		
		levelpaint = new LevelPainter(l, cm, psm); 
		bozelPainter = new BozelPainter(l, cm, psm);
		catapultPaint = new CatapultPainter(l, cm, psm);
		backgroundPainter = new BackgroundPainter(tracker, psm, cm);
		
		allPainters.add(levelpaint);
		allPainters.add(bozelPainter);
		allPainters.add(catapultPaint);
		allPainters.add(backgroundPainter);

		for(BufferedPainter painter : allPainters){
			rt.addBufferedPainter(painter);
			painter.addListener(this);
		}
		
		refresh();
		
		l.addLogger(this);
		this.setSize(new Dimension(psm.getWidth(), psm.getHeight()));
		this.setPreferredSize(new Dimension(psm.getWidth(), psm.getHeight()));
		this.psm = psm;
		this.cm = cm;
		psm.addListener(this);
		this.setBackground(psm.getBackgroundColor());
		
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				grabFocus();
			}
		};
		this.addMouseListener(mouseListener);
			
	}
	
	public void refresh(){
		for(BufferedPainter painter : allPainters){
			painter.hardRepaint();
		}
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int x = psm.getXTranslation();
		int y = psm.getYTranslation();
		g2d.translate(0, getHeight());
		g2d.scale(1, -1);
		g.drawImage(backgroundPainter.getImage(), 0, 0, null);
		g.translate(x, -y);
		for (ExplosionPainter expP : explosions) {
			if(expP.getImage()!=null){
				g.drawImage(expP.getImage(), 0, 0, null);
			}
		}
		g.drawImage(catapultPaint.getImage(), 0, 0, null);
		g.drawImage(levelpaint.getImage(), 0, 0, null);
		
		g.translate(-x, y);
		g.drawImage(bozelPainter.getImage(), 0, 0, null);
	}

	@Override
	public void onSettingsChanged(PainterSettingsModel source) {
		this.setBackground(source.getBackgroundColor());
		if(!source.drawsExplosions()){
			explosions.clear();
		}
		repaint();
	}
	
	@Override
	public void onImageReady(BufferedPainter source, int id) {
		repaint();
	}

	@Override
	public void onSourceWantsToBeRepainted(BufferedPainter source) {
	}
	
	public BufferedPainter getCatapultPainter() {
		return catapultPaint;
	}
	
	public BufferedPainter getLevelPainter(){
		return levelpaint;
	}

	@Override
	public void onNewExplosion(Explosion source) {
		if(psm.drawsExplosions()){
			ExplosionPainter exp = new ExplosionPainter(source, cm, psm);
			explosions.add(exp);
			rt.addBufferedPainter(exp);
			exp.repaint();
		}
	}

}
