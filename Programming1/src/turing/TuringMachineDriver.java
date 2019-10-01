package turing;

import java.util.ArrayList;
import java.io.FileWriter;

public class TuringMachineDriver {

	public static void main(String[] args) {

		//testProblem2a();
//		testProblem2bi();
		testProblem2bii();
//		testProblem2biii();
	}


	public static void testProblem2a() {
		TuringMachine twoRightMostSymbolsAreZeroMachine = new TuringMachine();
		ArrayList<String> log = new ArrayList<>();
		twoRightMostSymbolsAreZeroMachine.addTransitionRules( new TransitionRule[] {  
				//Rule(int currentState, char currentContent, int newState, char newContent, int direction)
				
				// State 0 rules
				new TransitionRule(0,'0',0,'0',1),
				new TransitionRule(0,'1',0,'1',1),
				new TransitionRule(0,'b',1,'b',-1),
				// State 1 rules
				new TransitionRule(1,'0',2,'b',-1),
				new TransitionRule(1,'1',3,'b',-1),
				new TransitionRule(1,'b',-2,'b',-1), // Informally designate -2 as the rejecting state
				// State 2 rules
				new TransitionRule(2,'0',-1,'b',-1), // Infomally designate -1 as the accepting state
				new TransitionRule(2,'1',-2,'b',-1),
				new TransitionRule(2,'b',-2,'b',-1),
				// State 3 rules
				new TransitionRule(3,'0',-2,'b',-1),
				new TransitionRule(3,'1',-2,'b',-1),
				new TransitionRule(3,'b',-2,'b',-1),
				
		});

		char[] inputString = new String("10100").toCharArray();
		Tape tape = new Tape(inputString);
		int finalState = twoRightMostSymbolsAreZeroMachine.execute(tape, log);
		
		outputTestResults(log);
		
		System.out.println("\nRunning machine #4.  Final state should be: ");
		System.out.println( "Final tape contents are: '" + tape.toString(false) + "' and the final state is: " + finalState);
	}

	public static void testProblem2bi() {
		TuringMachine binaryAdderMachine = new TuringMachine();
		ArrayList<String> logger = new ArrayList<>();
		binaryAdderMachine.addTransitionRules( new TransitionRule[] {  
				// Rule format: currentState, currentContent, newState, newContent, direction
				new TransitionRule(0, 'b', 0, 'b', 1),
				new TransitionRule(0, '0', 1, '0', 1),
				new TransitionRule(0, '1', 1, '1', 1),
				
				new TransitionRule(1, 'b', 2, 'b', 1),
				new TransitionRule(1, '0', 1, '0', 1),
				new TransitionRule(1, '1', 1, '1', 1),
				new TransitionRule(1, 'x', 1, 'x', 1),
				new TransitionRule(1, 'y', 1, 'y', 1),
				
				new TransitionRule(2, 'b', 3, 'b', -1),
				new TransitionRule(2, '0', 2, '0', 1),
				new TransitionRule(2, '1', 2, '1', 1),
				
				new TransitionRule(3, 'b', 9, 'b', -1),
				new TransitionRule(3, '0', 4, 'b', -1),
				new TransitionRule(3, '1', 6, 'b', -1),
				
				new TransitionRule(4, 'b', 5, 'b', -1),
				new TransitionRule(4, '0', 4, '0', -1),
				new TransitionRule(4, '1', 4, '1', -1),
				
				new TransitionRule(5, '0', 1, 'x', 1),
				new TransitionRule(5, '1', 1, 'y', 1),
				new TransitionRule(5, 'x', 5, 'x', -1),
				new TransitionRule(5, 'y', 5, 'y', -1),
				new TransitionRule(5, 'b', 1, 'x', 1),
				
				new TransitionRule(6, 'b', 7, 'b', -1),
				new TransitionRule(6, '0', 6, '0', -1),
				new TransitionRule(6, '1', 6, '1', -1),
				
				new TransitionRule(7, '0', 1, 'y', 1),
				new TransitionRule(7, '1', 8, 'x', -1),
				new TransitionRule(7, 'x', 7, 'x', -1),
				new TransitionRule(7, 'y', 7, 'y', -1),
				new TransitionRule(7, 'b', 1, 'y', 1),
				
				new TransitionRule(8, 'b', 1, '1', 1),
				new TransitionRule(8, '0', 1, '1', 1),
				new TransitionRule(8, '1', 8, '0', -1),

				new TransitionRule(9, 'b', -1, 'b', 1),
				new TransitionRule(9, '0', -1, '0', 1),
				new TransitionRule(9, '1', -1, '1', 1),
				new TransitionRule(9, 'x', 9, '0', -1),
				new TransitionRule(9, 'y', 9, '1', -1)

		});

		char[] inputString = new String("1011b1011").toCharArray();
		Tape tape = new Tape(inputString);
		int finalState = binaryAdderMachine.execute(tape, logger);
		
		outputTestResults(logger);
		
		System.out.println("\nRunning binaryAdderMachine. Output should be: 0101101010");
		System.out.println( "Final tape contents are: '" + tape.toString(false) + "' and the final state is: " + finalState);
	}

