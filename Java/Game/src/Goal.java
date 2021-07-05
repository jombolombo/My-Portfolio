import javafx.scene.image.Image;

/**
 * The class for the Goal Tile object.
 * 
 * @author James Ennis
 * @version 1.0
 */
public class Goal extends Tile {

	/**
	 * The constructor for the Goal Tile object.
	 */
	public Goal() {
		this.setPassable(true);
		this.setPassableEnemy(false);
		this.setImage(new Image("/goal.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		player.setWon(true);
	}

	@Override
	public String getName() {
		return "goal";
	}

}
