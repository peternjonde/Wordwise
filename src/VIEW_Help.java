import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * The {@code VIEW_Help} class provides a graphical user interface to display help information
 * for the WordWise application. It creates and shows a window with instructions and information
 * about the application's functionality.
 *
 * @author Ophelie Gross, Hayden Wies
 */
public class VIEW_Help {
    

    /**
     * Displays the help window for the WordWise application.
     * The window includes various sections with styled labels and icons, providing instructions
     * and information about how to use the application.
     * 
     * @param controller Application controller.
     */
    public static void display(Controller controller) {
        // Creates a new stage (window)
        Stage window = new Stage(); 
        // Sets the title of the window
        window.setTitle("Getting started with WordWise");

        // Creates and styles the title label
        Label titleLabel = new Label("Getting started with WordWise");
        titleLabel.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-padding: 10,10,10,10; " + // Padding around the text
                "-fx-border-width: 2; " + // Width of the border
                "-fx-border-color: #d3d3d3; " + // Color of the border
                "-fx-border-radius: 15; " + // Radius of the border, for rounded corners
                "-fx-background-radius: 5; " + // Radius of the background, for rounded corners
                "-fx-background-color: #d3d3d3;" );  // Color of the background
        titleLabel.setPrefSize(600, 50);// Sets preferred size for the title label

        // Creates and sets up the description label
        Label descriptionLabel = new Label("Select a file from your desktop.If it's a html or xml file, please choose the option to check the correction between tags or leave it unchoose. Errors will be highlighted and you will have the option to correct these errors through various methods. Upon completion, you will have the option to save the updated file, or discard all changes. You can also get the statistics related to your correction by clicking the button Get Stats.");
        descriptionLabel.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 16));
        descriptionLabel.setPrefWidth(500); // Sets preferred width
        descriptionLabel.setWrapText(true); // Enables text wrapping

        // Second title label with different content and style
        Label titleLabel2 = new Label("What do these buttons do ?");
        titleLabel2.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 16));
        titleLabel2.setStyle("-fx-padding: 10,70,10,70; " + // Padding around the text
                "-fx-border-width: 2; " + // Width of the border
                "-fx-border-color: #d3d3d3; " + // Color of the border
                "-fx-border-radius: 15; " + // Radius of the border, for rounded corners
                "-fx-background-radius: 5; " + // Radius of the background, for rounded corners
                "-fx-background-color: #d3d3d3; "); // Color of the background
        titleLabel2.setPrefSize(600, 50); // Sets preferred size for the title label2


        // Main VBox layout to stack everything vertically
        VBox layout = new VBox(20); // Spacing between vertical elements
        layout.getChildren().addAll(titleLabel, descriptionLabel, titleLabel2, createIconRow(controller));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));

        // Sets the scene and shows the window
        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();
    }


    /**
    * Creates a vertical box (VBox) containing rows of labels with icons.
    * Each row demonstrates a feature of the application with a descriptive icon and label.
    *
        * @return A {@code VBox} containing rows of labels with icons.
    */
    private static VBox createIconRow(Controller controller) {
        // Creates the first row of icons and labels
        HBox firstRow = new HBox(20, createLabelWithIcon(controller, "/Icons/skip.png", "Skip error"),
                                    createLabelWithIcon(controller, "/Icons/delete.png", "Delete word"));
        // Creates the second row of icons and labels                       
        HBox secondRow = new HBox(20, createLabelWithIcon(controller, "/Icons/skipall.png", "Skip all errors"),
                                        createLabelWithIcon(controller, "/Icons/save.png", "Save to dictionary"));
        firstRow.setAlignment(Pos.CENTER);
        secondRow.setAlignment(Pos.CENTER);
    
        // Combines both rows into a single VBox
        VBox combinedRows = new VBox(10, firstRow, secondRow);
        combinedRows.setAlignment(Pos.CENTER);
    
        return combinedRows;
    }


    /**
     * Creates a horizontal box (HBox) containing a label and an icon.
     * The icon and label are styled and aligned to represent a feature of the application.
     *
     * @param iconPath  The path to the icon image file.
     * @param labelText The text to be displayed on the label.
     * @return An {@code HBox} containing a styled icon and label.
     */
    private static HBox createLabelWithIcon(Controller controller, String iconPath, String labelText) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(20); 
        icon.setFitHeight(20); 

        // Creates and styles the icon background
        StackPane iconBackground = new StackPane(icon);
        iconBackground.setStyle("-fx-background-color: #9575CD; " +
                                "-fx-padding: 5; " +
                                "-fx-background-radius: 50%;"); // Circular background
        iconBackground.setAlignment(Pos.CENTER); // Center the icon within the StackPane

        // Create the label
        Label label = new Label(labelText);
        label.setFont(Font.font(controller.getFont(), FontWeight.BOLD, 14));
        label.setStyle("-fx-padding: 5 50 5 50; " +
                    "-fx-border-color: #c0c0c0; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 5; " +
                    "-fx-background-radius: 5; " +
                    "-fx-background-color: #ffffff; ");
        label.setMinWidth(200); // Set a fixed minimum width for the label
        label.setMaxWidth(200); // Set a fixed maximum width for the label
        label.setPrefWidth(200); // Set a fixed preferred width for the label
        label.setAlignment(Pos.CENTER); // Align the text to the center of the label

        // Create an HBox to hold the icon and label
        HBox hbox = new HBox(iconBackground, label);
        hbox.setSpacing(10); // Set the spacing between the icon and label
        hbox.setAlignment(Pos.CENTER); // Align the contents of the HBox to the center

        return hbox;
    }


}