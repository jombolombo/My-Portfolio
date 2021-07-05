import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class represents the controller for the 'Profile Selection' screen.
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class ProfileSelectionController {

	@FXML
    private Label currentLevel;

    @FXML
    private Label UserName;

    @FXML
    private Label dailyMessage;

    @FXML
    private Button prevProfileButton;

    @FXML
    private Label currentScore;

    @FXML
    private Button nextProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button deleteProfileButton;

    @FXML
    private Button playButton;
    @FXML
    private Button editorButton;
    
    @FXML
    private Button newProfileButton;

    private int currentProfile = 0;
    private Boolean createProfile = false;
    //
    public static PlayMusic music = new PlayMusic("music\\Roof.wav");
    public static boolean musicOn = false; 
    //
    /**
     * Initialises the 'Profile Selection' screen.
     */
    @FXML
	 public void initialize() {
    	if(musicOn == false) {
    		music.playMusicLoop();
    	}
    	musicOn = true;
    	if (Game.users.isEmpty() || createProfile) {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlScenes\\CreateProfile.fxml"));

			// Run the loader
			AnchorPane editRoot;
			try {
				editRoot = (AnchorPane)fxmlLoader.load();

			// Create a scene based on the loaded FXML scene graph
			Scene editScene = new Scene(editRoot);

			// Create a new stage (i.e., window) based on the edit scene
			Stage editStage = new Stage();
			editStage.setScene(editScene);
			editStage.setTitle("Create Profile");

			// Make the stage a modal window.
			// This means that it must be closed before you can interact with any other window from this application.
			editStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit scene and wait for it to be closed
			editStage.showAndWait();
//    		Stage stage = new Stage();
//    		Game.createNewProfile(stage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createProfile = false;
    	}
    	if (currentProfile == Game.users.size() - 1) {
    		nextProfileButton.setOpacity(0.5);
    		nextProfileButton.setDisable(true);
    	} else {
    		nextProfileButton.setOpacity(1);
    		nextProfileButton.setDisable(false);
    	}
    	if (currentProfile == 0) {
    		prevProfileButton.setOpacity(0.5);
    		prevProfileButton.setDisable(true);
    	} else {
    		prevProfileButton.setOpacity(1);
    		prevProfileButton.setDisable(false);
    	}
    	dailyMessage.setText(Game.loadDailyMessage());
    	UserName.setText(Game.users.get(currentProfile).getUserName());
    	currentScore.setText("CURRENT SCORE: " + Integer.toString(Game.users.get(currentProfile).getCurrentScore()));
    	currentLevel.setText(Game.users.get(currentProfile).getInfo());
	}

    public void createFirstProfile() {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlScenes\\CreateProfile.fxml"));

			// Run the loader
			AnchorPane editRoot;
			try {
				editRoot = (AnchorPane)fxmlLoader.load();

			// Create a scene based on the loaded FXML scene graph
			Scene editScene = new Scene(editRoot);

			// Create a new stage (i.e., window) based on the edit scene
			Stage editStage = new Stage();
			editStage.setScene(editScene);
			editStage.setTitle("To delete the last profile, you must create a new one");

			// Make the stage a modal window.
			// This means that it must be closed before you can interact with any other window from this application.
			editStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit scene and wait for it to be closed
			editStage.showAndWait();
//    		Stage stage = new Stage();
//    		Game.createNewProfile(stage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	dailyMessage.setText(Game.loadDailyMessage());
    	UserName.setText(Game.users.get(currentProfile).getUserName());
    	currentScore.setText("CURRENT SCORE: " + Integer.toString(Game.users.get(currentProfile).getCurrentScore()));
    	currentLevel.setText(Game.users.get(currentProfile).getInfo());
	}

    /**
     * Handles the action of the 'Delete Profile' button being pressed.
     * @param event The event that has occurred to the 'Delete Profile' button.
     */
    @FXML
    void handleDeleteProfileAction(ActionEvent event) {
    	Alert profileDeleteConfirmation = new Alert(AlertType.CONFIRMATION);
    	profileDeleteConfirmation.setTitle("Delete Confirmation");
    	profileDeleteConfirmation.setHeaderText(null);
    	profileDeleteConfirmation.setContentText("Are you sure you would like to delete this profile?");
    	Optional<ButtonType> result = profileDeleteConfirmation.showAndWait();
    	if (result.get() == ButtonType.OK) {
	    	if (Game.users.size() == 1) {
	        	Game.users.remove(0);
	        	Game.updateProfiles();
	        	createFirstProfile();
	        	Game.loadProfiles();
	    	} else {
	    		Game.users.remove(currentProfile);
	    		Game.updateProfiles();
	    		Game.loadProfiles();
	    		currentProfile = 0;
	    		
	    		initialize();
	    	}
    	}
    }

    /**
     * Handles the action of the 'Edit Profile' button being pressed.
     * @param event The event that has occurred to the 'Edit Profile' button.
     */
    @FXML
    void handleEditProfileAction(ActionEvent event) {
    	int sizeChanged = Game.users.size();
    	createProfile = true;
    	initialize();
    	if ((sizeChanged + 1) == Game.users.size()) {
    		Game.users.remove(currentProfile);
    		Game.updateProfiles();
    		Game.loadProfiles();
    		currentProfile = 0;
    		initialize();
    	}
    }

    /**
     * Handles the action of the 'New Profile' button being pressed.
     * @param event The event that has occurred to the 'New Profile' button.
     */
    @FXML
    void handleNewProfileAction(ActionEvent event) {
    	createProfile = true;
    	initialize();
    }

    /**
     * Handles the action of the 'Next Profile' button being pressed.
     * @param event The event that has occurred to the 'Next Profile' button.
     */
    @FXML
    void handleNextProfileAction(ActionEvent event) {
    	currentProfile = currentProfile + 1;
    	initialize();
    }

    /**
     * Handles the action of the 'Previous Profile' button being pressed.
     * @param event The event that has occurred to the 'Previous Profile' button.
     */
    @FXML
    void handlePrevProfileAction(ActionEvent event) {
    		currentProfile = currentProfile - 1;
    		initialize();
    }

    /**
     * Handles the action of the 'Play' button being pressed.
     * @param event The event that has occurred to the 'Play' button.
     */
    @FXML
    void handlePlayAction(ActionEvent event) {
    	Stage stage = (Stage) playButton.getScene().getWindow();
    	Game.playTypeMenu(stage);
    }
    
    @FXML
    /**
     * Handles the action of the 'Editor' button being pressed.
     * @param event The event that has occured to the 'Editor' button.
     */
    void handleEditorButton(ActionEvent action) {
    	Stage stage = (Stage) editorButton.getScene().getWindow();
    	Game.chosenProfile =currentProfile;
    	Game.currentUser = Game.users.get(currentProfile);
    	Game.editorOneMenu(stage);
    }
    
    /**
     * Handles the action of the 'Quit' button being pressed.
     * @param event The event that has occurred to the 'Quit' button.
     */
    @FXML
    void handleQuitAction(ActionEvent event) {
    	System.exit(0);
    }


   }