import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.Test;
import javafx.stage.Stage;
import javafx.application.Platform;


/**
 * @author Stephon Selvon
 */
public class TEST_Metrics {


    /**
     * Testing for the AddErrors function for the Metrics class
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @result sums up all the errors from the document that was uploaded 
     */
    @Test
    public void testAddErrors() throws NoSuchFieldException, IllegalAccessException {
        // Create an instance of MODEL_metrics
        MODEL_Metrics metrics = new MODEL_Metrics();

        // Create an array of ADT_SpellingError for testing
        ADT_SpellingError[] spellingErrors = new ADT_SpellingError[]{
            new ADT_SpellingError("word1", "misspelled", new String[]{"suggestion1", "suggestion2"}, 0, 5, false),
            new ADT_SpellingError("word2", "miscapitalized", new String[]{"suggestion3", "suggestion4"}, 6, 10, true),
            new ADT_SpellingError("word3", "double", new String[]{"suggestion5", "suggestion6"}, 11, 15, false),
            new ADT_SpellingError("word4", "misspelled", new String[]{"suggestion7", "suggestion8"}, 16, 20, true),
            new ADT_SpellingError("word5", "miscapitalized", new String[]{"suggestion9", "suggestion10"}, 21, 25, false)
        };

        // Call the addErrors method
        metrics.addErrors(spellingErrors);

        // Verify the counts using reflection
        assertEquals(2, getPrivateField(metrics, "misspelled"));
        assertEquals(2, getPrivateField(metrics, "miscapitalized"));
        assertEquals(1, getPrivateField(metrics, "dub"));
    }


    /**
     * Private method that is used ot reflect and use the varibales and methods that are not visible in the Metrics class 
     * 
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private int getPrivateField(MODEL_Metrics object, String fieldName) throws NoSuchFieldException, IllegalAccessException { 
        Field field = MODEL_Metrics.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (int) field.get(object);
    }


    /**
     * Testing the AddCorrection function for the Metrics class 
     * 
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @result add up all the correction in terms of words from the docuemtn that was passed 
     */
    @Test
    public void testAddCorrection() throws NoSuchFieldException , IllegalAccessException{
        // Create an instance of MODEL_Metrics
        MODEL_Metrics metrics = new MODEL_Metrics();

        // Create an ADT_SpellingError for testing
        ADT_SpellingError spellingError = new ADT_SpellingError("word1", "misspelled", new String[]{"suggestion1", "suggestion2"}, 0, 5, false);

        // Create ADT_UserCorrection instances for testing
        ADT_UserCorrection suggestionCorrection = new ADT_UserCorrection("suggestion", "suggestion", spellingError);
        ADT_UserCorrection customCorrection = new ADT_UserCorrection("custom", "custom", spellingError);
        ADT_UserCorrection ignoreCorrection = new ADT_UserCorrection("ignore", "ignore", spellingError);
        ADT_UserCorrection ignoreAllCorrection = new ADT_UserCorrection("ignoreall", "ignoreall", spellingError);
        ADT_UserCorrection saveCorrection = new ADT_UserCorrection("save", "save", spellingError);
        ADT_UserCorrection deleteCorrection = new ADT_UserCorrection("delete", "delete", spellingError);

        // Call the addCorrection method
        metrics.addCorrection(suggestionCorrection);
        metrics.addCorrection(customCorrection);
        metrics.addCorrection(ignoreCorrection);
        metrics.addCorrection(ignoreAllCorrection);
        metrics.addCorrection(saveCorrection);
        metrics.addCorrection(deleteCorrection);

        // Verify the counts using reflection
        assertEquals(1, getPrivateField(metrics, "suggestion"));
        assertEquals(1, getPrivateField(metrics, "custom"));
        assertEquals(1, getPrivateField(metrics, "ignore"));
        assertEquals(1, getPrivateField(metrics, "ignoreall"));
        assertEquals(1, getPrivateField(metrics, "save"));
        assertEquals(1, getPrivateField(metrics, "delete"));
    }


    /**
     * Testing the getStats method of the Metrics class 
     * @result would retreive the stats to show to the user 
     */
    @Test

    public void testGetStats() {

        //Run on JFXPanel
        Platform.runLater(() -> { 
            // Create an instance of MODEL_Metrics
            MODEL_Metrics metrics = new MODEL_Metrics();

            // Mocking a Controller instance for testing
            Controller controller = createSampleController();

            // Call the getStats method
            String[][] result = metrics.getStats(controller);

            // Verify the results
            assertEquals("Characters", result[0][0]);
            assertEquals("Words", result[1][0]);
            assertEquals("Lines", result[2][0]);
            assertEquals("Misspellings", result[3][0]);
            assertEquals("Miscapitalizations", result[4][0]);
            assertEquals("Double words", result[5][0]);
            assertEquals("Suggested corrections", result[6][0]);
            assertEquals("Custom corrections", result[7][0]);
            assertEquals("'Ignored' corrections", result[8][0]);
            assertEquals("'Ignored all' corrections", result[9][0]);
            assertEquals("Saves to dictionary", result[10][0]);
            assertEquals("Deletes", result[11][0]);

        });
    }


    /**
     * Creates a controller.
     * 
     * @return the controller 
     */
    private Controller createSampleController() {
        // Create a sample Controller instance with the desired state
        Stage mockingStage = new Stage();
        Controller controller = new Controller(mockingStage) {
            @Override
            public String getWorkingDocument() {
                return "This is a sample document.";
            }
            //overides the getextenion methpod 
            @Override
            public String getExtension() {
                return "txt";
            }
        };

        return controller;
    }
}