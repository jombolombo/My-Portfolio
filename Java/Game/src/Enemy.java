
// All enemies inherit from the Enemy abstract class,
// it contains all the getters and setters required as well as the abstract
// method move as all enemies have a unique movement type.

import javafx.scene.image.Image;

/**
 * The enemy class is an abstract class that contains the image and location of
 * the enemy, the move method is abstract as each type of enemy has a different
 * method of movement.
 * 
 * @author Iwan Bevans
 * @version 1.0
 */
public abstract class Enemy {
	private Image image;
	private int locationX;
	private int locationY;

	/**
	 * The abstract method for the movement of an enemy type.
	 * 
	 * @param level  The current level.
	 * @param player The player object.
	 */
	public abstract void move(Level level, Player player);

	/**
	 * Returns the image of the enemy.
	 * 
	 * @return image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the image of the enemy.
	 * 
	 * @param image The image to be set.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Returns the x location of the enemy.
	 * 
	 * @return The X-coordinate of the enemy.
	 */
	public int getLocationX() {
		return locationX;
	}

	/**
	 * Sets the x location of the enemy.
	 * 
	 * @param locationX The X-coordinate to be set.
	 */
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	/**
	 * Returns the y location of the enemy.
	 * 
	 * @return The Y-coordinate of the enemy.
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * Sets the y location of the enemy.
	 * 
	 * @param locationY The Y-coordinate to be set.
	 */
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public abstract String getName();
}
