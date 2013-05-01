import java.util.HashMap;
import javax.swing.ImageIcon;

/** 
 * This class holds all information about the player.
 * Keeping all current stats such as ships sunk,
 * all info about ships. Also this stores the players 
 * board that holds the current state of game for this player.
 * <p>
 * This class will also place a ship on the board,
 * after it has been validated. Also this class will
 * check if all ships have been sunk so a winner may be 
 * announced.  
 * 
 * @author Mike Cutalo
 * @version 3.0
 */
public class Player{

	/** 
	 * Players name
	 */	
	protected String playerName; 

	/** 
	 * The players board where ships resides
	 */
	protected Grid playerBoard;

	/** 
	 * Total number of turn taken
	 */
	protected int numTurns;

	/** 
	 * Total number of hits 
	 */
	protected int numHits;

	/** 
	 * Total number of misses 
	 */
	protected int numMissed;

	/** The number of ships sunk for this player*/
	protected int shipsSunk;

	/** Will map the Initial of the ship to the correct ship object */
	protected HashMap<Character, Ship> allShips;

	/**
	 * Constructs a new grid and creates a new hashmap,
	 * for all the ships.
	 */
	public Player()
	{
		this.playerBoard = new Grid();
		this.allShips = new HashMap<Character, Ship>();
	}

	/**
	 * Place a ship on the players board.
	 * 
	 * This method is called after validation is complete.
	 * Simply will place a ship on the players board, as well as
	 * save the ship into the players hashmap.
	 * 
	 * @param firstClick Coordinates of the players first selection.
	 * @param secondClick Coordinates of the players second selection.
	 * @param newShip Ship object will all info about the ship to be placed.
	 */
	public void placeShip(String firstClick, String secondClick, Ship newShip)
	{	
		int row = Integer.parseInt(Character.toString(firstClick.charAt(0)));
		int col = Integer.parseInt(Character.toString(firstClick.charAt(1)));
		newShip.setPlayerOwner(this.getPlayerName());

		if(firstClick.charAt(0) == secondClick.charAt(0)) //check if rows are equal
		{			
			for(int i=0; i < newShip.getMaxHit(); i++)
			{
				if(firstClick.charAt(1) < secondClick.charAt(1)) //Goes Right
				{
					this.playerBoard.getBoard()[row][col+i].setIcon(Game.currentShipImg.getIcon());
					this.playerBoard.getBoard()[row][col+i].setSpaceEmpty(false);
					this.playerBoard.getBoard()[row][col+i].setOccupyingShip(newShip.getBoatInit());
				}
				else //Goes Left
				{
					this.playerBoard.getBoard()[row][col-i].setIcon(Game.currentShipImg.getIcon());
					this.playerBoard.getBoard()[row][col-i].setSpaceEmpty(false);
					this.playerBoard.getBoard()[row][col-i].setOccupyingShip(newShip.getBoatInit());
				}
			}
		}
		else //else the columns are equal 
		{
			for(int i=0; i < newShip.getMaxHit(); i++)
			{
				if(firstClick.charAt(0) < secondClick.charAt(0)) //Goes Down
				{	
					this.playerBoard.getBoard()[row+i][col].setIcon(Game.currentShipImg.getIcon());
					this.playerBoard.getBoard()[row+i][col].setSpaceEmpty(false);
					this.playerBoard.getBoard()[row+i][col].setOccupyingShip(newShip.getBoatInit());	
				}
				else //Goes Up
				{	
					this.playerBoard.getBoard()[row-i][col].setIcon(Game.currentShipImg.getIcon());
					this.playerBoard.getBoard()[row-i][col].setSpaceEmpty(false);
					this.playerBoard.getBoard()[row-i][col].setOccupyingShip(newShip.getBoatInit());	
				}
			}
		}

		this.allShips.put(newShip.getBoatInit(), newShip); 
	}

	/**
	 * Place the ship in the grid
	 *
	 *This is called after all validation, it will simply
	 *place the ship in the location it is bound for.
	 *
	 * @param input The user input, with the location and orientation 
	 * @param newShip The ship that is going to be placed 
	 * @deprecated This is only used in the text-based game, since it requires different input.
	 */
	public void placePiece(char[] input, Ship newShip)
	{
		int row = this.playerBoard.getIntPlace(input[0]);
		int col = Integer.parseInt(Character.toString(input[1]));
		String shipPos = Character.toString(input[2]);

		if(shipPos.equals("h") || shipPos.equals("H"))
		{
			for(int i=0; i < newShip.getMaxHit(); i++)
			{
				this.playerBoard.getBoard()[row][col+i].setSpaceEmpty(false);
				this.playerBoard.getBoard()[row][col+i].setOccupyingShip(newShip.getBoatInit());

				//Set Board Pictures
				this.playerBoard.getBoard()[row][col+i].setIcon(Game.currentShipImg.getIcon());
			}	
		}else{
			for(int i=0; i < newShip.getMaxHit(); i++)
			{
				this.playerBoard.getBoard()[row+i][col].setSpaceEmpty(false);
				this.playerBoard.getBoard()[row+i][col].setOccupyingShip(newShip.getBoatInit());

				this.playerBoard.getBoard()[row+i][col].setIcon(Game.currentShipImg.getIcon());
			}
		}			
		this.allShips.put(newShip.getBoatInit(), newShip); 
	}

	/**
	 * Check what ships have been sunk and if game is over.
	 *
	 *If a ship has been sunk this method will set
	 *the ships isAlive to false because it is dead. If 
	 *all ships are dead it will report the game is over.
	 *
	 * @return isGameOver if true all player ships have been sunk
	 */
	protected boolean checkShips()
	{
		ImageIcon img = new ImageIcon(getClass().getResource("/turnImg/dead.jpg"));

		this.shipsSunk =0;	
		boolean isGameOver = false;

		char[] s ={'A','B','S','D','P'};

		for(int i=0; i < s.length; i++)
		{
			if(this.allShips.get(s[i]).getMaxHit() == this.allShips.get(s[i]).getSumHit())
			{
				this.allShips.get(s[i]).setAlive(false);
				this.shipsSunk += 1;	

				for(int x=0; x < 10; x++)
				{
					for(int m=0; m < 10; m++)
					{
						if(this.getPlayerBoard().getBoard()[x][m].getOccupyingShip() == s[i]){
							this.getPlayerBoard().getBoard()[x][m].setIcon(img);
							this.getPlayerBoard().getBoard()[x][m].setDisabledIcon(img);
						}
					}	
				}
			}			
		}
		
		if(this.shipsSunk == 5){
			return isGameOver = true;
		}else{
			return isGameOver;
		}
	}

	/* Getters and Setters */
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Grid getPlayerBoard() {
		return playerBoard;
	}
	public void setPlayerBoard(Grid playerBoard) {
		this.playerBoard = playerBoard;
	}
	public int getNumTurns() {
		return numTurns;
	}
	public void setNumTurns(int numTurns) {
		this.numTurns = numTurns;
	}
	public int getNumHits() {
		return numHits;
	}
	public void setNumHits(int numHits) {
		this.numHits = numHits;
	}
	public int getNumMissed() {
		return numMissed;
	}
	public void setNumMissed(int numMissed) {
		this.numMissed = numMissed;
	}
	public int getShipsSunk() {
		return shipsSunk;
	}
	public void setShipsSunk(int shipsSunk) {
		this.shipsSunk = shipsSunk;
	}
	public HashMap<Character, Ship> getAllShips() {
		return allShips;
	}
	public void setAllShips(HashMap<Character, Ship> allShips) {
		this.allShips = allShips;
	}
}