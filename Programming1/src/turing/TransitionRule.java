package turing;

/**
 * @author Hunter R. Caskey
 *
 * Data structure containing the necessary information to model a transition rule in a Turing machine. These are corresponding to the five-tuple
 * transitions described in the assignment specification. 
 */
public class TransitionRule {
	
	public int currentState;
	public char currentSymbol;
	public int newState;
	public char newSymbol;
	public int tapeChangeDirection;
	
	/**
	 * Constructor for a transition rule in a Turing machine.
	 * 
	 * @param currentState The state for which this rule applied to. 
	 * @param currentSymbol The symbol that must be under the read/write head of the TM for this rule to apply for.
	 * @param newState The state to transition to granted that this rule should be enforced given the current state of the Turing machine.
	 * @param newSymbol The symbol to write to the current cell under the read/write head of the Turing machine given that this rule should be enforced.
	 * @param direction The direction to shift the tape of the Turing machine after performing a write operation.
	 */
	public TransitionRule(int currentState, char currentSymbol, int newState, char newSymbol, int direction) {
		this.currentState = currentState;
		this.currentSymbol = currentSymbol;
		this.newState = newState;
		this.newSymbol = newSymbol;
		this.tapeChangeDirection = direction;
	}
}