import javafx.scene.image.Image;

/**
 * The class for the fire Tile object.
 * 
 * @author James Ennis
 * @version 1.0
 */
public class Fire extends Tile {

	/**
	 * The constructor for the Fire Tile object.
	 */
	private PlayMusic fire = new PlayMusic("music\\fire.wav");

	public Fire() {
		setPassable(true);
		setPassableEnemy(false);
		setImage(new Image("/fire.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!player.isFireBoots()) {
			player.setDead(true);
		} else {
			fire.playMusic();
		}
	}

	public String getName() {
		return "fire";
	}
}
