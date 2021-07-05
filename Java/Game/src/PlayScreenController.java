import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

/**
 * This class represents the controller for the 'Play' screen.
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class PlayScreenController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private BorderPane BorderPane;

    @FXML
    private Button restartButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button quitButton;

    /**
     * Handles the action of the 'Quit' button being pressed.
     * @param event The event that has occurred to the 'Quit' button.
     */
    @FXML
    void handleQuitAction(ActionEvent event) {
    	Stage stage = (Stage) quitButton.getScene().getWindow();
    	stage.close();
		Game.mainmenu(stage);
    }

    /**
     * Handles the action of the 'Restart' button being pressed.
     * @param event The event that has occurred to the 'Restart' button.
     * @throws FileNotFoundException 
     */
    @FXML
    void handleRestartAction(ActionEvent event) throws FileNotFoundException {
    	Game.restartGame();
    }

    /**
     * Handles the action of the 'Save' button being pressed.
     * @param event The event that has occurred to the 'Save' button.
     */
    @FXML
    void handleSaveAction(ActionEvent event) throws IOException {
    	Game.saveGame();
    	Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
		Game.mainmenu(stage);
    }

}
