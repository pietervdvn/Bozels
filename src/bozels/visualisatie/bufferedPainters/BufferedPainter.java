package bozels.visualisatie.bufferedPainters;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

import bozels.EventSource;
import bozels.SafeList;
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
public abstract class BufferedPainter extends EventSource<BufferedPainterListener> implements PainterSettingsModelListener{
	
	public abstract void paintContents(Graphics2D g);
	
	private final Object paintBoolLock = new Object();
	private boolean painting = false;
	
	private int repaintId = 0;
	
	private boolean wantsToBeRepainted = false;
	
	private BufferedImage image = null;
	
	private final PainterSettingsModel psm;
	
	public BufferedPainter(PainterSettingsModel psm) {
		this.psm = psm;
		psm.addListener(this);
	}
	
	public void hardRepaint(){
		while(!paint()){
		}
	}
		
	public boolean paint(){
		synchronized (paintBoolLock) {
			if(painting){
				return false;
			}
			painting = true;
		}
		try{
			BufferedImage image = new BufferedImage(psm.getWidth(), psm.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			try{
				Graphics2D graph = image.createGraphics();
				graph.setRenderingHint
					(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				paintContents(graph);
				this.image = image;
				throwImageDone(repaintId++);
			}catch (ConcurrentModificationException e) {
				return false;
			}
			/*Why using an try-catch instead of a synchronized?
			 * 
			 * Synchronizing on the gameObjects means that other operations, 
			 * such as adding and removing objects to the level, and simulting,
			 *  would have to wait while repainting.
			 * These actions will mostly happen from the Simulation thread.
			 * As painting is slow, this means the simulation thread would be blocked while painting.
			 * 
			 * We don't like that, as the simulation should be independent from the painting.
			 * Therefore, I prefer to skip frames and repaint again afterwards, than synchronizing.
			 *
			 * Real life tests turn out that this way of working results in very few skipped frames 
			 * (sometimes one or two during heavy calculations, e.g. explosions), whereas "synchronized"
			 * results in heavy lag.
			 *
			 */
			
			return true;
		}finally{
			synchronized (paintBoolLock) {
				painting = false;
			}
		}
		
	}

	public BufferedImage getImage() {
		return image;
	}

	public boolean isWantsToBeRepainted() {
		return wantsToBeRepainted;
	}

	public void repaint(){
		throwWantsRepaint();
	}
	
	@Override
	public void onSettingsChanged(PainterSettingsModel source) {
		repaint();
	}
	
	private void throwImageDone(int id){
		SafeList<BufferedPainterListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onImageReady(this, id);
			}
		}
	}
	
	private void throwWantsRepaint(){
		SafeList<BufferedPainterListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onSourceWantsToBeRepainted(this);
			}
		}
	}
	
	public PainterSettingsModel getPainterSettingsModel() {
		return psm;
	}
}
