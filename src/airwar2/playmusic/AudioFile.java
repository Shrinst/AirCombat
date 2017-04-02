package airwar2.playmusic;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioFile implements LineListener {

	private AudioInputStream audioInputStream;
	private AudioFormat audioFormat;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private volatile boolean is_playing; // volatile is needed to make multi-threading works

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
	
	public void play() {
		play(-10);
	}
	
	public void play(float volume) {
		gainControl.setValue(volume);
		clip.start();
		is_playing = true;
	}
	
	public boolean isPlaying() {
		return is_playing;
	}

	@Override
	public void update(LineEvent event) {		
		if (event.getType() == LineEvent.Type.START) {
			is_playing = true;
		} else if (event.getType() == LineEvent.Type.STOP) { // automatically calls the Java´s stop music method
			clip.stop();
			clip.flush();
			clip.setFramePosition(0); // The first frame of the sound
			is_playing = false;
		}
	}
}
