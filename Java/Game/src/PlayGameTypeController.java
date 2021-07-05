import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'Play Game Type' screen, where
 * the user selects which level pack to use.
 * 
 * @author James Fisher, 976729
 * @version 1.0
 */
public class PlayGameTypeController {
	private int currentProfile;

	@FXML
	private Button playBaseButton;

	@FXML
	private Button playUserMadeButton;

	@FXML
	private Button backButton;

	@FXML
	void handlePlayBaseAction(ActionEvent event) {
		Game.baseLevel = true;
		Game.chosenProfile = currentProfile;
		Stage stage = (Stage) playBaseButton.getScene().getWindow();
		Game.currentUser = Game.users.get(currentProfile);
		Game.userMenu(stage);
	}

	@FXML
	void handlePlayUserMadeAction(ActionEvent event) throws FileNotFoundException {
		Game.baseLevel = false;
		Stage stage = (Stage) playUserMadeButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		fileChooser.setInitialDirectory(new File(currentPath));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level Files", "*.csv"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("Open Level");
		if (selectedFile != null) {
			String filePath = selectedFile.getAbsolutePath();
			System.out.println(filePath);
			File levelName = new File(filePath);
			System.out.println(levelName.getName());
			Game.level = new Level(levelName.getName());
			Game.player = new Player(Game.level);
			Game.timeDelay = 0;
			ProfileSelectionController.music.StopMusic();
			ProfileSelectionController.musicOn = false;
			Game.game(stage);

		}
	}

	@FXML
	void handleBackAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		Game.mainmenu(stage);
	}
}
