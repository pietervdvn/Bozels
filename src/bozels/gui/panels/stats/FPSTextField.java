package bozels.gui.panels.stats;

import javax.swing.JTextField;

import bozels.gui.mainWindow.MainWindow;
import bozels.gui.mainWindow.MainWindowListener;
import bozels.simulationThread.SimulationThread;
import bozels.simulationThread.SimulationThreadListener;
import bozels.visualisatie.LevelDrawer;
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
public class FPSTextField extends JTextField implements
		BufferedPainterListener, MainWindowListener, SimulationThreadListener {
	private static final long serialVersionUID = 1L;

	private long previousFrame;

	private BufferedPainter painter;

	private int sum;
	private int number;

	private double prevFPS;

	public FPSTextField(MainWindow window) {
		this.setEditable(false);
		window.addListener(this);
		previousFrame = System.currentTimeMillis();
		SimulationThread.getTheSimulationThread().addListener(this);
		this.setText("0");
	}

	@Override
	public void onImageReady(BufferedPainter source, int repaintId) {
		long time = previousFrame
				- (previousFrame = System.currentTimeMillis());
		if(time != 0){
			int fps = -(int) (Math.floor(100000 / time) / 100);
		sum += fps;
		number++;
		prevFPS = (double) sum / number;

		if (number == 250) {
			sum = fps;
			number = 1;
		}
		
		this.setText("" + Math.floor(prevFPS*100)/100);
		}
	}

	@Override
	public void onSourceWantsToBeRepainted(BufferedPainter source) {
	}

	@Override
	public void onLevelPanelChanged(MainWindow source, LevelDrawer newDrawer) {
		if (painter != null) {
			painter.removeListener(this);
		}
		painter = newDrawer.getLevelPainter();
		if (painter == null) {
			return;
		}
		painter.addListener(this);

	}

	@Override
	public void onStateChanged() {
		previousFrame = System.currentTimeMillis();
	}

}
