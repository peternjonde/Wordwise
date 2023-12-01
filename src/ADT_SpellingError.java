/**
 * An object that represents a spelling error.
 * 
 * @author Hayden Wies
 */
public class ADT_SpellingError {
    
    public String word;
    public String errorType;
    public String[] suggestions;
    public int startIndex; // Index of the first character within the text document
    public int endIndex; // Index after the last character within the text document
    public boolean newSentence;


    /**
     * Data type that represents a spelling error within the document.
     * 
     * @param word The word which is an error.
     * @param errorType The type of error.
     * @param suggestions A list of correction suggestions (empty by default).
     * @param startIndex Index of the first character within the text document.
     * @param endIndex Index after the last character within the text document. 
     * @param newSentence Indicates whether the error occurred at the start of a new sentence.
     */
    public ADT_SpellingError(String word, String errorType, String[] suggestions, int startIndex, int endIndex, boolean newSentence) {
        this.word = word;
        this.errorType = errorType;
        this.suggestions = suggestions;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.newSentence = newSentence;
    }

}
