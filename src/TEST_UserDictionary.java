import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;


/**
 * @author Stephon Selvon
 */
public class TEST_UserDictionary {


    private MODEL_UserDictionary userDictionary;
    private String testFileName = "test_user_dict.txt";


    @BeforeEach
    void setUp() {
        // Create an instance of MODEL_UserDictionary for each test.
        userDictionary = new MODEL_UserDictionary();
    }

    @AfterEach
    void tearDown() {
        // Clean up the temporary file after each test.
        deleteTestFile(testFileName);
    }

    @Test
    void testAddWord() {
        // Arrange
        String testWord = "testword";

        // Act
        userDictionary.add(testWord);

        // Assert
        assertEquals(true,userDictionary.search(testWord));
    }

    @Test
    void testRemoveWord() {
        // Arrange
        String testWord = "testword";
        userDictionary.add(testWord);

        // Act
        userDictionary.remove(testWord);

        // Assert
        assertFalse(userDictionary.search(testWord));
    }

    @Test
    void testClearWords() {
        // Arrange
        userDictionary.add("testword1");
        userDictionary.add("testword2");

        // Act
        userDictionary.reset();

        // Assert
        assertFalse(userDictionary.search("testword1"));
        assertFalse(userDictionary.search("testword2"));
    }

    @Test
    void testContainsForPresentWord() {
        // Arrange
        String testWord = "testword";
        userDictionary.add(testWord);

        // Act and Assert
        assertTrue(userDictionary.search(testWord));
    }

    @Test
    void testContainsForAbsentWord() {
        // Arrange
        String testWord = "testword";

        // Act and Assert
        assertEquals(false,userDictionary.search(testWord));
    }

    // Helper method to delete a temporary test file.
    private void deleteTestFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
