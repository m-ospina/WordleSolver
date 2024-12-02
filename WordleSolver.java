import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class WordleSolver {
    //Iterates through the list of words and adds/outputs all viable words to a new arraylist
    public static ArrayList<String> findValidWords (ArrayList<String> wordleWordBank, String guess, String hints) {
        ArrayList<String> validWords = new ArrayList <String>();

        for (String currentWord : wordleWordBank) {
            if (checkRequirements(currentWord, guess, hints)) {
                validWords.add(currentWord);
            }
        }

        return validWords;
    }

    //Uses green yellow and gray hints to return false whenever words do not follow the hint.
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

    public static void main(String[] args) throws IOException {
        FileInputStream fileStream = new FileInputStream("wordle_wordbank.txt");
        Scanner fileReader = new Scanner(fileStream);
        Scanner scnr = new Scanner (System.in);
        ArrayList<String> wordleWordBank = new ArrayList<String> ();
        ArrayList<String> updatedWordBank = new ArrayList<String>();

        //Reads all possible wordle words from input from a file
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
                System.out.println("After guessing a word from this list, please provide your next guess.");
            }

            String guess = scnr.nextLine();

            System.out.println("\nInput all hints gained from this guess. \nType \"g\" for Green, \"y\" for Yellow, and \"x\" for Gray.");

            String hint = scnr.nextLine();
            
            //Boolean validates that guess and hint are same length. Checks that all characters in hint are valid characters.
            boolean isValidHint = (hint.length() == guess.length()) && (hint.chars().allMatch(c -> c == 'g' || c == 'x' || c == 'y')); 

            //While hint is not valid the program will prompt user to enter their hint until it is validated
            while (!isValidHint) {
                System.out.println("\nInvalid character or length, please try again and ensure your inputs are valid");
                System.out.println("\nInput all hints gained from this guess. \nType \"g\" for Green, \"y\" for Yellow, and \"x\" for Gray.");
                hint = scnr.nextLine();
                isValidHint = (hint.length() == guess.length()) && (hint.chars().allMatch(c -> c == 'g' || c == 'x' || c == 'y'));
            }


            if (hint.equals("ggggg")) { //Once all letters are green the word is correct and the code breaks.
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

        System.out.println("\nCongratulations on successfully guessing the wordle today!");
    }
}
