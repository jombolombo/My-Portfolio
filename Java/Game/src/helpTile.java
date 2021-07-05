
// The help tile class, an additional tile in the game that when the player steps on displays a help message
import javafx.scene.image.Image;

/**
 * The class for the helpTile object.
 * 
 * @author James Ennis
 * @version 1.0
 */
public class helpTile extends Tile {
	private String helpMessage;

	/**
	 * The constructor for the Fire Tile object.
	 * 
	 * @param helpMessage The message to be displayed.
	 */
	public helpTile(String helpMessage) {
		setImage(new Image("/helptile.png"));
		setPassable(true);
		setPassableEnemy(true);
		this.helpMessage = helpMessage;
	}

	/**
	 * Has no effect when touched but must be inherited from the abstract class.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		
	}

	/**
	 * Gets the help message
	 * 
	 * @return The help message String.
	 */
	public String getHelpMessage() {
		return helpMessage;
	}

	public String getName() {
		return "help: " + getHelpMessage();
	}
}
