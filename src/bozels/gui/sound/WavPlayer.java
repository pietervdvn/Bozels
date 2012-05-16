package bozels.gui.sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import bozels.EventSource;
import bozels.SafeList;
import bozels.valueWrappers.BooleanValue;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class WavPlayer extends EventSource<WavPlayerListener> implements ValueListener<Boolean>{

	private double volume = 1;
	private final BooleanValue mute = new BooleanValue(false," mute");
	private final BooleanValue muteAll;
	
	public WavPlayer(BooleanValue muteAll) {
		this.muteAll = muteAll;
		muteAll.addListener(this);
		mute.addListener(this);
	}

	public double getVolume() {
		return volume;
	}

	@Override
	public void onValueChanged(Value<Boolean> source, Boolean curVal) {
		throwSettingChanged();
	}
	
	public void setVolume(double volume) {
		if (volume == this.volume) {
			return;
		}
		this.volume = volume;
		throwSettingChanged();
	}

	public void mute(boolean mute) {
		this.mute.set(mute);
	}

	public boolean isMuted() {
		return muteAll.get() || mute.get();
	}
	
	public BooleanValue getMuteSwitch(){
		return mute;
	}

	public void play(BufferedSound wav, AudioFormat format)
			throws LineUnavailableException, IOException {
		if(isMuted()){
			return;
		}
		SourceDataLine line = AudioSystem
				.getSourceDataLine(format);
		
		line.open();
		line.start();
		VolumeInputStream in = new VolumeInputStream(wav);
		byte[] data = new byte[4016];
		in.read(data, 0, 128);// skip header to avoid "click"
		while (!isMuted() && in.read(data, 0, 4016) > 0) {
			line.write(data, 0, 4016);
		}
		line.close();
	}

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class VolumeInputStream extends InputStream {

		private final InputStream in;

		public VolumeInputStream(InputStream in) {
			this.in = in;
		}

		@Override
		public int read() throws IOException {
			return (int) (in.read() * volume);
		}
	}

	private void throwSettingChanged() {
		SafeList<WavPlayerListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().onSettingChanged(this);
			}
		}

	}
}
