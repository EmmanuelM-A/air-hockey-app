package ema.ui.scoreboard;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ema.database.DatabaseHandler;
import ema.helper.ProfanityFilter;

/**
 * Handles the displaying of the Add Score popup box.
 */
public class AddScorePopup {
    /**
     * Used to detcet any profanity in user inputs. 
     */
    private ProfanityFilter filter;

    /**
     * Creates a popup where users can input their name which will be saved on a scoreboard with their score.
     * @param playerScore The player's score.
     */
    public AddScorePopup(int playerScore) {
        filter = new ProfanityFilter("src\\main\\resources\\files\\inapporiateWords.csv");
        showValidatedInputDialog(playerScore);
    }

    /**
     * Displays the popup box.
     * @param score The player's score that will be added to the screboard.
     */
    private void showValidatedInputDialog(int score) {
        while (true) {
            JTextField textField = new JTextField();
            Object[] messageContent = {
                "If you wish to add your score to the scoreboard, enter your name below. (50 characters long)", textField
            };

            // Displays the JOptionPane and gets the success value.
            int option = JOptionPane.showConfirmDialog(null, messageContent, "Congratulations Challenger", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String input = textField.getText();

                if (validateInput(input)) {
                    System.out.println("Input valid");
                    if(showFeedback(input, score)) return;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Input invalid");
                }
            } else {
                return; // Cancel or close
            }
        }
    }

    /**
     * Checks to make sure the input is valid.
     * @param input The user input
     * @return True if valid and false otherwise.
     */
    private boolean validateInput(String input) {
        // Check if empty
        if(input.equals("")) return false;

        // Check length
        if(input.length() > 50) return false;

        // Check for inapporiate words
        if(filter.checkInput(input) == false) return false;

        return true;
    }

    /**
     * Displays the feedback boxes on a successful insertion or a fail.
     * @param input The player's input.
     * @param score The player's score.
     * @return True if the database insertion was successful and false otherwise.
     */
    private boolean showFeedback(String input, int score) {
        boolean success = DatabaseHandler.insertScore(input, score);

        if(success) {
            JOptionPane.showMessageDialog(null, "Insert Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error occured, try again!", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        return success;
    }    
}
