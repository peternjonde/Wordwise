import java.util.HashSet;
import java.util.Set;


/**
 * Object to correct the document.
 * 
 * @author Hayden Wies, Peter Njonde, Ophelie Gross
 */
public class MODEL_Corrector {

    
    MODEL_Dictionary dictionary;
    MODEL_UserDictionary userDictionary;


    /**
     * Cretes a new corrector object.
     * @param userDictionary The instance of the user dicitonary used within the application.
     */
    public MODEL_Corrector(MODEL_UserDictionary userDictionary) {
        this.userDictionary = userDictionary;
        this.dictionary = new MODEL_Dictionary();
    }

    
    /**
     * Algorithm to correct a provided document.
     * @param controller The application controller.
     * @param workingDocument The document to be corrected.
     * @return A list of all errors in the document.
     */
    public ADT_SpellingError[] correctDocument(Controller controller, String workingDocument) {
        String doc = workingDocument + " "; // Add space in case final word of document butts up against the very end of the string
        String extension = controller.getExtension();
        ADT_SpellingError[] errors = {};

        String word = "";
        String prevWord = "";
        int startIdx = -1;
        boolean newSentence = true;

        boolean outsideAngles = true; // Used for html and xml to identify when text is outside of angle brackets

        int i = 0;
        while (i < doc.length()) {
            char c = doc.charAt(i);

            if (Character.isAlphabetic(c) || c == 39) {
                if (extension.equals("html") || extension.equals("xml")) {
                    if (outsideAngles == true) {
                        if (startIdx == -1) startIdx = i;
                        word = word + c;
                    }
                } else {
                    // Is a character or '
                    if (startIdx == -1) startIdx = i;
                    word = word + c;
                }
            } else {
                if (word != "") {
                    // Check for error
                    ADT_SpellingError err = this.checkWord(word, prevWord, startIdx, i, newSentence);

                    if (err != null) {
                        // Create new array that has been resized
                        ADT_SpellingError[] newErrors = new ADT_SpellingError[errors.length + 1];
                        // Copy contents
                        for (int j = 0; j < errors.length; j++) newErrors[j] = errors[j];
                        // Add new error
                        newErrors[errors.length] = err;
                        // Update errors list
                        errors = newErrors;
                    }
                    // Reset variables
                    startIdx = -1;
                    newSentence = false;
                    prevWord = word;
                    word = "";
                }
                // If char is . or LF
                if (c == 46 || c == 10) newSentence = true;
                if (c == 60) {
                    if (controller.getEditInAngles() == false && (extension.equals("html") || extension.equals("xml"))) {
                        outsideAngles = false;
                    }
                }
                if (c == 62) {
                    if (extension.equals("html") || extension.equals("xml")) {
                        outsideAngles = true;
                    }
                }
            }

            i++;
        }

        return errors;
    }


    /**
     * Chceks a word within the document (word must be alphabetic).
     * 
     * @param word The word to be checked.
     * @param prevWord The previous word.
     * @param startIdx The start index within the document.
     * @param endIdx The end index within the document.
     * @param newSentence Declares whether the word is the start of a new sentence.
     * @return A spelling error if it exists, otherwise null.
     */
    private ADT_SpellingError checkWord(String word, String prevWord, int startIdx, int endIdx, boolean newSentence) {
        String strippedApostroph = ""; // Removes ' from word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c != 39) strippedApostroph = strippedApostroph + c;
        }

        String lowerCasedWord = strippedApostroph.toLowerCase(); // Lowercased word used to check dictionary
    
        
        // Check if the word is misspelled
        if (isMisspelled(lowerCasedWord)) {
            String[] suggestions = {};
            // String[] suggestions = makeSuggestions(word, "misspelled", false);
            return new ADT_SpellingError(word, "misspelled", suggestions, startIdx, endIdx, false);
        }
        
        // Check if the word is miscapitalized
        else if (isMiscapitalized(strippedApostroph, newSentence)) {
            String[] suggestions = {};
            // String[] suggestions = makeSuggestions(word, "miscapitalized", newSentence);
            return new ADT_SpellingError(word, "miscapitalized", suggestions, startIdx, endIdx, newSentence);
        }
    
        // Check if the word is a double word
        else if (isDouble(strippedApostroph, prevWord, newSentence)) {
            String[] suggestions = {};
            // String[] suggestions = makeSuggestions(word, "double", false);
            return new ADT_SpellingError(word, "double", suggestions, startIdx, endIdx, false);
        }
    
