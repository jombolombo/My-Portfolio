import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'create profile' screen.
 * 
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class CreateProfileController {

	@FXML
	private AnchorPane AnchorPane;

	@FXML
	private TextField nameBox;

	@FXML
	private Button confirmButton;

	@FXML
	private Button cancelButton;

	/**
	 * Initialises the create profile screen.
	 */
	public void initialize() {
	}

	/**
	 * Handles the action of the 'cancel' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'cancel' button.
	 */
	@FXML
	void handleCancelAction(ActionEvent event) {
		Stage stage = (Stage) AnchorPane.getScene().getWindow();
		stage.close();
	}

	/**
	 * Handles the action of the 'confirm' button being pressed and checks whether
	 * inputed data is valid.
	 * 
	 * @param event The event that has occurred to the 'confirm' button.
	 */
	@FXML
	void handleConfirmAction(ActionEvent event) {
		boolean flag = false;
		String inputtedName = nameBox.getText().replace(',', ' ');
		try {
			@SuppressWarnings("unused")
			int testing = Integer.parseInt(inputtedName);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot create user with this name");
			alert.setHeaderText(null);
			alert.setContentText("Cannot make a user with no letters in the name");
			alert.showAndWait();
			flag = true;
		} catch (NumberFormatException | NullPointerException nfe) {

		}
		for (int x = 0; x < Game.users.size(); x++) {
			if (inputtedName.equals(Game.users.get(x).getUserName())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error: Cannot create user with this name");
				alert.setHeaderText(null);
				alert.setContentText("Cannot make a user with the same name as another user");
				alert.showAndWait();
				flag = true;
			}
		}
		if (inputtedName.length() > 10) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot create user with this name");
			alert.setHeaderText(null);
			alert.setContentText("Cannot make a user with more then 10 characters");
			alert.showAndWait();
			flag = true;
		}
		if (inputtedName.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Cannot create user with no name");
			alert.setHeaderText(null);
			alert.setContentText("Cannot make a user with less then one character");
			alert.showAndWait();
		} else if (!flag) {
			try {
				File file = new File("savefile.txt");
				if (file.exists()) {
					Files.write(Paths.get("savefile.txt"), (inputtedName + ",1,").getBytes(),
							StandardOpenOption.APPEND);
				} else {
					Files.write(Paths.get("savefile.txt"), (inputtedName + ",1,").getBytes());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		Game.loadProfiles();
		Stage stage = (Stage) AnchorPane.getScene().getWindow();
		stage.close();

	}

}
