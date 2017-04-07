package airwar2.playmusic;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class MusicPlayer implements Runnable {

	private AudioFile[] musicFiles;
	private int currentSongIndex;
	private boolean running;

	/**
	 * Set the list of songs
	 * 
	 * @param files
	 *            the song's name
	 */
	public MusicPlayer(String... files) {
		musicFiles = new AudioFile[files.length];
		int i = 0;
		for (String file : files) {
			musicFiles[i] = new AudioFile("/airwar2/music/" + file + ".wav");
			i++;
		}
	}

	/**
	 * Reproduces a song, if it has ended, pass to another one
	 */
	@Override
	public void run() {
		running = true;
		AudioFile song = musicFiles[currentSongIndex];
		song.play();
		while (running) {
			if (!song.isPlaying()) {
				currentSongIndex++;
				if (currentSongIndex >= musicFiles.length) {
					currentSongIndex = 0;
				}
				song = musicFiles[currentSongIndex];
				song.play();
			}

			// Needed to give time to the threads to communicate each other, its
			// may no necessary if the AudioFile variable is volatile
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
