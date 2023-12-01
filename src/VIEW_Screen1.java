import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;


/**
 * Class VIEW_Screen1 handles the graphical user interface for the first screen of the application.
 * This class utilizes JavaFX components to render the UI elements and manage user interactions.
 * It primarily deals with user dictionary management and file selection functionalities.
 * 
 * @author Ophelie Gross, Hayden Wies
 */
public class VIEW_Screen1 {
    

    VIEW_UserDict userDict; // Instance of the edit used dictionary screen


    /**
     * Constructor for VIEW_Screen1 class.
     * Initializes the VIEW_Screen1 object with a specific user dictionary model.
     * This constructor sets up the VIEW_UserDict instance, which is used for managing 
     * and displaying the user dictionary in the application's user interface.
     *
     * The MODEL_UserDictionary instance passed as a parameter is used to initialize 
     * the VIEW_UserDict object, ensuring that the UI component has the necessary data 
     * model to work with. This setup is critical for maintaining a consistent and 
     * interactive user dictionary feature in the application.
     *
     * @param userDictionary The MODEL_UserDictionary object that provides the data model 
     *                       for the user dictionary. It is essential for the VIEW_UserDict 
     *                       to function correctly in managing and displaying dictionary data.
     */
    public VIEW_Screen1(MODEL_UserDictionary userDictionary) {
        this.userDict = new VIEW_UserDict(userDictionary);
    }


