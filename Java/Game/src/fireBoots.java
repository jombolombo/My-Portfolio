import javafx.scene.image.Image;

/**
 * The class for the fireBoots object.
 * 
 * @author Elliot Davis
 * @version 1.0
 */
public class fireBoots extends objectTile {

	/**
	 * The constructor for the fireBoots object.
	 */
	private PlayMusic music = new PlayMusic("music\\bleep.wav");

	public fireBoots() {
		setPickedUp(false);
		setPassable(true);
		setPassableEnemy(false);
		setImage(new Image("/fireboots.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * 
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!this.isPickedUp()) {
			music.playMusic();
			player.setFireBoots(true);
			this.setPickedUp(true);
			this.setPassableEnemy(true);
			this.setImage(new Image("/floor.png"));
		}
	}

	public String getName() {
		if (isPickedUp()) {
			return "floor";
		}
		return "fireBoots";
	}

}
