
// The dumb enemy will mindlessly walk in the direction of player getting stuck on walls if they get in the way

import javafx.scene.image.Image;

/**
 * Class for the most basic enemy type, the Dumb-Targeting enemy.
 * 
 * @author Iwan Bevans
 * @version 1.0
 */
public class dumbEnemy extends Enemy {

	/**
	 * The constructor for the dumb enemy class.
	 * 
	 * @param x The X-coordinate representing self starting location.
	 * @param y The Y-coordinate representing self starting location.
	 */
	public dumbEnemy(int x, int y) {
		setLocationX(x);
		setLocationY(y);
		setImage(new Image("/dumbenemy.png"));
	}

	/**
	 * The dumb enemy movement function moving the enemy in the farthest possible
	 * path to the player.
	 * 
	 * @param level  The current level.
	 * @param player The Player object.
	 */
	public void move(Level level, Player player) {
		boolean isNegativeX = false;
		boolean isNegativeY = false;
		int differenceX = getLocationX() - player.getLocationX();
		int differenceY = getLocationY() - player.getLocationY();
		if (differenceX < 0) {
			differenceX = differenceX * -1;
			isNegativeX = true;
		}
		if (differenceY < 0) {
			differenceY = differenceY * -1;
			isNegativeY = true;
		}
		if (differenceX > differenceY) {
			if (!isNegativeX && level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
				setLocationX(getLocationX() - 1);
				setImage(new Image("/dumbenemy.png"));
			} else if (isNegativeX && level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy()) {
				setLocationX(getLocationX() + 1);
				setImage(new Image("/dumbenemy_right.png"));
			}
		} else {
			if (!isNegativeY && level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
				setLocationY(getLocationY() - 1);
				setImage(new Image("/dumbenemy_up.png"));
			} else if (isNegativeY && level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
				setLocationY(getLocationY() + 1);
				setImage(new Image("/dumbenemy_down.png"));
			}
		}
	}

	public String getName() {
		return "enemy:dumb";
	}
}
