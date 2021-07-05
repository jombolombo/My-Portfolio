import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class PlayMusic {
	/**
	 * class that manages audio.
	 * 
	 * @author Joshua Matambo.
	 */
	private File musicFile;
	private Clip clip;

	/**
	 * create music player
	 * 
	 * @param filepath
	 */
	public PlayMusic(String filepath) {
		musicFile = new File(filepath);
	}

	/**
	 * play the song in music player.
	 */
	public void playMusic() {
		try {
			if (musicFile.exists()) {
				AudioInputStream audios = AudioSystem.getAudioInputStream(musicFile);
				clip = AudioSystem.getClip();
				System.out.println(audios.getFormat());
				clip.open(audios);
				clip.start();
			} else {
				System.out.println("music player error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * play and repeat audio.
	 */
	public void playMusicLoop() {
		try {
			if (musicFile.exists()) {
				AudioInputStream audios = AudioSystem.getAudioInputStream(musicFile);
				clip = AudioSystem.getClip();
				clip.open(audios);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				System.out.println("music player error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop playing muic.
	 */
	public void StopMusic() {
		clip.stop();
	}
}
