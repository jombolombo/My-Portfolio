
// The game class, contains the javafx for all the of the screens, and the game itself
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The game class, controls all of the menus and the running order of the game.
 * 
 * @author Reece Veiga
 * @version 1.0
 */
public class Game extends Application {
	// All of the global variables
	// The size of the window and canvas for the level
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 786;
	private static final int CANVAS_WIDTH = WINDOW_WIDTH - (WINDOW_WIDTH / 5);
	private static final int CANVAS_HEIGHT = WINDOW_HEIGHT - (WINDOW_HEIGHT / 5);
	// Changeable stats based on the settings of the game
	private static int GRID_CELL_WIDTH = 50;
	private static int GRID_CELL_HEIGHT = 50;
	private static int FINAL_LEVEL_NUMBER = 8;
	private static int INVENTORY_SIZE = 7;
	// Attributes that are called in multiple disjoint functions
	public static Level level;
	public static Player player;
	static ArrayList<userProfile> users;
	static ArrayList<String> userList = new ArrayList<String>();
	static userProfile currentUser;
	private static Canvas canvas;
	private static VBox inventory;
	private static VBox message;
	public static int timeDelay;
	private static long startTime;
	private static long endTime;
	public static int chosenProfile = 0;
	public static int chosenLevel = 0;
	public static boolean baseLevel = true; // Set to true if "Play Base Levels" is selected, and false if otherwise
	public static int mainLevel = 1;

	// The start function, sets the name of the game and then loads profiles or
	// creates a new profile,
	/**
	 * The initial method for the game, ensures a user exists before entering the
	 * main menu
	 * 
	 * @param stage The JavaFX stage for the main menu.
	 */
	public void start(Stage stage) {
		stage.setTitle("Pac Quest");
		loadProfiles();
		mainmenu(stage);
	}

