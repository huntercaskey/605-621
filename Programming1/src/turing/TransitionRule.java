package turing;

public class TransitionRule {
	
	public int currentState;
	public char currentSymbol;
	public int newState;
	public char newSymbol;
	public int tapeChangeDirection;
	
	public TransitionRule(int currentState, char currentSymbol, int newState, char newSymbol, int direction) {
		this.currentState = currentState;
		this.currentSymbol = currentSymbol;
		this.newState = newState;
		this.newSymbol = newSymbol;
		this.tapeChangeDirection = direction;
	}
}