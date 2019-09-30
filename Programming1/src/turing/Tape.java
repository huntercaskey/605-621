package turing;

public class Tape {

	private Cell currentCell;
	
	public Cell getCurrentCell() {
		return this.currentCell;
	}
	public char getContent() {
		return this.currentCell.content;
	}	
	public void setContent(char ch) {
		this.currentCell.content = ch;
	}

	public void moveLeft() {
		if(this.currentCell.prev == null) {
			Cell newCurrentCell = new Cell();
			newCurrentCell.next = this.currentCell;
			newCurrentCell.content = ' ';
			this.currentCell.prev = newCurrentCell;
		}
		this.currentCell = this.currentCell.prev;
	}

	public void moveRight() {
		if(this.currentCell.next == null) {
			Cell newCurrentCell = new Cell();
			newCurrentCell.prev = this.currentCell;
			newCurrentCell.content = ' ';
			this.currentCell.next = newCurrentCell;
		}
		this.currentCell = this.currentCell.next;
	}
	
	public String getTapeContents() {
		Cell cellPointer = this.currentCell;
		StringBuilder sb = new StringBuilder();
		
		// Move the cellPointer to the beginning of the tape.
		while(cellPointer.prev != null) {
			cellPointer = cellPointer.prev;
		}
		
	
		// Iterate until the end of the tape, recording the contents of the tape as wel go along.
		while(cellPointer.next != null) {
			sb.append(cellPointer.content);
			cellPointer = cellPointer.next;
		}
		
		return sb.toString().trim();
	}
	
	public Tape() {
		this.currentCell = new Cell();
		this.currentCell.content = ' ';
	}
	
	public Tape(char[] contents) {
		this.currentCell = new Cell();
		
		Cell cellPointer = this.currentCell;
		Cell prevCell = null;
		for(char c : contents) {
			cellPointer.prev = prevCell;
			cellPointer.content = c;
			cellPointer.next = new Cell();
			prevCell = cellPointer;
			cellPointer = cellPointer.next;
		}
		cellPointer.prev = prevCell;		
	}
}
