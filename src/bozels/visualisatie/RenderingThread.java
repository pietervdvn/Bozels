package bozels.visualisatie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import bozels.visualisatie.bufferedPainters.BufferedPainter;
import bozels.visualisatie.bufferedPainters.BufferedPainterListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class RenderingThread implements BufferedPainterListener {

	private final Set<BufferedPainter> toRepaint = new HashSet<BufferedPainter>();

	private final Thread thread;
	private boolean continueRunning = true;
	private final Object lock = new Object();

	// To be able to clear
	private List<BufferedPainter> bufferedPainters = new ArrayList<BufferedPainter>();

	private static RenderingThread rendThread = new RenderingThread();

	public static RenderingThread getTheRenderingThread() {
		return rendThread;
	}

	private RenderingThread() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (continueRunning) {
					while(repaint());
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							break;
						}
					}
				}
			}
		});
		thread.setName("Bozels: Image rendering thread");
		thread.start();
	}

	private boolean repaint() {
		Set<BufferedPainter> toRep = new HashSet<BufferedPainter>();
		synchronized (toRepaint) {
			//move contents from toRepaint to toRep
			toRep.addAll(toRepaint);
			toRepaint.clear();

		}
		for (Iterator<BufferedPainter> iterator = toRep.iterator(); iterator
				.hasNext();) {
			BufferedPainter bp = iterator.next();
			if (bp.paint()) {
				iterator.remove();
			}
		}
		
		synchronized (toRepaint) {
			toRepaint.addAll(toRep); // all which failed will be repainted next
			return !toRepaint.isEmpty();
		}

	}

	public void stop() {
		continueRunning = false;
		synchronized (lock) {
			lock.notifyAll();
		}
		thread.interrupt();
	}

	public void addBufferedPainter(BufferedPainter painter) {
		synchronized (bufferedPainters) {
			painter.addListener(this);
			bufferedPainters.add(painter);
		}
	}

	public void removePainter(BufferedPainter painter) {
		synchronized (bufferedPainters) {
			painter.removeListener(this);
			bufferedPainters.remove(painter);
		}
	}

	public void removeAll() {
		synchronized (bufferedPainters) {

			for (Iterator<BufferedPainter> iterator = bufferedPainters
					.iterator(); iterator.hasNext();) {
				BufferedPainter bp = iterator.next();
				bp.removeListener(this);
				iterator.remove();
			}
		}
	}
	
	@Override
	public void onSourceWantsToBeRepainted(BufferedPainter source) {
		synchronized (toRepaint) {
			toRepaint.add(source);
		}
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void onImageReady(BufferedPainter source, int id) {
	}

}
