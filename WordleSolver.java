import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class WordleSolver {
    public static void findValidWords (ArrayList<String> wordleWordBank, String guess, String hints) { //Interates through the list of words and adds/outputs all viable words to a new arraylist
        ArrayList<String> validWords = new ArrayList <String>();

        for (String currentWord : wordleWordBank) {
            if (checkRequirements(currentWord, guess, hints)) {
                validWords.add(currentWord);
            }
        }

        wordleWordBank.clear();
        wordleWordBank.addAll(validWords);
    }

    private static boolean checkRequirements (String currentWord, String guess, String hints) { //Uses green yellow and gray hints to return false whenever words do not follow the hint. 
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
        FileInputStream fileStream = new FileInputStream("C:/Users/Nicholas/Desktop/m-ospina/WordleSolver/wordle_wordbank.txt");
        Scanner fileReader = new Scanner(fileStream);
        Scanner scnr = new Scanner (System.in);
        ArrayList<String> wordleWordBank = new ArrayList<String>();

        while (fileReader.hasNextLine()) { //Reads all possible wordle words from input from a file
            String currentWord = fileReader.nextLine();
            wordleWordBank.add(currentWord);
        }

        fileStream.close();
        fileReader.close();
        
        for (int i = 1; i <= 6; i++) { //Loop that goes through all six possible guesses and alerts user what words are possible to guess based on the hints they've acquired.

            if (i == 1) {
                System.out.println("Welcome! Please provide your first guess!");
            }
            else {
                System.out.println("After guessing a word from this list, please provide your next guess.");
            }

            String guess = scnr.nextLine();

            System.out.println("\nInput all hints gained from this guess. \nType \"g\" for Green, \"y\" for Yellow, and \"x\" for Gray.");
            String hint = scnr.nextLine();

            // Nick Was Here ---------------------------------------------
            boolean isValidHint = hint.length() == guess.length() && hint.chars().allMatch(c -> c == 'g' || c == 'x' || c == 'y');

            while (!isValidHint) {
                System.out.println("Invalid character or length, please ensure your inputs are valid");
                System.out.println("\nInput all hints gained from this guess. \nType \"g\" for Green, \"y\" for Yellow, and \"x\" for Gray.");
                hint = scnr.nextLine();
                isValidHint = hint.length() == guess.length() && hint.chars().allMatch(c -> c == 'g' || c == 'x' || c == 'y');
            }
            // Nick Stopped Here ----------------------------------------

            if (hint.equals("ggggg")) { //Once all letters are green the word is correct and the code breaks.
                break;
            }

            findValidWords(wordleWordBank, guess, hint);
            System.out.println("\nPossible words after guess " + i + ":\n" + wordleWordBank);
        }

        scnr.close();

        System.out.println("\nCongratulations on successfully guessing the wordle today!");
    }
}
