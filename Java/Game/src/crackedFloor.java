
// A tile that breaks after the user steps off it
import javafx.scene.image.Image;

/**
 * The class for the cracked floor Tile object.
 * 
 * @author James Ennis
 * @version 1.0
 */
public class crackedFloor extends Tile {
	/**
	 * The constructor for the cracked floor.
	 * 
	 * @param cracked The boolean value representing if the floor is in the
	 *                'cracked' state.
	 */
	public crackedFloor(boolean cracked) {
		setPassable(cracked);
		setPassableEnemy(false);
		if (isPassable()) {
			setImage(new Image("/crackedfloor.png"));
		} else {
			setImage(new Image("/brokefloor.png"));
		}
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		setPassable(false);
	}

	public String getName() {
		return "cracked:" + isPassable();
	}
}
