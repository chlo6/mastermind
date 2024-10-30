/**
 *	Plays the game of MasterMind.
 * 	Objective: Guess the unknown 4-letter code, consisting of 6 
 * 	possible letters (A, B, C, D, E, F) in 10 turns or less. The letters 
 * 	can repeat, and some examples of codes are ABCD, FDDE, and BBBB.  
 * 
 * 	For each turn, you are prompted to guess, then a board will be 
 * 	displayed. This board contains your previous and current guesses, 
 * 	along with how many letters of each guess were partially correct 
 * 	(same letter, different position) or completely correct (same 
 * 	letter, same position). For example, if the 4-letter code was ABDC, 
 * 	and your guess was DBBF, then you would have 1 partially correct 
 * 	letter (D) and 1 fully correct letter (the first B). 
 * 
 * 	To run, cd into the directory with MasterMind.java, Dice.java, 
 * 	Prompt.java, Peg.java, and PegArray.java, then type in your terminal 
 * 	"javac -source 1.8 -target 1.8 MasterMind.java; java MasterMind" and 
 * 	it should run
 *
 *	@author	Chloe He
 *	@since	September 27, 2024
 */


public class MasterMind {

	private boolean reveal;			// true = reveal the master combination
	private PegArray[] guesses;		// the array of guessed peg arrays
	private PegArray master;		// the master (key) peg array
	
	private String inputStr = "";		// stores current guess
	private boolean gameLost = false;	// if the game was lost
	private boolean gameWon = false;	// if the game was won
	private int turn = 1;				// the # turn you are currently on

	// Constants
	private final int PEGS_IN_CODE = 4;		// Number of pegs in code
	private final int MAX_GUESSES = 10;		// Max number of guesses
	private final int PEG_LETTERS = 6;		// Number of different letters on pegs
											// 6 = A through F

	/**
	 * 	Constructor
	 * 	Initializes field variables, e.g. master PegArray and guesses
	 * 	PegArray
	 */
	public MasterMind() {
		master = new PegArray(PEGS_IN_CODE);
		guesses = new PegArray[MAX_GUESSES];
		for (int i = 0; i < MAX_GUESSES; i++) guesses[i] = new PegArray(PEGS_IN_CODE);
	}
	
	/** 
	 * 	Main class
	 * 	Calls the method that runs the game
	 */
	public static void main (String [] args) {
		MasterMind run = new MasterMind();
		run.playGame();
	}
	
	/** 
	 * 	Method that runs most of the game
	 * 	Calls other methods (e.g. for playing each turn and getting
	 * 	the user input) in order, checks if the game has been completed
	 */
	public void playGame() {
		printIntroduction();
		Prompt.getString("Hit the Enter key to start the game");

		printBoard();
		genMasterCode();
		
		
		while (!gameWon && !gameLost) {
			System.out.println("\nGuess " + turn + "\n");
			userInput();
			playTurn();
			printBoard();
			
			turn++;			
			if (turn > MAX_GUESSES) gameLost = true; 
		}
		
		if (gameWon) {
			turn--;
			System.out.println("\nNice work! You found the master code in " + turn + " guesses.");
		} else System.out.println("Oops. You were unable to find the solution in 10 guesses.");
	}

