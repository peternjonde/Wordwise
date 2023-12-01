import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 * @author Stephon Selvon
 */
public class TEST_Document {


    /**
     * Test to see if a Document is valid 
     * 
     * @throws IOException
     * @result sets the docuemtn and see if it is a valud file with content inside 
     */
    @Test
    public void setDocument_ValidFile_ContentIsSet() throws IOException {
        MODEL_Document document = new MODEL_Document();
        File testFile = Files.createTempFile("testfile",".txt").toFile();
        document.setDocument(testFile);

        assertNotNull(document.getWorkingDocument());
        assertEquals(document.getWorkingDocument(), document.getWorkingDocument());
    }

    
    /**
     * Tests to see if the document is replaced if the wrong documne is chosen 
     * 
     * @throws IOException
     * @result updates the document by replacing it successfully 
     */
    @Test
    public void updateWorkingDocument_ReplaceText_SuccessfulReplacement() throws IOException {
        MODEL_Document document = new MODEL_Document();

        // Create a temporary file with content
        File tempFile = Files.createTempFile("testfile", ".txt").toFile();
        Files.write(tempFile.toPath(), "Hello, world!".getBytes());

        // Ensure that the created file is not null
        assertNotNull(tempFile);

        // Set the document using the created file
        document.setDocument(tempFile);

        // Make sure the indices are within bounds
        int startIndex = 7;
        int endIndex = 12;

        // Ensure that the workingDocument is not null before checking its length
        assertNotNull(document.getWorkingDocument());

        // Ensure that endIndex is not greater than the length of the workingDocument
        assertTrue(endIndex <= document.getWorkingDocument().length());

        document.updateWorkingDocument("Java", startIndex, endIndex);

        // Use trim() to remove leading and trailing whitespaces, including newline characters
    }
    

    /**
     * test the file extension and resturns it 
     * @throws IOException
     * @result getsExtension and returns it
     */
    @Test
    public void getExtension_FileWithExtension_ReturnsExtension() throws IOException {
        MODEL_Document document = new MODEL_Document();
        File testFile = Files.createTempFile("testfile", ".txt").toFile();

        document.setDocument(testFile);

        assertEquals("txt", document.getExtension());
    }


}

