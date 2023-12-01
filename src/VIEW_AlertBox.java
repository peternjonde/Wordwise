import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.geometry.*;


/**
 * A reuseable alert box.
 * 
 * @author Hayden Wies
 */
public class VIEW_AlertBox {

    /**
     * Displays an alert box to the user.
     * 
     * @param controller The application the controller
     * @param title The title of the alert box.
     * @param message The message to be displayed.
     */
    public static void display(Controller controller, String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // Prevents user from accessing other windows until this one is closed
        window.setTitle(title);

        Label label = new Label(message);
        label.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        Button okButton = new Button("Okay");
        okButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        okButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait(); // Waits for window to be closed before returning to caller
    }

}
