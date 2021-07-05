import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.DragEvent;
import javafx.event.EventHandler;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthUI;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the second editor screen, where the user can actually
 * edit the chosen level
 * 
 * @author James Fisher 976729, Ben Tyler 991226, Joshua Matambo 986390
 * @version 1.0
 */

public class EditorScreenController {

	@FXML
	private Button backButton;

	@FXML
	private Button saveButton;

	@FXML
	private TextField levelName;

	@FXML
	private ScrollPane editorScrollPane;

	@FXML
	private ImageView view1 = new ImageView();
	@FXML
	private ImageView view2 = new ImageView();
	@FXML
	private ImageView view3 = new ImageView();
	@FXML
	private ImageView view4 = new ImageView();
	@FXML
	private ImageView view5 = new ImageView();
	@FXML
	private ImageView view6 = new ImageView();

	@FXML
	private Button previous = new Button();
	@FXML
	private Button next = new Button();

	private int firstIndex = 0;
	private int lastIndex = 5;

	private HashMap<ImageView, String> ImagesInView = new HashMap<ImageView, String>();// keeps what Image is in which
																						// path

	private String[] images = { "bluekey.png", "bluekeydoor.png", "brokefloor.png", "crackedfloor.png", "dumbenemy.png",
			"fire.png", "fireboots.png", "flippers.png", "floor.png", "goal.png", "greenkey.png", "greenkeydoor.png",
			"helptile.png", "ice.png", "lineenemy_right.png", "lineenemy_up.png", "player.png", "redkey.png",
			"redkeydoor.png", "shark.png", "shark_right.png", "smartenemy.png", "teleporter.png", "token.png",
			"tokendoor.png", "wall.png", "wallenemy.png", "water.png", "waterboots.png", "yellowkey.png",
			"yellowkeydoor.png" };

	public final static Image BACKGROUND_DEFAULT = new Image("floor.png");
	public final static Image LEVEL_BOUNDARY = new Image("wall.png");

	private String currentlyDragged; // image that is currently being dragged so it can be added to the 2-D array of
										// image names.

	@FXML
	private AnchorPane editor = new AnchorPane();

	private ArrayList<String> tiles = new ArrayList<String>();

	private ArrayList<String> entities = new ArrayList<String>();

	@FXML
	private ComboBox<String> tileInfo = new ComboBox<String>();
	@FXML
	private ComboBox<String> entityInfo = new ComboBox<String>();

	private int currentRow;
	private int currentColumn;

	private GridPane mapGrid = new GridPane();

	private GridState gridState;

