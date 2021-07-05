
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The level class constructs a 2d array of Tile objects representing the level
 * grid, using a file as input. Rebuilt, using base class from Reece Veiga,
 * Oliver Mance
 * 
 * @author Matthew Culley
 * @version 1.2
 */
public class Level {
	private int startingLocationX;
	private int startingLocationY;
	private int width;
	private int height;
	private Tile[][] tiles;
	private Tile[] tilesArray;
	private String fileName;
	private ArrayList<Enemy> allEnemies;
	private ArrayList<Teleporter> teleporters;

	/**
	 * Constructs a Level and populates grid with game object types based on data
	 * from a file.
	 * 
	 * @param inputtedFile The input .txt file.
	 * @throws FileNotFoundException if the given filename is non existant
	 */
	public Level(String inputtedFile) throws FileNotFoundException {
		// create arraylists for extra objects
		allEnemies = new ArrayList<Enemy>();
		teleporters = new ArrayList<Teleporter>();
		fileName = inputtedFile; // name of the file
		/*
		 * create arraylist of strings of the map strings contain metadata of each coord
		 */
		ArrayList<String> mapAl = readFileToMap();
		// create map by converting all strings to objects, then into a 2d array
		tiles = convert2d(convertStringToObjects(mapAl));
		// if there are teleporters in the level, connect them
		if (teleporters.size() > 0)
			connectTeleporters();
	}

	// converts a single d array into 2d
	private Tile[][] convert2d(Tile[] tiles) {
		// create new array
		Tile[][] newTiles = new Tile[width][height];
		int x;
		int y;
		// loop through the array, find the coords in 2d array
		for (int i = 0; i < width * height; i++) {
			x = i % width;
			y = i / width;
			// add to new array
			newTiles[x][y] = tiles[i];
		}
		return newTiles;
	}

	/*
	 * Read the data from the csv file, and turn it into an ArrayList containg the
	 * string names for each cell
	 */
	private ArrayList<String> readFileToMap() throws FileNotFoundException {
		ArrayList<String> mapAl = new ArrayList<String>();

		File csvFile = new File(getFileName());

		// Scanner to read data from csvFile
		Scanner in = null;
		in = new Scanner(csvFile);

		in.useDelimiter(",|\r"); // change the Scanner delimiter so it is applicable to csv files

		// read data from the csv file and add it to the array
		while (in.hasNext()) {
			mapAl.add(in.next().replace("\n", "")); // remove any unneccessary characters
		}
		in.close();

		// get the height and width of the map from the ArrayList
		// remove the items after so the ArrayList contains only the map data
		width = Integer.parseInt(mapAl.get(0));
		mapAl.remove(0);
		height = Integer.parseInt(mapAl.get(0));
		mapAl.remove(0);

		// Remove any remaining blank spaces so only tiles are read
		while (mapAl.get(0).isEmpty()) {
			mapAl.remove(0);
		}
		return mapAl;
	}