	/**
	 *	Print the introduction screen
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| ___  ___             _              ___  ___ _             _                       |");
		System.out.println("| |  \\/  |            | |             |  \\/  |(_)           | |                      |");
		System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | .  . | _  _ __    __| |                      |");
		System.out.println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| |\\/| || || '_ \\  / _` |                      |");
		System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | |  | || || | | || (_| |                      |");
		System.out.println("| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   \\_|  |_/|_||_| |_| \\__,_|                      |");
		System.out.println("|                                                                                    |");
		System.out.println("| WELCOME TO MONTA VISTA MASTERMIND!                                                 |");
		System.out.println("|                                                                                    |");
		System.out.println("| The game of MasterMind is played on a four-peg gameboard, and six peg letters can  |");
		System.out.println("| be used.  First, the computer will choose a random combination of four pegs, using |");
		System.out.println("| some of the six letters (A, B, C, D, E, and F).  Repeats are allowed, so there are |");
		System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations.  This \"master code\" is then hidden     |");
		System.out.println("| from the player, and the player starts making guesses at the master code.  The     |");
		System.out.println("| player has 10 turns to guess the code.  Each time the player makes a guess for     |");
		System.out.println("| the 4-peg code, the number of exact matches and partial matches are then reported  |");
		System.out.println("| back to the user. If the player finds the exact code, the game ends with a win.    |");
		System.out.println("| If the player does not find the master code after 10 guesses, the game ends with   |");
		System.out.println("| a loss.                                                                            |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME MASTERMIND!                                                        |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
	}

	
	/**
	 * 	Method that generates the master code, uses dice to generate
	 * 	a number between 1-6 (representing a-f), then converts it to a 
	 * 	char
	 */ 
	public void genMasterCode() {
		Dice dice = new Dice();
		for (int i = 0; i < 4; i++) {
			Peg peg = new Peg((char)(dice.roll() + 64));
			master.setPeg(i, peg);
			
		}
	}
	
	/**
	 *	Print the peg board to the screen
	 */
	public void printBoard() {
		// Print header
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
		System.out.print("| MASTER |");
		for (int a = 0; a < PEGS_IN_CODE; a++)
			if (reveal)
				System.out.printf("   %c   |", master.getPeg(a).getLetter());
			else
				System.out.print("  ***  |");
		System.out.println(" Exact Partial |");
		System.out.print("|        +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("               |");
		// Print Guesses
		System.out.print("| GUESS  +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------|");
		for (int g = 0; g < MAX_GUESSES - 1; g++) {
			printGuess(g);
			System.out.println("|        +-------+-------+-------+-------+---------------|");
		}
		printGuess(MAX_GUESSES - 1);
		// print bottom
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
	}
	
	/**
	 *	Print one guess line to screen
	 *	@param t	the guess turn
	 */
	public void printGuess(int t) {
		System.out.printf("|   %2d   |", (t + 1));
		// If peg letter in the A to F range
		char c = guesses[t].getPeg(0).getLetter();
		if (c >= 'A' && c <= 'F') {
			for (int p = 0; p < PEGS_IN_CODE; p++){
				System.out.print("   " + guesses[t].getPeg(p).getLetter() + "   |");
			}
			guesses[t].getExactMatches(master);
			guesses[t].getPartialMatches(master);
		}
		// If peg letters are not A to F range
		else {
			for (int p = 0; p < PEGS_IN_CODE; p++) {
				System.out.print("       |");
			}
		}
		
		System.out.printf("   %d      %d    |\n", guesses[t].getExact(), guesses[t].getPartial());
		if (guesses[t].getExact() == 4) {
			gameWon = true;
		}
	}
	
	/**
	 * 	Gets user's input, checks its validity
	 */
	public void userInput() {
		boolean found = false;
		while (!found) {
			found = true;
			inputStr = Prompt.getString("Enter the code using (A,B,C,D,E,F). For example, ABCD or abcd from left-to-right");
			if (inputStr.length() != PEGS_IN_CODE) found = false;
			else {
				for (int i = 0; i < PEGS_IN_CODE; i++) {
					char ch = Character.toLowerCase(inputStr.charAt(i));
					if (ch < 'a' || ch > 'f') found = false;
				}
			} 
			inputStr = inputStr.toUpperCase();
			
			if (!found) System.out.println("ERROR: Bad input, try again.");
		}
	}
	
	/**
	 * 	Plays each turn by storing the guessed pegs in the guesses 
	 * 	PegArray
	 */
	public void playTurn() {
		for (int i= 0; i < PEGS_IN_CODE; i++) {
			Peg guessPeg = new Peg(inputStr.charAt(i));
			guesses[turn-1].setPeg(i, guessPeg);
		}
	}
}
