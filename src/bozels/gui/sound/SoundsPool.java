package bozels.gui.sound;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import bozels.EventSource;
import bozels.SafeList;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.levelModel.core.Level;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.valueWrappers.BooleanValue;
import bozels.xml.levels.LevelDefinition;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SoundsPool extends EventSource<SoundsPoolListener> implements
		SuperModelListener {

	private static final String[] LOCATIONS = { "RedBozelLaunch",
			"AcceleratingBozelLaunch", "ExplodingBozelLaunch",
			"SplitBozelLaunch", "Explosion", "CatapultStretch", "Poof",
			"TargetPoof", "LevelFailed", "LevelComplete", "WoodBroken",
			"MetalBroken", "ConcreteDestroyed", "IceBroken", "Boost", "Theme",
			"Opening", "BackgroundSound2", "WoodCollision", "MetalCollision",
			"ConcreteCollision", "IceCollision", "Antigravity" };

	public final static int NORMAL_BOZEL = 0;
	public final static int ACCELERATING_BOZEL = 1;
	public final static int EXPLODING_BOZEL = 2;
	public final static int SPLIT_BOZEL = 3;

	public final static int EXPLOSION = 4;
	public final static int CATAPULT = 5;
	public final static int BOZEL_DELETED = 6;
	public final static int TARGET_DELETED = 7;

	public final static int LEVEL_FAILED = 8;
	public final static int LEVEL_SUCCESFULL = 9;

	public final static int WOOD_DESTROYED = 10;
	public final static int METAL_DESTROYED = 11;
	public final static int CONCRETE_DESTROYED = 12;
	public final static int ICE_DESTROYED = 13;

	public final static int BOOST = 14;

	public final static int THEME = 15;
	public final static int OPENING = 16;
	public final static int BACKGROUND = 17;

	public final static int WOOD_CONTACT = 18;
	public final static int METAL_CONTACT = 19;
	public final static int CONCRETE_CONTACT = 20;
	public final static int ICE_CONTACT = 21;

	public static final int ANTI_GRAVITY = 22;

	private BufferedSound[] sounds = new BufferedSound[LOCATIONS.length];

	private final List<SoundThread> threadPool = new ArrayList<SoundThread>(
			sounds.length);

	private final WavPlayer player;
	private final WavPlayer backgroundPlayer;
	private boolean initIsDone = false;

	public SoundsPool(SuperModel superModel, BooleanValue muteAll) {
		superModel.addListener(this);
		player = new WavPlayer(muteAll);
		backgroundPlayer = new WavPlayer(muteAll);
	}

	private void init(ResourceTracker tracker) {
		for (int i = 0; i < LOCATIONS.length; i++) {
			try {
				URL url = (tracker.getResource(
						"sound/" + LOCATIONS[i] + ".wav"));
				sounds[i] = new BufferedSound(url);
			} catch (UnsupportedAudioFileException e) {
				System.out.println("Warning! Could not load sound "
						+ LOCATIONS[i] + ": wrong audio format");
			} catch (IOException e) {
				System.out.println("Warning! Could not open sound "
						+ LOCATIONS[i]);
			} catch (Exception e) {
				System.out.println("Warning! Could not find sound "
						+ LOCATIONS[i]);
				e.printStackTrace();
			}
		}
		initIsDone = true;
		throwInitDone();
	}

	public WavPlayer getPlayer() {
		return player;
	}
	
	void playAndLoop(int numberToPlay, int numberToLoop){
		synchronized (threadPool) {
			SoundThread thread = searchAvailableSoundThread();
			try {
				thread.play(numberToPlay, numberToLoop, backgroundPlayer);
			} catch (LineUnavailableException e) {
			}
		}
	}

	public void play(final int number) {
		play(number, false);
	}

	public void play(final int number, boolean loop) {
		if (number < 0 || number > sounds.length) {
			throw new IllegalArgumentException("Not a valid sound number");
		}

		if (!initIsDone) {
			throw new IllegalArgumentException("Do initialisation first!");
		}
		synchronized (threadPool) {
			SoundThread thread = searchAvailableSoundThread();
			try {
				thread.loop(loop);
				thread.play(number);
			} catch (LineUnavailableException e) {
			}
		}
	}
	
	private SoundThread searchAvailableSoundThread(){
		for (SoundThread thread : threadPool) {
			synchronized (thread) {
				if (!thread.isPlaying()) {
					return thread;
				}
			}
		}
		SoundThread newThr = new SoundThread(sounds, player);
		threadPool.add(newThr);
		return newThr;
		
	}

	@Override
	public void doSlowInit(SuperModel source) {
		init(source.getResourceModel());
	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
	}

	public BufferedSound getSound(int key) {
		return sounds[key];
	}

	public boolean initIsDone() {
		return initIsDone;
	}

	private void throwInitDone() {
		SafeList<SoundsPoolListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext())
				l.next().initDone(this);
		}
	}
	
	public WavPlayer getWavPlayer(){
		return player;
	}
	
	public WavPlayer getBackgroundWavPlayer(){
		return backgroundPlayer;
	}

}
