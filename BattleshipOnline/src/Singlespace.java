import javax.swing.JButton;

/** 
 * Holds all information about a single space on the grid.
 * This class extends JButton, when applied to the grid
 * it will be shown as so.
 * 
 * @author Mike Cutalo
 * @version 2.0
 */
public class Singlespace extends JButton{

	/** 
	 * The initial of the ship occupying this space.
	 */
	private char occupyingShip;

	/** 
	 * Has the ship in this space been hit. 
	 */
	private boolean isHit;

	/** 
	 * If this place has no ship, has it been hit and it was a miss.
	 */
	private boolean isMiss;

	/**
	 * If this is empty it will be true.
	 */
	private boolean spaceEmpty;


	/**
	 * Constructor for a single space,
	 * will set all values to default. 
	 */
	public Singlespace(){
		isHit = false;
		isMiss = false;
		spaceEmpty = true;
		occupyingShip = ' ';
	}

	/* Getters and Setters */
	public char getOccupyingShip() {
		return occupyingShip;
	}
	public void setOccupyingShip(char occupyingShip) {
		this.occupyingShip = occupyingShip;
	}
	public boolean isHit() {
		return isHit;
	}
	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}
	public boolean isMiss() {
		return isMiss;
	}
	public void setMiss(boolean isMiss) {
		this.isMiss = isMiss;
	}
	public boolean isSpaceEmpty() {
		return spaceEmpty;
	}
	public void setSpaceEmpty(boolean spaceEmpty) {
		this.spaceEmpty = spaceEmpty;
	}
}