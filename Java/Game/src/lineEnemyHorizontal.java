
// The horizontal enemy class is an enemy type that moves left and
// right until it hits a wall and changes direction.

import javafx.scene.image.Image;

/**
 * Class for another enemy type, the Horizontal-Line enemy.
 * 
 * @author Iwan Bevans
 * @version 1.0
 */
public class lineEnemyHorizontal extends Enemy {
	private boolean movingRight;

	/**
	 * The constructor for the Horizontal-Line enemy class.
	 * 
	 * @param x The X-coordinate representing self starting location.
	 * @param y The Y-coordinate representing self starting location.
	 */
	public lineEnemyHorizontal(int x, int y, char direction) {
		setImage(new Image("/lineenemy.png"));
		setLocationX(x);
		setLocationY(y);
		if (direction == 'r') {
			movingRight = true;
		} else {
			movingRight = false;
		}
	}

	/**
	 * The dumb enemy movement function moving the enemy to the right until it
	 * cannot move any further, then it will move to the left.
	 * 
	 * @param level  The current level.
	 * @param player The Player object.
	 */
	public void move(Level level, Player player) {
		if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy() && movingRight) {
			setLocationX(getLocationX() + 1);
			setImage(new Image("/lineenemy_right.png"));
		} else {
			if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
				setLocationX(getLocationX() - 1);
				setImage(new Image("/lineenemy.png"));
				movingRight = false;
			} else {
				movingRight = true;
				setImage(new Image("/lineenemy_right.png"));
				setLocationX(getLocationX() + 1);
			}
		}
	}

	public String getName() {
		String direction;
		if (movingRight) {
			return "enemy:straight:r";
		} else {
			return "enemy:straight:l";
		}

	}
}
