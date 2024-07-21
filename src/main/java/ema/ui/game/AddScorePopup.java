package ema.ui.game;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ema.database.DatabaseHandler;
import ema.helper.ProfanityFilter;

public class AddScorePopup {
    private ProfanityFilter filter;
    public AddScorePopup(int playerScore) {
        filter = new ProfanityFilter();
        showValidatedInputDialog(playerScore);
    }

    private void showValidatedInputDialog(int score) {
        while (true) {
            JTextField textField = new JTextField();
            Object[] messageContent = {
                "If you wish to add your score to the scoreboard, enter your name below. (50 characters long)", textField
            };

            int option = JOptionPane.showConfirmDialog(null, messageContent, "Congratulations Challenger", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String input = textField.getText();

                if (validateInput(input)) {
                    if(showFeedback(input, score)) return;
                    System.out.println("Input valid");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Input invalid");
                }
            } else {
                return; // Cancel or close
            }
        }
    }

    private boolean validateInput(String input) {
        // Check if empty
        if(input.equals("")) return false;

        // Check length
        if(input.length() > 50) return false;

        // Check for inapporiate words
        if(filter.containsBadWords(input) == false) return false;
        //if(filter.filterText(input) != null) return false;

        return true;
    }

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
