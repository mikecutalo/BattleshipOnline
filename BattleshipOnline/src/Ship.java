/**
 * Holds all information about a ship.
 * 
 * @author Mike Cutalo
 * @version 2.0
 */
public class Ship{

	/** Full name of the boat */
	private String boatType;
	/** The initial of the boat, this is used as a key for the grid */
	private char boatInit;
	/** Current amount of times the boat has been hit */
	private int sumHit; 
	/** Max amount of times a boat can be hit */
	private int maxHit; 
	/** Name of player */
	private String playerOwner; 
	/** If this boat has been sunk or not*/
	private boolean isAlive; 
	/** If this ships sunk video has been played*/
	private boolean isVideoPlayed;


	/**
	 * Constructs a ship, sets default values of
	 * attributes.
	 */
	public Ship(){
		this.sumHit = 0;
		this.isAlive = true;
		this.isVideoPlayed = false;
	}

	/* Getters and Setters */
	public String getBoatType() {
		return boatType;
	}
	public void setBoatType(String boatType) {
		this.boatType = boatType;
	}
	public char getBoatInit() {
		return boatInit;
	}
	public void setBoatInit(char boatInit) {
		this.boatInit = boatInit;
	}
	public int getSumHit() {
		return sumHit;
	}
	public void setSumHit(int sumHit) {
		this.sumHit = sumHit;
	}
	public int getMaxHit() {
		return maxHit;
	}
	public void setMaxHit(int maxHit) {
		this.maxHit = maxHit;
	}
	public String getPlayerOwner() {
		return playerOwner;
	}
	public void setPlayerOwner(String playerOwner) {
		this.playerOwner = playerOwner;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public boolean isVideoPlayed() {
		return isVideoPlayed;
	}
	public void setVideoPlayed(boolean isVideoPlayed) {
		this.isVideoPlayed = isVideoPlayed;
	}	
}