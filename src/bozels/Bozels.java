package bozels;

import bozels.gui.mainWindow.MainWindow;
import bozels.gui.sound.SuperModelSoundPlayer;
import bozels.superModel.SuperModel;

/**
 * 
 * @author Pieter Vander Vennet
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
public class Bozels {

	public static void main(String[] args) {

		if (args.length != 0 && args.length > 0
				&& args[0].toLowerCase().equals("bench")) {
			benchmark = true;
		}
	
		bench("System time");
		
		System.out.println();
		System.out.println("         BOZELS  ");
		System.out.println("       +--------+");
		System.out.println();
		System.out.println(" Door Pieter Vander Vennet");
		System.out.println();

		System.out.println(" Met dank aan:");
		System.out.println("        Prof. dr. Kris Coolsaet");
		System.out.println("        Davy Hollevoet");
		System.out.println("        Nicolas Van Cleemput");
		bench("credits");

		try {
		//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		bench("Applying system Look and Feel");

		// The supermodel! Contains references to other models, which are used
		// through the entire program
		final SuperModel superModel = new SuperModel( );
		bench("Building superModel");

		// Builds the GUI
		new MainWindow(superModel);

		// plays nice/irritating angry birds sounds on certain events!
		// there IS a mute button in the GUI
		new SuperModelSoundPlayer(superModel);

		bench("Building GUI");

		/*
		 * Does the slow init
		 * 
		 * Slow init is for initializing slow GUI components, as the color
		 * chooser dialog and the filechooser. It loads these into memory, ready
		 * to be used immediately.
		 * 
		 * Slow init takes about 400 ms on my computer, and building the GUI
		 * (already done with "new MainWindow" around 400 This way, building
		 * displaying the GUI can be accelerated a lot
		 */
		superModel.throwDoInit();
		bench("Doing slow GUI init");

		superModel.getResourceModel().checkLocales();
	}
	
	private static boolean benchmark = false;
	static long begin = 0;

	/**
	 * Benchmarks action, and prints the time since the previous call.
	 */	
	private static void bench(String what) {
		if (benchmark) {
			long stop = System.currentTimeMillis();
			System.out.println(what + ": " + (stop - begin));
			begin = System.currentTimeMillis();
		}
	}

}
