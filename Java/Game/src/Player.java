
// The player class, contains the information about the player such as their inventory and all of the movement functions
import javafx.scene.image.Image;

/**
 * The player class contains the location of the player, their inventory and
 * functions for movements.
 * 
 * @author Reece Veiga
 * @version 1.0
 */
public class Player {
	private Image playerImage;
	private int locationX;
	private int locationY;
	private boolean fireBoots;
	private boolean flippers;
	private int tokens;
	private int redKeys;
	private int blueKeys;
	private int yellowKeys;
	private int greenKeys;
	private boolean dead;
	private boolean won;
	private PlayMusic bump = new PlayMusic("music\\bump.wav");

	/**
	 * The constructor for the player class, it creates their inventory and default
	 * state.
	 * 
	 * @param level The current level.
	 */
	public Player(Level level) {
		setDead(false);
		setWon(false);
		setPlayerImage(new Image("/player.png"));
		setLocationX(level.getStartingLocationX());
		setLocationY(level.getStartingLocationY());
		setFireBoots(false);
		setFlippers(false);
		setTokens(0);
		setRedKeys(0);
		setBlueKeys(0);
		setYellowKeys(0);
		setGreenKeys(0);
	}

	/**
	 * Checks whether the player can move down and then moves the player down.
	 * 
	 * @param level The current level.
	 */
	public void moveDown(Level level) {
		boolean move = true;
		if (level.getTiles()[locationX][locationY + 1] instanceof Door) {
			level.getTiles()[locationX][locationY + 1].onTouch(this);
			Door door = (Door) level.getTiles()[locationX][locationY + 1];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX][locationY + 1] instanceof tokenDoor) {
			level.getTiles()[locationX][locationY + 1].onTouch(this);
			tokenDoor door = (tokenDoor) level.getTiles()[locationX][locationY + 1];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX][locationY + 1].isPassable() && move) {
			for (int x = 0; x < level.getAllEnemies().size(); x++) {
				if (level.getAllEnemies().get(x).getLocationX() == locationX
						&& level.getAllEnemies().get(x).getLocationY() == locationY + 1) {
					setDead(true);
				}
				level.getAllEnemies().get(x).move(level, this);
				if (level.getAllEnemies().get(x).getLocationX() == locationX
						&& level.getAllEnemies().get(x).getLocationY() == locationY + 1) {
					setDead(true);
				}
			}
			level.getTiles()[locationX][locationY + 1].onTouch(this);
			if (level.getTiles()[locationX][locationY + 1] instanceof crackedFloor) {
				level.getTiles()[locationX][locationY + 1].setImage(new Image("/brokefloor.png"));
			}
			locationY = locationY + 1;
			setPlayerImage(new Image("/playerdown.png"));
		} else {
			bump.playMusic();
		}
	}

	/**
	 * Checks whether the player can move up and then moves the player up
	 * 
	 * @param level The current level.
	 */
	public void moveUp(Level level) {
		boolean move = true;
		if (level.getTiles()[locationX][locationY - 1] instanceof Door) {
			level.getTiles()[locationX][locationY - 1].onTouch(this);
			Door door = (Door) level.getTiles()[locationX][locationY - 1];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX][locationY - 1] instanceof tokenDoor) {
			level.getTiles()[locationX][locationY - 1].onTouch(this);
			tokenDoor door = (tokenDoor) level.getTiles()[locationX][locationY - 1];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX][locationY - 1].isPassable() && move) {
			for (int x = 0; x < level.getAllEnemies().size(); x++) {
				if (level.getAllEnemies().get(x).getLocationX() == locationX
						&& level.getAllEnemies().get(x).getLocationY() == locationY - 1) {
					setDead(true);
				}
				level.getAllEnemies().get(x).move(level, this);
				if (level.getAllEnemies().get(x).getLocationX() == locationX
						&& level.getAllEnemies().get(x).getLocationY() == locationY - 1) {
					setDead(true);
				}
			}
			level.getTiles()[locationX][locationY - 1].onTouch(this);
			if (level.getTiles()[locationX][locationY] instanceof crackedFloor) {
				level.getTiles()[locationX][locationY].setImage(new Image("/brokefloor.png"));
			}
			setPlayerImage(new Image("/playerup.png"));
			locationY = locationY - 1;
		} else {
			bump.playMusic();
		}
	}

	/**
	 * Checks whether the player can move right and then moves the player right.
	 * 
	 * @param level The current level.
	 */
	public void moveRight(Level level) {
		boolean move = true;
		if (level.getTiles()[locationX + 1][locationY] instanceof Door) {
			level.getTiles()[locationX + 1][locationY].onTouch(this);
			Door door = (Door) level.getTiles()[locationX + 1][locationY];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX + 1][locationY] instanceof tokenDoor) {
			level.getTiles()[locationX + 1][locationY].onTouch(this);
			tokenDoor door = (tokenDoor) level.getTiles()[locationX + 1][locationY];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX + 1][locationY].isPassable() && move) {
			for (int x = 0; x < level.getAllEnemies().size(); x++) {
				if (level.getAllEnemies().get(x).getLocationX() == locationX + 1
						&& level.getAllEnemies().get(x).getLocationY() == locationY) {
					setDead(true);
				}
				level.getAllEnemies().get(x).move(level, this);
				if (level.getAllEnemies().get(x).getLocationX() == locationX + 1
						&& level.getAllEnemies().get(x).getLocationY() == locationY) {
					setDead(true);
				}
			}
			level.getTiles()[locationX + 1][locationY].onTouch(this);
			if (level.getTiles()[locationX][locationY] instanceof crackedFloor) {
				level.getTiles()[locationX][locationY].setImage(new Image("/brokefloor.png"));
			}
			setPlayerImage(new Image("/player.png"));
			locationX = locationX + 1;
		} else {
			bump.playMusic();
		}
	}

	/**
	 * Checks whether the player can move left and then moves the player left.
	 * 
	 * @param level The current level.
	 */
	public void moveLeft(Level level) {
		boolean move = true;
		if (level.getTiles()[locationX - 1][locationY] instanceof Door) {
			level.getTiles()[locationX - 1][locationY].onTouch(this);
			Door door = (Door) level.getTiles()[locationX - 1][locationY];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX - 1][locationY] instanceof tokenDoor) {
			level.getTiles()[locationX - 1][locationY].onTouch(this);
			tokenDoor door = (tokenDoor) level.getTiles()[locationX - 1][locationY];
			if (door.isLocked()) {
				move = false;
			}
		}
		if (level.getTiles()[locationX - 1][locationY].isPassable() && move) {
			for (int x = 0; x < level.getAllEnemies().size(); x++) {
				if (level.getAllEnemies().get(x).getLocationX() == locationX - 1
						&& level.getAllEnemies().get(x).getLocationY() == locationY) {
					setDead(true);
				}
				level.getAllEnemies().get(x).move(level, this);
				if (level.getAllEnemies().get(x).getLocationX() == locationX - 1
						&& level.getAllEnemies().get(x).getLocationY() == locationY) {
					setDead(true);
				}
			}
			level.getTiles()[locationX - 1][locationY].onTouch(this);
			if (level.getTiles()[locationX][locationY] instanceof crackedFloor) {
				level.getTiles()[locationX][locationY].setImage(new Image("/brokefloor.png"));
			}
			setPlayerImage(new Image("/playerleft.png"));
			locationX = locationX - 1;
		} else {
			bump.playMusic();
		}
	}

	/**
	 * Returns the x location of the player.
	 * 
	 * @return The X-coordinate of player.
	 */
	public int getLocationX() {
		return locationX;
	}

	/**
	 * Sets the x location of the player.
	 * 
	 * @param locationX The desired X-coordinate.
	 */
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	/**
	 * Returns the y location of the player.
	 * 
	 * @return The Y-coordinate of player.
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * Sets the y location of the player.
	 * 
	 * @param locationY The desired Y-coordinate.
	 */
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	/**
	 * Returns the image of the player.
	 * 
	 * @return Image of the player.
	 */
	public Image getPlayerImage() {
		return playerImage;
	}

	/**
	 * Sets the image of the player.
	 * 
	 * @param playerImage The desired image.
	 */
	public void setPlayerImage(Image playerImage) {
		this.playerImage = playerImage;
	}

	/**
	 * Checks if the player has obtained FireBoots object.
	 * 
	 * @return A boolean of whether the player has FireBoots.
	 */
	public boolean isFireBoots() {
		return fireBoots;
	}

	/**
	 * Sets the fireBoots state.
	 * 
	 * @param fireBoots The desired state.
	 */
	public void setFireBoots(boolean fireBoots) {
		this.fireBoots = fireBoots;
	}

	/**
	 * Checks whether the player has obtained flippers.
	 * 
	 * @return A boolean of whether the player has flippers.
	 */
	public boolean isFlippers() {
		return flippers;
	}

	/**
	 * Sets the flippers state.
	 * 
	 * @param flippers The desired state.
	 */
	public void setFlippers(boolean flippers) {
		this.flippers = flippers;
	}

	/**
	 * Gets the amount of tokens the player currently has.
	 * 
	 * @return The amount of tokens.
	 */
	public int getTokens() {
		return tokens;
	}

	/**
	 * Sets the token amount the player has.
	 * 
	 * @param tokens The desired amount of tokens.
	 */
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}

	/**
	 * Gets the amount of red keys the player currently has.
	 * 
	 * @return The amount of red keys.
	 */
	public int getRedKeys() {
		return redKeys;
	}

	/**
	 * Sets the amount of red keys the player has.
	 * 
	 * @param redKeys The desired amount of red keys.
	 */
	public void setRedKeys(int redKeys) {
		this.redKeys = redKeys;
	}

	/**
	 * Gets the amount of blue keys the player currently has.
	 * 
	 * @return The amount of blue keys.
	 */
	public int getBlueKeys() {
		return blueKeys;
	}

	/**
	 * Sets the amount of blue keys the player has.
	 * 
	 * @param blueKeys The desired amount of blue keys.
	 */
	public void setBlueKeys(int blueKeys) {
		this.blueKeys = blueKeys;
	}

	/**
	 * Gets the amount of yellow keys the player currently has.
	 * 
	 * @return The amount of yellow keys.
	 */
	public int getYellowKeys() {
		return yellowKeys;
	}

	/**
	 * Sets the amount of yellow keys the player has.
	 * 
	 * @param yellowKeys The desired amount of yellow keys.
	 */
	public void setYellowKeys(int yellowKeys) {
		this.yellowKeys = yellowKeys;
	}

	/**
	 * Gets the amount of green keys the player currently has.
	 * 
	 * @return The amount of green keys.
	 */
	public int getGreenKeys() {
		return greenKeys;
	}

	/**
	 * Sets the amount of green keys the player has.
	 * 
	 * @param greenKeys The desired amount of green keys.
	 */
	public void setGreenKeys(int greenKeys) {
		this.greenKeys = greenKeys;
	}

	/**
	 * Gets the state of whether the player is dead or not.
	 * 
	 * @return True if player is dead, false otherwise.
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Sets the player dead state.
	 * 
	 * @param dead The desired state.
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Gets the won state of the player.
	 * 
	 * @return true if player has won, false otherwise.
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * Sets the won state of the player.
	 * 
	 * @param won The desired state.
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	public int getKeys(String colour) {
		switch (colour) {
		case "red":
			return getRedKeys();
		case "blue":
			return getBlueKeys();
		case "yellow":
			return getYellowKeys();
		case "green":
			return getGreenKeys();
		default:
			return 0;
		}
	}

	public void setKeys(String colour, int number) {
		switch (colour) {
		case "red":
			setRedKeys(number);
			break;
		case "blue":
			setBlueKeys(number);
			break;
		case "yellow":
			setYellowKeys(number);
			break;
		case "green":
			setGreenKeys(number);
			break;
		default:
			return;
		}
	}
}