        // If none of the above conditions are met, return null (indicating no error)
        return null;
    }
    

    /**
     * Checks if a word is misspelled.
     * 
     * @param word The word to be checked.
     * @return True if word is misspelled.
     */
    private boolean isMisspelled(String word) {
        if (this.dictionary.search(word) == false && this.userDictionary.search(word) == false) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Checks if a word is the start of a new sentence.
     * 
     * @param text The document text.
     * @param wordStartIndex The start index of the word.
     * @return True if the word is the start of a new sentence.
     */
    public boolean isNewSentence(String text, int wordStartIndex) {
        // The word is at the start of the document.
        if (wordStartIndex == 0) return true;
    
        // Look for sentence terminators before the current word.
        for (int i = wordStartIndex - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (c == '.' || c == '!' || c == '?') {
                // Check if there's whitespace after the terminator.
                // If so, it's a new sentence.
                if (text.substring(i + 1, wordStartIndex).trim().isEmpty()) {
                    return true;
                } else {
                    // If there's no whitespace, it's not a new sentence.
                    return false;
                }
            } else if (!Character.isWhitespace(c)) {
                // If we encounter a non-whitespace character before a sentence terminator, it's not a new sentence.
                return false;
            }
        }
    
        // If we don't find any sentence terminators before the word, it's not a new sentence.
        return false;
    }
    

    /**
     * Checks if a word is miscapitalized.
     * 
     * @param word The word to be checked.
     * @param newSentence Whether the word is start of a new sentence.
     * @return True if is miscapitalized.
     */
    private boolean isMiscapitalized(String word, boolean newSentence) {

        // Check if the word is at the start of a new sentence; it should be capitalized
        if (newSentence && word.length() > 0 && !Character.isUpperCase(word.charAt(0))) {
            // This should not be flagged if the word is capitalized correctly
            return true;
        }
    
        // Check for mixed capitalization within the word, ignoring the first character if it's a new sentence
        int startIndex = newSentence ? 1 : 0;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        for (int i = startIndex; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                hasUppercase = true;
            }
            if (Character.isLowerCase(word.charAt(i))) {
                hasLowercase = true;
            }
    
            // If the word has both uppercase and lowercase characters, it's miscapitalized
            if (hasUppercase && hasLowercase) {
                return true;
            }
        }
    
        // The word is not miscapitalized
        return false;
    }
    
    
    /**
     * Checks if a word is a repeat.
     * 
     * @param word Word to be checked.
     * @param prevWord The previous word.
     * @param newSentence Whether word is start of new sentence.
     * @return True if word is repeat.
     */
    private boolean isDouble(String word, String prevWord, boolean newSentence) {
        
        if (word.toLowerCase().equals(prevWord.toLowerCase()) && newSentence == false){
            return true;
        }

        return false;
    }


    /**
     * Make list of suggestions for an error.
     * 
     * @param word Word for suggestions to be generated for.
     * @param errorType The type of error.
     * @param newSentence Whether word is start of a new sentence.
     * @return A list of suggestions.
     */
    public String[] makeSuggestions(String word, String errorType, boolean newSentence) {
        Set<String> suggestions = new HashSet<>();
    
        switch (errorType) {
            case "misspelled":
                suggestions.addAll(generateMisspellingSuggestions(word));
                break;
            case "miscapitalized":
                suggestions.addAll(handleMixedCapitalization(word, newSentence));
                break;
            case "double":
                // No suggestions needed for double words
                break;
        }
    
        return suggestions.toArray(new String[0]);
    }
    

    /**
     *  Makes a list of suggestions for miscapitalized words.
     * 
     * @param word Word to generate suggesions for.
     * @param newSentence Whether word is start of new sentence.
     * @return List of suggestions.
     */
    private Set<String> handleMixedCapitalization(String word, boolean newSentence) {
        Set<String> suggestions = new HashSet<>();
    
        if (newSentence) {
            // Only capitalize the first letter if the word is at the beginning of a sentence
            if (word.length() > 0) {
                suggestions.add(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase());
            }
        } else {
            // For other miscapitalization cases, suggest different capitalization forms
            suggestions.add(word.toLowerCase());
            suggestions.add(word.toUpperCase());
            if (word.length() > 0) {
                suggestions.add(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase());
            }
        }
    
        return suggestions;
    }


    /**
     *  Creates list of suggestions for misspelled word.
     * 
     * @param word Word to create suggesitons for.
     * @return List of suggestions.
     */
    private Set<String> generateMisspellingSuggestions(String word) {
        int maxSugestions = 5;
        int numSuggestions = 0;
        Set<String> suggestions = new HashSet<>();
    
        // Remove each letter from the word to see if it creates a valid word
        for (int i = 0; i < word.length(); i++) {
            String candidate = word.substring(0, i) + word.substring(i + 1);
            if (isWordInDictionary(candidate) && numSuggestions < maxSugestions) {
                suggestions.add(candidate);
                numSuggestions++;
            }
        }
    
        // Insert each possible letter into each possible position
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String candidate = word.substring(0, i) + c + word.substring(i);
                if (isWordInDictionary(candidate) && numSuggestions < maxSugestions) {
                    suggestions.add(candidate);
                    numSuggestions++;
                }
            }
        }
    
        // Swap each pair of consecutive letters
        for (int i = 0; i < word.length() - 1; i++) {
            if (numSuggestions < maxSugestions) {
                char[] charArray = word.toCharArray();
                char temp = charArray[i];
                charArray[i] = charArray[i + 1];
                charArray[i + 1] = temp;
                String candidate = new String(charArray);
                if (isWordInDictionary(candidate) && numSuggestions < maxSugestions) {
                    suggestions.add(candidate);
                    numSuggestions++;
                }
            } else {
                break;
            }
        }
    
        return suggestions;
    }
    

    /**
     * Checks if word is in the dictionary.
     * 
     * @param word The word to be checked.
     * @return True if word is in dicionary.
     */
    private boolean isWordInDictionary(String word) {
        return this.dictionary.search(word);
    }
    
    
}
