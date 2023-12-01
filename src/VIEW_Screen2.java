import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.geometry.*;

/**
 * The {@code VIEW_Screen2} class represents the second screen in the application.
 * This screen is responsible for displaying and managing the spelling errors identified
 * in the document and allows users to correct these errors or skip them.
 *
 * @author Ophelie Gross, Hayden Wies 
 */

public class VIEW_Screen2 {

    private ADT_SpellingError[] allSpellingErrors = null; // Array to store all spelling errors
    private ADT_SpellingError spellingError = null; // Current spelling error being processed
    // UI components that are re-rendered during the application's use
    private VBox rightPannel = new VBox(20);
    private TextFlow text = new TextFlow();
    private CheckBox saveToDictToggle;
 /**
     * Renders the second screen of the application.
     * It sets up the UI components for error correction and provides functionality
     * for user interactions with the spelling errors.
     *
     * @param controller The controller managing the application logic.
     * @return The Scene representing the second screen.
     */
    
    public Scene render(Controller controller) {
        this.spellingError = null; // Reset current spelling error
        this.allSpellingErrors = controller.correctDocument(); // Correct the document and retrieve spelling errors
        controller.updatesErrorStats(this.allSpellingErrors);  // Update error statistics

        // ----- UI Components Setup ----- //

        // ----- Exit button with an icon -----// 
        Button exit = new Button("Exit"); // Creates the 'Exit' button
        exit.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Set font style and size
        exit.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;"); // Style the button
        exit.setPrefSize(150, 50); // Sets the preferred size of the button
        // Setting up the exit icon for the exit button
        Image exiticon = new Image(getClass().getResourceAsStream("/Icons/exit.png"));
        ImageView exiticonView = new ImageView(exiticon);
        exiticonView.setFitHeight(20); // Set the icon height 
        exiticonView.setFitWidth(20); // Set the icon width 
        exit.setGraphic(exiticonView); // Add icon to the button
        exit.setContentDisplay(ContentDisplay.LEFT); // This will position the icon to the left of the text
         // Setting the action for the exit button : When the 'exit' button is clicked, this code displays a confirmation dialog and, 
        // if the user confirms, changes the application's current scene to scene "1".
        exit.setOnAction(e -> {
            boolean response = VIEW_ConfirmBox.display(controller, "Exit?", "Are you sure you want to exit? Your changes will be lost.");
            if (response == true) controller.setScene("1");

        });

        // ----- Finish button with an icon -----//
        Button finish = new Button("Finish"); // Creates the 'Finish' button
        finish.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Style the button
        finish.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;"); // Style the button
        finish.setPrefSize(150, 50); // Sets the preferred size of the button
        // Setting up the finish icon for the edit user dict button
        Image finishicon = new Image(getClass().getResourceAsStream("/Icons/finish.png"));
        ImageView finishiconView = new ImageView(finishicon);
        finishiconView.setFitHeight(20); // Set the icon size
        finishiconView.setFitWidth(20); // Set the icon size
        finish.setGraphic(finishiconView); // Add icon to the button
        finish.setContentDisplay(ContentDisplay.LEFT); // Position the icon to the left of the text
        // Asign the action to the button 
        finish.setOnAction(e -> controller.setScene("3"));

        // ----- Help button with an icon -----// 
        Button help = new Button("Help"); // Creates the 'Help' button
        help.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Style the button
        help.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        help.setPrefSize(150, 50); // Sets the preferred size of the button
        // Creating and setting the help icon for the help button
        Image helpicon = new Image(getClass().getResourceAsStream("/Icons/interrogation.png"));
        ImageView helpiconView = new ImageView(helpicon);
        helpiconView.setFitHeight(20); // Set the icon size
        helpiconView.setFitWidth(20);  // Set the icon size
        help.setGraphic(helpiconView); // Add icon to the button
        help.setContentDisplay(ContentDisplay.LEFT); // Position the icon to the left of the text
         // Asign the action to the button 
        help.setOnAction(e -> VIEW_Help.display(controller));
        
         // Creates an HBox layout named 'progressOptions' with horizontal spacing of 20 pixels between children
        HBox progressOptions = new HBox(20);
        // Adds 'exit', 'finish', and 'help' buttons to the 'progressOptions' HBox
        progressOptions.getChildren().addAll(exit, finish, help);
         // Sets the alignment of the buttons inside 'progressOptions' to the top-left corner of the HBox
        progressOptions.setAlignment(Pos.TOP_LEFT);

        // ----- All document text ----- //
        this.renderText(controller); // Calls the method to render text in the document using the provided controller.
        text.setPadding(new Insets(10)); // Set padding inside the text flow
        
          // Wrap the text component in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(text); // Add the text component as the content of the ScrollPane
        scrollPane.setFitToWidth(true); // If you want the scrollPane to fit the width of the text
        scrollPane.setStyle("-fx-background-color: #FFF ;  -fx-background-radius: 15;"); // Style the ScrollPane
        scrollPane.setPrefSize(640, 500);  // Set preferred size of ScrollPane
        scrollPane.setMinWidth(640);  // Set minimum width 
        scrollPane.setMinHeight(500); // Set minimum height
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar as needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Never show horizontal scrollbar

        // Creates a new VBox for the left panel
        VBox leftPannel = new VBox(10);
        // Adds 'progressOptions' and 'scrollPane' to the left panel's children.
        leftPannel.getChildren().addAll(progressOptions, scrollPane);
        leftPannel.setAlignment(Pos.CENTER); // Centers the children within the left panel.
        leftPannel.setStyle("-fx-border-color: grey; " + // Sets the border color to grey.
                            "-fx-border-width: 2; " +  // Sets the border width to 2 pixels.
                            "-fx-background-color: #FFF; " + // Sets the background color to white.
                            "-fx-padding: 30; " + // Sets padding inside the panel.
                            "-fx-border-radius: 10; " + // Rounded corner radius for the border
                            "-fx-background-radius: 10;"); // Rounded corner radius for the background          
        leftPannel.setMinSize(700, 600); // Sets the minimum size of the left panel
        leftPannel.setMaxSize(700, 600); // Sets the maximum size of the left panel
        
        this.renderSpellingError(controller);// Calls the method to render the spelling errors using the provided controller.
        rightPannel.setAlignment(Pos.CENTER); // Centers the children within the right panel.
        rightPannel.setStyle("-fx-border-color: grey; " + // Sets the border color to grey.
                            "-fx-border-width: 2; " + // Sets the border width to 2 pixels.
                            "-fx-background-color: #FFF; " + // Sets the background color to white.
                            "-fx-padding: 30; " + // Sets padding inside the panel.
                            "-fx-border-radius: 10; " + // Rounded corner radius for the border
                            "-fx-background-radius: 10;"); // Rounded corner radius for the background
        rightPannel.setMinSize(400, 600); // Sets the minimum size of the right panel
        rightPannel.setMaxSize(400, 600); // Sets the maximum size of the right panel
        // Creates a new HBox with 20 pixels spacing between children.
        HBox pannels = new HBox(20);
        // Adds both the left and right panels to the HBox.
        pannels.getChildren().addAll(leftPannel, rightPannel);
        // Centers the panels within the HBox.
        pannels.setAlignment(Pos.CENTER);
        // Creates a new scene with the HBox as its root and sets its size to 1200x700 pixels.
        Scene scene = new Scene(pannels, 1200, 700);

        return scene; // Returns the created scene.
    }
    
    
    /**
     * Renders the text of the document including the spelling errors.
     * Spelling errors are represented as clickable buttons within the text.
     *
     * @param controller The controller managing the application logic.
     */
    private void renderText(Controller controller) {
        this.text.getChildren().clear(); // Clears any existing children from the text container.
        String workingDocument = controller.getWorkingDocument(); // Retrieves the current working document's text.

        if (this.allSpellingErrors.length > 0) { // Check if there are any spelling errors.
            // Loop through each spelling error.
            for (int i = 0; i < this.allSpellingErrors.length; i++) {
                ADT_SpellingError spellingError = this.allSpellingErrors[i]; // Get the current spelling error.

                // Check if the current error is the first in the document and has preceding text.
                if (i == 0 && spellingError.startIndex != 0) {
                    String preceeding = workingDocument.substring(0, spellingError.startIndex);// Extract text preceding the first error.
                    Text preceedingText = new Text(preceeding); // Create a text node for the preceding text.
                    preceedingText.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
                    this.text.getChildren().add(preceedingText); // Add the preceding text to the text container.
                }
                
                // Create a button for each spelling error.
                Button errButton = new Button(spellingError.word);
                errButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
                // Assign clicking to the button to the action
                errButton.setOnAction(e -> {
                    this.spellingError = spellingError;  // Set the current spelling error to the selected one.
                    this.spellingError.suggestions = controller.makeSuggestions(this.spellingError); // Generate suggestions for the current error.
                    this.renderSpellingError(controller); // Render the details of the current spelling error.
                });
                
                this.text.getChildren().add(errButton); // Add the error button to the text container.
                
                // Check for trailing text after the error.
                if (spellingError.endIndex - 1 < workingDocument.length()) {
                    if (i < this.allSpellingErrors.length - 1) {
                        // Extract text between the current error and the next error.
                        String trailing = workingDocument.substring(spellingError.endIndex, this.allSpellingErrors[i+1].startIndex);
                        Text trailingText = new Text(trailing); // Create a text node for the trailing text
                        trailingText.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
                        this.text.getChildren().add(trailingText); // Add the trailing text to the text container.
                    } else {
                        // Extract text from the end of the current error to the end of the document.
                        String trailing = workingDocument.substring(spellingError.endIndex, workingDocument.length());
                        Text trailingText = new Text(trailing); // Create a text node for the trailing text.
                        trailingText.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
                        this.text.getChildren().add(trailingText); // Add the trailing text to the text container.
                    }
                }
            }
        } else {
            Text documentText = new Text(workingDocument); // Create a text node for the entire document if there are no errors.
            documentText.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            this.text.getChildren().add(documentText); // Add the document text to the text container.
        }
    }