    /**
     * Renders the graphical user interface for the first screen of the application.
     * This method creates and arranges various JavaFX components to form the UI layout.
     * Components include buttons for file selection, editing the user dictionary, 
     * and accessing help information, along with settings for font preferences and additional options.
     * 
     * The method also defines the action handlers for these components, linking them to 
     * the appropriate functionalities in the provided controller.
     * The layout is arranged in a VBox with a specified style, and the method returns a Scene 
     * containing this layout, which can be displayed in the JavaFX application.
     *
     * @param controller The controller object that manages application logic and user interactions.
     *                   It is used here to handle actions like file selection and setting application preferences.
     * @return A Scene object containing the constructed UI layout for the first screen of the application.
     */
    public Scene render(Controller controller) {
        
        // Creating a spacer for better layout management
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Allows the spacer to grow as needed
        spacer.setMinHeight(90); // Sets a minimum height for the spacer
        
        // ----- Select File Button Setup ----- //
        Button selectFile = new Button("Select file");
        selectFile.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 16)); // Set font style and size
        selectFile.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;"); // Button styling
        selectFile.setPrefSize(200, 50); // Set preferred size of the button
        // Setting up the file icon for the select file button
        Image fileicon = new Image(getClass().getResourceAsStream("/Icons/file.png"));
        ImageView fileiconView = new ImageView(fileicon);
        fileiconView.setFitHeight(20); // Set the icon height 
        fileiconView.setFitWidth(20); // Set the icon width 
        selectFile.setGraphic(fileiconView); // Add icon to the button
        selectFile.setContentDisplay(ContentDisplay.LEFT); // This will position the icon to the left of the text
        selectFile.setPadding(new Insets(10, 20, 10, 20)); // Adjust padding

        // ----- Edit User Dictionary Button Setup ----- //
        Button editUserDict = new Button("Edit user dictionary");
        editUserDict.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Set font style and size
        editUserDict.setStyle("-fx-background-color: #F1F2F3; -fx-text-fill: black; -fx-background-radius: 15;"); // Button styling
        editUserDict.setPrefSize(220, 50); // Set preferred size of the button
        // Setting up the edit icon for the edit user dict button
        Image editicon = new Image(getClass().getResourceAsStream("/Icons/edit.png"));
        ImageView editiconView = new ImageView(editicon); 
        editiconView.setFitHeight(20); // Set the icon size
        editiconView.setFitWidth(20); // Set the icon size
        editUserDict.setGraphic(editiconView); // Add icon to the button
        editUserDict.setContentDisplay(ContentDisplay.LEFT); // Position the icon to the left of the text
        editUserDict.setOnAction(e -> openUserDict(controller)); // Asign the action to the button 

        // Checkbox for HTML/XML tag correction option
        CheckBox editInAngles = new CheckBox(); // Invoke file selection
        editInAngles.setText("Check text in HTML and XML tags? (Not recommended)"); 
        editInAngles.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        // Setting the action for the Select File button
        selectFile.setOnAction(e -> {
            boolean selected = controller.getFile(); // Invoke file selection
            if (selected == true) {
                controller.setEditInAngles(editInAngles.isSelected()); // Set the edit in angles option based on checkbox
                controller.setScene("2"); // Change to scene 2
            }
        });
        // Creating a spacer for better layout management
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS); // Allows the spacer to grow as needed
        spacer2.setMinHeight(90); // Sets a minimum height for the spacer

        // ----- Help Button Setup ----- //
        Button howToUse = new Button("How to use the app"); 
        howToUse.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Set font style and size
        howToUse.setStyle("-fx-background-color: #F1F2F3; -fx-text-fill: black; -fx-background-radius: 15;"); // Button styling
        howToUse.setPrefSize(200, 50); // Set preferred size of the button
        // Setting up the howtoUseicon icon for the howToUse button
        Image howToUseicon = new Image(getClass().getResourceAsStream("/Icons/interrogation.png"));
        ImageView howToUseView = new ImageView(howToUseicon);
        howToUseView.setFitHeight(20); // Set the icon size
        howToUseView.setFitWidth(20); // Set the icon size
        howToUse.setGraphic(howToUseView);
        howToUse.setContentDisplay(ContentDisplay.LEFT); // Position the icon to the left of the text
        howToUse.setOnAction(e -> VIEW_Help.display(controller)); // Asign the action to the button

        // Create and configure a label for the font setting option
        Label fontSettingLabel = new Label("Font setting:"); // Creates a label with the text "Font setting:".
        fontSettingLabel.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Sets the font of the label using the current font setting from the controller and a font size of 14.
        // Initialize a toggle group for the radio buttons
        ToggleGroup group = new ToggleGroup(); // Creates a new ToggleGroup. RadioButtons added to this group are mutually exclusive.
        
        // Create and configure the "Times New Roman" radio button
        RadioButton timesNewRoman = new RadioButton("Times New Roman"); // Creates a RadioButton with the label "Times New Roman".
        timesNewRoman.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Sets the font of the RadioButton using the current font setting from the controller and a font size of 14.
        timesNewRoman.setToggleGroup(group); // Adds the RadioButton to the previously created toggle group to ensure mutual exclusivity with other options.
        timesNewRoman.setOnAction(e -> { // Sets the action to be performed when this RadioButton is selected.
            controller.setFont("Times New Roman"); // Updates the font setting in the controller to "Times New Roman".
            controller.setScene("1"); // Changes the application scene (or view) to scene "1", reflecting the updated setting.
        });
        // Create and configure the "Arial" radio button
        RadioButton arial = new RadioButton("Arial"); // Creates a RadioButton with the label "Arial".
        arial.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14)); // Sets the font of the RadioButton using the current font setting from the controller and a font size of 14.
        arial.setToggleGroup(group); // Adds the RadioButton to the same toggle group as other font options to ensure mutual exclusivity.
        arial.setOnAction(e -> { // Sets the action to be performed when this RadioButton is selected.
            controller.setFont("Arial"); // Updates the font setting in the controller to "Arial".
            controller.setScene("1"); // Changes the application scene (or view) to scene "1", reflecting the updated setting.
        });
        // Retrieve the current font setting from the controller
        String font = controller.getFont(); // Gets the currently set font from the controller.
        // Check and set the appropriate radio button based on the current font setting
        if (font.equals("Times New Roman")) timesNewRoman.setSelected(true); // If the current font is "Times New Roman", select the corresponding radio button.
        else if (font.equals("Arial")) arial.setSelected(true); // If the current font is "Arial", select the corresponding radio button.

        // Create and configure a container for the font setting controls
        HBox settingsButtons = new HBox(20); // Creates a horizontal box (HBox) with a spacing of 20 pixels between children.
        settingsButtons.setAlignment(Pos.CENTER); // Sets the alignment of the contents within the HBox to the center.
        settingsButtons.getChildren().addAll(fontSettingLabel, timesNewRoman, arial); // Adds the font setting label and the two radio buttons to the HBox.

        // ----- Layout Setup ----- //
        VBox layout = new VBox(20); // Create VBox with spacing 20
        layout.getChildren().addAll(spacer, selectFile, editUserDict, spacer2, editInAngles, settingsButtons, howToUse); // Add components to layout
        layout.setAlignment(Pos.CENTER); // Center the components in the VBox
        layout.setStyle("-fx-background-color: #F1F2F3#;"); // Set the background color of the layout
        // Create and return the scene with the specified size
        Scene scene = new Scene(layout, 1200, 700);
        
        return scene;
    }


    /**
     * Opens and displays the user dictionary interface.
     * This method calls the `display` method of the `userDict` instance, 
     * passing the provided controller to it. The controller is used to manage 
     * the application logic and handle user interactions within the user dictionary interface.
     * 
     * This method serves as a bridge between the main application and the user dictionary 
     * interface, ensuring that the correct controller is utilized for managing the user 
     * dictionary functionalities.
     *
     * @param controller The controller object that manages application logic and user interactions.
     */
    private void openUserDict(Controller controller) {
        this.userDict.display(controller); // Display the user dictionary view
    }

    
}