	@FXML
	/**
	 * Creates the initial state of the Editor screen by adding entities to the drag
	 * and drop section and to the drop down lists and adding event listeners to
	 * each entity.
	 */
	private void initialize() {
		// Add tile and entity options to drop-down lists
		Collections.addAll(tiles,
				new String[] { "floor", "wall", "water", "fire", "goal", "flippers", "fireBoots", "token", "tokenDoor",
						"redKey", "redKeyDoor", "blueKey", "blueKeyDoor", "greenKey", "greenKeyDoor", "yellowKey",
						"yellowKeyDoor", "teleporter", "crackedFloor", "brokeFloor", "help" });
		Collections.addAll(entities, new String[] { "----", "player", "enemyDumb", "enemySmart", "enemyShark",
				"enemyWall", "enemyStraightLeft", "enemyStraightRight", "enemyStraightUp", "enemyStraightDown" });
		tileInfo.setItems(FXCollections.observableArrayList(tiles));
		entityInfo.setItems(FXCollections.observableArrayList(entities));

		// Add listeners to drop-down lists
		tileInfo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int rowCount = 0;
				int colCount = 0;
				for (Node node : mapGrid.getChildren()) {
					if (rowCount == currentRow && colCount == currentColumn) {
						Button button = (Button) node;
						TextInputDialog cellInfo = new TextInputDialog();
						cellInfo.setTitle("Additional Cell Info Required");
						cellInfo.setHeaderText(null);
						Alert inputError = new Alert(AlertType.ERROR);
						inputError.setTitle("Additional Cell Info Error");
						inputError.setHeaderText(null);
						switch (newValue) { // Switch statements to set complex cell info for teleporters, token doors
											// and help tiles
						case "teleporter":
							cellInfo.setContentText("Enter the teleporter pairing");
							cellInfo.showAndWait();
							try {
								int pairing = Integer.parseInt(cellInfo.getEditor().getText());
								newValue = "teleporter:" + pairing;
								updateGrid(button, newValue, new Image("teleporter.png"));
							} catch (Exception e) {
								inputError.setContentText("Teleporter pairing must be a number, please try again");
								inputError.showAndWait();
								updateGrid(button, "floor", new Image("floor.png"));
							}
							break;

						case "tokenDoor":
							cellInfo.setContentText("Enter the number of tokens needed to open this door");
							cellInfo.showAndWait();
							try {
								int tokens = Integer.parseInt(cellInfo.getEditor().getText());
								newValue = "door:token:" + tokens;
								updateGrid(button, newValue, new Image("tokendoor.png"));
							} catch (Exception e) {
								inputError.setContentText("Tokens needed must be a number, please try again");
								inputError.showAndWait();
								updateGrid(button, "floor", new Image("floor.png"));
							}
							break;

						case "help":
							cellInfo.setContentText("Enter the help message that will be displayed");
							cellInfo.showAndWait();
							String message = cellInfo.getEditor().getText();
							newValue = "help:" + message;
							updateGrid(button, newValue, new Image("helptile.png"));
							break;

						default: // Nested switch statement to separate complex tiles from basic tiles
							switch (newValue) {
							case "blueKey":
								updateGrid(button, "key:blue", new Image("bluekey.png"));
								break;
							case "blueDoor":
								updateGrid(button, "door:blue", new Image("bluekeydoor.png"));
								break;
							case "greenKey":
								updateGrid(button, "key:green", new Image("greenkey.png"));
								break;
							case "greenDoor":
								updateGrid(button, "door:green", new Image("greenkeydoor.png"));
								break;
							case "redKey":
								updateGrid(button, "key:red", new Image("redkey.png"));
								break;
							case "redDoor":
								updateGrid(button, "door:red", new Image("redkeydoor.png"));
								break;
							case "yellowKey":
								updateGrid(button, "key:yellow", new Image("yellowkey.png"));
								break;
							case "yellowDoor":
								updateGrid(button, "door:yellow", new Image("yellowkeydoor.png"));
								break;
							case "crackedFloor":
								updateGrid(button, "cracked:true", new Image("crackedfloor.png"));
								break;
							case "brokeFloor":
								updateGrid(button, "cracked:false", new Image("brokefloor.png"));
								break;
							case "fireBoots":
								updateGrid(button, "fireBoots", new Image("fireboots.png"));
								break;
							default: // Any tile where the csv name is the same as the image file.png uses the
										// default mapping
								updateGrid(button, newValue, new Image(newValue + ".png"));
								break;
							}
							break;
						}
					}
					colCount++;
					if (colCount == gridState.getWidth()) {
						colCount = 0;
						rowCount++;
					}
				}
			}
		});

		entityInfo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int rowCount = 0;
				int colCount = 0;
				for (Node node : mapGrid.getChildren()) {
					if (rowCount == currentRow && colCount == currentColumn) {
						Button button = (Button) node;
						switch (newValue) {
						case "----":
							updateGrid(button, "floor", new Image("floor.png"));
							break;
						case "enemyStraightUp":
							updateGrid(button, "enemy:straight:u", new Image("lineenemy_up.png"));
							break;
						case "enemyStraightDown":
							updateGrid(button, "enemy:straight:d", new Image("lineenemy_down.png"));
							break;
						case "enemyStraightLeft":
							updateGrid(button, "enemy:straight:l", new Image("lineenemy.png"));
							break;
						case "enemyStraightRight":
							updateGrid(button, "enemy:straight:r", new Image("lineenemy_right.png"));
							break;
						case "enemyWall":
							updateGrid(button, "enemy:wall:a:u", new Image("wallenemy.png"));
							break;
						case "enemyDumb":
							updateGrid(button, "enemy:dumb", new Image("dumbenemy.png"));
							break;
						case "enemySmart":
							updateGrid(button, "enemy:smart", new Image("smartenemy.png"));
							break;
						case "enemyShark":
							updateGrid(button, "enemy:shark", new Image("shark.png"));
							break;
						default:
							updateGrid(button, newValue, new Image(newValue + ".png"));
							break;
						}
					}
					colCount++;
					if (colCount == gridState.getWidth()) {
						colCount = 0;
						rowCount++;
					}
				}
			}
		});

		// this method displays the images when this page is loaded for the first time.
		view1.setImage(new Image(images[0]));
		ImagesInView.put(view1, images[0]);
		view2.setImage(new Image(images[1]));
		ImagesInView.put(view2, images[1]);
		view3.setImage(new Image(images[2]));
		ImagesInView.put(view3, images[2]);
		view4.setImage(new Image(images[3]));
		ImagesInView.put(view4, images[3]);
		view5.setImage(new Image(images[4]));
		ImagesInView.put(view5, images[4]);
		view6.setImage(new Image(images[5]));
		ImagesInView.put(view6, images[5]);
	}

	/**
	 * Handles the action of the 'back' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'back' button.
	 */
	@FXML
	void handleBackAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		Game.editorOneMenu(stage);
	}

	/**
	 * Handles the action of the 'save' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'save' button.
	 * @throws IOException throws an exception if an error occurs during saving
	 */
	@FXML
	void handleSaveAction(ActionEvent event) throws IOException {
		ArrayList<String> invalidNames = new ArrayList<String>();
		Collections.addAll(invalidNames,
				new String[] { "level1", "level2", "level3", "level4", "level5", "level6", "level7", "level8" });
		Alert saveLevelConfirmation = new Alert(AlertType.CONFIRMATION);
		saveLevelConfirmation.setTitle("Save Level Confirmation");
		saveLevelConfirmation.setHeaderText(null);
		saveLevelConfirmation.setContentText("Are you sure you would like to save this level?");
		Optional<ButtonType> result = saveLevelConfirmation.showAndWait();
		if (result.get() == ButtonType.OK) { // Only attempt to save the level if the user selects "Yes"
			Alert saveError = new Alert(AlertType.ERROR);
			System.out.println(levelName.getText());
			if (levelName.getText().isEmpty()) { // Don't save if no level name has been entered
				saveError.setTitle("Save Error");
				saveError.setHeaderText(null);
				saveError.setContentText("There is no level name entered, please enter a name and try again");
				saveError.showAndWait();
			} else if (invalidNames.contains(levelName.getText())) { // Don't save if the user is trying to overwrite
																		// one of the base levels
				saveError.setTitle("Save Error");
				saveError.setHeaderText(null);
				saveError.setContentText("Cannot overwrite a default level, please use a different name");
				saveError.showAndWait();
			} else { // Any other level name should be valid
				String imageName = levelName.getText() + ".png";
				File imageLocation = new File("./src/levelImages/" + imageName);
				imageLocation.mkdirs(); // Create an empty png file at the directory above
				WritableImage mapSnapshot = new WritableImage(500, 500);
				mapGrid.snapshot(new SnapshotParameters(), mapSnapshot); // Take a snapshot of
				// the entire gridpane
				BufferedImage mapImage = null;
				try {
					// Write snapshot to empty image file created above
					ImageIO.write(SwingFXUtils.fromFXImage(mapSnapshot, mapImage), "png", imageLocation);

					int width = gridState.getWidth();
					int height = gridState.getHeight();

					String fileName = levelName.getText() + ".csv";
					File csvLevelFile = new File(fileName);

					try (PrintWriter csvOutput = new PrintWriter(csvLevelFile)) {
						StringBuilder sb = new StringBuilder();
						sb.append(gridState.getWidth() + ",");
						sb.append(gridState.getHeight() + ",");
						sb.append("\n");

						for (int row = 0; row < width; row++) {
							for (int col = 0; col < height; col++) {
								sb.append(gridState.getCellStates()[row][col].getCellName() + ",");
							}
							sb.append("\n");
						}
						System.out.println(sb.toString());
						csvOutput.write(sb.toString());
						Alert saveLevelSuccess = new Alert(AlertType.INFORMATION);
						saveLevelSuccess.setTitle("Save Level Success");
						saveLevelSuccess.setHeaderText(null);
						saveLevelSuccess.setContentText("The level has been successfully saved");
						saveLevelSuccess.showAndWait();
					} catch (Exception e) {

					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@FXML
	/**
	 * When the mouse is dragged, update the image
	 * 
	 * @param event The event to be handled when the mouse is dragged
	 */
	public void DragImage(MouseEvent event) {
		// image variable get the name of the ImageView calling this method.
		String image = (event.getTarget()).toString().substring(13, 18);
		ClipboardContent content = new ClipboardContent();
		Dragboard db;
		if (image.equals("view1")) {
			db = view1.startDragAndDrop(TransferMode.ANY);
			content.putImage(view1.getImage());
			currentlyDragged = ImagesInView.get(view1);
			db.setContent(content);
		} else if (image.equals("view2")) {
			db = view2.startDragAndDrop(TransferMode.ANY);
			content.putImage(view2.getImage());
			currentlyDragged = ImagesInView.get(view2);
			db.setContent(content);
		} else if (image.equals("view3")) {
			db = view3.startDragAndDrop(TransferMode.ANY);
			content.putImage(view3.getImage());
			currentlyDragged = ImagesInView.get(view3);
			db.setContent(content);
		} else if (image.equals("view4")) {
			db = view4.startDragAndDrop(TransferMode.ANY);
			content.putImage(view4.getImage());
			currentlyDragged = ImagesInView.get(view4);
			db.setContent(content);
		} else if (image.equals("view5")) {
			db = view5.startDragAndDrop(TransferMode.ANY);
			content.putImage(view5.getImage());
			currentlyDragged = ImagesInView.get(view5);
			db.setContent(content);
		} else if (image.equals("view6")) {
			db = view6.startDragAndDrop(TransferMode.ANY);
			content.putImage(view6.getImage());
			currentlyDragged = ImagesInView.get(view6);
			db.setContent(content);
		} else {
			System.out.println("error on DragImage method");
		}
		event.consume();
	}

	@FXML
	/**
	 * Gets the next lot of images in the editor if possible
	 * 
	 * @param event The ActionEvent when the Next Button is pressed
	 */
	void nextSetOfImages(ActionEvent event) {
		ImagesInView.replaceAll((k, v) -> null);
		firstIndex = lastIndex + 1;
		int lastPlace = 0;
		if (!(lastIndex + 1 >= images.length)) {
			if ((lastIndex + 1) < images.length && images[lastIndex + 1] != null) {
				view1.setImage(new Image(images[lastIndex + 1]));
				ImagesInView.put(view1, images[lastIndex + 1]);
			} else {
				view1.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 1;
				}

			}
			if ((lastIndex + 2) < images.length && images[lastIndex + 2] != null) {
				view2.setImage(new Image(images[lastIndex + 2]));
				ImagesInView.put(view2, images[lastIndex + 2]);
			} else {
				view2.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 2;
				}
			}
			if ((lastIndex + 3) < images.length && images[lastIndex + 3] != null) {
				view3.setImage(new Image(images[lastIndex + 3]));
				ImagesInView.put(view3, images[lastIndex + 3]);
			} else {
				view3.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 3;
				}
			}
			if ((lastIndex + 4) < images.length && images[lastIndex + 4] != null) {
				view4.setImage(new Image(images[lastIndex + 4]));
				ImagesInView.put(view4, images[lastIndex + 4]);
			} else {
				view4.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 4;
				}
			}
			if ((lastIndex + 5) < images.length && images[lastIndex + 5] != null) {
				view5.setImage(new Image(images[lastIndex + 5]));
				ImagesInView.put(view5, images[lastIndex + 5]);
			} else {
				view5.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 5;
				}
			}
			if ((lastIndex + 6) < images.length && images[lastIndex + 6] != null) {
				view6.setImage(new Image(images[lastIndex + 6]));
				ImagesInView.put(view6, images[lastIndex + 6]);
			} else {
				view6.setImage(null);
				if (lastPlace == 0) {
					lastPlace = 6;
				}
			}
		}
		if (lastPlace == 0) {
			lastIndex = lastIndex + 6;
			if (lastPlace >= images.length) {
				lastIndex = lastIndex - 6;
			}
		} else {
			lastIndex = lastIndex + lastPlace;
			if (lastIndex >= images.length) {
				lastIndex = images.length - 1;
			}
		}
	}

	/**
	 * when back button is pressed, loads the previous images if possible.
	 * 
	 * @param event
	 */
	@FXML
	void previousSetOfImages(ActionEvent event) {
		ImagesInView.replaceAll((k, v) -> null);
		if (firstIndex >= images.length) {
			firstIndex = images.length - 1;
		}
		lastIndex = firstIndex - 1;
		if (lastIndex != -1) {
			if ((firstIndex - 6) >= 0 && images[firstIndex - 6] != null) {
				view1.setImage(new Image(images[firstIndex - 6]));

				ImagesInView.put(view1, images[firstIndex - 6]);
			} else {
				view1.setImage(null);
			}
			if ((firstIndex - 5) >= 0 && images[firstIndex - 5] != null) {
				view2.setImage(new Image(images[firstIndex - 5]));
				ImagesInView.put(view2, images[firstIndex - 5]);
			} else {
				view2.setImage(null);
			}
			if ((firstIndex - 4) >= 0 && images[firstIndex - 4] != null) {
				view3.setImage(new Image(images[firstIndex - 4]));
				ImagesInView.put(view3, images[firstIndex - 4]);
			} else {
				view3.setImage(null);
			}
			if ((firstIndex - 3) >= 0 && images[firstIndex - 3] != null) {
				view4.setImage(new Image(images[firstIndex - 3]));
				ImagesInView.put(view4, images[firstIndex - 3]);
			} else {
				view4.setImage(null);
			}
			if ((firstIndex - 2) >= 0 && images[firstIndex - 2] != null) {
				view5.setImage(new Image(images[firstIndex - 2]));
				ImagesInView.put(view5, images[firstIndex - 2]);
			} else {
				view5.setImage(null);
			}
			if ((firstIndex - 1) >= 0 && images[firstIndex - 1] != null) {
				view6.setImage(new Image(images[firstIndex - 1]));
				ImagesInView.put(view6, images[firstIndex - 1]);
			} else {
				view6.setImage(null);
			}
		}

		firstIndex = firstIndex - 6;
		if (firstIndex < 0) {
			firstIndex = firstIndex + 6;
		}

	}

	/**
	 * This method is called in Game.java to generate a dynamic editor grid with the
	 * user specified size
	 * 
	 * @param x The x-dimension/width of the level.
	 * @param y The y-dimension/height of the level.
	 */
	public void setLevelDimensions(int x, int y) {
		this.gridState = new GridState(x, y);
		System.out.println("GridState made");
		for (int row = 0; row < y; row += 1) {
			for (int col = 0; col < x; col += 1) {
				final int R = row;
				final int C = col;
				ImageView emptyImage = new ImageView(BACKGROUND_DEFAULT);
				if (row == 0 || row == y - 1) {
					emptyImage.setImage(LEVEL_BOUNDARY);
					gridState.getCellStates()[col][row].setCellName("wall");
				} else if (col == 0 || col == x - 1) {
					emptyImage.setImage(LEVEL_BOUNDARY);
					gridState.getCellStates()[col][row].setCellName("wall");
				}
				emptyImage.setFitWidth(500 / x);
				emptyImage.setFitHeight((500 / y));

				Button button = new Button("", emptyImage);
				button.setPadding(new Insets(1, 1, 1, 1));

				mapGrid.add(button, col, row, 1, 1);

				button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(new DropShadow(6, Color.BLUE)));
				button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
				button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					System.out.println(R + ", " + C);
					currentRow = R;
					currentColumn = C;
					String cellName = gridState.getCellStates()[R][C].getCellName();
					if (tiles.contains(cellName)) {
						tileInfo.setValue(cellName);
					} else if (entities.contains(cellName)) {
						entityInfo.setValue(cellName);
					}

				});
				button.addEventHandler(DragEvent.DRAG_OVER, e -> e.acceptTransferModes(TransferMode.ANY));
				button.addEventHandler(DragEvent.DRAG_DROPPED, e -> {
					System.out.println(R + ", " + C);
					currentRow = R;
					currentColumn = C;
					Image backgroundImage = new Image(currentlyDragged);
					String cellName = currentlyDragged.substring(0, currentlyDragged.length() - 4);
					TextInputDialog cellInfo = new TextInputDialog();
					cellInfo.setTitle("Additional Cell Info Required");
					cellInfo.setHeaderText(null);
					Alert inputError = new Alert(AlertType.ERROR);
					inputError.setTitle("Additional Cell Info Error");
					inputError.setHeaderText(null);
					System.out.println(cellName);
					switch (cellName) { // Switch statements to set complex cell info for teleporters, token doors and
										// help tiles
					case "teleporter":
						cellInfo.setContentText("Enter the teleporter pairing");
						cellInfo.showAndWait();
						try {
							int pairing = Integer.parseInt(cellInfo.getEditor().getText());
							cellName = "teleporter:" + pairing;
							updateGrid(button, cellName, new Image("teleporter.png"));
						} catch (Exception e1) {
							inputError.setContentText("Teleporter pairing must be a number, please try again");
							inputError.showAndWait();
							updateGrid(button, "floor", new Image("floor.png"));
						}
						break;

					case "tokendoor":
						cellInfo.setContentText("Enter the number of tokens needed to open this door");
						cellInfo.showAndWait();
						try {
							int tokens = Integer.parseInt(cellInfo.getEditor().getText());
							cellName = "door:token:" + tokens;
							updateGrid(button, cellName, new Image("tokendoor.png"));
						} catch (Exception e1) {
							inputError.setContentText("Tokens needed must be a number, please try again");
							inputError.showAndWait();
							updateGrid(button, "floor", new Image("floor.png"));
						}
						break;

					case "helptile":
						cellInfo.setContentText("Enter the help message that will be displayed");
						cellInfo.showAndWait();
						String message = cellInfo.getEditor().getText();
						cellName = "help:" + message;
						updateGrid(button, cellName, new Image("helptile.png"));
						break;

					default: // Nested switch statement to separate complex tiles from basic tiles
						switch (cellName) {
						case "bluekey":
							updateGrid(button, "key:blue", new Image("bluekey.png"));
							break;
						case "bluekeydoor":
							updateGrid(button, "door:blue", new Image("bluekeydoor.png"));
							break;
						case "greenkey":
							updateGrid(button, "key:green", new Image("greenkey.png"));
							break;
						case "greenkeydoor":
							updateGrid(button, "door:green", new Image("greenkeydoor.png"));
							break;
						case "redkey":
							updateGrid(button, "key:red", new Image("redkey.png"));
							break;
						case "redkeydoor":
							updateGrid(button, "door:red", new Image("redkeydoor.png"));
							break;
						case "yellowkey":
							updateGrid(button, "key:yellow", new Image("yellowkey.png"));
							break;
						case "yellowkeydoor":
							updateGrid(button, "door:yellow", new Image("yellowkeydoor.png"));
							break;
						case "crackedfloor":
							updateGrid(button, "cracked:true", new Image("crackedfloor.png"));
							break;
						case "brokefloor":
							updateGrid(button, "cracked:false", new Image("brokefloor.png"));
							break;
						case "fireboots":
							updateGrid(button, "fireBoots", new Image("fireboots.png"));
							break;
						case "lineenemy_up":
							updateGrid(button, "enemy:straight:u", new Image("lineenemy_up.png"));
							break;
						case "lineenemy_down":
							updateGrid(button, "enemy:straight:d", new Image("lineenemy_down.png"));
							break;
						case "lineenemy": // Left
							updateGrid(button, "enemy:straight:l", new Image("lineenemy.png"));
							break;
						case "lineenemy_right":
							updateGrid(button, "enemy:straight:r", new Image("lineenemy_right.png"));
							break;
						case "wallenemy":
							updateGrid(button, "enemy:wall:a:u", new Image("wallenemy.png"));
							break;
						case "dumbenemy":
							updateGrid(button, "enemy:dumb", new Image("dumbenemy.png"));
							break;
						case "smartenemy":
							updateGrid(button, "enemy:smart", new Image("smartenemy.png"));
							break;
						case "shark":
							updateGrid(button, "enemy:shark", new Image("shark.png"));
							break;
						default: // Any tile where the csv name is the same as the image file.png uses the
									// default mapping
							updateGrid(button, cellName, new Image(cellName + ".png"));
							break;
						}
						break;
					}
				});
			}
		}
		editorScrollPane.setContent(mapGrid);
	}

	/**
	 * Method to update grid to existing level.
	 * 
	 * @param levelFile The csv file that contains the existing level data.
	 * @throws FileNotFoundException throws an exception if no file exists
	 */
	public void setExistingLevel(File levelFile) throws FileNotFoundException {
		ArrayList<String> tileArray = new ArrayList<String>();
		Scanner in = new Scanner(levelFile);
		in.useDelimiter(",|\r");

		while (in.hasNext()) {
			tileArray.add(in.next().replace("\n", ""));
		}
		in.close();
		System.out.println(tileArray.toString());

		int width = Integer.parseInt(tileArray.get(0)); // Store the level width to compare the row count below with
		tileArray.remove(0);
		tileArray.remove(0); // Remove width and height from array so they aren't read as tiles

		// Remove any remaining blank spaces so only tiles are read
		while (tileArray.get(0).isEmpty()) {
			tileArray.remove(0);
			System.out.println(tileArray.toString());
		}

		int tileCount = 0;
		int rowCount = 0;
		int colCount = 0;
		for (Node node : mapGrid.getChildren()) {
			currentRow = rowCount;
			currentColumn = colCount;
			Button button = (Button) node;
			String cellName = tileArray.get(tileCount);
			switch (cellName) {
			case "floor":
				updateGrid(button, "floor", new Image("floor.png"));
				break;
			case "wall":
				updateGrid(button, "wall", new Image("wall.png"));
				break;
			case "water":
				updateGrid(button, "water", new Image("water.png"));
				break;
			case "fire":
				updateGrid(button, "fire", new Image("fire.png"));
				break;
			case "goal":
				updateGrid(button, "goal", new Image("goal.png"));
				break;
			case "player":
				updateGrid(button, "player", new Image("player.png"));
				break;
			case "token":
				updateGrid(button, "token", new Image("token.png"));
				break;
			case "flippers":
				updateGrid(button, "flippers", new Image("flippers.png"));
				break;
			case "fireBoots":
				updateGrid(button, "fireBoots", new Image("fireboots.png"));
				break;
			default:
				// Create pattern matchers for more complex tiles
				Pattern helpPattern = Pattern.compile("^help:[a-zA-Z|\" \"|.|,|0-9]+$");
				Matcher helpMatcher = helpPattern.matcher(cellName);

				Pattern crackedFloorPattern = Pattern.compile("^cracked:{1}(true|false)$");
				Matcher crackedFloorMatcher = crackedFloorPattern.matcher(cellName);

				Pattern teleporterPattern = Pattern.compile("^teleporter:[0-9]+$");
				Matcher teleporterMatcher = teleporterPattern.matcher(cellName);

				Pattern doorPattern = Pattern.compile("^door:(red|blue|green|yellow|token:[1-9])$");
				Matcher doorMatcher = doorPattern.matcher(cellName);

				Pattern keyPattern = Pattern.compile("^key:(red|blue|green|yellow)$");
				Matcher keyMatcher = keyPattern.matcher(cellName);

				Pattern enemyPattern = Pattern
						.compile("^enemy:((smart|dumb)|(wall:[ac]:[udlr]|straight:[udlr])|shark)$");
				Matcher enemyMatcher = enemyPattern.matcher(cellName);

				if (helpMatcher.matches()) {
					updateGrid(button, cellName, new Image("helptile.png"));

				} else if (crackedFloorMatcher.matches()) {
					String[] parts = cellName.split(":");
					boolean cracked = Boolean.parseBoolean(parts[1]);
					if (cracked) {
						updateGrid(button, cellName, new Image("crackedfloor.png"));
					} else {
						updateGrid(button, cellName, new Image("brokefloor.png"));
					}
				} else if (teleporterMatcher.matches()) {
					updateGrid(button, cellName, new Image("teleporter.png"));
				} else if (doorMatcher.matches()) {
					String[] parts = cellName.split(":");
					if (parts[1].equals("token")) {
						updateGrid(button, cellName, new Image("tokendoor.png"));
					} else {
						switch (parts[1]) {
						case "red":
							updateGrid(button, cellName, new Image("redkeydoor.png"));
							break;
						case "blue":
							updateGrid(button, cellName, new Image("bluekeydoor.png"));
							break;
						case "green":
							updateGrid(button, cellName, new Image("greenkeydoor.png"));
							break;
						case "yellow":
							updateGrid(button, cellName, new Image("yellowkeydoor.png"));
							break;
						default:
							break;
						}
					}
				} else if (keyMatcher.matches()) {
					String[] parts = cellName.split(":");
					switch (parts[1]) {
					case "red":
						updateGrid(button, cellName, new Image("redkey.png"));
						break;
					case "blue":
						updateGrid(button, cellName, new Image("bluekey.png"));
						break;
					case "green":
						updateGrid(button, cellName, new Image("greenkey.png"));
						break;
					case "yellow":
						updateGrid(button, cellName, new Image("yellowkey.png"));
						break;
					default:
						break;
					}
				} else if (enemyMatcher.matches()) {
					String[] parts = cellName.split(":");
					switch (parts[1]) {
					case "straight":
						char direction = parts[2].charAt(0);
						if (direction == 'u') {
							updateGrid(button, cellName, new Image("lineenemy_up.png"));
						} else if (direction == 'd') {
							updateGrid(button, cellName, new Image("lineenemy_down.png"));
						} else if (direction == 'l') {
							updateGrid(button, cellName, new Image("lineenemy.png"));
						} else if (direction == 'r') {
							updateGrid(button, cellName, new Image("lineenemy_right.png"));
						}
						break;
					case "wall":
						updateGrid(button, cellName, new Image("wallenemy.png"));
						break;
					case "dumb":
						updateGrid(button, cellName, new Image("dumbenemy.png"));
						break;
					case "smart":
						updateGrid(button, cellName, new Image("smartenemy.png"));
						break;
					case "shark":
						updateGrid(button, cellName, new Image("shark.png"));
						break;
					default:
						break;
					}
				}
				break;
			}
			tileCount++;

			colCount++;
			if (colCount == width) {
				colCount = 0;
				rowCount++;
			}
		}
	}

	/**
	 * Method to update grid when image is drag-and-dropped/when the drop-down list
	 * updates.
	 * 
	 * @param button    The button node that is being updated in the grid.
	 * @param cellName  The name of the tile being stored in a format that the csv
	 *                  reader can read.
	 * @param cellImage The name of the image file used to represent the tile.
	 */
	public void updateGrid(Button button, String cellName, Image cellImage) {
		System.out.println("Now updating");
		ImageView newBackground = new ImageView(cellImage);
		newBackground.setFitWidth(500 / gridState.getWidth());
		newBackground.setFitHeight((500 / gridState.getWidth()));
		button.setGraphic(null); // Remove any existing graphic to prevent image overlap
		button.setGraphic(newBackground);
		gridState.setCell(cellImage, currentColumn, currentRow);
		gridState.getCellStates()[currentColumn][currentRow].setCellName(cellName);
	}

}