    /**
     * Renders the current spelling error and provides UI components for user interaction.
     * Allows the user to see error details and make corrections.
     *
     * @param controller The controller managing the application logic.
     */
    private void renderSpellingError(Controller controller) {
        this.rightPannel.getChildren().clear(); // Clears all children from the right panel.

        if (this.spellingError != null) { // Check if there is a current spelling error to display.
            Label error = new Label(spellingError.word); // Creates a label with the spelling error word.
            error.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 18)); // Sets font for the error label.

            // Creating and setting up the label for the type of error.
            Label errorType = new Label(spellingError.errorType); // Creates a label with the error type.
            errorType.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Sets font for the error type label.
            // Setting up a container for the error details.
            VBox errorDetails = new VBox(10); // Creates a VBox with spacing of 10 for error details.
            errorDetails.setAlignment(Pos.CENTER); // Sets alignment to center for the VBox.
            errorDetails.getChildren().addAll(error, errorType); // Adds error and error type labels to the VBox.

            // Creating and setting up the label for suggestions.
            Label suggestionsLabel = new Label("Suggestions:"); // Creates a label for suggestions.
            suggestionsLabel.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 14)); // Styles the suggestions label.
            suggestionsLabel.setStyle("-fx-text-fill :grey");

            // Creating buttons for each spelling suggestion.
            Button[] suggestionButtons = new Button[this.spellingError.suggestions.length]; // Initializes an array of buttons for suggestions.
            for (int i = 0; i < this.spellingError.suggestions.length; i++) { // Loop to create buttons for each suggestion.
                String suggestion = this.spellingError.suggestions[i]; // Gets a single suggestion.
                suggestionButtons[i] = new Button(suggestion); // Creates a button for the suggestion.
                suggestionButtons[i].setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
                suggestionButtons[i].setOnAction(e -> { // Sets an action for when the suggestion button is clicked.
                    String correctedWord = suggestion; // Sets the corrected word to the suggestion.
                    ADT_UserCorrection correction = new ADT_UserCorrection(correctedWord, "suggestion", this.spellingError); // Creates a correction object.
                    controller.makeCorrection(correction, saveToDictToggle.isSelected()); // Instructs the controller to make the correction
                    this.updateWithCorrection(controller, correctedWord, false); // Updates the UI with the correction.
                });
            }
            // Creating and setting up a text field for custom correction input.
            TextField customText = new TextField(); // Creates a new text field for custom correction.
            customText.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 14));
            customText.setPromptText("Custom correction"); // Sets a prompt text for the text field.
            customText.setPrefSize(160, 20);

            // ----- Submit button with an icon -----// 
            Button submitCustom = new Button("Submit"); // Creates a submit button.
            submitCustom.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 14));
            submitCustom.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");// Styles the submit button.
            submitCustom.setPrefSize(100, 20); // Sets the preferred size of the submit button.
            Image submitCustomicon = new Image(getClass().getResourceAsStream("/Icons/submit.png")); // Loads an icon image for the submit button.
            ImageView submitCustomView = new ImageView(submitCustomicon); // Creates an ImageView for the submit icon.
            submitCustomView.setFitHeight(15); // Sets the height of the submit icon
            submitCustomView.setFitWidth(15);  // Sets the width of the submit icon.
            submitCustom.setGraphic(submitCustomView); // Sets the graphic (icon) for the submit button.
            submitCustom.setContentDisplay(ContentDisplay.LEFT); // Sets the content display of the button to have the icon on the left.
            submitCustom.setOnAction(e -> { // Sets an action for when the submit button is clicked.
                String correctedWord = customText.getText(); // Retrieves the text entered in the customText field.
                ADT_UserCorrection correction = new ADT_UserCorrection(correctedWord, "custom", this.spellingError); // Creates a correction object for the custom text.
                controller.makeCorrection(correction, saveToDictToggle.isSelected()); // Instructs the controller to make the custom correction.
                this.updateWithCorrection(controller, correctedWord, false); // Updates the UI with the custom correction.
            });

            // ----- Save to dictionary checkbox with purple checkmark-----//
            saveToDictToggle = new CheckBox("Save to dictionary"); // Creates a checkbox for saving to dictionary.
            saveToDictToggle.setStyle("-fx-mark-color: #9575CD;"); // Styles the checkbox.
            saveToDictToggle.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

            // ----- Skip button with an icon -----// 
            Button ignore = new Button("Skip"); // Creates a skip button.
            ignore.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            ignore.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;"); // Sets the style of the button
            ignore.setPrefSize(160, 20); // Sets the preferred size of the button to 140 pixels wide and 20 pixels high.
            // Setting up the icon for the skip button
            Image Skipicon = new Image(getClass().getResourceAsStream("/Icons/skip.png"));
            ImageView SkipiconView = new ImageView(Skipicon); // Creates an ImageView to display the 'Skipicon' image.
            SkipiconView.setFitHeight(15); // Sets the height of the icon
            SkipiconView.setFitWidth(15); // Sets the width of the icon 
            ignore.setGraphic(SkipiconView); // Applying the icon to the skip button
            ignore.setContentDisplay(ContentDisplay.LEFT); //  Positions the icon to the left of the text on the button.
            ignore.setOnAction(e -> { // Defining the action when the skip button is pressed
                String correctedWord = this.spellingError.word; // Retrieves the word from the current spelling error.
                ADT_UserCorrection correction = new ADT_UserCorrection(correctedWord, "ignore", this.spellingError); // Creates a correction object for the action 'ignore'.
                controller.makeCorrection(correction, saveToDictToggle.isSelected()); // Instructs the controller to make the correction and checks if the save to dictionary option is selected.
                this.updateWithCorrection(controller, correctedWord, false); // Updates the UI and internal state with the made correction.
            });

            // ----- Skip all button with an icon -----// 
            Button ignoreAll = new Button("Skip all"); // Creates a new Button object named 'ignore' with the label "Skip".
            ignoreAll.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            ignoreAll.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");// Sets the style of the button
            ignoreAll.setPrefSize(160, 20);  // Sets the preferred size of the button 
            // Setting up the icon for the skip button
            Image ignoreAllicon = new Image(getClass().getResourceAsStream("/Icons/skipall.png")); // Loads the 'skip' icon image from resources.
            ImageView ignoreAllView = new ImageView(ignoreAllicon); // Creates an ImageView to display the 'Skipicon' image.
            ignoreAllView.setFitHeight(15);  // Sets the height of the icon
            ignoreAllView.setFitWidth(15);  // Sets the width of the icon
            ignoreAll.setGraphic(ignoreAllView); // Sets the 'SkipiconView' as the graphic (icon) of the 'ignore' button.
            ignoreAll.setContentDisplay(ContentDisplay.LEFT); // Positions the icon to the left of the text on the button.
            // Defining the action when the skip button is pressed
            ignoreAll.setOnAction(e -> {
                String correctedWord = this.spellingError.word; // Retrieves the word from the current spelling error.
                for (ADT_SpellingError err : this.allSpellingErrors) { // Creates a correction object for the action 'ignore'.
                    if (err.word.equals(this.spellingError.word) == true) {
                        this.spellingError = err;
                        ADT_UserCorrection correction = new ADT_UserCorrection(correctedWord, "ignoreall", this.spellingError);
                        controller.makeCorrection(correction, saveToDictToggle.isSelected()); // Updates the UI and internal state with the made correction.
                    }
                }
                this.updateWithCorrection(controller, correctedWord, true);
            });

            // ----- Save to Dictionary with an icon -----// 
            Button save = new Button("Save to dictionary");
            save.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            save.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
            save.setPrefSize(160, 20);
            Image saveIcon = new Image(getClass().getResourceAsStream("/Icons/save.png"));
            ImageView SaveIconView = new ImageView(saveIcon);
            SaveIconView.setFitHeight(15); 
            SaveIconView.setFitWidth(15);
            save.setGraphic(SaveIconView);
            save.setContentDisplay(ContentDisplay.LEFT);
            save.setOnAction(e -> {
                String correctedWord = this.spellingError.word; 
                ADT_UserCorrection correction = new ADT_UserCorrection(this.spellingError.word, "save", this.spellingError); 
                controller.makeCorrection(correction, true); 
                this.updateWithCorrection(controller, correctedWord, true); 
            });

            // ----- Delete with an icon -----// 
            Button delete = new Button("Delete");
            delete.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            delete.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
            delete.setPrefSize(160, 20);
            Image deleteIcon = new Image(getClass().getResourceAsStream("/Icons/delete.png"));
            ImageView deleteIconView = new ImageView(deleteIcon);
            deleteIconView.setFitHeight(15); 
            deleteIconView.setFitWidth(15);
            delete.setGraphic(deleteIconView);
            delete.setContentDisplay(ContentDisplay.LEFT);
            delete.setOnAction(e -> {
                String correctedWord = ""; 
                ADT_UserCorrection correction = new ADT_UserCorrection(correctedWord, "delete", this.spellingError); 
                controller.makeCorrection(correction, saveToDictToggle.isSelected()); 
                this.updateWithCorrection(controller, correctedWord, false); 
            });

            // Creating a container for suggestion buttons
            VBox suggestions = new VBox(20);
            suggestions.getChildren().add(suggestionsLabel); 
            for (Button s : suggestionButtons) suggestions.getChildren().add(s); // Adds the suggestions label to the VBox.
            suggestions.setAlignment(Pos.TOP_CENTER); // Aligns the content of the VBox to the top center.
            // Styling the suggestions VBox
            suggestions.setStyle("-fx-border-color: grey; " + 
                    "-fx-border-width: 2; " + 
                    "-fx-background-color: #F1F2F3; " + 
                    "-fx-padding: 30; " + 
                    "-fx-border-radius: 10; " + 
                    "-fx-background-radius: 10;"); 
            suggestions.setMinSize(290, 320); // Sets the minimum size of the VBox.
            suggestions.setMaxSize(290, 320); // Sets the maximum size of the VBox

            // Creating a container for custom correction
            HBox customCorrection = new HBox(20);
            customCorrection.getChildren().addAll(customText, submitCustom); // Adds the custom text field and submit button to the HBox.
            customCorrection.setAlignment(Pos.CENTER); // Aligns the content of the HBox to the center.
            
            // Creating a container for custom correction
            GridPane correctionOptions = new GridPane(); 
            correctionOptions.setHgap(5); 
            correctionOptions.setVgap(5); 
            correctionOptions.setAlignment(Pos.CENTER);
            correctionOptions.add(ignore, 0, 0); 
            correctionOptions.add(ignoreAll, 1, 0); 
            correctionOptions.add(save, 0, 1); 
            correctionOptions.add(delete, 1, 1); 

            this.rightPannel.getChildren().addAll(errorDetails, suggestions, customCorrection, saveToDictToggle, correctionOptions); // Adding all elements to the right panel
        }
    }

    /**
     * Updates the UI and internal state after a spelling correction is made.
     * It adjusts the spelling errors list and re-renders the text and error panel.
     *
     * @param controller The controller managing the application logic.
     * @param correctedWord The word that was corrected.
     * @param skipAll Flag indicating if the correction should apply to all similar errors.
     */
    private void updateWithCorrection(Controller controller, String correctedWord, boolean skipAll) {
        // Removing the corrected word from the list of all spelling errors
        int numOfOccurrences = 0; // Counter for occurrences of the corrected word.
        for (ADT_SpellingError err : this.allSpellingErrors) {
            // Checking if the error word matches the corrected word.
            if (err.word.toLowerCase().equals(this.spellingError.word.toLowerCase()) == true) numOfOccurrences++;
        }
        // Creating a new array of spelling errors after correction
        ADT_SpellingError[] updatedAllSpellingErrors = skipAll ? new ADT_SpellingError[this.allSpellingErrors.length-numOfOccurrences] : new ADT_SpellingError[this.allSpellingErrors.length-1];

        int shiftValue = correctedWord.length() - this.spellingError.word.length(); // Calculating the shift in index due to the correction.

        int i = 0;
        for (ADT_SpellingError err : this.allSpellingErrors) {
            // Updating the index of spelling errors after the corrected word.
            if (err.startIndex != this.spellingError.startIndex) {
                // Adjusting the start and end index of subsequent errors.
                if (err.startIndex > this.spellingError.startIndex) {
                    err.startIndex = err.startIndex + shiftValue;
                    err.endIndex = err.endIndex + shiftValue;
                }
                // Updating the array of all spelling errors.
                if (skipAll == true && err.word.toLowerCase().equals(this.spellingError.word.toLowerCase()) == false) {
                    updatedAllSpellingErrors[i] = err;
                    i++;
                } else if (skipAll == false) {
                    updatedAllSpellingErrors[i] = err;
                    i++;
                }
            }

        }
        
        this.allSpellingErrors = updatedAllSpellingErrors; // Updating the main list of all spelling errors.
        
         // Clearing the current spelling error and re-rendering the UI.
        this.spellingError = null; // Resetting the current spelling error.
        this.renderText(controller); // Re-rendering the text with updated errors.
        this.renderSpellingError(controller); // Re-rendering the spelling error panel.
    } 


}