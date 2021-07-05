import javafx.scene.image.Image;

/**
 * The class for the tokenTile object.
 * @author Elliot Davis
 * @version 1.0
 */
public class tokenTile extends objectTile {
	/**
	 * The constructor for the tokenTile object.
	 */
	private PlayMusic music = new PlayMusic("music\\coins.wav");
	public tokenTile() {
		this.setImage(new Image("/token.png"));
		this.setPassable(true);
		this.setPassableEnemy(false);
		this.setPickedUp(false);
	}

	/**
	 * The method for handling player collision with self.
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		if (!this.isPickedUp()) {
			music.playMusic();
			player.setTokens(player.getTokens() + 1);
			this.setPickedUp(true);
			this.setPassableEnemy(true);
			this.setImage(new Image("/floor.png"));
		}
	}
	public String getName() {
		if (isPickedUp()) {
			return "floor";
		}
		return "token";
	}
}
