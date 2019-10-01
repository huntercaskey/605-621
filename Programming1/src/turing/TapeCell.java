package turing;

public class TapeCell {
	
	public char symbol;  // The character in this cell.
	public TapeCell next;     // Pointer to the cell to the right of this one.
	public TapeCell prev;     // Pointer to the cell to the left of this one.
	
	public TapeCell() {
		this.symbol = 'b';
	}
	
}

