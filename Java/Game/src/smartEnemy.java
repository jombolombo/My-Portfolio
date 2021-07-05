// The smart enemy class, this enemy uses breadth first search to return the best path from the
// enemy to the player and follows that path

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * The smartEnemy class, a more complex enemy that uses path finding
 * to reach the player.
 * @author Reece Veiga
 * @version 1.0
 */
public class smartEnemy extends Enemy{

	/**
	 * The constructor for the smartEnemy class.
	 * @param x The X-coordinate representing self starting location.
	 * @param y The Y-coordinate representing self starting location.
	 */
	public smartEnemy(int x,int y) {
		setImage(new Image("/smartenemy.png"));
		setLocationX(x);
		setLocationY(y);
	}

	/**
	 * The movement function of the smartEnemy, it uses the methods pathFinder and
	 * reconstructPath to find the best path to the player
	 * @param level The current level.
	 * @param player The Player object.
	 */
	public void move(Level level,Player player) {
		int currentX = getLocationX();
		int currentY = getLocationY();
		String[][] prev = pathFinder(currentX,currentY,level);
		boolean[][] path = reconstructPath(prev,currentX,currentY,player.getLocationX(),player.getLocationY(),level);

		if (path[getLocationX()+1][getLocationY()]) {
			setLocationX(getLocationX() + 1);
			setImage(new Image("/smartenemy_right.png"));
		} else if (path[getLocationX()-1][getLocationY()]) {
			setLocationX(getLocationX() - 1);
			setImage(new Image("/smartenemy.png"));
		} else if (path[getLocationX()][getLocationY()+1]) {
			setLocationY(getLocationY() + 1);
			setImage(new Image("/smartenemy_down.png"));
		} else if (path[getLocationX()][getLocationY()-1]) {
			setLocationY(getLocationY() - 1);
			setImage(new Image("/smartenemy_up.png"));
		}
	}

	// this function takes in the array of all nodes parent locations and goes from the players location to
	// the current location of the enemy and returns an 2d array of booleans, the true values is the path
	// the false values is other tiles not in the best path
	/**
	 * Constructs a 2d array of booleans, whose values are true if they are the best possible path
	 * @param prev The previous path constructed.
	 * @param currentX The current X-coordinate of the Player.
	 * @param currentY The current Y-coordinate of the Player.
	 * @param goalX The current X-coordinate of self.
	 * @param goalY The current Y-coordinate of self.
	 * @param level The current Level.
	 * @return path The optimal path of traversal.
	 */
	private boolean[][] reconstructPath(String[][] prev,int currentX,int currentY,int goalX, int goalY, Level level) {
		boolean[][] path = new boolean[level.getWidth()][level.getHeight()];
		for (int x = 0; x < level.getHeight(); x++) {
			for (int y = 0; y < level.getWidth(); y++) {
				path[y][x] = false;
			}
		} String parentString = prev[goalX][goalY];
		while (parentString != null) {
			Scanner in = new Scanner(parentString);
			in.useDelimiter(",");
			int parentX = in.nextInt();
			int parentY = in.nextInt();
			parentString = prev[parentX][parentY];
			path[parentX][parentY] = true;
			if (parentString != null) {
				in.close();
			}
		} return path;
	}

	// This function returns a 2d array of the locations of each nodes parent node, this can be used to
	// construct the best path from the enemy to the player. The function uses a breadth first search
	// algorithm
	/**
	 * Constructs a tree of nodes to calculate the optimal path a Shark should take.
	 * @param currentX Current X-location of enemy.
	 * @param currentY Current Y-location of enemy.
	 * @param level The current level.
	 * @return prev A 2d array containing the values of all the nodes parent nodes from a level.
	 */
	private String[][] pathFinder(int currentX, int currentY, Level level) {
		int[] dr = {-1,1,0,0};
		int[] dc = {0,0,1,-1};
		Queue<int[]> bfs = new LinkedList<int[]>();
		bfs.add(new int[] {currentX,currentY});
		boolean[][] passed = new boolean[level.getWidth()][level.getHeight()];
		for (int x = 0; x < level.getHeight(); x++) {
			for (int y = 0; y < level.getWidth(); y++) {
				passed[y][x] = false;
			}
		} passed[currentX][currentY] = true;
		String[][] prev = new String[level.getWidth()][level.getHeight()];
		for (int x = 0; x < level.getHeight(); x++) {
			for (int y = 0; y < level.getWidth(); y++) {
				prev[y][x] = null;
			}
		}while (!bfs.isEmpty()) {
			int[] node = bfs.remove();
			for (int i = 0; i < 4; i++) {
				int rr = node[0] + dr[i];
				int cc = node[1] + dc[i];
				if (level.getTiles()[rr][cc].isPassableEnemy() && !passed[rr][cc]) {
					bfs.add(new int[] {rr,cc});
					passed[rr][cc] = true;
					prev[rr][cc] = (Integer.toString(node[0]).concat(",").concat(Integer.toString(node[1])));
				}
			}
		} return prev;
	}
	public String getName() {
		return "enemy:smart";
	}
}
