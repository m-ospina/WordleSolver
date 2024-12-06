import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class WordleSolver {

    //Iterates through the list of words and outputs all viable words to a new arraylist.
    public static ArrayList<String> findValidWords (ArrayList<String> wordleWordBank, String guess, String hints) {
        ArrayList<String> validWords = new ArrayList <String>();

        for (String currentWord : wordleWordBank) {
            if (checkRequirements(currentWord, guess, hints)) {
                validWords.add(currentWord);
            }
        }

        return validWords;
    }

    //Uses green yellow and gray hints to return false whenever words do not follow hint guidelines.
    private static boolean checkRequirements (String currentWord, String guess, String hints) {  
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char hintChar = hints.charAt(i);

        switch (hintChar) {
            case 'g':
                if (currentWord.charAt(i) != guessChar) {
                    return false;
                } 
                break;
            
            case 'y':
                if (currentWord.charAt(i) == guessChar || !currentWord.contains(String.valueOf(guessChar))) {
                    return false;
                }
                break;
            
            case 'x':
                if (currentWord.contains(String.valueOf(guessChar))) {
                    return false;
                }
                break;
        
            }
        }

        return true;
    }

    //Validates that both guess and hint are 5 letters long.
    public static boolean validateLength (String guess, String hint) {
        boolean sameLength;

        if (guess.length() == 5 && hint.length() == 5) {
            sameLength = true;
        }
        else {
            sameLength = false;
        }

        return sameLength;
    }

    //Validates that all characters in the hint represent green yellow or gray.
    public static boolean validateHint (String guess, String hint) {
        boolean validChars = false;

        for (int c = 0; c < hint.length(); c++) {
            char currChar = hint.charAt(c);
            if (currChar == 'g' || currChar == 'y' || currChar == 'x') {
                validChars = true;
            }
            else {
                validChars = false;
                break;
            }
        }

        return validChars;
    }

    public static void promptForHint() {
        System.out.println("\nInput all hints gained from this guess. \nType \"g\" for Green, \"y\" for Yellow, and \"x\" for Gray.");
    }

    public static void lengthErrorMessage() {
        System.out.println("\nInvalid length for guess, please provide a valid guess.");
    }

    public static void characterErrorMessage() {
        System.out.println("\nInvalid character, please try again and ensure your characters are valid.");
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fileStream = new FileInputStream("wordle_wordbank.txt");
        Scanner fileReader = new Scanner(fileStream);
        Scanner scnr = new Scanner (System.in);
        ArrayList<String> wordleWordBank = new ArrayList<String> ();
        ArrayList<String> updatedWordBank = new ArrayList<String>();

        //Reads all possible wordle words from input from a file.
        while (fileReader.hasNextLine()) { 
            String currentWord = fileReader.nextLine();
            wordleWordBank.add(currentWord);
        }

        fileStream.close();
        fileReader.close();
        
        //Loop that goes through all six possible guesses and alerts user what words are possible to guess based on the hints they've acquired.
        for (int i = 1; i <= 6; i++) { 
            if (i == 1) {
                System.out.println("Welcome! Please provide your first guess!");
            }
            else {
                System.out.println("\nAfter guessing a word from this list, please provide your next guess.");
            }

            String guess = scnr.nextLine();
            
            //Prompts user to reenter guess until length is valid.
            while (guess.length() != 5) {
                lengthErrorMessage();
                guess = scnr.nextLine();
            }

            promptForHint();
            String hint = scnr.nextLine();
            
            //Initializes boolean values that validate hint characters and length using validation methods.
            boolean isValidHint = validateHint(guess, hint);
            boolean isSameLength = validateLength(guess, hint);

            //If false, returns an error message prompting user to reenter hint characters until length matches guess length.
            while (!isSameLength) {
                lengthErrorMessage();
                promptForHint();
                hint = scnr.nextLine();
                isSameLength = validateLength(guess, hint);
            }

            //If false, returns an error message prompting user to reenter hint characters until all characters match valid character choices.
            while (!isValidHint) {
                characterErrorMessage();
                promptForHint();
                hint = scnr.nextLine();
                isValidHint = validateHint(guess, hint);
            }

            //Once all letters are green the word is correct and the code breaks.
            if (hint.equals("ggggg")) { 
                break;
            }
            
            //Maintains hints from previous guesses in the same ArrayList and updates all previous valid words, rather than remake the ArrayList on every iteration.
            if (i == 1) {
                updatedWordBank = findValidWords(wordleWordBank, guess, hint);
            }
            else {
                updatedWordBank = findValidWords(updatedWordBank, guess, hint);
            }

            System.out.println("\nPossible words after guess " + i + ":\n" + updatedWordBank);
        }

        scnr.close();

        //Outputs a congratulatory message when the user inputs all green letters
        System.out.println("\nCongratulations on successfully guessing today's wordle!");
    }
}
