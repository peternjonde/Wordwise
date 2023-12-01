/**
 * An object that represents a user correction.
 * 
 * @author Hayden Wies
 */
public class ADT_UserCorrection {
    

    public String correctedWord; // Correction
    public String correctionType; // see NOTE.txt for possible values
    public ADT_SpellingError spellingError;


    /**
     * A user correciton object
     * 
     * @param correctedWord The corrected version of the word.
     * @param correctionType The type of correction.
     * @param spellingError The related error object.
     */
    public ADT_UserCorrection(String correctedWord, String correctionType, ADT_SpellingError spellingError) {
        this.correctedWord = correctedWord;
        this.correctionType = correctionType;
        this.spellingError = spellingError;
    }

}
