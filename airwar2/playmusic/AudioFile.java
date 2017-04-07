package airwar2.playmusic;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 */
public class AudioFile implements LineListener {

	private AudioInputStream audioInputStream;
	private AudioFormat audioFormat;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private volatile boolean is_playing; // volatile is needed to make
											// multi-threading works

	/**
	 * Set the song that will played
	 * 
	 * @param fileName
	 *            the song location
	 */
	public AudioFile(String fileName) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(fileName));
			audioFormat = audioInputStream.getFormat();
			info = new DataLine.Info(Clip.class, audioFormat);
			clip = (Clip) AudioSystem.getLine(info);
			clip.addLineListener(this); // IMPORTANT!!!
			clip.open(audioInputStream);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Plays the song
	 */
	public void play() {
		play(-10);
	}

	/**
	 * Plays the song
	 * 
	 * @param volume
	 *            the song's volume
	 */
	public void play(float volume) {
		gainControl.setValue(volume);
		clip.start();
		is_playing = true;
	}

	/**
	 * Returns if the sound is playing
	 * 
	 * @return if the sound is playing
	 */
	public boolean isPlaying() {
		return is_playing;
	}

	/**
	 * 
	 * Checks if the sound effect has ended
	 */
	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.START) {
			is_playing = true;
		} else if (event.getType() == LineEvent.Type.STOP) { // automatically
																// calls the
																// Java´s stop
																// music method
			clip.stop();
			clip.flush();
			clip.setFramePosition(0); // The first frame of the sound
			is_playing = false;
		}
	}
}
