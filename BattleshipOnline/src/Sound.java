import java.applet.Applet;
import java.applet.AudioClip;

public class Sound implements Runnable
{
	private Thread soundThread = new Thread(this,"Sound Thread");
	public AudioClip clip = null;	
	
	public Sound(){
		this.clip = null;
	}
	
	public void BattleStations(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/BattleStations.au"));
		this.soundThread.start();
	}
	
	public void StatusReport(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/StatusReport.au"));
		this.soundThread.start();
	}
	
	public void Authorization(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/Authorization.au"));
		this.soundThread.start();
	}
	
	public void ShipPlaced(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/MakeItSo.wav"));
		this.soundThread.start();
	}
	
	public void ShipHit(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/FIRE.wav"));		
		this.soundThread.start();
	}
	
	public void ComputerHit(){
		this.clip = Applet.newAudioClip(getClass().getResource("/sounds/braceForImpact.wav"));		
		this.soundThread.start();
	}
	
	public void Miss(){
		
	}
	
	@Override
	public void run() {
		this.clip.play();		
	}
}