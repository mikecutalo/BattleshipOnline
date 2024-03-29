import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/** 
 * The game class extends JApplet and implements ActionListener.
 * This is where the Applet GUI is created and rendered. All of the
 * GUI components necessary for the game are declared here.
 * <p>
 * This will take user input, from this input it will handle all ship,
 * placement and validation. As a player is placing ships it will 
 * update what ship is currently being placed on the board.
 * <p>
 * After all ships are placed and the game is ready to start, 
 * it will drop the listeners for this class and pass them over
 * to the Turn class to start the game.
 *  
 * @author Mike Cutalo
 * @version 3.0
 */
public class Game extends JApplet implements ActionListener
{
	/** Holds the current ship that is being placed*/
	public static JPanel shipPlace = new JPanel();
	/** Not being used at the moment but would show player statics in center of applet */
	public static JPanel playerStats = new JPanel(); 
	/** Holds the sunken ships for the human board */
	public static JPanel humanSunk = new JPanel();
	/** Holds the sunken ships for the computer board*/
	public static JPanel computerSunk = new JPanel();
	/** Holds the name panels of the players */
	public static JPanel allNames = new JPanel(new BorderLayout());
	/** Holds name of human player.*/
	public static JPanel humanName = new JPanel();
	/** Holds name of computer player.*/
	public static JPanel computerName = new JPanel();
	/** If ship is not placed in correct spot error will be set.*/
	public static JLabel errorMsg;
	/** Icon of current ship being placed.*/
	public static ImageIcon currShipPlaceImg;
	/** Current image being displayed on board.*/
	public static JLabel currentShipImg;
	/** Current name of ship of being placed.*/
	public static JLabel currentShipText;
	/** Current size of ship being placed.*/
	public static int currentShipSize=0;
	/** Is set to false when all ships are placed.*/
	public static boolean placeShipMode = true;
	/** Has the game started yet, true when it has.*/
	public static boolean gameMode = false;
	/** Coordinates of players first selection.*/
	public static String firstChoice = "";
	/** Coordinates of the players second selection*/
	public static String secondChoice = "";

	/** Human player object will store all data for human*/
	public Player human = new Player();
	/** Computer player object will store all data for computer*/
	public AI computer = new AI();	
	/** Handles Turns for the game*/
	public Turn gameTime = new Turn();

	/** This will hold both computer or online players grid */
	public JPanel computerGrid;
	/** Store URL get parameters*/
	Map<String, String> paramValue = new HashMap<String, String>();
	/** This is the onlineGame, the PVP stands for Player vs Player. It will handle all interaction over the sockets.*/
	PVP onlineGame = new PVP();
		
