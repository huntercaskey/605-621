package turing;


// A test program for the TuringMachine class.  It creates three machines
// and runs them.  The output from the program indictes the expected behavior.

public class TestTuringMachine {

	public static void main(String[] args) {

//		testMachine1();
//		testMachine2();
//		testMachine3();
		testMachine4();

	}

	
	public static void testMachine1() {
		TuringMachine writeMachine = new TuringMachine();

		writeMachine.addRules( new Rule[] {  // writes Hello on the tape then halts
				new Rule(0,' ',1,'H',1),
				new Rule(1,' ',2,'e',1),
				new Rule(2,' ',3,'l',1),
				new Rule(3,' ',4,'l',1),
				new Rule(4,' ',-1,'o',1)
		});

		System.out.println("Running machine #1.  Output should be:  Hello");
		Tape tape = new Tape();
		int finalState = writeMachine.run(tape);
		System.out.println( "Actual output is:                      '" + tape.getTapeContents() + "' and the final state is: " + finalState);
	}

	public static void testMachine2() {
		TuringMachine badMachine = new TuringMachine();
		badMachine.addRules( new Rule[] {  // writes ERROR on the tape then fails
				new Rule(0,' ',1,'R',-1),
				new Rule(1,' ',2,'O',-1),
				new Rule(2,' ',3,'R',-1),
				new Rule(3,' ',4,'R',-1),
				new Rule(4,' ',5,'E',-1) // no rule for state 5!
		});

		System.out.println("\nRunning machine #2.  Should throw an IllegalStateExcpetion.");
		Tape tape = new Tape();
		try {
			badMachine.run(tape);
			System.out.println("No Error was thrown.");
		}
		catch (IllegalStateException e) {
			System.out.println("Caught Illegal Argument Exception, with error message:");
			System.out.println(e.getMessage());
		}
	}

	public static void testMachine3() {
		String input = "aababbbababbabbaba";  // a string of a's and b's for input to the copy machine
		Tape tape = new Tape();
		for (int i = 0; i < input.length(); i++) {
			tape.setContent(input.charAt(i));
			tape.moveRight();
		}
		tape.moveLeft();  // now, input is written on the tape, with the machine on the rightmost character

		TuringMachine copyMachine = new TuringMachine();  // copies a string of a's and b's;
		                                                  // machine must start on leftmost char in the string		
		copyMachine.addRules(new Rule[] {
				new Rule(0,'a',1,'x',-1),  // rules for copying an a
				new Rule(1,'a',1,'a',-1),
				new Rule(1,'b',1,'b',-1),
				new Rule(1,' ',2,' ',-1),
				new Rule(2,'a',2,'a',-1),
				new Rule(2,'b',2,'b',-1),
				new Rule(2,' ',3,'a',1),
				new Rule(3,'a',3,'a',1),
				new Rule(3,'b',3,'b',1),
				new Rule(3,' ',3,' ',1),
				new Rule(3,'x',0,'x',-1),
				new Rule(3,'y',0,'y',-1),
				
				new Rule(0,'b',4,'y',-1),  // rules for copying a b
				new Rule(4,'a',4,'a',-1),
				new Rule(4,'b',4,'b',-1),
				new Rule(4,' ',5,' ',-1),
				new Rule(5,'a',5,'a',-1),
				new Rule(5,'b',5,'b',-1),
				new Rule(5,' ',7,'b',1),
				new Rule(7,'a',7,'a',1),
				new Rule(7,'b',7,'b',1),
				new Rule(7,' ',7,' ',1),
				new Rule(7,'x',0,'x',-1),
				new Rule(7,'y',0,'y',-1),
				
				new Rule(0,' ',8,' ',1),  // rules that change x and y back to a and b, then halt
				new Rule(8,'x',8,'a',1),
				new Rule(8,'y',8,'b',1),
				new Rule(8,' ',-1,' ',-1)
		});
		
		System.out.println("\nRunning machine #3.  Output should be: " + input + " " + input);
		int finalState = copyMachine.run(tape);
		System.out.println( "Actual output is:                      '" + tape.getTapeContents() + "' and the final state is: " + finalState);
	}
	
	public static void testMachine4() {
		TuringMachine twoRightMostSymbolsAreZeroMachine = new TuringMachine();

		twoRightMostSymbolsAreZeroMachine.addRules( new Rule[] {  
				//Rule(int currentState, char currentContent, int newState, char newContent, int direction)
				
				// State 0 rules
				new Rule(0,'0',0,'0',1),
				new Rule(0,'1',0,'1',1),
				new Rule(0,' ',1,' ',-1),
				// State 1 rules
				new Rule(1,'0',2,' ',-1),
				new Rule(1,'1',3,' ',-1),
				new Rule(1,' ',-2,' ',-1),
				// State 2 rules
				new Rule(2,'0',-1,' ',-1),
				new Rule(2,'1',-2,' ',-1),
				new Rule(2,' ',-2,' ',-1),
				// State 3 rules
				new Rule(3,'0',-2,' ',-1),
				new Rule(3,'1',-2,' ',-1),
				new Rule(3,' ',-2,' ',-1),
				
		});

		char[] inputString = new String("10100").toCharArray();
		System.out.println("\nRunning machine #4.  Final state should be: -1");
		Tape tape = new Tape(inputString);
		int finalState = twoRightMostSymbolsAreZeroMachine.run(tape);
		System.out.println( "Actual output is:                      '" + tape.getTapeContents() + "' and the final state is: " + finalState);
	}
}