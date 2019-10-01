package turing;

/**
 * @author Hunter R. Caskey
 *
 * Data structure representing the tape component of a Turing machine. The underlying data is modeled as a double linked list where the TapeCell class are the atomic elements of the list.
 */
public class Tape {

	// Cell that is under the read/write head of the Turing machine.
	private TapeCell currentCell;
	
	/**
	 * Default constructor for a Tape object with a single blank cell.
	 */
	public Tape() {
		this.currentCell = new TapeCell();
		this.currentCell.symbol = 'b';
	}
	
	/**
	 * Constructor for a Tape object with initialized tape contents.
	 * 
	 * @param contents The input string to create the Tape object with. Each character in the input string is converted to a cell.
	 */
	public Tape(char[] contents) {
		this.currentCell = new TapeCell();
		
		TapeCell prevCell = new TapeCell();
		prevCell.next = this.currentCell;
		
		TapeCell cellPointer = this.currentCell;
		for(char c : contents) {
			cellPointer.prev = prevCell;
			cellPointer.symbol = c;
			cellPointer.next = new TapeCell();
			prevCell = cellPointer;
			cellPointer = cellPointer.next;
		}
		
		cellPointer.prev = prevCell;
	}
	
	/**
	 * Get the current cell under the read/write head of the machine.
	 * 
	 * @return The cell under the read/write head.
	 */
	public TapeCell currentCell() {
		return this.currentCell;
	}
	
	/**
	 * Read the contents under the read/write head of the TM.
	 * 
	 * @return The symbol in the TapeCell object under the read/write head of the TM.
	 */
	public char readCell() {
		return this.currentCell.symbol;
	}	
	
	/**
	 * Set the contents under the read/write head of the TM.
	 * 
	 * @param ch The new symbol to insert into the read/write head's cell.
	 */
	public void writeCell(char ch) {
		this.currentCell.symbol = ch;
	}

	/**
	 * Shifts the read/write head of the TM to the left by one cell. If the read/write head is at the leftmost position in the tape a new 
	 * cell will be created initialized to a blank 'b'.
	 */
	public void shiftLeft() {
		if(this.currentCell.prev == null) {
			TapeCell newCurrentCell = new TapeCell();
			newCurrentCell.next = this.currentCell;
			newCurrentCell.symbol = 'b';
			this.currentCell.prev = newCurrentCell;
		}
		this.currentCell = this.currentCell.prev;
	}

	/**
	 * Shifts the read/write head of the TM to the right by one cell. If the read/write head is at the rightmost position in the tape a new 
	 * cell will be created initialized to a blank 'b'.
	 */
	public void shiftRight() {
		if(this.currentCell.next == null) {
			TapeCell newCurrentCell = new TapeCell();
			newCurrentCell.prev = this.currentCell;
			newCurrentCell.symbol = 'b';
			this.currentCell.next = newCurrentCell;
		}
		this.currentCell = this.currentCell.next;
	}
	
	/**
	 * @param showHead boolean indication to designate the symbol under the read/write head of the TM with '[]'.
	 * @return A string representation of the entire contents of the TM tape.
	 */
	public String toString(boolean showHead) {
		TapeCell cellPointer = this.currentCell;
		StringBuilder sb = new StringBuilder();
		
		// Move the cellPointer to the beginning of the tape.
		while(cellPointer.prev != null) {
			cellPointer = cellPointer.prev;
		}
		
		// Iterate until the end of the tape, recording the contents of the tape
		while(cellPointer != null) {
			if(showHead && cellPointer == this.currentCell) {
				sb.append("[" + cellPointer.symbol + "]");
			}
			else {
				sb.append(cellPointer.symbol);
			}
			cellPointer = cellPointer.next;
		}
		
		return sb.toString().trim();
	}
	

}
