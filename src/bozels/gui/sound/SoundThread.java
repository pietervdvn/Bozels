package bozels.gui.sound;

import java.io.IOException;
import java.lang.Thread.State;

import javax.sound.sampled.LineUnavailableException;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SoundThread implements Runnable, WavPlayerListener {

	private final Thread thread = new Thread(this);
	private int toPlay;
	private boolean isPlaying = false;
	private boolean stop = false;

	private boolean loop;
	private int loopNumber = -1;

	private final BufferedSound[] sounds;
	private WavPlayer player;

	public SoundThread(BufferedSound[] sounds, WavPlayer player) {
		thread.setDaemon(true);
		this.player = player;
		player.addListener(this);
		this.sounds = sounds;
	}

	public void setPlayer(WavPlayer player){
		if(this.player != null){
			this.player.removeListener(this);
		}
		this.player = player;
		this.player.addListener(this);
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public synchronized void play(int toPlay, int loopNumberAfter, WavPlayer backgroundPlayer) throws LineUnavailableException{
		setPlayer(backgroundPlayer);
		play(toPlay);
		this.loopNumber = loopNumberAfter;
		this.loop = true;
	}

	public synchronized void play(int toPlay) throws LineUnavailableException {
		if (isPlaying) {
			throw new LineUnavailableException();
		}
		isPlaying = true;
		this.toPlay = toPlay;
		if (thread.getState() == State.NEW) {
			thread.start();
		}
		synchronized (this) {
			this.notifyAll();
		}
	}

	public void stop() {
		stop = true;
		synchronized (this) {
			this.notifyAll();
		}
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				if (sounds[toPlay] != null && !sounds[toPlay].isPlaying()) {
					synchronized (sounds[toPlay]) {
						BufferedSound sound = sounds[toPlay];
						sound.setPlaying(true);
						sound.reset();
						try {
							player.play(sound, sound.getFileFormat()
									.getFormat());
						} catch (IllegalStateException e) {
							e.printStackTrace();
						}
						sound.setPlaying(false);
						if(loopNumber != -1){
							toPlay = loopNumber;
							loopNumber = -1;
						}
					}
				}
			} catch (LineUnavailableException e) {
			} catch (IOException e) {
			}
			if (!player.isMuted() && loop) {
				continue;
			}
			synchronized (this) {
				try {
					isPlaying = loop || false;
					this.wait();
					isPlaying = true;
				} catch (InterruptedException e) {
					if (stop) {
						return;
					}
				}
			}
		}
	}

	public boolean loops() {
		return loop;
	}

	public void loop(boolean loop) {
		this.loop = loop;
	}

	@Override
	public void onSettingChanged(WavPlayer source) {
		if (loop) {
			synchronized (this) {
				this.notifyAll();
			}
		}
	}
}
