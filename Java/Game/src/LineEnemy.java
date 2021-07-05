// The horizontal enemy class is an enemy type that moves left and
// right until it hits a wall and changes direction.

import javafx.scene.image.Image;

/**
 * Class for another enemy type, the Horizontal-Line enemy.
 * @author Matthew Culley
 * @version 1.0
 */
public class LineEnemy extends Enemy{
    private boolean movingRight;
    private char direction;

    /**
     * The constructor for the Horizontal-Line enemy class.
     * @param x The X-coordinate representing self starting location.
     * @param y The Y-coordinate representing self starting location.
     */
    public LineEnemy(int x, int y, char direction) {
        setImage(new Image("/lineenemy.png"));
        setLocationX(x);
        setLocationY(y);
        setDirection(direction);
    }

    private void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * The dumb enemy movement function moving the enemy to the
     * right until it cannot move any further, then it will move to the left.
     * @param level The current level.
     * @param player The Player object.
     */
    public void move(Level level,Player player) {
        switch (direction) {
            case 'u':
                if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
                    setLocationY(getLocationY() - 1);
                    setImage(new Image("/lineenemy_up.png"));
                } else if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
                    setDirection('d');
                    setLocationY(getLocationY() + 1);
                    setImage(new Image("/lineenemy_down.png"));
                }
                break;
            case 'd':
                if (level.getTiles()[getLocationX()][getLocationY() + 1].isPassableEnemy()) {
                    setLocationY(getLocationY() + 1);
                    setImage(new Image("/lineenemy_down.png"));
                } else if (level.getTiles()[getLocationX()][getLocationY() - 1].isPassableEnemy()) {
                    setDirection('u');
                    setLocationY(getLocationY() - 1);
                    setImage(new Image("/lineenemy_up.png"));
                } else {
                    break;
                }
            case 'l':
                if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
                    setLocationX(getLocationX() - 1);
                    setImage(new Image("/lineenemy.png"));
                } else if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy()) {
                    setDirection('r');
                    setLocationX(getLocationX() + 1);
                    setImage(new Image("/lineenemy_right.png"));
                }
                break;
            case 'r':
                if (level.getTiles()[getLocationX() + 1][getLocationY()].isPassableEnemy()) {
                    setLocationX(getLocationX() + 1);
                    setImage(new Image("/lineenemy_right.png"));
                } else if (level.getTiles()[getLocationX() - 1][getLocationY()].isPassableEnemy()) {
                    setDirection('l');
                    setLocationX(getLocationX() - 1);
                    setImage(new Image("/lineenemy.png"));
                } else {
                    break;
                }
        }
    }

    /**
     * gets the direction the enemy is travelling in
     * @return char of the direction the enemy is travelling in, u,d,l,r
     */
    private char getDirection() {
        return this.direction;
    }

    /**
     * get the name and metadata of the enemy
     * @return the name and metadata of the enemy
     */
    public String getName() {
        String direction;
        if (movingRight) {
            return "enemy:straight:r";
        } else {
            return "enemy:straight:l";
        }

    }
}
