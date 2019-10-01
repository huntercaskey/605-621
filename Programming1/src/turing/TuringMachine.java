package turing;

import java.util.ArrayList;

/**
 * @author Hunter R. Caskey
 * 
 * Data structure containing a set of rules that describe the behavior of a single-tape Turing machine. This machine recognizes a wildcard '*' character as a convenience character.
 * The Turing machine operated in a sequence of state transitions, each of which is recorded and added to a collection of log statement strings.
 *
 */
public class TuringMachine {
	
	private ArrayList<TransitionRule> rules = new ArrayList<TransitionRule>();  

	/**
	 * Add a single transition rule to the definition of a Turing machine.
	 * 
	 * @param rule A five-tuple rule describing a state transition which defines the behavior of the TM. 
	 */
	public void addTransitionRule(TransitionRule rule) {
		rules.add(rule);
	}
	
	/**
	 * Adds a set of transition rules to serve as teh definition of a Turing machine.
	 * 
	 * @param rules  An array of TransitionRules that serve as the definition of a Turing machine.
	 */
	public void addTransitionRules(TransitionRule[] rules) {
		for (TransitionRule rule : rules) {
			addTransitionRule(rule);
		}
	}

	/**
	 * Continuously cycles the execution of a Turing machine until a halting state is reached.
	 * 
	 * @param tape The input program given to the Turing machine.
	 * @param logger Container reference to pass back to the calling function that collects the debug prints of the program.
	 * @return The final state of the Turing machine upon program termination.
	 * @throws IllegalStateException Throws an error if the Turing machine enters a configuration for which no elements from the set of transition rules are applicable.
	 */
	public int execute(Tape tape, ArrayList<String> logger) throws IllegalStateException {
		
		// By convention 0 represents the initial state of all Turing machines.
		int currentState = 0;
		StringBuilder sb;
		
		// Iterate until a halting state is reached. By convention, -1 indicated an accept/computastion done state and -2 represents a reject state.
		while (currentState >= 0) {
			
			sb = new StringBuilder(); // Capture log statement for this execution cycle of the TM. 
			
			char currentContent = tape.readCell(); // Current symbol under the read/write head.
			
			// Find the applicable rule to apply against the Turing machine given its current state.
			TransitionRule transitionFunction = null;
			for (TransitionRule rule : rules) {
				if ((rule.currentSymbol == currentContent || rule.currentSymbol == '*') && rule.currentState == currentState) {
					transitionFunction = rule;
					break;
				}
			}
			
			if (transitionFunction == null) { // Error handling
				throw new IllegalStateException("Cannot find an applicable rule; tape contents='" 
						+ tape.toString(true) + "', state=" + currentState + ", currentContent='" + currentContent + "'");
			}
			
			sb.append("Current Tape Contents: '" + tape.toString(true) + "' ");
			sb.append("Current State: " + transitionFunction.currentState + " ");
			sb.append("Current Content: '" + transitionFunction.currentSymbol + "' ");
			sb.append("New State: " + transitionFunction.newState + " ");
			sb.append("New Content: '" + transitionFunction.newSymbol + "' ");
			sb.append("Move Direction: " + transitionFunction.tapeChangeDirection + " ");
				
			// Adjust the current state of the TM based off the applicable rule.
			currentState = transitionFunction.newState;
			
			// Wildcard '*' symbol indicating the contents of the read/write head should remain unchanged.
			if(transitionFunction.newSymbol != '*') {
				tape.writeCell(transitionFunction.newSymbol);
			}
			
			// Supports not changing the position of the tape via a direction of '0'
			if (transitionFunction.tapeChangeDirection == -1) {
				tape.shiftLeft();
			}
			else if(transitionFunction.tapeChangeDirection == 1) {
				tape.shiftRight();
			}
			
			sb.append("New Tape Contents: '" + tape.toString(true) + "' ");
			
			logger.add(sb.toString());
			

		}
		return currentState;
	}	

}