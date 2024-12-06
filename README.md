# What is the WordleSolver?
This program is used to help users solve the daily Wordle more efficiently by making use of user guesses and hints gained from user input and outputting all viable words. 

Viable words are found after comparing all words from the predetermined list of possible answers and analyzing if the words follow the guidelines of the hints gained up until that point. 

## How does the WordleSolver work? 
The main method calls on the text file containing all the possible answers and saves them to an ArrayList for comparing.

Then, in the main loop, the program welcomes the user to the WordleSolver and asks the user to provide their first guess.

User input must include a five-letter word and a five-letter group of hints that represent the colors of their hints. Green letters are signified by 'g', yellow by 'y', and gray by 'x'.

Based on user input, the information gained from the colors of the hints will allow the program to find all viable words for subsequent guesses and output these words to the user, asking that they use these words for a more efficient Wordle experience.

## Validation 
Methods such as _validateLength_ and _validateHint_ check to see that both guess and hint are five characters long and that the letters of the hint solely consist of g, y, and x. When hints are valid, a true boolean value is output, and if false, a false value is output.

## Error Handling
Error handling is controlled by while loops that use these boolean values and when false, pauses the main loop to prompt the user to input a valid guess and hint. 
- When conditions are not met, error messages are output through the _lengthErrorMessage_ and _characterErrorMessage_ methods which then call the _promptForHint_ method, notifying the user that their inputs are not valid so that the user may provide valid inputs.

## Finding all possible guesses
Once the inputs are valid, the _checkRequirements_ method compares the letters of the guess and their respective hints, validating whether the words in the word bank follow the green, yellow, and gray guidelines. This is, that green letters are correctly placed, yellow letters are present but incorrectly placed, and gray letters are absent from the answer.

## Output
All of the words that meet the requirements of the hints are then saved to a new ArrayList in the _findValidWords_ method, which then is used to output all viable guesses to the user. The program asks that the user make use of one of the viable words in their next guess. 

## Success!
The main loop runs until the user's hint matches **_"ggggg"_** signifying that all letters of their guess are green and that they have correctly guessed today's Wordle. Once this input is received, the code breaks and outputs a congratulatory message for the user's success in guessing the Wordle. 
