import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'level selection' screen.
 * 
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class levelSelectionController {

	@FXML
	private Button prevLevelButton;

	@FXML
	private Button nextLevelButton;

	@FXML
	private Label levelNumber;

	@FXML
	private ImageView levelImage;

	@FXML
	private Button playButton;

	@FXML
	private Button leaderBoardButton;

	@FXML
	private Button backButton;

	@FXML
	private AnchorPane AnchorPane;

	private int levelNum = 1;

	/**
	 * Initialises the 'level selection' screen.
	 */
	@FXML
	public void initialize() {
		levelNumber.setText("LEVEL " + levelNum);
		levelImage
				.setImage(new Image(getClass().getResource("levelImages\\Level" + levelNum + ".png").toExternalForm()));
		if (Game.currentUser.getMaxLevel() == levelNum) {
			nextLevelButton.setOpacity(0.5);
			nextLevelButton.setDisable(true);
		} else {
			nextLevelButton.setOpacity(1);
			nextLevelButton.setDisable(false);
		}
		if (levelNum == 1) {
			prevLevelButton.setOpacity(0.5);
			prevLevelButton.setDisable(true);
		} else {
			prevLevelButton.setOpacity(1);
			prevLevelButton.setDisable(false);
		}
	}

	/**
	 * Handles the action of the 'Next Level' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'Next Level' button.
	 */
	@FXML
	void handleNextLevelAction(ActionEvent event) {
		levelNum++;
		initialize();
	}

	/**
	 * Handles the action of the 'Previous Level' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'Previous Level' button.
	 */
	@FXML
	void handlePrevLevelAction(ActionEvent event) {
		levelNum--;
		initialize();
	}

	/**
	 * Handles the action of the 'Play Level' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'Play Level' button.
	 * @throws FileNotFoundException
	 */
	@FXML
	void handlePlayLevelAction(ActionEvent event) throws FileNotFoundException {
		LevelNum.levelNum = levelNum;
		Game.level = new Level("level" + Integer.toString(levelNum) + ".csv");
		Game.player = new Player(Game.level);
		Game.timeDelay = 0;
		Stage stage = (Stage) playButton.getScene().getWindow();
		ProfileSelectionController.music.StopMusic();
		ProfileSelectionController.musicOn = false;
		Game.game(stage);
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

	/**
	 * Handles the action of the 'Leaderboard' button being pressed.
	 * 
	 * @param event The event that has occurred to the 'Leaderboard' button.
	 */
	@FXML
	void handleLeaderboardAction(ActionEvent event) {
		Game.chosenLevel = levelNum - 1;
		Stage stage = (Stage) leaderBoardButton.getScene().getWindow();
		Game.displayHighScores(stage);
	}

}
