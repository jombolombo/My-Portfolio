
/**
 * The object tile class, contains data relating to whether the object has been
 * picked up.
 * 
 * @author James Ennis
 * @version 1.0
 */
public abstract class objectTile extends Tile {
	private boolean pickedUp;

	/**
	 * Returns the state of if the item has been picked up.
	 * 
	 * @return true if item has been picked up, false otherwise.
	 */
	protected boolean isPickedUp() {
		return pickedUp;
	}

	/**
	 * Sets the boolean value of picked up state.
	 * 
	 * @param pickedUp The boolean value to be set.
	 */
	protected void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
}
