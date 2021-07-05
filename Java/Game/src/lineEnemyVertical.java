
// The vertical enemy class is an enemy type that moves up and
// down until it hits a wall and changes direction.

import javafx.scene.image.Image;

/**
 * Class for another enemy type, the Vertical-Line enemy.
 * 
 * @author Iwan Bevans
 * @version 1.0
 */
public class lineEnemyVertical extends Enemy {
	private boolean movingUp;

	/**
	 * The constructor for the Vertical-Line enemy class.
	 * 
	 * @param x The X-coordinate representing self starting location.
	 * @param y The Y-coordinate representing self starting location.
	 */
	public lineEnemyVertical(int x, int y, char direction) {
		setImage(new Image("/lineenemy.png"));
		setLocationX(x);
		setLocationY(y);
		if (direction == 'u') {
			movingUp = true;
		} else {
			movingUp = false;
		}
	}

	/**
	 * The dumb enemy movement function moving the enemy upwards until it cannot
	 * move any further, then it will move downwards.
	 * 
	 * @param level  The current level.
	 * @param player The Player object.
	 */
	public void move(Level level, Player player) {
		if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy() && movingUp) {
			setLocationY(getLocationY() - 1);
			setImage(new Image("/lineenemy_up.png"));
		} else {
			if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
				setLocationY(getLocationY() + 1);
				setImage(new Image("/lineenemy_down.png"));
				movingUp = false;
			} else {
				movingUp = true;
				setImage(new Image("/lineenemy_up.png"));
				setLocationY(getLocationY() - 1);
			}
		}
	}

	public String getName() {
		String direction;
		if (movingUp) {
			return "enemy:straight:u";
		} else {
			return "enemy:straight:d";
		}

	}
}
