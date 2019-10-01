package turing;

public class Tape {

	private TapeCell currentCell;
	
	public Tape() {
		this.currentCell = new TapeCell();
		this.currentCell.symbol = 'b';
	}
	
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
		
		// Set the last blank cell to point to the previous
		cellPointer.prev = prevCell;
	}
	
	public TapeCell currentCell() {
		return this.currentCell;
	}
	public char readCell() {
		return this.currentCell.symbol;
	}	
	public void writeCell(char ch) {
		this.currentCell.symbol = ch;
	}

	public void shiftLeft() {
		if(this.currentCell.prev == null) {
			TapeCell newCurrentCell = new TapeCell();
			newCurrentCell.next = this.currentCell;
			newCurrentCell.symbol = 'b';
			this.currentCell.prev = newCurrentCell;
		}
		this.currentCell = this.currentCell.prev;
	}

	public void shiftRight() {
		if(this.currentCell.next == null) {
			TapeCell newCurrentCell = new TapeCell();
			newCurrentCell.prev = this.currentCell;
			newCurrentCell.symbol = 'b';
			this.currentCell.next = newCurrentCell;
		}
		this.currentCell = this.currentCell.next;
	}
	
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
