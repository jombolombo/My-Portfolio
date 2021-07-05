import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;

/**
 * This class is a logical representation of the edited map, containing
 * information about each map cell allowing the editing of complex cells like
 * teleporter.
 * 
 * @author Ben Tyler 991226
 * @version 1.0
 */

public class GridState {
	private CellState[][] cellStates;
	private int width;
	private int height;

	/**
	 * Creates a grid of all the tiles combined to create a structure for the level
	 * 
	 * @param width  The width of the level
	 * @param height The height of the level
	 */
	public GridState(int width, int height) {
		this.width = width;
		this.height = height;

		this.cellStates = new CellState[width][height];

		for (int row = 0; row < height; row += 1) {
			for (int col = 0; col < width; col += 1) {
				this.cellStates[col][row] = new CellState(EditorScreenController.BACKGROUND_DEFAULT);
				this.cellStates[col][row].setCellName("floor");
			}
		}
	}

	/**
	 * Returns the entire grid as a 2D array
	 * 
	 * @return A 2D array of type CellState
	 */
	public CellState[][] getCellStates() {
		return cellStates;
	}

	/**
	 * Returns the width of the Grid
	 * 
	 * @return An integer representing the width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the Grid
	 * 
	 * @return An integer representing the height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the image of a tile in the GridState
	 * 
	 * @param cellBackground The image to set
	 * @param x              The x position of the tile in the array
	 * @param y              The y position of the tile in the array
	 */
	public void setCell(Image cellBackground, int x, int y) {
		this.cellStates[x][y] = new CellState(cellBackground);
	}
}
