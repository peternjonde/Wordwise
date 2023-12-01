import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Stephon Selvon
 */
class TEST_Corrector extends JFXPanel {


    MODEL_UserDictionary userDictionary;


    /** 
     * Used to test the correctdocuemtn being used and not a wrong one.
     * 
     * @param userDictionary all the words added by the user.
     * @result brings the correct docuemtn that would return no errors.
     */
    @Test
    public void correctDocument_shouldReturnNoErrorsForCorrectDocument() {
        // Given
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            Controller mockController = new Controller(mockStage); // mock controller is being used 
            MODEL_Corrector corrector = new MODEL_Corrector(userDictionary);

            // When
            String workingDocument = "This is a correct document.";

            //Run on JavaFX Application Thread
            
            ADT_SpellingError[] errors = corrector.correctDocument(mockController, workingDocument);

            // Then
            assertEquals(0, errors.length);
        });
    }
   

    /** 
     * To test the IsNewSentence class from the Corrector class
     * 
     * @result brings in a new sentence into the function that can then be used to be corrected 
     */
    @Test
    void isNewSentence() {
        // Given
        MODEL_Corrector corrector = new MODEL_Corrector(userDictionary);

        // Test case 1: Word at the start of the document
        assertTrue(corrector.isNewSentence("This is a test.", 0));

        // Test case 2: Word after a sentence terminator with whitespace
        assertEquals(false,corrector.isNewSentence("This is a test. Another sentence.", 19));

        // Test case 3: Word after a sentence terminator without whitespace
        assertFalse(corrector.isNewSentence("This is a test.Another sentence.", 18));

        // Test case 4: Word not at the start and no terminators before
        assertFalse(corrector.isNewSentence("This is a test.", 10));

        // Test case 5: Word after whitespace before a non-terminator
        assertFalse(corrector.isNewSentence("This is a test. Another sentence.", 18));
    }


    /**
     * To test the suggestion function of the Corrector class
     *
     * @result creates any suggestions using an algorithm from a word that has been seen as incorrect 
     */
    @Test
    void makeSuggestions_shouldReturnCorrectSuggestions() {
        // Given
        MODEL_Corrector corrector = new MODEL_Corrector(userDictionary);

        // Test case 1: Misspelled word
        String[] misspelledSuggestions = corrector.makeSuggestions("tezt", "misspelled", false);
        assertNotNull(misspelledSuggestions);
        assertEquals(1, misspelledSuggestions.length);
        assertEquals(false,arrayContains(misspelledSuggestions, "test"));
        assertEquals(false,arrayContains(misspelledSuggestions, "text"));
        assertEquals(false,arrayContains(misspelledSuggestions, "tent"));
        assertEquals(false,arrayContains(misspelledSuggestions, "toast"));

        //Test case 2: Miscapitalized word
        String[] capitalizationSuggestions = corrector.makeSuggestions("word", "miscapitalized", true);
        assertNotNull(capitalizationSuggestions);
        assertEquals(1, capitalizationSuggestions.length);
        assertTrue(arrayContains(capitalizationSuggestions, "Word"));
        assertEquals(false,arrayContains(capitalizationSuggestions, "WORD"));
        assertEquals(false,arrayContains(capitalizationSuggestions, "wORD"));

        // Test case 3: Double word
        String[] doubleSuggestions = corrector.makeSuggestions("double", "double", false);
        assertNotNull(doubleSuggestions);
        assertEquals(0, doubleSuggestions.length);
    }

    
    // Helper method to check if an array contains a specific string
    private boolean arrayContains(String[] array, String target) {
        for (String element : array) {
            if (element.equals(target)) {
                return true;
            }
        }
        return false;
    }
}