	// The main game screen, starts the timer and sets the message box, save bar and
	// restart buttons and the inventory screen
	/**
	 * The game screen, displays the map of all the tiles and the inventory/buttons
	 * 
	 * @param stage, The JavaFX stage for the game.
	 */
	public static void game(Stage stage) {
		startTime = System.currentTimeMillis();
		try {
			startTime = System.currentTimeMillis();
			// creates the canvas used for all the images of the tiles and places it on the
			// pane along with the help message
			message = new VBox();
			message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT / 2);
			message.setMinSize(CANVAS_WIDTH, GRID_CELL_HEIGHT * 3);
			BorderPane root = new BorderPane();
			root.setStyle("-fx-border-color:blue; " + "-fx-border-width: 7px; " + "-fx-background-color:black");
			canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
			root.setLeft(canvas);
			Font font = new Font("Broadway", 20);
			// creates the savebar at the bottom of the screen and the buttons it uses
			HBox saveBar = new HBox();
			saveBar.setPadding(new Insets(10, 50, 50, 50));
			Button restartButton = new Button("Restart");
			restartButton.setStyle("-fx-border-color:blue; " + "-fx-border-width: 3px; "
					+ "-fx-background-color:black; " + "-fx-text-fill:yellow;");
			restartButton.setFont(font);
			restartButton.setMinSize((WINDOW_HEIGHT - CANVAS_HEIGHT), (WINDOW_HEIGHT - CANVAS_HEIGHT) / 4);
			Button saveButton = new Button("Quick save");
			saveButton.setMinSize((WINDOW_HEIGHT - CANVAS_HEIGHT), (WINDOW_HEIGHT - CANVAS_HEIGHT) / 4);
			saveButton.setStyle("-fx-border-color:blue; " + "-fx-border-width: 3px; " + "-fx-background-color:black; "
					+ "-fx-text-fill:yellow;");
			saveButton.setFont(font);
			Button quitButton = new Button("Quit");
			quitButton.setMinSize((WINDOW_HEIGHT - CANVAS_HEIGHT), (WINDOW_HEIGHT - CANVAS_HEIGHT) / 4);
			quitButton.setStyle("-fx-border-color:blue; " + "-fx-border-width: 3px; " + "-fx-background-color:black; "
					+ "-fx-text-fill:yellow;");
			quitButton.setFont(font);
			saveBar.getChildren().addAll(restartButton, saveButton, quitButton);
			saveBar.setLayoutY((double) WINDOW_HEIGHT - (WINDOW_HEIGHT - CANVAS_HEIGHT));
			restartButton.setOnAction(e -> {
				try {
					restartGame();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			});
			saveButton.setOnAction(e -> {
				try {
					saveGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stage.close();
				mainmenu(stage);
			});
			quitButton.setOnAction(e -> {
				stage.close();
				mainmenu(stage);
			});
			// creates the inventory sidebar
			inventory = new VBox();
			root.getChildren().addAll(message, saveBar, inventory);

			// draws the map
			draw();

			// creates the scene and takes key presses into account
			Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				try {
					processKeyEvent(event, stage);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			});
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads all of the profiles from the save file, reads a user as the current
	 * user.
	 */
	public static void loadProfiles() {
		// Creates an arraylist based on all the userprofiles from the savefile
		users = new ArrayList<userProfile>();
		Scanner input = FileAccess.getFile("savefile.txt");
		input.useDelimiter("\r\n|,");
		while (input.hasNext()) {
			userProfile currentProfile = new userProfile(input.next(), input.nextInt());
			while (input.hasNextInt()) {
				currentProfile.fillTime(input.nextInt());
			}
			users.add(currentProfile);
		}
		input.close();
	}

	/**
	 * Creates a new profile in the list of users and adds it to the save file.
	 * 
	 * @param stage The JavaFX stage for the create profile screen.
	 */
	public void createNewProfile(Stage stage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("fxmlScenes\\CreateProfile.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the main menu of the game.
	 * 
	 * @param stage, The JavaFX stage for the main menu screen.
	 */
	public static void mainmenu(Stage stage) {
		// creates the main menu
		for (userProfile x : users) {
			userList.add(x.getInfo());
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ProfileSelectionController.class.getResource("fxmlScenes\\ProfileSelection.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates the play type menu of the game.
	 * 
	 * @param stage The JavaFX stage for the play type menu.
	 */
	public static void playTypeMenu(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PlayGameTypeController.class.getResource("fxmlScenes\\PlayGameTypeScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a screen that lets you choose from all available levels.
	 * 
	 * @param stage The JavaFX stage for the select a level screen.
	 */
	public static void selectLevel(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(levelSelectionController.class.getResource("fxmlScenes\\LevelSelection.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates select next level screen.
	 * 
	 * @param stage The JavaFX stage for the select next level screen.
	 */
	public static void selectNextLevel(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(levelSelectionController.class.getResource("fxmlScenes\\NextLevelSelector.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates the win screen at end of campaign.
	 * 
	 * @param stage The JavaFX stage for the end of game screen.
	 */
	public static void winScreen(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(levelSelectionController.class.getResource("fxmlScenes\\WinScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a daily message from a given website.
	 * 
	 * @return The String containing the daily message.
	 */
	public static String loadDailyMessage() {
		String message = null;
		try {
			URL url = new URL("http://cswebcat.swan.ac.uk/puzzle");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String puzzle = in.readLine();
				String solvedPuzzle = "";
				for (int x = 0; x < puzzle.length(); x++) {
					char characterAtX = puzzle.charAt(x);
					int ascii = (int) characterAtX;
					if (x % 2 == 0) {
						ascii = ascii + 1;
						if (ascii > (int) 'Z') {
							ascii = ascii - 26;
						}
					} else {
						ascii = ascii - 1;
						if (ascii < (int) 'A') {
							ascii = ascii + 26;
						}
					}
					solvedPuzzle = solvedPuzzle + (char) ascii;
				}
				URL url2 = new URL("http://cswebcat.swan.ac.uk/message?solution=" + solvedPuzzle);
				HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
				con2.setRequestMethod("GET");
				con2.setRequestProperty("User-Agent", "Mozilla/5.0");
				int responseCode2 = con.getResponseCode();
				if (responseCode2 == HttpURLConnection.HTTP_OK) { // success
					in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
					message = in.readLine();
					;
					in.close();
				}
			}
		} catch (UnknownHostException e1) {
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return message;
	}

	// The function that loads the save into a new level
	/**
	 * Creates a new level from saved user data and saves the player's stats.
	 * 
	 * @param selectedIndex The index of the user data to be loaded.
	 * @param stage         The JavaFX stage of the game.
	 */
	static void loadSave(int selectedIndex, Stage stage) {
		if (selectedIndex < 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot load save file");
			alert.setHeaderText(null);
			alert.setContentText("No user is currently selected to load from");
			alert.showAndWait();
		} else {
			currentUser = users.get(selectedIndex);
			File file = new File((users.get(selectedIndex).getUserName()) + "playersave.txt");
			File file2 = new File((users.get(selectedIndex).getUserName()) + "levelsave.csv");
			currentUser = users.get(selectedIndex);
			try {
				level = new Level((users.get(selectedIndex).getUserName()) + "levelsave.csv");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			player = new Player(level);
			try {
				Scanner input = new Scanner(file);
				input.useDelimiter(",");
				String savedLevelName = input.next();
				player.setBlueKeys(input.nextInt());
				player.setFireBoots(input.nextBoolean());
				player.setFlippers(input.nextBoolean());
				player.setGreenKeys(input.nextInt());
				player.setRedKeys(input.nextInt());
				player.setTokens(input.nextInt());
				player.setYellowKeys(input.nextInt());
				timeDelay = input.nextInt();
				input.close();
//				file.delete();
//				file2.delete();
				game(stage);
				level.setLevelName(savedLevelName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * Displays the screen of all the levels the user can select.
	 * 
	 * @param stage The JavaFX stage of the level selection screen.
	 */
	static void userMenu(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ContinueScreenController.class.getResource("fxmlScenes\\ContinueScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the first level editor screen where the user can either create a new
	 * level or edit an existing one
	 * 
	 * @param stage The JavaFX stage of the first level editor screen
	 */
	static void editorOneMenu(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EditorTypeController.class.getResource("fxmlScenes\\EditorTypeScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the second level editor screen where the user can actually edit the
	 * chosen level
	 * 
	 * @param stage The JavaFX stage of the second level editor screen
	 */
	static void editorTwoMenu(Stage stage, int x, int y, boolean newLevel, File levelFile) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EditorScreenController.class.getResource("fxmlScenes\\EditorScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			// Obtain instance of Editor Controller from FXMLLoader, and pass in level x and
			// y into it
			EditorScreenController controller = loader.<EditorScreenController>getController();
			if (newLevel) {
				controller.setLevelDimensions(x, y);
			} else {
				System.out.println("x = " + x + ", y = " + y);
				controller.setLevelDimensions(x, y);
				controller.setExistingLevel(levelFile);
			}
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the high score screen.
	 * 
	 * @param stage The JavaFX stage for the high score screen.
	 */
	static void displayHighScores(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(leaderBoardController.class.getResource("fxmlScenes\\LeaderBoard.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates user profiles after levels have been completed/new profiles are made.
	 */
	static void updateProfiles() {
		try {
			if (users.isEmpty()) {
				Files.write(Paths.get("savefile.txt"), ("").getBytes());
			} else {
				Files.write(Paths.get("savefile.txt"),
						(users.get(0).getUserName() + "," + users.get(0).getMaxLevel() + ",").getBytes());
				for (int x = 0; x < users.get(0).getLevelTimes().size(); x++) {
					int currentTime = users.get(0).getLevelTimes().get(x);
					Files.write(Paths.get("savefile.txt"), (Integer.toString(currentTime) + ",").getBytes(),
							StandardOpenOption.APPEND);
				}
				users.remove(0);
				for (userProfile x : users) {
					Files.write(Paths.get("savefile.txt"), (x.getUserName() + "," + x.getMaxLevel() + ",").getBytes(),
							StandardOpenOption.APPEND);
					for (int y = 0; y < x.getLevelTimes().size(); y++) {
						Files.write(Paths.get("savefile.txt"),
								(Integer.toString(x.getLevelTimes().get(y)) + ",").getBytes(),
								StandardOpenOption.APPEND);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes a quick save file which the game can be continued from at a later
	 * time.
	 */
	static void saveGame() throws IOException {

		endTime = System.currentTimeMillis() - startTime;
		int finalTime = (int) Math.round(endTime / 1000F) + timeDelay;
		message.getChildren().clear();
		Label messageText = new Label("Game has been saved");
		messageText.setMinSize(GRID_CELL_WIDTH * 3, GRID_CELL_HEIGHT);
		message.getChildren().add(messageText);

		ArrayList<String> levelStrings = new ArrayList<String>();

		String addToArray = "";
		for (int y = 0; y < level.getHeight(); y++) {
			for (int x = 0; x < level.getWidth(); x++) {
				addToArray = level.getTiles(x, y).getName();
				for (int i = 0; i < level.getAllEnemies().size(); i++) {
					if (level.getAllEnemies().get(i).getLocationX() == x
							&& level.getAllEnemies().get(i).getLocationY() == y) {
						addToArray = level.getAllEnemies().get(i).getName();
					}
				}
				if (player.getLocationX() == x && player.getLocationY() == y) {
					addToArray = "player";
				}
				levelStrings.add(addToArray);
			}
		}

		// create filewriter object to write to he level file
		FileWriter fwLevel = new FileWriter(currentUser.getUserName() + "levelsave.csv");
		// add the sizes of the map to the file
		fwLevel.append(Integer.toString(level.getWidth()));
		fwLevel.append(",");
		fwLevel.append(Integer.toString(level.getHeight()));
		fwLevel.append("\r");

		// add the contents of the arraylist to the file
		// when count = the width of the map, start a new line
		int count = 0;
		for (String levelString : levelStrings) {
			if (count == level.getWidth() - 1) {
				fwLevel.append(levelString);
				fwLevel.append("\r");
				count = 0;
			} else {
				fwLevel.append(levelString);
				fwLevel.append(",");
				count++;
			}
		}
		fwLevel.close();

		Files.write(Paths.get(currentUser.getUserName() + "playersave.txt"),
				(level.getLevelName() + "," + (player.getBlueKeys()) + "," + (player.isFireBoots()) + ","
						+ (player.isFlippers()) + "," + (player.getGreenKeys()) + "," + (player.getRedKeys()) + ","
						+ (player.getTokens()) + "," + (player.getYellowKeys()) + "," + (finalTime)).getBytes());

	}

	/**
	 * Restarts the current level.
	 */
	static void restartGame() throws FileNotFoundException {
		startTime = System.currentTimeMillis();
		level = new Level(level.getFileName());
		player = new Player(level);
		draw();
	}

	/**
	 * Draws all tiles, enemies and the player onto the canvas.
	 */
	public static void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		// This is the camera controls, ensures that the camera will always be on the
		// player
		int minY = (int) (player.getLocationY() - (Math.floor((CANVAS_HEIGHT / GRID_CELL_HEIGHT) / 2)));
		int maxY = (int) (player.getLocationY() + (Math.floor((CANVAS_HEIGHT / GRID_CELL_HEIGHT) / 2)));
		int minX = (int) (player.getLocationX() - (Math.floor((CANVAS_WIDTH / GRID_CELL_WIDTH) / 2)));
		int maxX = (int) (player.getLocationX() + (Math.floor((CANVAS_WIDTH / GRID_CELL_WIDTH) / 2)));
		if (maxX > level.getWidth()) {
			minX = minX - (maxX - level.getWidth());
			maxX = level.getWidth();
		}
		if (maxY > level.getHeight()) {
			minY = minY - (maxY - level.getHeight());
			maxY = level.getHeight();
		}
		if (minX < 0) {
			maxX = maxX - minX;
			if (maxX > level.getWidth()) {
				maxX = level.getWidth();
			}
			minX = 0;
		}
		if (minY < 0) {
			maxY = maxY - minY;
			if (maxY > level.getHeight()) {
				maxY = level.getHeight();
			}
			minY = 0;
		}
		// draws in all the objects
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				gc.drawImage(level.getTiles()[x][y].getImage(),
						(x - maxX + (CANVAS_WIDTH / GRID_CELL_WIDTH)) * GRID_CELL_WIDTH,
						(y - maxY + (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) * GRID_CELL_HEIGHT);
				for (int i = 0; i < level.getAllEnemies().size(); i++) {
					if (x == level.getAllEnemies().get(i).getLocationX()
							&& y == level.getAllEnemies().get(i).getLocationY()) {
						gc.drawImage(level.getAllEnemies().get(i).getImage(),
								(x - maxX + (CANVAS_WIDTH / GRID_CELL_WIDTH)) * GRID_CELL_WIDTH,
								(y - maxY + (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) * GRID_CELL_HEIGHT);
					}
				}
			}
		}
		gc.drawImage(player.getPlayerImage(),
				(player.getLocationX() - maxX + (CANVAS_WIDTH / GRID_CELL_WIDTH)) * GRID_CELL_WIDTH,
				(player.getLocationY() - maxY + (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) * GRID_CELL_HEIGHT);

		inventory.setLayoutX(WINDOW_WIDTH - (WINDOW_WIDTH - CANVAS_WIDTH));
		inventory.getChildren().clear();
		Font font = new Font("Broadway", 20);
		Label tokens = new Label("Tokens: ".concat(Integer.toString(player.getTokens())));
		tokens.setFont(font);
		tokens.setStyle("-fx-text-fill:yellow;");
		tokens.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label redKeys = new Label("Red keys: ".concat(Integer.toString(player.getRedKeys())));
		redKeys.setFont(font);
		redKeys.setStyle("-fx-text-fill:yellow;");
		redKeys.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label blueKeys = new Label("Blue keys: ".concat(Integer.toString(player.getBlueKeys())));
		blueKeys.setFont(font);
		blueKeys.setStyle("-fx-text-fill:yellow;");
		blueKeys.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label greenKeys = new Label("Green keys: ".concat(Integer.toString(player.getGreenKeys())));
		greenKeys.setFont(font);
		greenKeys.setStyle("-fx-text-fill:yellow;");
		greenKeys.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label yellowKeys = new Label("Yellow Keys: ".concat(Integer.toString(player.getYellowKeys())));
		yellowKeys.setFont(font);
		yellowKeys.setStyle("-fx-text-fill:yellow;");
		yellowKeys.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label fireBoots = new Label("Fire Boots: ".concat(String.valueOf(player.isFireBoots())));
		fireBoots.setFont(font);
		fireBoots.setStyle("-fx-text-fill:yellow;");
		fireBoots.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		Label flippers = new Label("Flippers: ".concat(String.valueOf(player.isFlippers())));
		flippers.setFont(font);
		flippers.setStyle("-fx-text-fill:yellow;");
		flippers.setMinSize((WINDOW_WIDTH - CANVAS_WIDTH), (WINDOW_HEIGHT / INVENTORY_SIZE));
		inventory.getChildren().addAll(tokens, redKeys, blueKeys, greenKeys, yellowKeys, fireBoots, flippers);
		////////////////////////////////////////////////////////////////////////////////////////////////
	}

	/**
	 * Processes events when a key is pressed.
	 * 
	 * @param event The key event that has occurred.
	 * @param stage The JavaFX stage for the game.
	 */
	public static void processKeyEvent(KeyEvent event, Stage stage) throws FileNotFoundException {
		message.getChildren().clear();
		switch (event.getCode()) {
		case DOWN:
			player.moveDown(level);
			while (level.getTiles()[player.getLocationX()][player.getLocationY()] instanceof Ice
					&& level.getTiles()[player.getLocationX()][player.getLocationY() + 1].isPassable()) {
				draw();
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.moveDown(level);
			}
			if (level.getTiles()[player.getLocationX()][player.getLocationY() + 1] instanceof Door) {
				Door door = (Door) level.getTiles()[player.getLocationX()][player.getLocationY() + 1];
				if (door.isLocked()) {
					message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT);
					Label messageText = new Label("The door is locked");
					Font font = new Font("Broadway", 15);
					messageText.setFont(font);
					messageText.setStyle("-fx-text-fill:yellow;");
					messageText.setPadding(new Insets(2, 2, 2, 10));
					messageText.setMinSize(GRID_CELL_WIDTH * 4, GRID_CELL_HEIGHT);
					message.getChildren().add(messageText);
				}
			}
			if (level.getTiles()[player.getLocationX()][player.getLocationY() + 1] instanceof tokenDoor) {
				tokenDoor door2 = (tokenDoor) level.getTiles()[player.getLocationX()][player.getLocationY() + 1];
				draw();
				Label messageText2 = new Label(
						"Requires ".concat(Integer.toString(door2.getAmount()).concat(" Token(s)")));
				Font font = new Font("Broadway", 15);
				messageText2.setFont(font);
				messageText2.setStyle("-fx-text-fill:yellow;");
				messageText2.setPadding(new Insets(2, 2, 2, 10));
				messageText2.setMinSize(CANVAS_WIDTH / 2, 100);
				message.getChildren().add(messageText2);
			}
			break;
		case UP:
			player.moveUp(level);
			while (level.getTiles()[player.getLocationX()][player.getLocationY()] instanceof Ice
					&& level.getTiles()[player.getLocationX()][player.getLocationY() - 1].isPassable()) {
				draw();
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.moveUp(level);
			}
			if (level.getTiles()[player.getLocationX()][player.getLocationY() - 1] instanceof Door) {
				Door door = (Door) level.getTiles()[player.getLocationX()][player.getLocationY() - 1];
				if (door.isLocked()) {
					message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT);
					Label messageText = new Label("The door is locked");
					Font font = new Font("Broadway", 15);
					messageText.setFont(font);
					messageText.setStyle("-fx-text-fill:yellow;");
					messageText.setPadding(new Insets(2, 2, 2, 10));
					messageText.setMinSize(GRID_CELL_WIDTH * 4, GRID_CELL_HEIGHT);
					message.getChildren().add(messageText);
				}
				if (level.getTiles()[player.getLocationX()][player.getLocationY() - 1] instanceof tokenDoor) {
					tokenDoor door2 = (tokenDoor) level.getTiles()[player.getLocationX()][player.getLocationY() + 1];
					Label messageText2 = new Label(
							"Requires ".concat(Integer.toString(door2.getAmount()).concat(" Token(s)")));
					Font font = new Font("Broadway", 15);
					messageText2.setFont(font);
					messageText2.setStyle("-fx-text-fill:yellow;");
					messageText2.setPadding(new Insets(2, 2, 2, 10));
					messageText2.setMinSize(CANVAS_WIDTH / 2, 100);
					message.getChildren().add(messageText2);
				}
			}
			break;
		case RIGHT:
			player.moveRight(level);
			while (level.getTiles()[player.getLocationX()][player.getLocationY()] instanceof Ice
					&& level.getTiles()[player.getLocationX() + 1][player.getLocationY() + 1].isPassable()) {
				draw();
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.moveRight(level);
			}
			if (level.getTiles()[player.getLocationX() + 1][player.getLocationY()] instanceof Door) {
				Door door = (Door) level.getTiles()[player.getLocationX() + 1][player.getLocationY()];
				if (door.isLocked()) {
					message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT);
					Label messageText = new Label("The door is locked");
					messageText.setMinSize(GRID_CELL_WIDTH * 3, GRID_CELL_HEIGHT);
					Font font = new Font("Broadway", 15);
					messageText.setFont(font);
					messageText.setStyle("-fx-text-fill:yellow;");
					messageText.setPadding(new Insets(2, 2, 2, 10));
					messageText.setMinSize(GRID_CELL_WIDTH * 4, GRID_CELL_HEIGHT);
					message.getChildren().add(messageText);
				}
				if (level.getTiles()[player.getLocationX() + 1][player.getLocationY()] instanceof tokenDoor) {
					tokenDoor door2 = (tokenDoor) level.getTiles()[player.getLocationX()][player.getLocationY() + 1];
					Label messageText2 = new Label(
							"Requires ".concat(Integer.toString(door2.getAmount()).concat(" Token(s)")));
					Font font = new Font("Broadway", 15);
					messageText2.setFont(font);
					messageText2.setStyle("-fx-text-fill:yellow;");
					messageText2.setPadding(new Insets(2, 2, 2, 10));
					messageText2.setMinSize(CANVAS_WIDTH / 2, 100);
					message.getChildren().add(messageText2);
				}
			}
			break;
		case LEFT:
			player.moveLeft(level);
			while (level.getTiles()[player.getLocationX()][player.getLocationY()] instanceof Ice
					&& level.getTiles()[player.getLocationX() - 1][player.getLocationY()].isPassable()) {
				draw();
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.moveLeft(level);
			}
			if (level.getTiles()[player.getLocationX() - 1][player.getLocationY()] instanceof Door) {
				Door door = (Door) level.getTiles()[player.getLocationX() - 1][player.getLocationY()];
				if (door.isLocked()) {
					message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT);
					Label messageText = new Label("The door is locked");
					Font font = new Font("Broadway", 15);
					messageText.setFont(font);
					messageText.setStyle("-fx-text-fill:yellow;");
					messageText.setPadding(new Insets(2, 2, 2, 10));
					messageText.setMinSize(GRID_CELL_WIDTH * 4, GRID_CELL_HEIGHT);
					message.getChildren().add(messageText);
				}
			}
			if (level.getTiles()[player.getLocationX() - 1][player.getLocationY()] instanceof tokenDoor) {
				tokenDoor door2 = (tokenDoor) level.getTiles()[player.getLocationX() - 1][player.getLocationY()];
				Label messageText2 = new Label(
						"Requires ".concat(Integer.toString(door2.getAmount()).concat(" Token(s)")));
				Font font = new Font("Broadway", 15);
				messageText2.setFont(font);
				messageText2.setStyle("-fx-text-fill:yellow;");
				messageText2.setPadding(new Insets(2, 2, 2, 10));
				messageText2.setMinSize(CANVAS_WIDTH / 2, 100);
				message.getChildren().add(messageText2);
			}
			break;
		default:
			break;
		}
		if (level.getTiles()[player.getLocationX()][player.getLocationY()] instanceof helpTile) {
			message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT / 2);
			helpTile tile = (helpTile) level.getTiles()[player.getLocationX()][player.getLocationY()];
			Label helpMessage = new Label(tile.getHelpMessage());
			helpMessage.setMinSize(CANVAS_WIDTH / 2, GRID_CELL_HEIGHT * 3);
			helpMessage.setWrapText(true);
			Font font = new Font("Broadway", 15);
			helpMessage.setFont(font);
			helpMessage.setStyle("-fx-text-fill:yellow;");
			helpMessage.setPadding(new Insets(2, 2, 2, 10));
			message.getChildren().add(helpMessage);
		}
		if (player.isDead()) {
			message.setLayoutY(CANVAS_HEIGHT + GRID_CELL_HEIGHT);
			Label messageText = new Label("You died!");
			messageText.setMinSize(GRID_CELL_WIDTH * 2, GRID_CELL_HEIGHT);
			Font font = new Font("Broadway", 15);
			messageText.setFont(font);
			messageText.setStyle("-fx-text-fill:yellow;");
			messageText.setPadding(new Insets(2, 2, 2, 10));
			message.getChildren().add(messageText);
			PlayMusic deathMusic = new PlayMusic("music\\gameover.wav");
			deathMusic.playMusic();
			restartGame();
		}
		if (player.isWon())  {
			endTime = System.currentTimeMillis() - startTime;
			int finalTime = (int) Math.round(endTime / 1000F) + timeDelay;
			if (baseLevel) { // If played level is a base level
				LevelNum.highestScore = false;
				int levelNumber = Integer.parseInt((level.getFileName().substring(5)).substring(0,
						(level.getFileName().substring(5).length() - 4)));
				if (currentUser.getMaxLevel() == levelNumber && currentUser.getMaxLevel() < FINAL_LEVEL_NUMBER) {
					currentUser.setMaxLevel(currentUser.getMaxLevel() + 1);
				}
				if (currentUser.getLevelTimes().size() > levelNumber - 1) {
					if (currentUser.getLevelTimes().get(levelNumber - 1) > finalTime) {
						LevelNum.highestScore = true;
						currentUser.getLevelTimes().set(levelNumber - 1, finalTime);
					}
				} else {
					LevelNum.highestScore = true;
					currentUser.getLevelTimes().add(finalTime);
				}
				int savedIndex = users.indexOf(currentUser);
				updateProfiles();
				loadProfiles();
				currentUser = users.get(savedIndex);
				stage.close();
				if (LevelNum.levelNum > 0 && LevelNum.levelNum < 8) {
					LevelNum.time = finalTime;
					Game.selectNextLevel(stage);
				} else if (LevelNum.levelNum == 8) {
					Game.winScreen(stage);
				} else {
					Game.selectLevel(stage);
				}
			} else {
				stage.close();
				Game.mainmenu(stage);
			}
		}
		draw();
		event.consume();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
