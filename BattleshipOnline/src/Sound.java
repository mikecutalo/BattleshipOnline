import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Handles all sound for the game.
 * 
 * This class will play any and all audio,
 * in a thread for the game.
 * 
 * @author Mike Cutalo
 * @version 1.0
 */
public class Sound implements Runnable
{
	/** The Thread the audio will be played in */
	private Thread soundThread = new Thread(this,"Sound Thread");
	/** Holds the value of the current audio clip being played */
	public AudioClip clip = null;	
	
	/**
	 * Constructs a new sound object.
	 */
	public Sound(){
		this.clip = null;
	}
	
	/**
	 * Gets the sound BattleStations and then
	 * starts the thread to play it.
	 */
	public void BattleStations(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/BattleStations.au"));
		this.soundThread.start();
	}

	/**
	 * Gets the sound BattleStations and then
	 * starts the thread to play it.
	 */
	public void StatusReport(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/StatusReport.au"));
		this.soundThread.start();
	}

	/**
	 * Gets the sound Authorization and then
	 * starts the thread to play it.
	 */
	public void Authorization(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/Authorization.au"));
		this.soundThread.start();
	}

	/**
	 * Gets the sound MakeItSo and then
	 * starts the thread to play it.
	 */
	public void ShipPlaced(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/MakeItSo.wav"));
		this.soundThread.start();
	}
	
	/**
	 * Gets the sound FIRE and then
	 * starts the thread to play it.
	 */	
	public void ShipHit(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/FIRE.wav"));		
		this.soundThread.start();
	}

	/**
	 * Gets the sound BraceForImpact and then
	 * starts the thread to play it.
	 */
	public void ComputerHit(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/braceForImpact.wav"));		
		this.soundThread.start();
	}
	
	/**
	 * Gets the sound miss and then
	 * starts the thread to play it.
	 */	
	public void ShipMiss(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/miss.wav"));		
		this.soundThread.start();
	}

	/**
	 * When a thread is started it will play,
	 * the current audio stored in clip.
	 */
	public void run() {
		this.clip.play();		
	}
}