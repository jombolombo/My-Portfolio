// The wall enemy class, the wall enemy follows the closest wall moving in a square shape
import javafx.scene.image.Image;

/**
 * The wall enemy is a simple enemy that moves following a wall
 * @author Matthew Culley
 * @version 1.0
 */
public class wallEnemy extends Enemy {
	/**
	 * The constructor for the wall-following enemy.
	 * @param x The starting X-coordinate for the enemy.
	 * @param y The starting Y-coordinate for the enemy.
	 */
	private char direction;
	private char rotation;
	private int prevX;
	private int prevY;

	/**
	 * Enemy moves around walls, cannot move if not adjacent to a wall
	 * @param x x location of the enemy
	 * @param y y location of the enemy
	 * @param direction the direction of the wall the enemy is attatched too
	 * @param rotation whether the enemy is rotating clockwise or anticlockwise
	 */
	public wallEnemy(int x, int y, char direction, char rotation) {
		setImage(new Image("/wallenemy.png"));
		setLocationX(x);
		setLocationY(y);
		setDirection(direction);
		setRotation(rotation);
	}

	/**
	 * The move function for the wall enemy detects the nearest wall and follows alongside it.
	 * @param level The current level.
	 */
	public void move(Level level, Player player){
		switch (direction){
			//if attatched upwards
			case 'u':
				switch (rotation) {
					//if moving clockwise
					case 'c':
						if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy() || (getLocationX() == prevX && getLocationY() - 1 == prevY)) {
							if (level.getTiles()[getLocationX() - 1][ getLocationY()].isPassableEnemy()) {
								this.setLocationX(getLocationX() - 1);
							} else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
								setLocationY(getLocationY() + 1);
								this.setDirection('l');
							}  else if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() + 1);
								this.setDirection('d');
							}
						} else {
							setLocationY(getLocationY() - 1);
							setDirection('r');
						}
						break;
					case 'a':
						if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy() || (getLocationX() == prevX && getLocationY() - 1 == prevY)) {
							if (level.getTiles()[getLocationX() + 1][ getLocationY()].isPassableEnemy()) {
								this.setLocationX(getLocationX() + 1);
							} else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
								setLocationY(getLocationY() + 1);
								this.setDirection('r');
							}  else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() - 1);
								this.setDirection('d');
							}
						} else {
							setLocationY(getLocationY() - 1);
							setDirection('l');
						}
						break;
				}
			case 'd':
				switch (rotation) {
					case 'c':
						if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy() || (getLocationX() == prevX && getLocationY() + 1 == prevY)) {
							if (level.getTiles()[getLocationX() + 1][ getLocationY()].isPassableEnemy()) {
								this.setLocationX(getLocationX() + 1);
							} else if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
								setLocationY(getLocationY() - 1);
								this.setDirection('r');
							}  else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() - 1);
								this.setDirection('u');
							}
						} else {
							setLocationY(getLocationY() + 1);
							setDirection('l');
						}
						break;
					case 'a':
						if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy() || (getLocationX() == prevX && getLocationY() + 1 == prevY)) {
							if (level.getTiles()[getLocationX() - 1][ getLocationY()].isPassableEnemy()) {
								this.setLocationX(getLocationX() - 1);
							} else if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
								setLocationY(getLocationY() - 1);
								this.setDirection('l');
							}  else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() + 1);
								this.setDirection('u');
							}
						} else {
							setLocationY(getLocationY() + 1);
							setDirection('r');
						}
						break;
				}
			case 'l':
				switch (rotation) {
						case 'c':
							if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy() || (getLocationX() - 1 == prevX && getLocationY() == prevY)) {
								if (level.getTiles()[getLocationX()][ getLocationY() + 1].isPassableEnemy()) {
									this.setLocationY(getLocationY() + 1);
								} else if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy()) {
									setLocationX(getLocationX() + 1);
									this.setDirection('d');
								}  else if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
									setLocationY(getLocationY() - 1);
									this.setDirection('r');
								}
							} else {
								setLocationX(getLocationX() - 1);
								setDirection('u');
							}
							break;
						case 'a':
							if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy() || (getLocationX() - 1 == prevX && getLocationY() == prevY)) {
								if (level.getTiles()[getLocationX()][ getLocationY() - 1].isPassableEnemy()) {
									this.setLocationY(getLocationX() - 1);
								} else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
									setLocationX(getLocationX() + 1);
									this.setDirection('u');
								}  else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
									setLocationY(getLocationY() + 1);
									this.setDirection('r');
								}
							} else {
								setLocationX(getLocationX() - 1);
								setDirection('d');
							}
							break;
				}
			case 'r':
				switch (rotation) {
					case 'c':
						if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy() || (getLocationX() + 1 == prevX && getLocationY() == prevY)) {
							if (level.getTiles()[getLocationX()][ getLocationY() - 1].isPassableEnemy()) {
								this.setLocationY(getLocationY() - 1);
							} else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() - 1);
								this.setDirection('u');
							}  else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
								setLocationY(getLocationY() + 1);
								this.setDirection('l');
							}
						} else {
							setLocationX(getLocationX() + 1);
							setDirection('d');
						}
						break;
					case 'a':
						if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy() || (getLocationX() + 1 == prevX && getLocationY() == prevY)) {
							if (level.getTiles()[getLocationX()][ getLocationY() + 1].isPassableEnemy()) {
								this.setLocationY(getLocationX() + 1);
							} else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
								setLocationX(getLocationX() - 1);
								this.setDirection('d');
							}  else if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
								setLocationY(getLocationY() - 1);
								this.setDirection('l');
							}
						} else {
							setLocationX(getLocationX() + 1);
							setDirection('u');
						}
						break;
				}
		}
		prevX = getLocationX();
		prevY = getLocationY();
	}

	/**
	 * gets the direction of the wall the enemy is attatched to
	 * @return char direction of the wall the enemy is attatched to
	 */
	public char getDirection() {
		return this.direction;
	}

	/**
	 * set the direction of the wall the enemy is attatched to
	 * @param direction the direction of the wall the enemy is attatched to
	 */
	public void setDirection(char direction) {
		this.direction = direction;
	}

	/**
	 * get the rotation of the enemy
 	 * @return char a for anticlockwise, c for clockwise
	 */
	public char getRotation() {
		return this.rotation;
	}

	/**
	 * set the rotation of the enemy
	 * @param direction a for anticlockwise c for clockwise
	 */
	public void setRotation(char direction) {
		this.rotation = direction;
	}

	/**
	 * get the name of the enemy and its metadata
	 * @return string contataining the name and metadata
	 */
	public String getName() {
		return "enemy:wall:c:u";
	}

	/**
	 * get the previous y location of the enemy
	 * @return the previous y location of the enemy
	 */
	public int getPrevY() {
		return prevY;
	}

	/**
	 * set the previous y location of the enemy
	 * @param prevY the previous y location of the enemy
	 */
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	/**
	 * get the previous x location of the enemy
	 * @return the previous x location of the enemy
	 */
	public int getPrevX() {
		return prevX;
	}

	/**
	 * set the previous x location of the enemy
	 * @param prevX the previous x location of the enemy
	 */
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}
}
