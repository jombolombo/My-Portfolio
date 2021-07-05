import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'continue' screen.
 * 
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class ContinueScreenController {

	@FXML
	private Button continueButton;

	@FXML
	private Button selectLevelButton;

	@FXML
	private Button backButton;

	@FXML
	private AnchorPane AnchorPane;

	/**
	 * Initialises the continue screen.
	 */
	@FXML
	public void initialize() {
		File file = new File((Game.users.get(Game.chosenProfile).getUserName()) + "playersave.txt");
		if (!file.exists()) {
			continueButton.setOpacity(0.5);
			continueButton.setDisable(true);
		} else {
			continueButton.setOpacity(1);
			continueButton.setDisable(false);
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
		Game.playTypeMenu(stage);
	}

	/**
	 * Handles the action of the 'continue' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'continue' button.
	 */
	@FXML
	void handleContinueAction(ActionEvent event) {
		ProfileSelectionController.music.StopMusic();
		ProfileSelectionController.musicOn = false;
		Stage stage = (Stage) continueButton.getScene().getWindow();
		Game.loadSave(Game.chosenProfile, stage);
	}

	/**
	 * Handles the action of the 'select level' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'select level' button.
	 */
	@FXML
	void handleSelectLevelAction(ActionEvent event) {
		Stage stage = (Stage) selectLevelButton.getScene().getWindow();
		Game.selectLevel(stage);
	}

}
