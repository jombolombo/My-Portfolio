import javafx.scene.image.Image;

/**
 * The class for the Key object.
 * 
 * @author Matthew Culley
 * @version 1.0
 */
public class Key extends objectTile {
	private String colour;

	/**
	 * The constructor for the yellowKey object.
	 */
	public Key(String colour) {
		this.colour = colour;
		setImage(new Image("/" + getColour() + "key.png"));
		setPassable(true);
		setPassableEnemy(false);
		setPickedUp(false);
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!this.isPickedUp()) {
			this.setPickedUp(true);
			this.setPassableEnemy(true);
			player.setKeys(getColour(), player.getKeys(getColour()) + 1);
			this.setImage(new Image("/floor.png"));
		}
	}

	/**
	 * gets the colour of the key
	 * 
	 * @return the colour of the key
	 */
	public String getColour() {
		return this.colour;
	}

	/**
	 * get the name and metadata of the key
	 * 
	 * @return char {u,d,l,r} the name and metadata of the key
	 */
	public String getName() {
		if (isPickedUp()) {
			return "floor";
		}
		return "key:" + getColour();
	}
}