	public static void testProblem2bii() {
		TuringMachine binarySubtractionMachine = new TuringMachine();
		ArrayList<String> logger = new ArrayList<>();
		binarySubtractionMachine.addTransitionRules( new TransitionRule[] {  
				// Rule format: currentState, currentContent, newState, newContent, direction
				new TransitionRule(0, 'b', 1, 'b', +1),
				new TransitionRule(0, '*', 0, '*', +1),
				
				new TransitionRule(1, 'b', 2, 'b', -1),
				new TransitionRule(1, '*', 1, '*', +1),
				
				new TransitionRule(2, '0', 2, '1', -1),
				new TransitionRule(2, '1', 3, '0', +1),
				//new Rule(2, 'b', 8, 'b', +1), 
				
				new TransitionRule(3, 'b', 4, 'b', -1),  
				new TransitionRule(3, '*', 3, '*', +1),  
				
				new TransitionRule(4, 'b', 5, 'b', -1),  
				new TransitionRule(4, '*', 4, '*', -1),
				
				new TransitionRule(5, '0', 5, '1', -1),
				new TransitionRule(5, '1', 6, '0', +1),
				
				new TransitionRule(6, 'b', 7, 'b', +1),   
				new TransitionRule(6, '*', 6, '*', +1), 
				
				new TransitionRule(7, 'b', 8, 'b', -1),  
				new TransitionRule(7, '*', 7, '*', +1), 
				
				
				new TransitionRule(8, 'b', 13, 'b', +1), // go to cleanup
				new TransitionRule(8, '0', 8, '0', -1), 
				new TransitionRule(8, '1', 9, '1', -1),
				
				new TransitionRule(9, 'b', 10, 'b', -1),
				new TransitionRule(9, '*', 9, '*', -1),
				
				new TransitionRule(10, 'b', 13, 'b', +1), // go to cleanup
				new TransitionRule(10, '0', 10, '0', -1), 
				new TransitionRule(10, '1', 11, '1', +1),
				
				new TransitionRule(11, 'b', 12, 'b', +1),
				new TransitionRule(11, '*', 11, '*', +1),
				
				new TransitionRule(12, 'b', 2, 'b', -1),
				new TransitionRule(12, '*', 12, '*', +1),
				
				new TransitionRule(13, 'b', -1, 'b', 0),
				new TransitionRule(13, '*', 13, 'b', +1)
				
				
		});

		char[] inputString = new String("10b1010").toCharArray();
		Tape tape = new Tape(inputString);
		int finalState = binarySubtractionMachine.execute(tape, logger);
		
		outputTestResults(logger);
		
		System.out.println("Running binarySubtractionMachine. Output should be: '0101100'");
		System.out.println( "Final tape contents are: '" + tape.toString(false) + "' and the final state is: " + finalState);
	}
 
