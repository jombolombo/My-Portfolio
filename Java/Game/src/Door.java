// The abstract class of doors checks if the door is locked or not
/**
 * The abstract class for the Door object.
 * @author Elliot Davis
 * @version 1.0
 */

import javafx.scene.image.Image;

/**
 * The class for the Door object, all colours consolodated into one class.
 * 
 * @author Matthew Culley
 * @version 1.0
 */
public class Door extends Tile {
	private boolean isLocked;
	private String colour;

	/**
	 * constructor for new door object
	 * 
	 * @param colour the colour of the door
	 */
	public Door(String colour) {
		this.setPassable(true);
		this.setPassableEnemy(false);
		this.setLocked(true);
		this.colour = colour;
		this.setImage(new Image("/" + getColour() + "keydoor.png"));
	}

	/**
	 * Get the colour of the door
	 * 
	 * @return String, the colour of the door
	 */
	public String getColour() {
		return this.colour;
	}

	public void onTouch(Player player) {
		if (isLocked() && player.getKeys(getColour()) > 0) {
			player.setKeys(getColour(), player.getKeys(getColour()) - 1);
			setLocked(false);
			setPassableEnemy(true);
			this.setImage(new Image("/floor.png"));
		}
	}

	/**
	 * Returns the boolean value of whether the door is 'locked'.
	 * 
	 * @return true if door is 'locked'.
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Sets the value of the locked state of the Door.
	 * 
	 * @param isLocked The boolean value representing the 'locked' state.
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * get the name adn metadata of the door
	 * 
	 * @return the name adn metadata of the door
	 */
	public String getName() {
		if (!isLocked()) {
			return "floor";
		}
		return "door:" + getColour();
	}
}
