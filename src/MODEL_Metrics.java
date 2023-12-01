/**
 * Logging class to keep track of metrics.
 * 
 * @author Peter Njonde, Hayden Wies
 */
public class MODEL_Metrics {


    private int misspelled = 0;
    private int miscapitalized = 0;
    private int dub = 0;
    private int suggestion = 0;
    private int custom = 0;
    private int ignore = 0;
    private int ignoreall = 0;
    private int save = 0;
    private int delete = 0;


    /**
     * Updates the number of errors in the document upon spell-checking.
     * 
     * @param spellingError All errors in the document.
     */
    public void addErrors(ADT_SpellingError[] allSpellingErrors) {

        for (ADT_SpellingError spellingError : allSpellingErrors) {
             if (spellingError.errorType.equals("misspelled")) {
                misspelled++;
            } else if (spellingError.errorType.equals("miscapitalized")) {
                miscapitalized++;
            } else if (spellingError.errorType.equals("double")) {
                dub++;
            }
        }
    }


    /**
     * Updates correction count.
     * 
     * @param correction Correction to be added.
     */
    public void addCorrection(ADT_UserCorrection correction) {

        // Correction type increase
        if (correction.correctionType.equals("suggestion")) {
            suggestion++;
        } else if (correction.correctionType.equals("custom")) {
            custom++;
        } else if (correction.correctionType.equals("ignore")) {
            ignore++;
        } else if (correction.correctionType.equals("ignoreall")) {
            ignoreall++;
        } else if (correction.correctionType.equals("save")) {
            save++;
        } else if (correction.correctionType.equals("delete")) {
            delete++;
        }       

    }

    /**
     * Gets all stats from the document and editing process.
     * 
     * @param controller Controller for the application
     */
    public String[][] getStats(Controller controller) {

        String doc = controller.getWorkingDocument() + " ";
        int numCharacters = 0;
        int numWords = 0;
        int numLines = 0;

        boolean isInBrackets = false;
        boolean isWord = false;
        String extension = controller.getExtension();
        for (int i = 0; i < doc.length(); i++) {

            char c = doc.charAt(i);
            if (extension == "html" || extension == "xml") {
                if (c == 60) isInBrackets = true;
                if (c == 62) isInBrackets = false;
            }

            if (Character.isAlphabetic(c)) {
                isWord = true;
            } else if (i > 0 && Character.isAlphabetic(doc.charAt(i-1))) {
                isWord = true;
            } else {
                isWord = false;
            }

            if (c != 32 && c != 10 && isInBrackets == false) numCharacters++;

            if (isWord == true && Character.isAlphabetic(c) == false) numWords++;
            // if ((isWord == true && (c == 32 || c == 10)) || (i == doc.length()-1) && isInBrackets == false) numWords++;

            if (c == 10 && isInBrackets == false) numLines++;

        }

        // 2D array of metrics
        String[][] metrics = {
                { "Characters", String.valueOf(numCharacters) },
                { "Words", String.valueOf(numWords) },
                { "Lines", String.valueOf(numLines) },
                { "Misspellings", String.valueOf(misspelled) },
                { "Miscapitalizations", String.valueOf(miscapitalized) },
                { "Double words", String.valueOf(dub) },
                { "Suggested corrections", String.valueOf(suggestion) },
                { "Custom corrections", String.valueOf(custom) },
                { "'Ignored' corrections", String.valueOf(ignore) },
                { "'Ignored all' corrections", String.valueOf(ignoreall) },
                { "Saves to dictionary", String.valueOf(save) },
                { "Deletes", String.valueOf(delete) }

        };

        return metrics;
    }

}
