import javafx.scene.image.Image;

/**
 * The class for the Water Tile object.
 * @author James Ennis
 * @version 1.0
 */
public class Water extends Tile{
	/**
	 * The constructor for the Water tile object.
	 */
	private PlayMusic water = new PlayMusic("music\\water.wav");
	public Water() {
		setPassable(true);
		setPassableEnemy(false);
		setImage(new Image("/water.png"));
	}

	/**
	 * The method for handling player collision with self.
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!player.isFlippers()) {
			player.setDead(true);
		}else {
			
			water.playMusic();
		}
	}

	@Override
	public String getName() {
		return "water";
	}

}
