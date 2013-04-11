import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class Animation extends Game implements Runnable
{
	private Thread animationThread = new Thread(this, "Animation Thread");
	private int imgCounter=0;
	public ImageIcon[] shipEffect = new ImageIcon[3];

	public Player p;
	public AI a;
	
	public Animation()
	{
		this.p = new Player();
		this.a = new AI();
	}
	
	//Animation here!!!!!!!
	//we will run the run() to start the thread
	//the way i see it one thread for each pic
	//each stopping at a different time.
	
	public void ShipSinking() throws IOException
	{
		System.out.println("InShipSinking");
		this.shipEffect[0] = new ImageIcon(getClass().getResource("/turnImg/warn.jpg"));
		this.shipEffect[1] = new ImageIcon(getClass().getResource("/turnImg/bomb.jpg"));
		this.shipEffect[2] = new ImageIcon(getClass().getResource("/turnImg/boom.jpg"));

		animationThread.start();
	}
	
	
	@Override
	public void run() 
	{
		Thread myThread = Thread.currentThread();
		
		while(animationThread == myThread)
		{
			System.out.println("In RUN METHOD!!!" + imgCounter);
			//instead of paint method
			//can i just repaint the comp board?
			myThread = Thread.currentThread();
			
			this.p.getPlayerBoard().getBoard()[0][0].setIcon(shipEffect[imgCounter]);
			imgCounter++;
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		
	}
//	
//	public void paint(Graphics g)
//	{
//		//last parameter look it up?
//		g.drawImage(shipEffect[imgCounter],0,0, this.p.getPlayerBoard());		
//	}
	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public AI getA() {
		return a;
	}

	public void setA(AI a) {
		this.a = a;
	}
}
