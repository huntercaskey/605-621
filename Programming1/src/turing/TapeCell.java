package turing;

/**
 * @author Hunter R. Caskey
 * 
 * Class representing a cell in a tape on a Turing machine. This class serves as the atomic data element in the Tape data structure. This class is modeled after the element component of a double linked list, 
 * i.e. it contains pointers to the previous and next cells in the tape.
 *
 */
public class TapeCell {
	
	public char symbol;
	public TapeCell next;
	public TapeCell prev;
	
	/**
	 * Constructor for a new tape cell. By default the contents of a cell are assigned to the blank symbol 'b'.
	 */
	public TapeCell() {
		this.symbol = 'b';
	}
	
}

