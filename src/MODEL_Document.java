import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.util.List;


/**
 * Class to store and manipulate the document.
 * 
 * @author Hayden Wies
 */
public class MODEL_Document {
    

    private String extension = "";
    private String originalDocument;
    private String workingDocument;


    /**
     * Populate the original document with text from a provided file.
     * Set working document equal to original document.
     * 
     * @param selectedFile The file selected from the FileChooser object.
     */
    public void setDocument(File selectedFile) throws IOException {
        int i = selectedFile.getName().lastIndexOf('.');
        if (i > 0) {
            this.extension = selectedFile.getName().substring(i+1);
        }

        List<String> lines = Files.readAllLines(selectedFile.toPath(), StandardCharsets.UTF_8);
        StringBuilder content = new StringBuilder();

        for (String line : lines) {
            content.append(line).append("\n");
        }

        String doc = content.toString();
        this.originalDocument = doc;
        this.workingDocument = originalDocument;
    }


    /**
     * Gets the working document.
     * 
     * @return The current working document.
     */
    public String getWorkingDocument() {
        return this.workingDocument;
    }


    /**
     * Replaces a given portion of the working document.
     * 
     * @param replaceText Text to be added into document.
     * @param startIndex Start index of string that will be replaced.
     * @param endIndex End index of string that will be replaced.
     */
    public void updateWorkingDocument(String replaceText, int startIndex, int endIndex) {
        if (startIndex == 0) {
            this.workingDocument = replaceText + this.workingDocument.substring(endIndex);
        } else if (endIndex == this.workingDocument.length()) {
            this.workingDocument = this.workingDocument.substring(0, startIndex) + replaceText;
        } else {
            this.workingDocument = this.workingDocument.substring(0, startIndex) + replaceText + this.workingDocument.substring(endIndex, this.workingDocument.length());
        }
    }


    /**
     * Gets extension of file.
     * Does not include period preceeding extension name.
     * 
     * @return The file extension.
     */
    public String getExtension() {
        return this.extension;
    }


}
