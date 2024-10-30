/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 * 
 * 	This class contains various methods that are used to operate on 
 * 	different arrays of pegs, for example setting certain values,
 * 	checking for matches, etc.
 *
 *  @author	Chloe He
 *  @since	September 27, 2024
*/

public class PegArray {

	// array of pegs
	private Peg [] pegs;
	private int pegnumber;

	// the number of exact and partial matches for this array
	// as matched against the master.
	// Precondition: these values are valid after getExactMatches() and getPartialMatches()
	//	are called
	private int exactMatches, partialMatches;
		
	/**
	 *	Constructor
	 *	@param numPegs	number of pegs in the array
	 */
	public PegArray(int numPegs) {
		pegnumber = numPegs;
		pegs = new Peg [numPegs];
		for (int i = 0; i < numPegs; i++) {
			Peg peg = new Peg(' ');
			pegs[i] = peg;
		}
			
	}
	
	/**
	 *	Return the peg object
	 *	@param n	The peg index into the array
	 *	@return		the peg object
	 */
	public Peg getPeg(int n) { 
		return pegs[n]; 
	}
	
	/**
	 *  Finds exact matches between master (key) peg array and this peg array
	 *	Postcondition: field exactMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 */
	public void getExactMatches(PegArray master) { 
		int count = 0;
		for (int i = 0; i < pegnumber; i++) {
			if (getPeg(i).getLetter()==master.getPeg(i).getLetter()) count++;
		}
		exactMatches = count; 
	}
	
	/**
	 *  Find partial matches between master (key) peg array and this peg array
	 *	Postcondition: field partialMatches contains the matches with the master
	 *  @param master	The master (code) peg array
	 *	@return			The number of partial matches
	 */
	public void getPartialMatches(PegArray master) { 
		int count = 0;
		int [] freq2 = new int[6];
		
		for (int i = 0; i < 6; i++) freq2[i] = 0;
		
		for (int i = 0; i < pegnumber; i++) {
			freq2[master.getPeg(i).getLetter() - 65]++;
		}
		
		for (int i = 0; i < pegnumber; i++) {
			if (pegs[i].getLetter() != master.getPeg(i).getLetter() && freq2[pegs[i].getLetter()-65] > 0) {
				count++;
				freq2[getPeg(i).getLetter() - 65] --;
			}
		}
		
		partialMatches = count; 
		
	}
	
	// Accessor methods
	// Precondition: getExactMatches() and getPartialMatches() must be called first
	public int getExact() { return exactMatches; }
	public int getPartial() { return partialMatches; }
	
	/**
	 * Method that sets pegs to certain values
	 * @param ind	index that you want the peg to be set at
	 * @param peg	value that you want the peg to be set at
	 */
	public void setPeg(int ind, Peg peg) {
		pegs[ind] = peg;
	}
	
	/** 
	 * Method that prints the pegs
	 */
	public void printPegs() {
		for (int i = 0; i < pegnumber; i++) {
			System.out.print(getPeg(i).getLetter() + " ");
		} System.out.println();
	}

}
