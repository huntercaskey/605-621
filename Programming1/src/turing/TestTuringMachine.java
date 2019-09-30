package turing;

import java.util.ArrayList;
import java.io.FileWriter;

// A test program for the TuringMachine class.  It creates three machines
// and runs them.  The output from the program indictes the expected behavior.

public class TestTuringMachine {

	public static void main(String[] args) {

//		testMachine1();
//		testMachine2();
//		testMachine3();
		//testProblem2a();
		testProblem2bi();

	}

	
	public static void testMachine1() {
		TuringMachine writeMachine = new TuringMachine();
		ArrayList<String> log = new ArrayList<>();
		writeMachine.addRules( new Rule[] {  // writes Hello on the tape then halts
				new Rule(0,'b',1,'1',1),
				new Rule(1,'b',2,'0',1),
				new Rule(2,'b',3,'1',1),
				new Rule(3,'b',4,'0',1),
				new Rule(4,'b',-1,'1',1)
		});

		System.out.println("Running machine #1.  Output should be:  10101");
		Tape tape = new Tape();
		int finalState = writeMachine.run(tape, log);
		System.out.println( "Actual output is: '" + tape.getTapeContents(false) + "' and the final state is: " + finalState);
	}

	public static void testMachine2() {
		TuringMachine badMachine = new TuringMachine();
		ArrayList<String> log = new ArrayList<>();
		badMachine.addRules( new Rule[] {  // writes ERROR on the tape then fails
				new Rule(0,'b',1,'R',-1),
				new Rule(1,'b',2,'O',-1),
				new Rule(2,'b',3,'R',-1),
				new Rule(3,'b',4,'R',-1),
				new Rule(4,'b',5,'E',-1) // no rule for state 5!
		});

		System.out.println("\nRunning machine #2.  Should throw an IllegalStateExcpetion.");
		Tape tape = new Tape();
		try {
			badMachine.run(tape, log);
			System.out.println("No Error was thrown.");
		}
		catch (IllegalStateException e) {
			System.out.println("Caught Illegal Argument Exception, with error message:");
			System.out.println(e.getMessage());
		}
	}

	public static void testMachine3() {
		String input = "110100010100100101";  // a string of a's and b's for input to the copy machine
		Tape tape = new Tape();
		for (int i = 0; i < input.length(); i++) {
			tape.setContent(input.charAt(i));
			tape.moveRight();
		}
		tape.moveLeft();  // now, input is written on the tape, with the machine on the rightmost character

		TuringMachine copyMachine = new TuringMachine();  // copies a string of a's and b's;
		                                                  // machine must start on leftmost char in the string		
		ArrayList<String> log = new ArrayList<>();
		copyMachine.addRules(new Rule[] {
				new Rule(0,'0',1,'2',-1),  // rules for cop3ing 0n 0
				new Rule(1,'0',1,'0',-1),
				new Rule(1,'1',1,'1',-1),
				new Rule(1,'b',2,'b',-1),
				new Rule(2,'0',2,'0',-1),
				new Rule(2,'1',2,'1',-1),
				new Rule(2,'b',3,'0',1),
				new Rule(3,'0',3,'0',1),
				new Rule(3,'1',3,'1',1),
				new Rule(3,'b',3,'b',1),
				new Rule(3,'2',0,'2',-1),
				new Rule(3,'3',0,'3',-1),
				
				new Rule(0,'1',4,'3',-1),  // rules for cop3ing 0 1
				new Rule(4,'0',4,'0',-1),
				new Rule(4,'1',4,'1',-1),
				new Rule(4,'b',5,'b',-1),
				new Rule(5,'0',5,'0',-1),
				new Rule(5,'1',5,'1',-1),
				new Rule(5,'b',7,'1',1),
				new Rule(7,'0',7,'0',1),
				new Rule(7,'1',7,'1',1),
				new Rule(7,'b',7,'b',1),
				new Rule(7,'2',0,'2',-1),
				new Rule(7,'3',0,'3',-1),
				
				new Rule(0,'b',8,'b',1),  // rules th0t ch0nge 2 0nd 3 10ck to 0 0nd 1, then h0lt
				new Rule(8,'2',8,'0',1),
				new Rule(8,'3',8,'1',1),
				new Rule(8,'b',-1,'b',-1)
		});
		
		System.out.println("\nRunning machine #3.  Output should be: " + input + " " + input);
		int finalState = copyMachine.run(tape, log);
		System.out.println( "Actual output is: '" + tape.getTapeContents(false) + "' and the final state is: " + finalState);
	}
	
