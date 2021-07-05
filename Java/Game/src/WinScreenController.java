import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WinScreenController {
	/**
	 * the controls the screen displayed at end of campaign.
	 * 
	 * @author Joshua Matambo.
	 */
	@FXML
	private Button back;

	/**
	 * return to start page.
	 * 
	 * @param event
	 */
	@FXML
	void handleBackAction(ActionEvent event) {
		Stage stage = (Stage) back.getScene().getWindow();
		Game.mainmenu(stage);
	}
}
