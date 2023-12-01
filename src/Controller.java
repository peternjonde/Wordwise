import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.*;


public class Controller {

    private Stage window;
    private MODEL_Corrector corrector;
    private MODEL_Document document;
    private MODEL_Metrics metrics;
    private MODEL_UserDictionary userDictionary;
    private boolean editInAngles;
    private String font;


    /**
     * Creates an instance of the controller object and consequently starts the app.
     * 
     * @param primaryStage The stage for app to be displayed within.
     */
    public Controller(Stage primaryStage) {
        this.userDictionary = new MODEL_UserDictionary();
        
        // Initialize data
        this.document = new MODEL_Document();
        this.corrector = new MODEL_Corrector(this.userDictionary);
        this.metrics = new MODEL_Metrics();
        
        // Configure window
        this.window = primaryStage; // Rename primaryStage to window
        window.setTitle("WordWise");

        this.font = this.getFontFromFile();

        this.setScene("1");
        window.show();
    }


    /**
     *  Sets the scene to be displayed.
     * 
     * @param sceneId The id of the scene to be displayed.
     */
    public void setScene(String sceneId) {
        Scene scene = null;
        switch (sceneId) {
            case "1":
                VIEW_Screen1 screen1 = new VIEW_Screen1(this.userDictionary);
                scene = screen1.render(this);
                break;
            case "2":
                VIEW_Screen2 screen2 = new VIEW_Screen2();
                scene = screen2.render(this);
                break;
            case "3":
                VIEW_Screen3 screen3 = new VIEW_Screen3();
                scene = screen3.render(this);
                break;
            default:
                break;
        }

        if (scene != null) this.window.setScene(scene);
    }


    /**
     * Used to get a file and save it as a string.
     * If the file exists and saves successfully, scene is moved to screen 2.
     */
    public boolean getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Valid files", "/*.txt", "/*.html", "/*.xml"));
        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                document.setDocument(selectedFile);
            } catch (IOException e) {
                VIEW_AlertBox.display(this, "Error", "There was an issue opening the file.");
            }

            return true;
            
        } else {
            return false;
        }
    }


    /**
     * Used to save file once user is done editing.
     */
    public boolean saveFile() {
        FileChooser fileChooser = new FileChooser();

        ExtensionFilter txtExt = new ExtensionFilter("Text file", "*.txt");
        ExtensionFilter htmlExt = new ExtensionFilter("HTML file", "*.html");
        ExtensionFilter xmlExt = new ExtensionFilter("XML file", "*.xml");
        fileChooser.getExtensionFilters().addAll(txtExt, htmlExt, xmlExt);

        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName("newfile");

        String ext = document.getExtension();
        if (ext.contains("txt")) fileChooser.setSelectedExtensionFilter(txtExt);
        if (ext.contains("html")) fileChooser.setSelectedExtensionFilter(htmlExt);
        if (ext.contains("xml")) fileChooser.setSelectedExtensionFilter(xmlExt);

        File selectedFile = fileChooser.showSaveDialog(window);
        
        if (selectedFile != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                writer.write(document.getWorkingDocument());
                writer.close();
            } catch (IOException e) {
                VIEW_AlertBox.display(this, "Error", "There was an issue saving the file.");
            }

            return true;
        } else {

            return false;
        }
    }

    /**
     * Used to get text of the working document.
     * @return (String) Complete document text.
     */
    public String getWorkingDocument() {
        return this.document.getWorkingDocument();
    }


    /**
     * Used to get extension of the working file.
     * @return Extension name
     */
    public String getExtension() {
        return this.document.getExtension();
    }


    /**
     * 
     * @param editInAngles
     */
    public void setEditInAngles(boolean editInAngles) {
        this.editInAngles = editInAngles;
    }


    /**
     * 
     * @return
     */
    public boolean getEditInAngles() {
        return this.editInAngles;
    }

    
    /**
     * Will correct the text of the working document and return a list of all spelling errors.
     */
    public ADT_SpellingError[] correctDocument() {
        String workingDocument = this.getWorkingDocument();
        return corrector.correctDocument(this, workingDocument);
    }


    /**
     * Called whenever a user makes a correction to an error.
     * Logs a correction in metrics.
     */
    public void makeCorrection(ADT_UserCorrection correction, boolean saveToDict) {
        this.document.updateWorkingDocument(correction.correctedWord, correction.spellingError.startIndex, correction.spellingError.endIndex);
        this.metrics.addCorrection(correction);
        if (correction.correctionType == "custom" && saveToDict) userDictionary.add(correction.correctedWord); // Save custom corrected word to user dictionary
        if (correction.correctionType == "save") userDictionary.add(correction.spellingError.word); // Save word marked as error to user dictionary
    }


    /**
     * Creates suggestions for a word.
     * 
     * @param spellingError Spelling error object.
     * @return A list of suggestions.
     */
    public String[] makeSuggestions(ADT_SpellingError spellingError) {
        return this.corrector.makeSuggestions(spellingError.word, spellingError.errorType, spellingError.newSentence);
    }


    /**
     *  Updates metrics with all errors.
     * 
     * @param allSpellingErrors List of all errors in document.
     */
    public void updatesErrorStats(ADT_SpellingError[] allSpellingErrors) {
        this.metrics.addErrors(allSpellingErrors);
    }


    /**
     * Will get all stats from metrics and return it to screen 3.
     * 
     * @return Array of stats that contains objects that have a stat name and value / count.
     */
    public String[][] getStats() {
        return this.metrics.getStats(this);
    }


    /**
     * Gets the font from settings.txt
     * 
     * @return The name of the font.
     */
    public String getFontFromFile() {
        File file = new File("settings.txt");

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String font = bufferedReader.readLine(); // First line in settings is font name

            bufferedReader.close();

            return font.strip();

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }


    /**
     * Gets the stored font.
     *  
     * @return The font name.
     */
    public String getFont() {
        return this.font;
    }


    /**
     * Sets a font in the settings.txt
     * 
     * @param font The name of the font.
     */
    public void setFont(String font) {
        File file = new File("settings.txt");

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(font);
            this.font = font;

            bufferedWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
