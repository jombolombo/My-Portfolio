import javafx.scene.image.Image;

/**
 * The class for the Floor Tile object.
 * 
 * @author James Ennis
 * @version 1.0
 */
public class Floor extends Tile {

	/**
	 * The floor class constructor, the values will always be fixed.
	 */
	public Floor() {
		setPassable(true);
		setPassableEnemy(true);
		setImage(new Image("/floor.png"));
	}

	/**
	 * The floor has no effect when touched but must be inherited from the abstract
	 * class.
	 */
	public void onTouch(Player player) {
	}

	public String getName() {
		return "floor";
	}
}
