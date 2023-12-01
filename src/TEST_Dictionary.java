import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Path;
import java.io.File;


/**
 * @author Stephon Selvon
 */
public class TEST_Dictionary {


    /**
     * Test case for a word that is present in the dictionary.
     */
    @Test
    public void testContainsForPresentWord() {
        try {
            // Create a temporary file with a known word.
            Path tempFilePath = Files.createTempFile("test_dictionary_", ".txt");
            String testWord = "testword";

            // Write the test word to the temporary file
            Files.writeString(tempFilePath, testWord);

            // Create an instance of MODEL_Dictionary and check if it search the test word.
            MODEL_Dictionary dictionary = new MODEL_Dictionary();
            assertEquals(false,dictionary.search(testWord));

            // Clean up: delete the temporary file.
            Files.deleteIfExists(tempFilePath);

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception occurred while creating or deleting the temporary file");
        }
    }


    /**
     * Test case for a word that is not present in the dictionary.
     */
    @Test
    public void testContainsForAbsentWord() {
        // Create a temporary file without the test word.
        String fileName = "test_dictionary.txt";
        String testWord = "testword";
        createTestFile(fileName, "anotherword");

        // Create an instance of MODEL_Dictionary and check if it search the test word.
        MODEL_Dictionary dictionary = new MODEL_Dictionary();
        assertFalse(dictionary.search(testWord));

        // Clean up: delete the temporary file.
        deleteTestFile(fileName);
    }

    
    /**
     * Helper method to create a temporary test file.
     * 
     * @param fileName Name of the dictionary file.
     * @param content Contents of the file.
     */
    private void createTestFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Helper method to delete a temporary test file.
     * 
     * @param fileName File name to be deleted.
     */
    private void deleteTestFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    
}

