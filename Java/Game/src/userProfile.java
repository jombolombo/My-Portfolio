
// The user profile class
import java.util.ArrayList;

/**
 * The user profile class, contains data such as the username, their max level
 * and all of their level times
 * 
 * @author Prithvi Kulkarni
 * @version 1.0
 */
public class userProfile {
	private String userName;
	private int maxLevel;
	private ArrayList<Integer> levelTimes;

	/**
	 * The userprofile constructor.
	 * 
	 * @param name  The user name.
	 * @param level The maximum level the user has reached.
	 */
	public userProfile(String name, int level) {
		userName = name;
		maxLevel = level;
		levelTimes = new ArrayList<Integer>();
	}

	/**
	 * The method to add times into a list
	 * 
	 * @param time The time to be added.
	 */
	public void fillTime(int time) {
		levelTimes.add(time);
	}

	/**
	 * Gets the Max level reached message.
	 * 
	 * @return The message as a String.
	 */
	public String getInfo() {
		return "CURRENT LEVEL: " + Integer.toString(maxLevel);
	}

	/**
	 * Returns the userprofiles name.
	 * 
	 * @return The name as a String.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the userProfiles max level.
	 * 
	 * @return The max level reached.
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * Returns list of the users times.
	 * 
	 * @return The list of the user's level times.
	 */
	public ArrayList<Integer> getLevelTimes() {
		return levelTimes;
	}

	/**
	 * The method to change the level.
	 * 
	 * @param maxLevel The desired level.
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	/**
	 * Gets the current score.
	 * 
	 * @return The current score.
	 */
	public int getCurrentScore() {
		int currentScore = 0;
		for (int i = 0; i < levelTimes.size(); i++) {
			int leveltime = levelTimes.get(i);
			if (leveltime < 5) {
				currentScore = currentScore + 50;
			} else if (leveltime >= 5 && leveltime < 10) {
				currentScore = currentScore + 35;
			} else if (leveltime >= 10 && leveltime < 15) {
				switch(leveltime) {
					case 10: 
						currentScore = currentScore + 30;
						break;
					case 11:
						currentScore = currentScore + 25;
						break;
					case 12:
						currentScore = currentScore + 20;
						break;
					case 13:
						currentScore = currentScore + 15;
						break;
					default:
						currentScore = currentScore + 10;
						
				}
			} else {
				currentScore = currentScore + 5;
			}
		}
		return  currentScore;
	}

}
