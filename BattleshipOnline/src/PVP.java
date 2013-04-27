import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This will handle Player vs. Player Game Play.
 * 
 * 
 * @author Mike Cutalo
 */
public class PVP implements Runnable{
	
	private final String SERVERNAME = "bill.kutztown.edu";
	private final int PORT = 15009;	
	private PrintWriter out;
	private BufferedReader in;
	
	private Thread runClient;
	private Player localPlayer;
	private Player remotePlayer;
	private Socket socket;
	private Turn onLineTurn;
	


	public PVP(){
		localPlayer = new Player();
		remotePlayer = new Player();
	}
	
	public void connetToServer() throws UnknownHostException, IOException, InterruptedException
	{	
		socket = new Socket(SERVERNAME, PORT);		
		System.out.println("Starting connection ...");
		
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
		getData();				
	}
	
	public void getData() throws InterruptedException{
		System.out.println("Starthing runClient Thread..");
		
		runClient = new Thread(this,"ClientConnection");
		runClient.start();
	}
	
	public void sendBoardData()
	{
		System.out.println("Sending Board data now...");
		
		for(int i=0; i < 10; i++)
		{
			for(int j=0; j < 10; j++)
			{
				if(localPlayer.getPlayerBoard().getBoard()[i][j].getOccupyingShip() == ' '){
					out.println(i+""+j+"E");
				}else{
					out.println(i+""+j+""+localPlayer.getPlayerBoard().getBoard()[i][j].getOccupyingShip());	
				}				
			}
		}
		
		out.println("00x");
	}
	
	public void sendData(String data){
		out.println(data);
	}

	public void createShips(){
		
		System.out.println("CreatingShips For opponent...");
		
		int [] shipMaxHit = {5,4,3,3,2};
		char [] shipInit = {'A','B','S','D','P'};
		String [] shipType ={"Aircraft Carrier", "BattleSship","Submarine","Destroyer","Patrol Boat"};		
		Ship newShip = new Ship();
		
		for(int i=0; i<5; i++){
			newShip.setMaxHit(shipMaxHit[i]);
			newShip.setBoatInit(shipInit[i]);
			newShip.setBoatType(shipType[i]);
			
			remotePlayer.allShips.put(shipInit[i], newShip);
			newShip = new Ship();
		}	
				        	  
//	  		setOnLinePlayer(remotePlayer);
//	  		setPVP(true);								

	     System.out.println("Done with CreateShips()");
	}
	

	
	public void closeSocket(){
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Closing the socket");
			e.printStackTrace();
		}
	}
	
	
	// cant start the game before both players are ready!
	public void startTurnListen(){		
		while(onLineTurn == null){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		onLineTurn.setOnLineGame(this);
		onLineTurn.startListening();
	}
	
	public void run(){
		System.out.println("In Run method for Thread");
		String inputData;
		boolean boardDataDone = false;
		while(true){
			try {
				inputData = in.readLine();
				
				if(inputData.equals(null)){
					Thread.sleep(500);
				}else{
					
					System.out.println("Data from server:" + inputData);
					
					int row = Integer.parseInt(String.valueOf(inputData.charAt(0)));
					int col = Integer.parseInt(String.valueOf(inputData.charAt(1)));
					char key = inputData.charAt(2);
					
					if(boardDataDone == false){	
						if(key == 'A'){
							remotePlayer.getPlayerBoard().getBoard()[row][col].setOccupyingShip('A');
							remotePlayer.getPlayerBoard().getBoard()[row][col].setSpaceEmpty(false);
						}else if(key == 'B'){
							remotePlayer.getPlayerBoard().getBoard()[row][col].setOccupyingShip('B');
							remotePlayer.getPlayerBoard().getBoard()[row][col].setSpaceEmpty(false);
						}else if(key == 'S'){
							remotePlayer.getPlayerBoard().getBoard()[row][col].setOccupyingShip('S');
							remotePlayer.getPlayerBoard().getBoard()[row][col].setSpaceEmpty(false);
						}else if(key == 'D'){
							remotePlayer.getPlayerBoard().getBoard()[row][col].setOccupyingShip('D');
							remotePlayer.getPlayerBoard().getBoard()[row][col].setSpaceEmpty(false);
						}else if(key == 'P'){
							remotePlayer.getPlayerBoard().getBoard()[row][col].setOccupyingShip('P');
							remotePlayer.getPlayerBoard().getBoard()[row][col].setSpaceEmpty(false);
						}else if(key == 'x'){
							createShips();
							boardDataDone = true;
							startTurnListen();
						}
					}
					
					
					if(key == 'H'){
//						Animation hitAni = new Animation();
//						hitAni.setPlayer(getOnLineTurn().getHuman());
//						hitAni.shipSinking(row,col);
						
						this.getOnLineTurn().onLinePlayerHit(row, col);
					}else if(key == 'M'){
						this.getOnLineTurn().onLinePlayerMiss(row, col);
					}
					
				}
			} catch (IOException e) {	
				closeSocket();
				e.printStackTrace();
			} catch (InterruptedException e) {
				closeSocket();
				e.printStackTrace();
			}
		}
	}
	
	//Getters & Setters//
	public Player getLocalPlayer() {
		return localPlayer;
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public Player getRemotePlayer() {
		return remotePlayer;
	}

	public void setRemotePlayer(Player remotePlayer) {
		this.remotePlayer = remotePlayer;
	}
	
	public Turn getOnLineTurn() {
		return onLineTurn;
	}

	public void setOnLineTurn(Turn onLineTurn) {
		this.onLineTurn = onLineTurn;
	}
}