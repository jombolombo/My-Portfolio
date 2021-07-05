import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'leaderboard' screen.
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class leaderBoardController {

    @FXML
    private Label leaderBoardLabel;

    @FXML
    private Label player1Score;

    @FXML
    private Label player2Score;

    @FXML
    private Label player3Score;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane AnchorPane;

    /**
     * Initialises the 'leaderboard' screen.
     */
    @FXML
    void initialize() {

    	    	int[] topTimes = new int[Game.users.size()];
    			userProfile[] profiles = new userProfile[Game.users.size()];
    			for (int x = 0; x < Game.users.size();x++) {
    				if (Game.users.get(x).getLevelTimes().size()-1 < Game.chosenLevel) {
    					topTimes[x] = 0;
    				} else {
    					topTimes[x] = Game.users.get(x).getLevelTimes().get(Game.chosenLevel);
    				} profiles[x] = Game.users.get(x);
    			} for (int x = 0; x < Game.users.size()-1;x++) {
    				for (int y =0; y < Game.users.size()-1;y++) {
    					if (topTimes[y] < topTimes[y+1]) {
    						int temp = topTimes[y];
    						userProfile temp2 = profiles[y];
    						topTimes[y] = topTimes[y+1];
    						profiles[y] = profiles[y+1];
    						topTimes[y+1] = temp;
    						profiles[y+1] = temp2;
    					}
    				}
    			}
    		if (profiles.length == 0) {
    			player1Score.setText("No score");
    			player2Score.setText("No score");
    			player3Score.setText("No score");
    		} else if (profiles.length == 1) {
    			player1Score.setText(profiles[0].getUserName()+": "+topTimes[0]+" Seconds");
    			player2Score.setText("No score");
    			player3Score.setText("No score");
    		} else if (profiles.length == 2) {
    			player1Score.setText(profiles[1].getUserName()+": "+topTimes[1]+" Seconds");
    			player2Score.setText(profiles[0].getUserName()+": "+topTimes[0]+" Seconds");
    			player3Score.setText("No score");
    		} else {
    		player1Score.setText(profiles[profiles.length-1].getUserName()+": "+topTimes[profiles.length-1]+" Seconds");
    		player2Score.setText(profiles[profiles.length-2].getUserName()+": "+topTimes[profiles.length-2]+" Seconds");
    		player3Score.setText(profiles[profiles.length-3].getUserName()+": "+topTimes[profiles.length-3]+" Seconds");
    	}
    }

    /**
     * Handles the action of the 'back' button being pressed.
     * @param event The event that has occurred to the 'back' button.
     */
    @FXML
    void handleBackAction(ActionEvent event) {
    	Stage stage = (Stage) backButton.getScene().getWindow();
    	Game.userMenu(stage);
    }

}