	public static void testProblem2biii() {
		TuringMachine binaryMultiplierMachine = new TuringMachine();
		ArrayList<String> logger = new ArrayList<>();
		binaryMultiplierMachine.addTransitionRules( new TransitionRule[] {  
				//Rule(int currentState, char currentContent, int newState, char newContent, int direction) {
				new TransitionRule(00, 'b', 01, 'a', +1),
				new TransitionRule(00, '*', 00, '*', +1),
				new TransitionRule(01, 'b', 02, 'B', -1),
				new TransitionRule(01, '*', 01, '*', +1),
				new TransitionRule(02, '0', 02, 'p', -1),
				new TransitionRule(02, '1', 03, 'y', +1),
				new TransitionRule(02, 'x', 02, 'p', -1),
				new TransitionRule(02, 'y', 02, 'q', -1),
				new TransitionRule(02, 'a', 50, 'a', +1),
				new TransitionRule(03, 'b', 04, 'c', -1),
				new TransitionRule(03, '*', 03, '*', +1),
				new TransitionRule(04, 'b', 05, 'b', +1),
				new TransitionRule(04, '*', 04, '*', -1),
				new TransitionRule(05, 'x', 05, 'x', +1),
				new TransitionRule(05, 'y', 05, 'y', +1),
				new TransitionRule(05, '0', 06, 'x', +1),
				new TransitionRule(05, '1', 07, 'y', +1),
				new TransitionRule(05, 'a', 10, 'a', -1),
				new TransitionRule(06, 'b', 04, '0', -1),
				new TransitionRule(06, '*', 06, '*', +1),
				new TransitionRule(07, 'b', 04, '1', -1),
				new TransitionRule(07, '*', 07, '*', +1),
				
				new TransitionRule(10, 'x', 10, '0', -1),
				new TransitionRule(10, 'y', 10, '1', -1),
				new TransitionRule(10, 'b', 11, 'b', +1),
				new TransitionRule(11, 'a', 20, 'a', +1),
				new TransitionRule(11, '*', 11, '*', +1),
                                      
				new TransitionRule(20, 'p', 21, 'x', +1),
				new TransitionRule(20, 'q', 21, 'y', +1),
				new TransitionRule(20, 'B', 2 , 'B', -1),
				new TransitionRule(20, '*', 20, '*', +1),
				new TransitionRule(21, 'b', 22, '0', -1),
				new TransitionRule(21, '*', 21, '*', +1),
				new TransitionRule(22, 'a', 20, 'a', +1),
				new TransitionRule(22, '*', 22, '*', -1),
                                      
				new TransitionRule(50, 'b', 51, 'b', +1),
				new TransitionRule(50, '*', 50, '*', -1),
				new TransitionRule(51, 'B', 52, 'B', -1),
				new TransitionRule(51, '*', 51, 'b', +1),
				new TransitionRule(52, 'b', 55, '0', +1),
				
				new TransitionRule(55, 'b', 56 , 'b', -1),
				new TransitionRule(55, '*', 55 , '*', +1),
				new TransitionRule(56, '0', 57 , 'b', -1),
				new TransitionRule(56, '1', 58 , 'b', -1),
				new TransitionRule(56, 'c', 80 , 'b', -1),  
				new TransitionRule(56, 'B', 99, 'b', -1),  
				new TransitionRule(57, 'B', 59 , 'B', -1),
				new TransitionRule(57, '*', 57 , '*', -1),
				new TransitionRule(58, 'B', 60 , 'B', -1),
				new TransitionRule(58, '*', 58 , '*', -1),
				new TransitionRule(59, 'x', 59 , 'x', -1),
				new TransitionRule(59, 'y', 59 , 'y', -1),
				new TransitionRule(59, '0', 55 , 'x', +1),
				new TransitionRule(59, '1', 55 , 'y', +1),
				new TransitionRule(59, 'b', 55 , 'x', +1),
				new TransitionRule(60, 'x', 60 , 'x', -1),
				new TransitionRule(60, 'y', 60 , 'y', -1),
				new TransitionRule(60, '0', 61 , '1', +1),
				new TransitionRule(60, '1', 60 , '0', -1),
				new TransitionRule(60, 'b', 61 , '1', +1),
				new TransitionRule(61, 'x', 62 , 'x', -1),
				new TransitionRule(61, 'y', 62 , 'y', -1),
				new TransitionRule(61, 'B', 62 , 'B', -1),
				new TransitionRule(61, '*', 61 , '*', +1),
				new TransitionRule(62, '0', 55 , 'x', +1),
				new TransitionRule(62, '1', 55 , 'y', +1),
				
				new TransitionRule(80, 'b', 55, 'b', +1),
				new TransitionRule(80, 'x', 80, '0', -1),
				new TransitionRule(80, 'y', 80, '1', -1),
				new TransitionRule(80, '*', 80, '*', -1),


				new TransitionRule(99, 'b', -1, 'b', +1),
				new TransitionRule(99, '*', 99, '*', -1)
		});

		char[] inputString = new String("1111b10").toCharArray();
		Tape tape = new Tape(inputString);
		int finalState = binaryMultiplierMachine.execute(tape, logger);
		
		outputTestResults(logger);
		
		System.out.println("Running binaryMultiplierMachine. Output should be: '110111'");
		System.out.println( "Final tape contents are: '" + tape.toString(false) + "' and the final state is: " + finalState);
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