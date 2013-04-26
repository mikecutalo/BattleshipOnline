import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer{
	private ServerSocket server = null;
	private final int PORT = 15009;
	
	private Client playerOne;
	private Client playerTwo;
	
	public ClientServer(){
		server = null;
		this.playerOne = new Client();
		this.playerTwo = new Client();
	}
	
	public void StartListening() throws IOException, InterruptedException
	{
		server = new ServerSocket(PORT);
		
		while(true){
			Socket playerSocket = server.accept();

			//Client player;
			//player = new Client(playerSocket);
			//p = new Client(playerSocket);
			//Thread t = new Thread(player);
			//t.start();
			
			if(playerOne.client.isConnected() != true)
			{
				System.out.println("Established Connection PlayerOne");
				playerOne = new Client(playerSocket);
				playerOne.setName("playerOne");
				
			}
			else if(playerOne.client.isConnected() == true)
			{
				System.out.println("Established Connection PlayerTwo");
				playerTwo = new Client(playerSocket);
				playerTwo.setName("playerTwo");
				
				//Bother players are connected so now sent them each other 
				playerOne.setClientTwo(playerTwo);
				playerTwo.setClientTwo(playerOne);
				
				//after things are ready start Both threads...
				Thread t1 = new Thread(playerOne);
				t1.start();
				
				Thread t2 = new Thread(playerTwo);
				t2.start();
				
			}
			Thread.sleep(2500);
		}
	}
	

	public class Client implements Runnable{
		private String name ="";
		private Socket client;
		private Client clientTwo;

		Client(){
			this.client = new Socket();
		}

		Client(Socket client){
			this.client = client;
		}
		
		public void run(){
			String line;
			BufferedReader in = null;
			PrintWriter out = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				//This should send to the OTHER client 
				out = new PrintWriter(clientTwo.client.getOutputStream(), true);
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//System.out.println("Setting Up Streams" + this.name);
			
			while(true){	
				try{		
					line = in.readLine();
										
					if(line.equals(null)){
						System.out.println("Sleep ....");
						Thread.sleep(500);
					}else{
						//System.out.println("Something From user : " + line);
						out.println(line);
					}
					
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
		
		public void finalize(){
			try {
				client.close();
				System.out.println("Server Closed" + this.name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public Client getClientTwo() {
			return clientTwo;
		}

		public void setClientTwo(Client clientTwo) {
			this.clientTwo = clientTwo;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		ClientServer gameServer = new ClientServer();
		gameServer.StartListening();
	}
}