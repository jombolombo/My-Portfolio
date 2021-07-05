import javafx.scene.image.Image;

/**
 * The class for the Wall Tile type.
 * @author James Ennis
 * @version 1.0
 */
public class Wall extends Tile{
	/**
	 * The constructor for the Wall Tile class.
	 */
	public Wall() {
		setPassable(false);
		setPassableEnemy(false);
		setImage(new Image("/wall.png"));
	}

	public void onTouch(Player player) {}

	@Override
	public String getName() {
		return "wall";
	}
}