	/**
	 * Creates the GUI for the JApplet
	 *
	 * This method will create all the GUI elements for the
	 * JApplet. Also generating the player boards, and applying
	 * to the Applet. This will also read in the URL parameters,
	 * that will dictate what type of game it is (Online or Computer).
	 */
	public void init()
	{		
		String url = getDocumentBase().toString();
		String paramaters="";
		
	   if (url.indexOf("?") > -1) {
		   paramaters = url.substring(url.indexOf("?") + 1);	   
	   }
	   
	   StringTokenizer paramGroup = new StringTokenizer(paramaters, "&");
	   
	   while(paramGroup.hasMoreTokens()){
 
		   StringTokenizer value = new StringTokenizer(paramGroup.nextToken(), "=");
		   paramValue.put(value.nextToken(), value.nextToken()); 
	   }
	   
		if(paramValue.size() != 0 && paramValue.get("type").equals("hvh"))
		{
			try {
				onlineGame.connetToServer();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		shipPlace = new JPanel();
		playerStats = new JPanel(); 
		humanSunk = new JPanel();
		computerSunk = new JPanel();
		allNames = new JPanel(new BorderLayout());
		placeShipMode = true;
		gameMode = false;
		firstChoice = "";
		secondChoice = "";
		this.human = new Player();
		this.computer = new AI();
		this.gameTime = new Turn();	
		this.human.setPlayerName("Human");
		this.computer.setPlayerName("AI");
		
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout(10,10));
		setSize(800, 520);

		allNames = new JPanel(new BorderLayout());
		allNames.setBackground(Color.BLACK);
		
		humanName = new JPanel();
		humanName.setBackground(Color.BLACK);
		
		computerName = new JPanel();
		computerName.setBackground(Color.BLACK);
		
		humanName.setPreferredSize(new Dimension(350,30));
		humanName.setMaximumSize(new Dimension(350,30));
		humanName.setSize(350,25);
    	
		computerName.setPreferredSize(new Dimension(350,30));
		computerName.setMaximumSize(new Dimension(350,30));
		computerName.setSize(350,30);

		JLabel hLabel = new JLabel("Human");
		hLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
		hLabel.setForeground(Color.WHITE);
		humanName.add(hLabel);

		JLabel cLabel;
		
		if(paramValue.size() != 0 && paramValue.get("type").equals("hvb")){
			cLabel = new JLabel("Borg");	
		}else{
			cLabel = new JLabel("Opponent");
		}
		
		
		cLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
		cLabel.setForeground(Color.WHITE);
		computerName.add(cLabel);


		allNames.add(humanName, BorderLayout.WEST);
		allNames.add(computerName, BorderLayout.EAST);
		add(allNames, BorderLayout.NORTH);

		//Human Panel & Start listening to human board
		JPanel humanGrid = human.getPlayerBoard().CreateGridPanel(true);

		for(int i=0; i < 10; i++)
		{
			for(int j=0; j < 10; j++)
			{
				human.getPlayerBoard().getBoard()[i][j].addActionListener(this);
			}
		}	
		add(humanGrid, BorderLayout.WEST);

		//AI Panel
		if(paramValue.size() != 0 && paramValue.get("type").equals("hvb")){
			computerGrid = computer.getPlayerBoard().CreateGridPanel(true);	
			add(computerGrid, BorderLayout.EAST);			
		}

		//South Panel
		shipPlace.setLayout(new BorderLayout());	
		shipPlace.setBackground(Color.BLACK);
		
		//New ship panel
		JPanel shipPlacementWest = new JPanel();
		shipPlacementWest.setBackground(Color.BLACK);
		
		JLabel nowPlacing = new JLabel("Now Placeing : ");
		nowPlacing.setForeground(Color.WHITE);
		
		shipPlacementWest.add(nowPlacing);
		shipPlacementWest.add(currentShipImg = new JLabel());
		shipPlacementWest.add(currentShipText = new JLabel());
		shipPlacementWest.add(errorMsg = new JLabel());
		
		currentShipText.setForeground(Color.WHITE);
		errorMsg.setForeground(Color.WHITE);
		
		
		shipPlace.add(shipPlacementWest, BorderLayout.WEST);		
		add(shipPlace, BorderLayout.SOUTH);

		try {
			displayShip();
		} catch (IOException e) {
			e.printStackTrace();
		}		
				
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Image img = getImage(getCodeBase(),"startrek/trekIcon.png");
		Point hotSpot = new Point(0,0);
		Cursor cursor = toolkit.createCustomCursor(img, hotSpot,"trek");
		setCursor(cursor);
		
		this.gameTime.setThisGame(this);
		
		repaint();
		validate();
	}

	/**
	 * Start a new Game
	 *
	 * Asks the user if you would like to play again, 
	 * if so then it will simply reload the page.
	 */
	public void NewGame()
	{
		ImageIcon gameImg = new ImageIcon(getClass().getResource("/popup/game.jpg"));
		URL newGame = null;
		
		try {
			newGame = new URL("http://bill.kutztown.edu/~mcuta697/csc421/StarTrek/GameStart.html");

			if (JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again?", 
			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, gameImg)
			    == JOptionPane.YES_OPTION)
			{		
				this.getAppletContext().showDocument(newGame,"_self");
			}
			else
			{
				this.getAppletContext().showDocument(newGame,"_self");
			}
		
		}catch(MalformedURLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Display the current ship being placed 
	 *
	 * Simply displays the ship that is currently
	 * being placed by the user.
	 * 
	 * @throws IOExecption
	 */
	public void displayShip() throws IOException
	{		
		BufferedImage tempImage;

		switch(human.getAllShips().size())
		{
			case 0:		
				tempImage = ImageIO.read(getClass().getResource("/startrek/starship.jpg"));
				currShipPlaceImg = new ImageIcon(getClass().getResource("/startrek/starship.jpg"));
				currentShipImg.setIcon(new ImageIcon(tempImage));
				currentShipText.setText("Aircraft Carrier");
				currentShipSize = 5;
			break;			
			case 1:
				tempImage = ImageIO.read(getClass().getResource("/startrek/enterprise.jpg"));
				currShipPlaceImg = new ImageIcon(getClass().getResource("/startrek/enterprise.jpg"));
				currentShipImg.setIcon(new ImageIcon(tempImage));
				currentShipText.setText("Battleship");
				currentShipSize = 4;
			break;			
			case 2:
				tempImage = ImageIO.read(getClass().getResource("/startrek/nebula.jpg"));
				currShipPlaceImg = new ImageIcon(getClass().getResource("/startrek/nebula.jpg"));
				currentShipImg.setIcon(new ImageIcon(tempImage));
				currentShipText.setText("Submarine");
				currentShipSize = 3;
			break;			
			case 3:
				tempImage = ImageIO.read(getClass().getResource("/startrek/Akira.jpg"));
				currShipPlaceImg = new ImageIcon(getClass().getResource("/startrek/Akira.jpg"));
				currentShipImg.setIcon(new ImageIcon(tempImage));
				currentShipText.setText("Destroyer");
				currentShipSize = 3;
			break;			
			case 4:
				tempImage = ImageIO.read(getClass().getResource("/startrek/patrol.jpg"));
				currShipPlaceImg = new ImageIcon(getClass().getResource("/startrek/patrol.jpg"));
				currentShipImg.setIcon(new ImageIcon(tempImage));
				currentShipText.setText("Patrol Boat");
				currentShipSize = 2;
			break;
		}
	}
	
	/**
	 * Output text to the html.
	 * 
	 * This method will call a java script function, with 
	 * one parameter message. That holds the value of the desired
	 * text to show the user.
	 * 
	 * @param message
	 */
	public void TellUser(String message){
		final AppletContext Handle = getAppletContext();
		try {
			
			Handle.showDocument(new URL("javascript:Waiting(\""+ message +"\")"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		      
	}
	
	/**
	 * Play Winning Video.
	 * 
	 * Calls a java script function that spawns a new pop up
	 * window with he winning video.
	 * 
	 * @throws Exception
	 */
	public void PlayGameWin(){
		final AppletContext Handle = getAppletContext();
		new Thread() {
		   	 public void run() {
		          try {
		        	  Handle.showDocument(new URL("javascript:PlayGameWin()"));
		          }
		          catch (Exception e) {System.out.println(e);}
		     }
	     }.start();
	}

	/**
	 * Play Lose Video.
	 * 
	 * Calls a java script function that spawns a new pop up
	 * window with he losing video.
	 * 
	 * @throws Exception
	 */
	public void PlayGameLose(){
		final AppletContext Handle = getAppletContext();
		new Thread() {
		   	 public void run() {
		          try {
		        	  Handle.showDocument(new URL("javascript:PlayGameLose()"));
		          }
		          catch (Exception e) {System.out.println(e);}
		     }
	     }.start();
	}
	
	/**
	 * Play Ship Explosion Video.
	 * 
	 * Calls a java script function that spawns a new pop up
	 * window with he ship explosion video.
	 * 
	 * @throws Exception
	 */
	public void PlayExplosionVideo() {		
		final AppletContext Handle = getAppletContext();
		new Thread() {
		   	 public void run() {
		          try {
		        	  Handle.showDocument(new URL("javascript:PlaySunkenShip()"));
		          }
		          catch (Exception e) {System.out.println(e);}
		     }
	     }.start();
	}

	/**
	 * Handles all actions when setting up the game.
	 *
	 * This method will validate a users first and second click,
	 * when a user clicks the same spot twice the ship will be removed
	 * and the user can pick a different spot to place the ship. This
	 * method will continue to be called until all ships are placed.
	 * While the human player is placing the ships, the computer will also
	 * place ships at the same time. When all ships are validated and placed
	 * the game will start, handing over listeners to the Turn class.
	 */
	public void actionPerformed(ActionEvent e)
	{					
		if(firstChoice == "")
		{
			firstChoice = e.getActionCommand();
			errorMsg.setText("");
			this.human.getPlayerBoard().PossibleBoatSelect(true);			
		}
		else if(secondChoice =="")
		{
			secondChoice = e.getActionCommand();

			if(firstChoice.equals(secondChoice))
			{
				this.human.getPlayerBoard().PossibleBoatSelect(false);
				firstChoice = "";
				secondChoice = "";			
			}
		}

		if(placeShipMode == true && firstChoice != "" && secondChoice != "")
		{	
			if(this.human.getPlayerBoard().validateClick(firstChoice, secondChoice, currentShipSize))
			{	
				Sound s = new Sound();
				s.ShipPlaced();
				
				Ship tempShip = new Ship();
				tempShip.setMaxHit(currentShipSize);
				tempShip.setBoatInit(currentShipText.getText().toString().charAt(0));
				tempShip.setBoatType(currentShipText.getText().toString());

				this.human.placeShip(firstChoice, secondChoice, tempShip);
				this.human.getPlayerBoard().PossibleBoatSelect(false);

				Ship compShip = new Ship();
				compShip.setMaxHit(currentShipSize);
				compShip.setBoatInit(currentShipText.getText().toString().charAt(0));
				compShip.setBoatType(currentShipText.getText().toString());
				this.computer.placeAIShips(compShip);

				firstChoice = "";
				secondChoice = "";

				if(this.human.getAllShips().size() == 5)
				{
					for(int i=0; i < 10; i++)
					{
						for(int j=0; j < 10; j++)
						{
							this.human.getPlayerBoard().getBoard()[i][j].removeActionListener(this);
						}
					}	
										
					Sound battleStations = new Sound();
					battleStations.BattleStations();

					
					System.out.println("game Type: ---- " + paramValue.get("type"));
					
					if(paramValue.get("type").equals("hvb"))
					{
						System.out.println("Player Vs Computer Mode");
						
						this.gameTime.setHuman(human);
						this.gameTime.setComputer(computer);
						this.gameTime.setPVP(false);
						this.gameTime.setThisGame(this);
						this.gameTime.startListening();
						TellUser("Game Starting");

					}else{
						
						System.out.println("PVP MODE ");
						
						//Setting Up the Turn Class 
						this.gameTime.setHuman(human);
						this.gameTime.setOnLineGame(onlineGame);
						this.gameTime.setPVP(true);
						this.gameTime.setThisGame(this);
						
						
						//Sending the Trun class to the PVP class
						//and sending the baord data
						//will also pass the local player
						this.onlineGame.setOnLineTurn(this.gameTime);
						this.onlineGame.setLocalPlayer(this.human);
						this.onlineGame.sendBoardData();
						
						TellUser("Waiting For Opponent.");
					}
					placeShipMode = false;
				}
			}
			else
			{
				human.getPlayerBoard().PossibleBoatSelect(false);
				validate();
				errorMsg.setText("Is Not in a Valid Place Try Again !!");
				firstChoice = "";
				secondChoice = "";
			}
		}

		try {
			displayShip();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Getters && Setters //
	public JPanel getComputerGrid() {
		return computerGrid;
	}
	public void setComputerGrid(JPanel computerGrid) {
		this.computerGrid = computerGrid;
	}		
}