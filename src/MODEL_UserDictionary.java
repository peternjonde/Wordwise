import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * This class will create a new UserDictionary object to be initialized with the launch of the WordWise program.
 * This User Dictionary will be stored in a text file called 'user_dict.txt' in which the user can add words in the case that the system dictionary 
 * does not recognize a word which the user intends to be correct.
 * The user also has the option to remove a word from the User Dictionary, as well as clear all words from it.
 * Any edits to this User Dictionary will persist through subsequent sessions with the app.
 * 
 * @author Tanya Pant
 */
public class MODEL_UserDictionary {
    

    private String fileName = "user_dict.txt"; // The file from which the dictionary will be sourced is the 'user_dict.txt' file.
    private Set<String> dictionary = new HashSet<>(); // Initialize the hashset which will be used to store each unique word.


    /**
     * Constructor to build the dictionary object upon initialization.
     */
    public MODEL_UserDictionary() {
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
        File file = new File(this.fileName);

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
     * @param word word which needs to be normalized, then searched for in the dictionary hashset
     * @return boolean true if the word is in the hashset or false if it is not
     */
    public boolean search(String word) {
        return this.dictionary.contains(this.normalize(word));
    }


    /**
     * This method will add a word into the 'user_dict.txt' file by directly writing to it using a Buffered Writer and File Writer.
     * Before the word is added, it will be normalized (trimmed and made all lower case) to match the convention of the dictionary class,
     * therefore allowing the upper case letters to be handled by the spell checker class.
     * 
     * @param addedWord addedWord which is the word that the user wants to add to the User Dictionary
     */
    public void add(String addedWord) {
        File file = new File(this.fileName); // File object referencing user dict

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.append(this.normalize(addedWord));
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.build();
    }


    /**
     * This method will remove a word from the user_dict.txt file in three steps. Firstly, it will read from the 'user_dict.txt' file using a Buffered Reader 
     * and File Reader, then it will write that word to a temporary file called 'temp.txt'. If the word to be removed is found, then it will not be written to 
     * the text file. Finally, once the whole 'user_dict.txt' file has been read, it will be deleted and the 'temp.txt' file will be renamed to 'user_dict.txt'.
     * 
     * @param removedWord removedWord which is the word that the user wants to remove from the User Dictionary.
     */
    public void remove(String removedWord) {
        File file = new File(this.fileName); // File object referencing user dict
        File tempFile = new File("temp.txt"); // Temp file object representing updated user dict

        try {
            FileReader fileReader = new FileReader(file);
            FileWriter fileWriter = new FileWriter(tempFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String word;
            while ((word = bufferedReader.readLine()) != null) {
                if (this.normalize(word).equals(this.normalize(removedWord)) == false) {
                    bufferedWriter.append(word);
                    bufferedWriter.newLine();
                }
            }

            file.delete();
            tempFile.renameTo(file);

            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.build();
    }


    /**
     * This method will remove all the words from the 'user_dict.txt' file by overwriting all of the words in the dictionary with an empty line.
     * The overwriting will be done with a Buffered Writer and File Writer.
     */
    public void reset() {
        File file = new File(this.fileName);

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("");
            
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.build();
    }


}
