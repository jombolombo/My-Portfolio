
// The ice tile is slippery and when stepped on will send
// the player forward until they hit a wall or get off the ice.
import javafx.scene.image.Image;

/**
 * The class for the Ice Tile object.
 * 
 * @author Unknown
 * @version 1.0
 */
public class Ice extends Tile {

	/**
	 * The Ice class constructor, the values will always be fixed.
	 */
	public Ice() {
		setPassable(true);
		setPassableEnemy(false);
		setImage(new Image("/ice.png"));
	}

	/**
	 * The Ice tile has no effect when touched but must be inherited from the
	 * abstract class.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
	}

	public String getName() {
		return "ice";
	}
}