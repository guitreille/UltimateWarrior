package Main;

import javax.sound.sampled.*;

//this class comes from the projectTemplate of our teachers and assistants. 

public class PlaySound implements Runnable {
	final static String MUSIC_FOLDER = "/Music/";

	private String fileName;
	private boolean loopContinuously;
	
	private Clip clip;

	public PlaySound(String fileName, boolean loopContinuously) {
		this.fileName = fileName;
		this.loopContinuously = loopContinuously;
	}

	public void run() {
		try {
			AudioInputStream as = AudioSystem.getAudioInputStream(getClass()
					.getResourceAsStream(MUSIC_FOLDER + fileName)); //.toURI().toURL()

			clip = AudioSystem.getClip();

			clip.open(as);
			if (loopContinuously)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("De requested music file " + fileName
					+ " could not be found or loaded.");
		}
	}
	
	public void stop(){
		clip.stop();
	}
}
