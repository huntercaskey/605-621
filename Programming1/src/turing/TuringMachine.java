package turing;

import java.util.ArrayList;

public class TuringMachine {
	
	private ArrayList<TransitionRule> rules = new ArrayList<TransitionRule>();  

	public void addTransitionRule(TransitionRule rule) {
		rules.add(rule);
	}
	
	public void addTransitionRules(TransitionRule[] rules) {
		for (TransitionRule rule : rules) {
			addTransitionRule(rule);
		}
	}

	public int execute(Tape tape, ArrayList<String> logger) throws IllegalStateException {
		
		int currentState = 0;
		StringBuilder sb;
		
		while (currentState >= 0) {
			
			sb = new StringBuilder();
			char currentContent = tape.readCell();
			TransitionRule transitionFunction = null;
			for (TransitionRule rule : rules) {
				if ((rule.currentSymbol == currentContent || rule.currentSymbol == '*') && rule.currentState == currentState) {
					transitionFunction = rule;
					break;
				}
			}
			if (transitionFunction == null) {
				throw new IllegalStateException("Cannot find an applicable rule; tape contents='" 
						+ tape.toString(true) + "', state=" + currentState + ", currentContent='" + currentContent + "'");
			}
			
			sb.append("Current Tape Contents: '" + tape.toString(true) + "' ");
			sb.append("Current State: " + transitionFunction.currentState + " ");
			sb.append("Current Content: '" + transitionFunction.currentSymbol + "' ");
			sb.append("New State: " + transitionFunction.newState + " ");
			sb.append("New Content: '" + transitionFunction.newSymbol + "' ");
			sb.append("Move Direction: " + transitionFunction.tapeChangeDirection + " ");
				
			
			currentState = transitionFunction.newState;
			if(transitionFunction.newSymbol != '*') {
				tape.writeCell(transitionFunction.newSymbol);
			}
			if (transitionFunction.tapeChangeDirection == -1) {
				tape.shiftLeft();
			}
			else if(transitionFunction.tapeChangeDirection == 1) {
				tape.shiftRight();
			}
			
			sb.append("New Tape Contents: '" + tape.toString(true) + "' ");
			
			// Add the record of this Turing machine operation to the log
			logger.add(sb.toString());
			
			// System.out.println(log.get(log.size() -1)); //Debug

		}
		return currentState;
	}	

}