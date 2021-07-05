import javafx.scene.image.Image;

/**
 * The class for the Flippers object.
 * 
 * @author Elliot Davis
 * @version 1.0
 */
public class Flippers extends objectTile {

	/**
	 * The constructor for the Flippers object.
	 */
	private PlayMusic music = new PlayMusic("music\\bleep.wav");

	public Flippers() {
		this.setPickedUp(false);
		this.setPassable(true);
		this.setPassableEnemy(false);
		this.setImage(new Image("/flippers.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!this.isPickedUp()) {
			music.playMusic();
			player.setFlippers(true);
			this.setPickedUp(true);
			this.setPassableEnemy(true);
			this.setImage(new Image("/floor.png"));
		}
	}

	public String getName() {
		if (isPickedUp()) {
			return "floor";
		}
		return "flippers";
	}

}