	/*
	 * Convert the array list of strings into Cell[] to create the map also creates
	 * collectible and enemy objects
	 */
	private Tile[] convertStringToObjects(ArrayList<String> stringAl) {
		Tile[] map = new Tile[stringAl.size()];
		// for each item within the ArrayList, create the appropriate objects,
		// and store that in the same index in the map array
		for (int i = 0; i < stringAl.size(); i++) {
			// get the x and y coordinates of the object at index i
			int x = i % width;
			int y = i / width;

			switch (stringAl.get(i)) {
			case "floor":
				map[i] = new Floor();
				break;
			case "wall":
				map[i] = new Wall();
				break;
			case "water":
				map[i] = new Water();
				break;
			case "fire":
				map[i] = new Fire();
				break;
			// below are collectibles that are in the level
			// a floor cell may need to be created for each of these
			// the floor or appropriate cell will be added to the map,
			case "goal":
				map[i] = new Goal();
				break;
			case "player":
				map[i] = new Floor();
				startingLocationX = x;
				startingLocationY = y;
				break;
			case "token":
				map[i] = new tokenTile();
				break;
			case "flippers":
				map[i] = new Flippers();
				break;
			case "fireBoots":
				map[i] = new fireBoots();
				break;
			default:
				break;
			}

			// check for objects that have metadata, and deal with the metadata
			// use regex to match to different objects, then read their metadat afterwords

			// check for teleporters

			// done by creating a pattern to check the text,
			// matching the pattern to the text
			// if it matches split the text into the data parts and create a new object

			// set pattern to help tile pattern
			Pattern helpPattern = Pattern.compile("^help:[a-zA-Z|\" \"|.|,|0-9]+$");
			Matcher helpMatcher = helpPattern.matcher(stringAl.get(i));

			// if pattern matches create object
			if (helpMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				map[i] = new helpTile(parts[1]);
			}

			// set pattern to cracked floor pattern
			Pattern crackedFloorPattern = Pattern.compile("^cracked:{1}(true|false)$");
			Matcher crackedFloorMatcher = crackedFloorPattern.matcher(stringAl.get(i));

			// if pattern matches create object
			if (crackedFloorMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				boolean cracked = Boolean.parseBoolean(parts[1]);
				map[i] = new crackedFloor(cracked);
			}

			// set pattern to the teleporter regex
			Pattern teleporterPattern = Pattern.compile("^teleporter:[0-9]+$");
			Matcher teleporterMatcher = teleporterPattern.matcher(stringAl.get(i));

			// if pattern matches create object
			if (teleporterMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				String teleporterPairing = parts[1];
				Teleporter tele = new Teleporter(x, y, Integer.parseInt(teleporterPairing));
				teleporters.add(tele);
				map[i] = tele;
			}

			// create doors
			Pattern doorPattern = Pattern.compile("^door:(red|blue|green|yellow|token:[1-9])$");
			Matcher doorMatcher = doorPattern.matcher(stringAl.get(i));

			if (doorMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				// if the door is a token door, then create a token door, otherwise create a
				// colour door
				if (parts[1].equals("token")) {
					map[i] = new tokenDoor(Integer.parseInt(parts[2]));
					System.out.println(Integer.parseInt(parts[2]));
				} else {
					switch (parts[1]) {
					case "red":
						map[i] = new Door("red");
						break;
					case "blue":
						map[i] = new Door("blue");
						break;
					case "green":
						map[i] = new Door("green");
						break;
					case "yellow":
						map[i] = new Door("yellow");
						break;
					default:
						break;
					}
				}
			}

			// create keys
			Pattern keyPattern = Pattern.compile("^key:(red|blue|green|yellow)$");
			Matcher keyMatcher = keyPattern.matcher(stringAl.get(i));

			if (keyMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				switch (parts[1]) {
				case "red":
					map[i] = new Key("red");
					break;
				case "blue":
					map[i] = new Key("blue");
					break;
				case "green":
					map[i] = new Key("green");
					break;
				case "yellow":
					map[i] = new Key("yellow");
					break;
				default:
					break;
				}
			}

			// create enemies
			Pattern enemyPattern = Pattern.compile("^enemy:((smart|dumb)|(wall:[ac]:[udlr]|straight:[udlr])|shark)$");
			Matcher enemyMatcher = enemyPattern.matcher(stringAl.get(i));
			// set the map at that location to grund so they can stand on it
			if (enemyMatcher.matches()) {
				String[] parts = stringAl.get(i).split(":");
				switch (parts[1]) {
				case "straight":
					char direction = parts[2].charAt(0);
					allEnemies.add(new LineEnemy(x, y, direction));
					map[i] = new Floor();
					System.out.println("CREATING STRAIGHT");
					break;
				case "wall":
					allEnemies.add(new wallEnemy(x, y, parts[2].charAt(0), parts[3].charAt(0)));
					map[i] = new Floor();
					System.out.println("CREATING WALL");
					break;
				case "dumb":
					allEnemies.add(new dumbEnemy(x, y));
					map[i] = new Floor();
					System.out.println("CREATING DUMB");
					break;
				case "smart":
					allEnemies.add(new smartEnemy(x, y));
					map[i] = new Floor();
					System.out.println("CREATING SMART");
					break;
				case "shark":
					allEnemies.add(new Shark(x, y));
					map[i] = new Water();
					System.out.println("CREATING SHARK");
					break;
				default:
					break;
				}
			}
		}
		// if the map doesnt contain enough objects and thus incomplete
		if (map.length < getWidth() * getHeight()) {
			System.out.println("Error loading map, the map does contain all cells");
			System.exit(0);
		}
		return map;
	}

	/**
	 * gets the name of the level
	 * 
	 * @return he name of the level
	 */
	public String getLevelName() {
		return this.fileName;
	}

	/**
	 * sets the name of the level
	 * 
	 * @param fileName the name of the level
	 */
	public void setLevelName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get the X location of the player starting postion.
	 * 
	 * @return The X coordinate of the player starting location.
	 */
	public int getStartingLocationX() {
		return startingLocationX;
	}

	/**
	 * Get the Y location of the player starting postion.
	 * 
	 * @return The Y coordinate of the player starting location.
	 */
	public int getStartingLocationY() {
		return startingLocationY;
	}

	/**
	 * Get the width of the level grid.
	 * 
	 * @return The width of level grid.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the height of the level grid.
	 * 
	 * @return The height of level grid.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the 2d array of Tile objects.
	 * 
	 * @return The 2D-Array of Tile objects.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * Returns the tile at the given position in the array
	 * 
	 * @param x The x position of the tile
	 * @param y The y position of the tile
	 * @return Returns a Tile object at the set position
	 */
	public Tile getTiles(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * Get string of the filename.
	 * 
	 * @return The String of the filename.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Get the Arraylist of all enemies and their grid locations.
	 * 
	 * @return The ArrayList of all enemies.
	 */
	public ArrayList<Enemy> getAllEnemies() {
		return allEnemies;
	}

	// connect the teleporters together so you can move between them
	private void connectTeleporters() {
		int numberOfPairs = teleporters.size() / 2;
		// for each pair
		for (int p = 0; p < numberOfPairs; p++) {
			// get the first tele in the list
			Teleporter head = teleporters.get(0);
			teleporters.remove(0);
			// iterate the remaining list to find its partner
			for (int i = 0; i < teleporters.size(); i++) {
				// if its partner
				if (head.getPairingId() == teleporters.get(i).getPairingId()) {
					// connect the partners
					head.setPartner(teleporters.get(i));
					teleporters.get(i).setPartner(head);
					// remove the second tele from the arraylist
					teleporters.remove(i);
				}
			}
		}
	}
}
