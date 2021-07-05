import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class NextLevelSelectorController {

	/**
	 * class that gives user the option to move to the next level, retry or quit.
	 * 
	 * @author Joshua Matambo
	 */
	@FXML
	private ImageView levelImage;

	@FXML
	private Button next;

	@FXML
	private Button retry;

	@FXML
	private Button exit;
	
	 @FXML
	 private Label score = new Label();

	@FXML
	public void initialize() {
		LevelNum.levelNum++;
		levelImage.setImage(
				new Image(getClass().getResource("levelImages\\Level" + LevelNum.levelNum + ".png").toExternalForm()));
		score.setTextFill(javafx.scene.paint.Paint.valueOf("#eee718"));
		if (LevelNum.highestScore) {
			score.setText("New Fastest Time: " + LevelNum.time); 
		}else {
			score.setText("Your Time is: " + LevelNum.time); 
		}
		
	}

	/**
	 * loads and plays the next level.
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	@FXML
	void handlePlayLevelAction(ActionEvent event) throws FileNotFoundException {
		Game.level = new Level("level" + Integer.toString(LevelNum.levelNum) + ".csv");
		Game.player = new Player(Game.level);
		Game.timeDelay = 0;
		Stage stage = (Stage) next.getScene().getWindow();
		Game.game(stage);
	}

	/**
	 * lets player reply level.
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	@FXML
	void handleRetryLevelAction(ActionEvent event) throws FileNotFoundException {
		LevelNum.levelNum--;
		Game.level = new Level("level" + Integer.toString(LevelNum.levelNum) + ".csv");
		Game.player = new Player(Game.level);
		Game.timeDelay = 0;
		Stage stage = (Stage) retry.getScene().getWindow();
		Game.game(stage);
	}

	/**
	 * returns user to start page.
	 * 
	 * @param event
	 */
	@FXML
	void handleBackAction(ActionEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		Game.mainmenu(stage);
	}
	
}
