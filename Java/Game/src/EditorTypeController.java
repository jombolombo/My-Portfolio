import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This class represents the first editor screen, where the user chooses between
 * creating a new level or editing an existing one
 * 
 * @author James Fisher, 976729
 * @version 1.0
 */

public class EditorTypeController {

	@FXML
	private TextField createNewX;
	@FXML
	private TextField createNewY;
	@FXML
	private Button createNewButton;
	@FXML
	private Button editExistingButton;
	@FXML
	private Button backButton;

	/**
	 * Handles the event that 'Create New Level' has been selected.
	 * 
	 * @param event The event that has occurred to the 'Create New Level' button.
	 */
	@FXML
	void handleCreateNewAction(ActionEvent event) {
		int x, y;
		Alert createLevelError = new Alert(AlertType.ERROR);
		createLevelError.setTitle("Create New Level Error");
		createLevelError.setHeaderText(null);
		try {
			// Try to obtain integers from the x and y input boxes
			// If they are not integers, then do not create the new level
			x = Integer.parseInt(createNewX.getText());
			y = Integer.parseInt(createNewY.getText());
			// Assuming the requested dimensions are suitable, create the level and open the
			// editor screen
			if (x > 1 && x <= 30 && y > 1 && y <= 30) {
				Stage stage = (Stage) createNewButton.getScene().getWindow();
				Game.editorTwoMenu(stage, x, y, true, null);
			} else {
				createLevelError.setContentText(
						"X and/or Y values are not between 2 and 30, please choose a suitable level size.");
				createLevelError.show();
			}
		} catch (Exception e) {
			createLevelError
					.setContentText("X and/or Y values are either empty or are not integers, please try again.");
			createLevelError.show();
		}
	}

	/**
	 * Handles the event that 'Edit Existing Level' has been selected.
	 * 
	 * @param event The event that has occurred to the 'Edit Existing Level' Button.
	 * @throws FileNotFoundException throws an exception if no file exists
	 */
	@FXML
	void handleEditExistingAction(ActionEvent event) throws FileNotFoundException {
		Stage stage = (Stage) editExistingButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("Open Level");
		if (selectedFile != null) {
			String filePath = selectedFile.getAbsolutePath();
			System.out.println(filePath);
			Scanner in = new Scanner(selectedFile);
			in.useDelimiter(",|\r");
			int x = Integer.parseInt(in.next());
			int y = Integer.parseInt(in.next());
			in.close();
			System.out.println(x + "x" + y);
			Game.editorTwoMenu(stage, x, y, false, selectedFile);

		}
	}

	/**
	 * Handles the deletion of files when the "Delete button" is clicked.
	 * 
	 * @param event The event that triggers when the delete button is clicked.
	 */
	@FXML
	void handleDeletionAction(ActionEvent event) {
		ArrayList<String> invalidNames = new ArrayList<String>();
		Collections.addAll(invalidNames, new String[] { "level1.csv", "level2.csv", "level3.csv", "level4.csv",
				"level5.csv", "level6.csv", "level7.csv", "level8.csv" });

		Stage stage = (Stage) editExistingButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("Open Level");
		if (selectedFile != null) {
			if (invalidNames.contains(selectedFile.getName())) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Delete Base Level Error");
				errorAlert.setHeaderText(null);
				errorAlert.setContentText(
						"This is one of the base levels, and so it cannot be deleted, please try again");
				errorAlert.showAndWait();
			} else {
				Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
				confirmAlert.setTitle("Delete Level Confirmation");
				confirmAlert.setHeaderText("Warning!");
				confirmAlert.setContentText("Are you sure you want to delete this level?");
				System.out.println(selectedFile.getName());
				Optional<ButtonType> choice = confirmAlert.showAndWait();
				if (choice.get() == ButtonType.OK) {
					selectedFile.delete();
				}
			}
		}
	}

	/**
	 * Handles the action of the 'back' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'back' button.
	 */
	@FXML
	void handleBackAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		Game.mainmenu(stage);
	}
}
