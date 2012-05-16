package bozels.gui.sound;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class BufferedSound extends InputStream {

	private final AudioFileFormat fileFormat;
	private int index = 0;

	private final byte[] bytes;
	
	private boolean isPlaying;

	public BufferedSound(URL url) throws UnsupportedAudioFileException, IOException {
		DataInputStream in = null;
		try {
			fileFormat = AudioSystem.getAudioFileFormat(url);
			in = new DataInputStream(new BufferedInputStream(url.openStream()));
			bytes = new byte[in.available()];
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) (((256 + in.readByte()) % 256) - 128);
 			}
			
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
	
	public AudioFileFormat getFileFormat() {
		return fileFormat;
	}

	public boolean isReady(){
		return index == 0;
	}
	
	public void reset() {
		index = 0;
	}
	
	@Override
	public int read() throws IOException {
		try{
			return bytes[index++] + 128;
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
}
