import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.geometry.*;


/**
 * A reuseable confirmation prompt.
 * 
 * @author Hayden Wies
 */
public class VIEW_ConfirmBox {
    
    
    private static boolean response;


    /**
     * Renders a confirm prompt that asks a user a question and returns a boolean answer.
     * 
     * @param controller The application controller.
     * @param title The title of the window.
     * @param message The message prompt for the user.
     * @return True if the user says yes, false if the user says no.
     */
    public static boolean display(Controller controller, String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // Prevents user from accessing other windows until this one is closed
        window.setTitle(title);
    
        Label label = new Label(message);
        label.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        Button noButton = new Button("No");
        noButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        noButton.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        noButton.setPrefSize(140, 20);
        noButton.setOnAction(e -> {
            response = false;
            window.close();
        });

        Button yesButton = new Button("Yes");
        yesButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        yesButton.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        yesButton.setPrefSize(140, 20);
        yesButton.setOnAction(e -> {
            response = true;
            window.close();
        });

        HBox options = new HBox(10);
        options.getChildren().addAll(noButton, yesButton);
        options.setAlignment(Pos.CENTER);
        
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, options);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait(); // Waits for window to be closed before returning to caller

        return response;
    }

}