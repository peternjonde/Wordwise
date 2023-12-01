import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * This class will create a new Dictionary object to be initialized with the launch of the WordWise program.
 * Each word from the dictionary will be loaded from the 'words_alpha.txt' file (which was provided in the project specification) as a unique value into a hashset.
 * There is also a search method which will check if a given word exists in the dictionary.
 * 
 * @author Tanya Pant
 */
public class MODEL_Dictionary {
    

    private String fileName = "words_alpha.txt"; // The file from which the dictionary will be sourced is the 'words_alpha.txt' file.
    private Set<String> dictionary = new HashSet<>(); // Initialize the hashset which will be used to store each unique word.


    /**
     * Constructor to build the dictionary object upon initialization.
     */
    public MODEL_Dictionary() {
        this.build();
    }


    /**
     * This method will access each individual word from the text file and set each word to lower case, since upper case values 
     * will be handled by the spell check class).
     * 
     * @param String word which needs to be normalized (trimmed and set to lowercase)
     * @return String of the normalized word
     */
    private String normalize(String word) {
        return word.toLowerCase().trim();
    }


    /**
     * This method will open the file and read from it using a BufferedReader and FileWriter. Then, the hashset add() function is used 
     * to add unique values to the hashset.
     */
    private void build() {
        String fileName = this.fileName;
        File file = new File(fileName);

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String word;
            while ((word = bufferedReader.readLine()) != null) {
                this.dictionary.add(this.normalize(word));
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method will search for a given word in the hashset and return either true or false, depending on whether the word is in the dictionary or not.
     * In order to look for the word properly within the hashset, the normalize function will be applied, since the in-built dictionary only contains 
     * lower case letters.
     * 
     * @param String word which needs to be normalized, then searched for in the dictionary hashset
     * @return boolean true if the word is in the hashset or false if it is not
     */
    public boolean search(String word) {
        return this.dictionary.contains(this.normalize(word));
    }

    
}
