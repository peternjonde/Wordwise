import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * The VIEW_UserDict class manages the user interface for the user dictionary.
 * It includes functionalities for displaying and modifying the user dictionary.
 * This class is part of the application's graphical user interface, utilizing JavaFX components.
 * 
 * @author Peter Njonde, Ophelie Gross, Hayden Wies
 */
public class VIEW_UserDict {

    
    private MODEL_UserDictionary userDictionary; // Instance of MODEL_UserDictionary for user dictionary data management
    private final ObservableList<String> wordList = FXCollections.observableArrayList(); // ObservableList to store and update the list of words in the user dictionary


    /**
     * Constructs a VIEW_UserDict object with a given user dictionary model.
     * Initializes the userDictionary instance with the provided model.
     *
     * @param userDictionary The user dictionary model used for data management.
     */
    public VIEW_UserDict(MODEL_UserDictionary userDictionary) {

        this.userDictionary = userDictionary;
    }


    /**
     * Displays the user dictionary interface.
     * Initializes and sets up the JavaFX components for user interaction.
     *
     * @param controller The controller object that manages application logic.
     */
    public void display(Controller controller) {

        wordList.clear();
        this.readUserDict();

        Stage primaryStage = new Stage();

        TextField wordTextField = new TextField();
        wordTextField.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));
        wordTextField.setStyle("-fx-background-radius: 15;");

        Button addButton = new Button("Add Word");
        addButton.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        addButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        Button removeButton = new Button("Remove Selected Word");
        removeButton.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        removeButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #9575CD; -fx-text-fill: black; -fx-background-radius: 15;");
        resetButton.setFont(Font.font(controller.getFont(), FontWeight.NORMAL, 14));

        ListView<String> listView = new ListView<>(wordList);
        listView.setStyle("-fx-background-radius: 15;");

        addButton.setOnAction(event -> addWordToList(wordTextField.getText()));
        removeButton.setOnAction(event -> removeSelectedWord(listView.getSelectionModel().getSelectedItem()));
        resetButton.setOnAction(event -> clearList());
        HBox inputBox = new HBox(wordTextField, addButton);
        inputBox.setSpacing(10);
        inputBox.setPadding(new Insets(10));

        HBox r = new HBox(removeButton,resetButton);
        r.setSpacing(10);
        r.setPadding(new Insets(10));

        VBox root = new VBox(inputBox, listView, r);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("User Dictionary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Adds a given word to the word list and updates the user dictionary.
     * This method first checks if the provided word is not empty.
     * If the word is non-empty, it is added to the `wordList`, an ObservableList, 
     * and also to `userDictionary`, ensuring that both the UI and data model are updated.
     *
     * @param word The word to be added to the list and user dictionary. 
     *             It should not be null or empty.
     */
    private void addWordToList(String word) {

        if (!word.isEmpty()) {
            wordList.add(word);
            this.userDictionary.add(word);
        }
    }


    /**
     * Removes a selected word from the word list and the user dictionary.
     * This method checks if the provided word is not null before proceeding.
     * If the word is valid (not null), it is removed from both the `wordList`, 
     * which is an ObservableList used for displaying words in the UI, 
     * and from `userDictionary`, which maintains the data model.
     * 
     * This ensures that both the display and the underlying data are kept in sync.
     *
     * @param word The word to be removed from the list and user dictionary.
     *             The word should not be null.
     */
    private void removeSelectedWord(String word) {

        if (word != null) {
            wordList.remove(word);
            this.userDictionary.remove(word);
        }
    }


    /**
     * Clears the word list and resets the user dictionary.
     * This method clears all entries from the `wordList`, an ObservableList 
     * used for displaying words in the UI. It also calls the `reset` method 
     * on `userDictionary` to clear and reset the data in the user dictionary model.
     * 
     * This is useful for situations where a complete refresh or reset of the data 
     * and display is required, ensuring that both the UI and the underlying data model
     * are in a consistent, empty state.
     */
    private void clearList(){

        wordList.clear();
        this.userDictionary.reset();
    }


    /**
     * Reads words from a user dictionary file and populates them into the word list.
     * This method attempts to open and read a file named "user_dict.txt". 
     * Each line in the file is expected to contain a word, and these words are added 
     * to the `wordList`, which is an ObservableList used for displaying words in the UI.
     * 
     * If the file "user_dict.txt" is not found, it catches a FileNotFoundException 
     * and prints a message indicating that the file was not found. 
     * This method is essential for initializing the word list with existing data 
     * from the user dictionary at the start of the application or when a refresh is needed.
     * 
     * Note: This method assumes the file is in the default directory of the application.
     */
    private void readUserDict(){

        try {
        
        	File file = new File("user_dict.txt");
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                wordList.add(input.nextLine());
            }
            input.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }


}