	public static void testProblem2a() {
		TuringMachine twoRightMostSymbolsAreZeroMachine = new TuringMachine();
		ArrayList<String> log = new ArrayList<>();
		twoRightMostSymbolsAreZeroMachine.addRules( new Rule[] {  
				//Rule(int currentState, char currentContent, int newState, char newContent, int direction)
				
				// State 0 rules
				new Rule(0,'0',0,'0',1),
				new Rule(0,'1',0,'1',1),
				new Rule(0,'b',1,'b',-1),
				// State 1 rules
				new Rule(1,'0',2,'b',-1),
				new Rule(1,'1',3,'b',-1),
				new Rule(1,'b',-2,'b',-1), // Informally designate -2 as the rejecting state
				// State 2 rules
				new Rule(2,'0',-1,'b',-1), // Infomally designate -1 as the accepting state
				new Rule(2,'1',-2,'b',-1),
				new Rule(2,'b',-2,'b',-1),
				// State 3 rules
				new Rule(3,'0',-2,'b',-1),
				new Rule(3,'1',-2,'b',-1),
				new Rule(3,'b',-2,'b',-1),
				
		});

		char[] inputString = new String("10100").toCharArray();
		System.out.println("\nRunning machine #4.  Final state should be: -1");
		Tape tape = new Tape(inputString);
		int finalState = twoRightMostSymbolsAreZeroMachine.run(tape, log);
		
		outputTestResults(log);
		
		System.out.println( "Final tape contents are: '" + tape.getTapeContents(false) + "' and the final state is: " + finalState);
	}

	public static void testProblem2bi() {
		TuringMachine binaryAdderMachine = new TuringMachine();
		ArrayList<String> logger = new ArrayList<>();
		binaryAdderMachine.addRules( new Rule[] {  
				// Rule format: currentState, currentContent, newState, newContent, direction
				new Rule(0, 'b', 0, 'b', 1),
				new Rule(0, '0', 1, '0', 1),
				new Rule(0, '1', 1, '1', 1),
				
				new Rule(1, 'b', 2, 'b', 1),
				new Rule(1, '0', 1, '0', 1),
				new Rule(1, '1', 1, '1', 1),
				new Rule(1, 'x', 1, 'x', 1),
				new Rule(1, 'y', 1, 'y', 1),
				
				new Rule(2, 'b', 3, 'b', -1),
				new Rule(2, '0', 2, '0', 1),
				new Rule(2, '1', 2, '1', 1),
				
				new Rule(3, 'b', 9, 'b', -1),
				new Rule(3, '0', 4, 'b', -1),
				new Rule(3, '1', 6, 'b', -1),
				
				new Rule(4, 'b', 5, 'b', -1),
				new Rule(4, '0', 4, '0', -1),
				new Rule(4, '1', 4, '1', -1),
				
				new Rule(5, '0', 1, 'x', 1),
				new Rule(5, '1', 1, 'y', 1),
				new Rule(5, 'x', 5, 'x', -1),
				new Rule(5, 'y', 5, 'y', -1),
				new Rule(5, 'b', 1, 'x', 1),
				
				new Rule(6, 'b', 7, 'b', -1),
				new Rule(6, '0', 6, '0', -1),
				new Rule(6, '1', 6, '1', -1),
				
				new Rule(7, '0', 1, 'y', 1),
				new Rule(7, '1', 8, 'x', -1),
				new Rule(7, 'x', 7, 'x', -1),
				new Rule(7, 'y', 7, 'y', -1),
				new Rule(7, 'b', 1, 'y', 1),
				
				new Rule(8, 'b', 1, '1', 1),
				new Rule(8, '0', 1, '1', 1),
				new Rule(8, '1', 8, '0', -1),

				new Rule(9, 'b', -1, 'b', 1),
				new Rule(9, '0', -1, '0', 1),
				new Rule(9, '1', -1, '1', 1),
				new Rule(9, 'x', 9, '0', -1),
				new Rule(9, 'y', 9, '1', -1)

		});

		char[] inputString = new String("101100101b101").toCharArray();
		System.out.println("\nRunning machine #5. Final state should be: -1");
		Tape tape = new Tape(inputString);
		int finalState = binaryAdderMachine.run(tape, logger);
		
		outputTestResults(logger);
		
		System.out.println( "Final tape contents are: '" + tape.getTapeContents(false) + "' and the final state is: " + finalState);
	}

	
	public static void outputTestResults(ArrayList<String> logger) {
		if(logger.size() <= 30) {
			for(String msg : logger) {
				System.out.println(msg);
			}
		}
		else {
			FileWriter writer;
			try {
				writer = new FileWriter("output.txt");
				for(String msg : logger) {
					writer.write(msg + System.lineSeparator());
				}
				writer.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}