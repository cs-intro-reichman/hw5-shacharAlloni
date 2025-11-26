public class Wordle {

    // Reads all words from dictionary filename into a String array.
    public static String[] readDictionary(String filename) {
		In in = new In(filename);
        String[] dict = in.readAllStrings();

        //printArray(dictionary); // Checking.
        return dict;
    }

    // Choose a random secret word from the dictionary. 
    // Hint: Pick a random index between 0 and dict.length (not including) using Math.random()
    public static String chooseSecretWord(String[] dict) {

        double rand = Math.random();
        double index = rand * dict.length;
        //System.out.println(dict[(int) index]); // Checking.
		return dict[(int) index];
    }

    // Simple helper: check if letter c appears anywhere in secret (true), otherwise
    // return false.
    public static boolean containsChar(String secret, char c) {
        if (secret.indexOf(c) != -1) {
            return true;
        }
		return false;
    }

    // Compute feedback for a single guess into resultRow.
    // G for exact match, Y if letter appears anywhere else, _ otherwise.
    public static void computeFeedback(String secret, String guess, char[] resultRow) {
        char[] guessArr = guess.toCharArray();
        char[] secretArr = secret.toCharArray();

        for(int i = 0; i < secretArr.length; i++) {
            int location = secret.indexOf(guessArr[i]);
            if (guessArr[i] == secretArr[i]) {
                resultRow[i] = 'G';
            }

            else if (location == -1) {
                resultRow[i] = '_';
            }
            else {
                resultRow[i] = 'Y';
            }
            // Better way to write the code according to the real game - Do not match with autograding.
            /*int location = new String(secretArr).indexOf(guessArr[i]);
            if (location == i) {
                resultRow[i] = 'G';
                secretArr[i] = '0'; // Better answer according to the real game.
            }
            else if (location == -1) {
                resultRow[i] = '_';
            }
            else {
                resultRow[i] = 'Y';
                secretArr[location] = '0'; // Better answer according to the real game.
            }
            */
        }
    }

    // Store guess string (chars) into the given row of guesses 2D array.
    // For example, of guess is HELLO, and row is 2, then after this function 
    // guesses should look like:
    // guesses[2][0] // 'H'
	// guesses[2][1] // 'E'
	// guesses[2][2] // 'L'
	// guesses[2][3] // 'L'
	// guesses[2][4] // 'O'
    public static void storeGuess(String guess, char[][] guesses, int row) {
		char[] guessArr = guess.toCharArray();
        for(int i = 0; i < guessArr.length; i++) {
            guesses[row][i] = guessArr[i];
        }
    }

    // Prints the game board up to currentRow (inclusive).
    public static void printBoard(char[][] guesses, char[][] results, int currentRow) {
        System.out.println("Current board:");
        for (int row = 0; row <= currentRow; row++) {
            System.out.print("Guess " + (row + 1) + ": ");
            for (int col = 0; col < guesses[row].length; col++) {
                System.out.print(guesses[row][col]);
            }
            System.out.print("   Result: ");
            for (int col = 0; col < results[row].length; col++) {
                System.out.print(results[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if all entries in resultRow are 'G'.
    public static boolean isAllGreen(char[] resultRow) {
        for(int i = 0; i < resultRow.length; i++) {
            if (resultRow[i] != 'G') {
                return false;
            }
        }
		return true;
    }

    // Prints an array of strings for checking.
    public static void printArray(String[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }

    // Prints an array of chars for checking.
    public static void printArray(char[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }

    // Check if a string is inside array of strings.
    public static boolean isValid(String str) {
        if (str.length() == 5) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

        int WORD_LENGTH = 5;
        int MAX_ATTEMPTS = 6;
        
        // Read dictionary
        String[] dict = readDictionary("dictionary.txt");

        // Choose secret word
        String secret = chooseSecretWord(dict);
        //System.out.println(secret);


        // Prepare 2D arrays for guesses and results
        char[][] guesses = new char[MAX_ATTEMPTS][WORD_LENGTH];
        char[][] results = new char[MAX_ATTEMPTS][WORD_LENGTH];

        //computeFeedback(secret, "LIYJA", results[0]); // Check compureFeedback
        //printArray(results[0]);

        // Prepare to read from the standard input 
        In inp = new In();

        int attempt = 0;
        boolean won = false;

        while (attempt < MAX_ATTEMPTS && !won) {

            String guess = "";
            boolean valid = false;

            // Loop until you read a valid guess
            while (!valid) {
                System.out.print("Enter your guess (5-letter word): ");
                guess = inp.readString();

                if (isValid(guess) == false) {
                    System.out.println("Invalid word. Please try again.");
                } else {
                    valid = true;
                }
            }

            storeGuess(guess, guesses, attempt);
            computeFeedback(secret, guess, results[attempt]);

            // Print board
            printBoard(guesses, results, attempt);

            // Check win
            if (isAllGreen(results[attempt])) {
                System.out.println("Congratulations! You guessed the word in " + (attempt + 1) + " attempts.");
                won = true;
            }

            attempt++;
        }

        if (!won) {
            System.out.println("Sorry, you did not guess the word.");
            System.out.println("The secret word was: " + secret);
        }

        inp.close();
    }
}
