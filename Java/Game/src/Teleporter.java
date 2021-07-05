import javafx.scene.image.Image;

/**
 * The class for the Teleporter Tile object.
 * @author James Ennis
 * @version 1.0
 */
public class Teleporter extends Tile{

	private int pairingId;
	private Teleporter partner;
	private int x;
	private int y;
	private PlayMusic teleport = new PlayMusic("music\\teleport.wav");
	/**
 	 * Constructs the teleporter in x,y coordinate and which teleporter it connects.
 	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param pairing The pairing id of the other teleporter.
 	 */
	public Teleporter(int x, int y, int pairing) {
		setImage(new Image("/floor.png"));
		setPairingId(pairing);
		setX(x);
		setY(y);
		setPassable(true);
		setPassableEnemy(false);
		setImage(new Image("/teleporter.png"));		
	}
	
	/**
	 * The constructor of the teleporter class, it creates pointers
	 * to the coordinates of its paired teleporter.
	 */
//	public Teleporter(int toLocationX, int toLocationY) {
//		this.toLocationX = toLocationX;
//		this.toLocationY = toLocationY;
//		setPassable(true);
//		setPassableEnemy(false);
//		setImage(new Image("/teleporter.png"));
//	}

	/**
	 * The method for handling player collision with self.
	 * @param player The player object.
	 */
	public void onTouch(Player player) {
		teleport.playMusic();
		player.setLocationX(getToLocationX());
		player.setLocationY(getToLocationY());
	}

	/**
	 * Gets the value for the X-coordinate of the to-location.
	 * @return X-coordinate of to-location.
	 */
	public int getToLocationX() {
		return partner.getX();
	}
	/**
	 * Gets the value for the Y-coordinate of the to-location.
	 * @return Y-coordinate of to-location.
	 */
	public int getToLocationY() {
		return partner.getY();
	}




	/**
 	 * Get the pariringId.
 	 * @return pairingId.
 	 */
	public int getPairingId() {
		return pairingId;
	}

	/**
 	 * Set the pairingId.
 	 * @param The new pairingId.
 	 */
	public void setPairingId(int pairingId) {
		this.pairingId = pairingId;
	}

	/**
 	 * Get the x coordinate.
 	 * @return x The x coordinate.
 	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the x coordinate.
 	 * @param x The new x coordinate.
 	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
 	 * Get the y coordinate.
 	 * @return y The y coordinate.
 	 */
	public int getY() {
		return y;
	}

	/**
 	 * Set the y coordinate.
 	 * @param y The new y coordinate.
 	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the partner.
 	 * @return partner.
 	 */
	public Teleporter getPartner() {
		return partner;
	}

	/**
 	 * Set the partner.
 	 * @param partner The new partner.
 	 */
	public void setPartner(Teleporter partner) {
		this.partner = partner;
	}
	
	/**
 	 * Prints the pairingId.
 	 * @return pairingId for teleporter.
 	 */
	public String getName() {
		return  "teleporter:" + pairingId;
	}

}
