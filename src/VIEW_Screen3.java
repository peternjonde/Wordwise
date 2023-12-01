import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;


/**
 * The third screen of the application.
 * Allows user to discard or save document and view various stats.
 * 
 * @author Ophelie Gross, Hayden Wies
 */
public class VIEW_Screen3 {
    
    
    // Placeholder for statistics data to be retrieved from the controller.
    private String[][] docStats = null;
    private VBox statsWrapper = new VBox();


    /**
     * Renders screen 3 in the main window.
     * 
     * @param controller The application controller.
     * @return The scene to be rendered.
     */
    public Scene render(Controller controller) {
        // ----- Components ----- //
        // Discard changes button with a trash icon.
        Button discardChanges = new Button("Discard changes");
        // Styling the button with a purple background and rounded corners.
        discardChanges.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        discardChanges.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        // Setting the preferred size for uniformity.
        discardChanges.setPrefSize(200, 50);
        // Load the trash icon image.
        Image discardIcon = new Image(getClass().getResourceAsStream("/Icons/delete.png"));
        ImageView discardIconView = new ImageView(discardIcon);
        // Set the icon size within the button.
        discardIconView.setFitHeight(20); 
        discardIconView.setFitWidth(20);
        // Add the icon to the button and position it.
        discardChanges.setGraphic(discardIconView);
        discardChanges.setContentDisplay(ContentDisplay.LEFT);
        // Define the action on button click, changing the scene to "1".
        discardChanges.setOnAction(e -> {
            boolean response = VIEW_ConfirmBox.display(controller, "Discard changes?", "Are you sure you want to discard changes? All progress will be lost.");
            if (response == true) controller.setScene("1");
        });

        // Save changes button with a save icon
        Button saveChanges = new Button("Save changes");
        // Similar styling and sizing as the discard button.
        saveChanges.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        saveChanges.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        saveChanges.setPrefSize(200, 50);
        // Load the save icon image.
        Image SaveIcon = new Image(getClass().getResourceAsStream("/Icons/save.png"));
        ImageView SaveIconView = new ImageView(SaveIcon);
        // Set the icon size within the button.
        SaveIconView.setFitHeight(20); 
        SaveIconView.setFitWidth(20);
        // Add the icon to the button and position it.
        saveChanges.setGraphic(SaveIconView);
        saveChanges.setContentDisplay(ContentDisplay.LEFT);
        // Define the action on button click to save the file and change the scene.
        saveChanges.setOnAction(e -> {
            boolean saved = controller.saveFile();
            if (saved) controller.setScene("1");
        });
        
        // Get stats button with a stats icon.
        Button getStats = new Button("Get stats");
        getStats.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        // Styling the button with a larger size to emphasize its function.
        getStats.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        getStats.setPrefSize(500, 50);
        // Load the stats icon image.
        Image getStatsIcon = new Image(getClass().getResourceAsStream("/Icons/get.png"));
        ImageView getStatsIconView = new ImageView(getStatsIcon);
        // Set the icon size within the button.
        getStatsIconView.setFitHeight(20); 
        getStatsIconView.setFitWidth(20);
        // Add the icon to the button and position it.
        getStats.setGraphic(getStatsIconView);
        getStats.setContentDisplay(ContentDisplay.LEFT);
        // Define the action on button click to get stats and refresh the display.
        getStats.setOnAction(e -> {
            this.docStats = controller.getStats();
            this.renderStats(controller);
        });

        // Options container for the discard and save buttons.
        HBox options = new HBox(100);
        options.getChildren().addAll(discardChanges, saveChanges);
        options.setAlignment(Pos.CENTER);

        this.statsWrapper.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(options, getStats, statsWrapper);
        layout.setAlignment(Pos.CENTER);

        // Create the scene with the specified size.
        Scene scene = new Scene(layout, 1200, 700);

        // Return the created scene.
        return scene;
    }


    /**
     * Dynamically renders the stats segment of the third screen.
     * 
     * @param controller The application controller.
     */
    private void renderStats(Controller controller) {
        statsWrapper.getChildren().clear();

        VBox stats = new VBox(20);
        // VBox container for the statistics display.
        stats.setAlignment(Pos.CENTER);
        // Styling the VBox with a background, border, and rounded corners.
        stats.setStyle("-fx-background-color: #F8F8FF; -fx-border-color: #d3d3d3; " +
                    "-fx-border-width: 2; -fx-border-radius: 15; " +
                    "-fx-background-radius: 15; -fx-padding: 20;");
        // Setting a fixed size for the VBox to ensure consistent layout.
        stats.setPrefSize(500, 500);
        stats.setMinSize(500, 500);
        stats.setMaxSize(500, 500);

        // GridPane for aligning the stat labels and values in a tabular form.
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40); // Horizontal gap between columns
        grid.setVgap(20); // Vertical gap between rows

        // Loop through the temporary statistics data and create a row for each.
        for (int i = 0; i < this.docStats.length; i++) {
            Label stat = new Label(this.docStats[i][0]);
            stat.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            Label count = new Label(this.docStats[i][1]);
            count.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
            GridPane.setHalignment(stat, HPos.RIGHT); // Align the label to the right
            GridPane.setHalignment(count, HPos.LEFT); // Align the count to the left
            grid.add(stat, 0, i); // Add label to the first column
            grid.add(count, 1, i); // Add count to the second column
        }

        stats.getChildren().add(grid); // Add the GridPane to the stats VBox.

        this.statsWrapper.getChildren().add(stats);
    }


}