import javafx.scene.image.Image;

/**
 * The class for the tokenDoor object.
 * @author Elliot Davis
 * @version 1.0
 */
public class tokenDoor extends Tile{
	private int amount;
	private boolean isLocked;

	/**
	 * The constructor for the tokenDoor.
	 * @param amount The amount of tokens needed to unlock it.
	 */
	public tokenDoor(int amount) {
		this.amount = amount;
		setPassable(true);
		setLocked(true);
		setPassableEnemy(false);
		setImage(new Image("/tokendoor.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (isLocked() && player.getTokens() >= amount) {
			setLocked(false);
			setPassableEnemy(true);
			setImage(new Image("/floor.png"));
		}
	}

	@Override
	public String getName() {
		if (!isLocked()) {
			return "floor";
		}
		return "door:token:" + getAmount();
	}
	/**
	 * Gets the amount of tokens a door needs to be unlocked.
	 * @return The amount of tokens needed.
	 * */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Returns the boolean value of whether the door is 'locked'.
	 * @return true if door is 'locked'.
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Sets the value of the locked state of the Door.
	 * @param isLocked The boolean value representing the 'locked' state.
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

}
