import javafx.scene.image.Image;

/**
 * This class is a logical representation of a single map cell, containing
 * information allowing the editing of complex cells like teleporter.
 * 
 * @author Ben Tyler 991226
 * @version 1.0
 */

public class CellState {
	private Image currentImage;
	private String cellName;

	/**
	 * Creates a cellState with a name and image
	 * 
	 * @param currentImage The image of the cell
	 */
	public CellState(Image currentImage) {
		this.currentImage = currentImage;
	}

	/**
	 * Returns the name of the cell
	 * 
	 * @return String name of cell
	 */
	public String getCellName() {
		return cellName;
	}

	/**
	 * Sets the name of the cell
	 * 
	 * @param name String: name
	 */
	public void setCellName(String name) {
		cellName = name;
	}

	/**
	 * Returns the image of the cell
	 * 
	 * @return The image of type Image
	 */
	public Image getCurrentImage() {
		return currentImage;
	}

	/**
	 * Sets the image of the cellState
	 * 
	 * @param currentImage The image to set
	 */
	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}
}
