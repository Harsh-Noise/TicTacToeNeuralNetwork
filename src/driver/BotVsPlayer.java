package driver;

import java.util.*;

import botPackage.*;
import tttPackage.*;

public class BotVsPlayer {
	
	public static ArrayList<Double> matrixTurn(ArrayList<Double> inputs, NodeMatrix matrix){
		//Updates the board given an input
		//run inputs through the matrix and get the move preferences
		//System.out.println(inputs.toString());
		matrix.setInputs(inputs);
		matrix.calculate();
		ArrayList<Double> outputs = new ArrayList<Double>(matrix.getOutputs());
		
		//find the highest ranked unmade move
		//System.out.println("Getting highest unplayed move");
		int highest = -2;
		double highestScore = -2.0; 
		for(int x = 0; x < outputs.size(); x++) {   
			//System.out.println(outputs.get(x) + " " + inputs.get(x));
			if(outputs.get(x) > highestScore && inputs.get(x) == 0) {
				highest = x;
				highestScore = outputs.get(x);
			}
		}
		
		//update inputs with new move
		inputs.set(highest, 1.0);
		
		//trim timer off the end to be updated
		inputs.remove(inputs.size() - 1);
		inputs.remove(inputs.size() - 1);
		
		//return updated inputs
		return(inputs);
	}
	
	public static ArrayList<Double> listInvert(ArrayList<Double> inputs){
		//inverts the inputs, specifically for when it is O's move
		ArrayList<Double> outputs = new ArrayList<Double>();
		for(int x = 0; x < inputs.size(); x++) {
			outputs.add(inputs.get(x) * -1);
		}
		return(outputs);
	}
	
	public static void main(String[] args) {

		
		//Create bot w/ dna
		int inputs = 11;
		int outputs = 9;
		int[] layers = {8,8,8,8};
		String DNA = "[-3, -1, -3, 7, -1, 5, 6, -3, 1, 2, -9, 1, -7, 0, -2, 0, 1, -3, 8, 7, -7, 8, -3, 10, 7, -9, -2, -8, -3, 9, -9, 2, -1, 6, 1, 8, 8, 0, 1, -5, -6, -7, 5, -5, 10, 6, 7, 3, -2, 7, 9, 7, -3, 6, 7, -4, 4, 0, 6, 3, 8, -1, 9, 6, 0, -8, 0, -5, 7, -2, 4, 10, -9, -7, 2, -7, 4, -4, -6, 9, 2, 1, -3, 6, -1, 7, 10, 0, 7, 8, 8, -2, 2, 9, -9, -4, -7, -1, -7, 3, 9, -4, -2, 4, -4, 5, -9, 6, 2, 8, -9, 3, -3, -10, -5, -6, -2, 7, -5, 6, 10, 3, 2, -8, -7, -10, -3, 6, -2, 8, 8, -8, 6, -6, 9, 8, 1, 0, 5, 2, 10, 6, -8, 9, -5, 4, 9, -5, -1, 3, -4, -8, 7, 2, -4, 6, -4, -8, 9, 3, 3, 3, -3, -3, -9, 2, -6, -7, -7, 8, -1, 0, 1, -2, 8, 0, 3, 4, 7, -1, -7, 3, 8, -5, 6, 5, -2, -3, 10, 6, -4, -3, 10, -8, 1, 6, 6, -7, 6, -9, -4, 7, -1, -7, 2, 5, 8, 4, -6, 9, -9, -3, 4, 4, -1, 3, -8, -2, -6, 1, 2, 1, -3, 6, 7, 5, -7, -1, -9, 9, -4, 10, 8, -1, -2, 3, -4, 2, 2, 9, 3, -6, -9, -9, -1, -8, -2, 2, -9, 3, 3, -6, 9, -1, 7, -3, 8, -6, -2, -4, 7, -3, -7, 7, -6, -9, 9, 1, -2, 5, -7, -5, -9, 1, 7, -5, -2, -10, -1, 8, -3, -10, 7, 4, -6, -4, 2, -3, 5, 9, 0, 3, 7, 6, -3, -9, -5, 3, 8, -8, -7, -6, -7, 5, -2, 6, 8, 6, -10, -6, 1, -5, 8, -2, -2, -4, 7, 5, 5, -6, -8, -6, -7, 10, 7, -2, -4, -5, 2, 9, -1, -9, -3, -8, 3, 4, -3, -9, 3, -9, -8, 3, -8, -4, 9, 6, 0, -1, 1, -5, -10, 2, 9, -9, 6, 10, 7, 7, -2, 8, 8, 3, 2, 6, 4, 2, -8, -10, -7, -6, 3, 3, 4, -5, -1, -7, -1, -7, -4, 4, 10, 6, 8, 5, -7, -4, -4, -1, -2, -8, -8, 1, 8, 1, 2, -1, -10, 10, -9, 5, 1, -2, 4, 9, 10, -1, -1, 1, -10, -10, -1, 6, 8, 8, -2, -7, -6, 4, -8, 9, -5, 9, 8, 0, -10, 10, 3, -8, 7, 3, -2, -10, 5, -7, -1, -6, 10, -8, 9, 6, -8, 2, -3, -8, 3, 1, -3, -9, 3, -7, 6, 0, 9, -4, -1, -4, 10, 0, -6, 1, 1, 5, -3, -2, -1, 8, 8, 1, -2, -6, 10, 0, 7, -8, 1, 3, 3, -9, -7, 5";
		Bot bot = new Bot(inputs, outputs, layers, DNA);
		boolean botX = true;
		
		
		
		try (Scanner scan = new Scanner(System.in)) {
			//Prepare list of tic tac toe moves
			GameTree tttTree = new GameTree();
			GameBoard board = new GameBoard();
			tttTree.addNodes(board);
			tttTree.getScores();
			
			//runs a game between two bots, and updates their rank
			NodeMatrix one = new NodeMatrix(bot);
					 
			//timer for measuring time passed
			double timer = 0.0;
			int timerInt = 0;
					
			//ArrayList representing the current state of the board
			ArrayList<Double> boardInputs = new ArrayList<Double>();

			//Initialize the board with all zeros (minus one because timer will be added and updated later)
			for(int x = 0; x < bot.getInput()-2; x++) {
				boardInputs.add(0.0);
			}		
			
			//loop as long as the board isn't full or in a victory state
			while(!tttTree.getBoardNode(boardInputs).board.isStop()) {
				
				//System.out.println("Start of turn " + boardInputs.toString());
				//System.out.println("X turn status: " + isXTurn);
				if(botX && timerInt % 2 == 0) {
					//if it is X's turn
					
					//append timer to the end as the last input node
					boardInputs.add(timer);
					boardInputs.add(1.0);

					//given the board state, X makes a move
					//System.out.println("Pre-move status: " + boardInputs.toString());
					boardInputs = new ArrayList<Double>(matrixTurn(boardInputs, one));				
					//System.out.println("Post-move status: " + boardInputs.toString());
					
					//Add the score of the board
					//System.out.println("Sending tree to get score" + boardInputs.toString());
					//System.out.println("Score will be: " + tttTree.getBoardNode(boardInputs).getXScoreRatio());
					
					
					//signal that it is now O's turn and increment timer
					timerInt++;
					timer += 0.1;
				}else if(botX == false && timerInt % 2 == 1) {
					//if it is O's turn
					//Invert board state
					boardInputs = new ArrayList<Double>(listInvert(boardInputs));
					
					//append timer to the end as the last input node
					boardInputs.add(timer);
					boardInputs.add(-1.0);

					//given the board state, O makes a move
					//System.out.println("Pre-move status: " + boardInputs.toString());
					boardInputs = new ArrayList<Double>(matrixTurn(boardInputs, one));
					//System.out.println("Post-move status: " + boardInputs.toString());
					
					//Invert board state again
					boardInputs = new ArrayList<Double>(listInvert(boardInputs));
					
					//Add the score of the board
					//System.out.println("Sending tree to get score" + boardInputs.toString());
					//System.out.println("Score will be: " + tttTree.getBoardNode(boardInputs).getYScoreRatio());
					
					//signal that it is now X's turn and increment timer
					timerInt++;
					timer += 0.1;
				}else {				
					System.out.println("User's turn");
					tttTree.getBoardNode(boardInputs).board.print();
					tttTree.printBranches(tttTree.getBoardNode(boardInputs));

					
					boolean turnDone = false;
					System.out.print("Choice: ");

					while(!turnDone) {
						int choice = scan.nextInt();
						if(boardInputs.get(choice) == 0) {
							if(timerInt % 2 == 0) {
								boardInputs.set(choice, 1.0);
							} else {
								boardInputs.set(choice, -1.0);
							}
							
							turnDone = true;
						}
					}
					//signal that it is now O's turn and increment timer
					timerInt++;
					timer += 0.1;
				}
				
				//System.out.println("End of turn " + boardInputs.toString());
				//System.out.println("Stop status:" + tttTree.getBoardNode(boardInputs).board.isStop() + "\n");
			}
			//System.out.println("Ended");
			//check if the board is in a winning state
			if(tttTree.getBoardNode(boardInputs).board.isWin()) {
				//if the winner is X
				if(tttTree.getBoardNode(boardInputs).board.winTeam() == 1) {
					System.out.println("X win");
					//double X's score
					//if the winner is O
				}else {
					System.out.println("O win");
					//double O's score
				}
			}else {
				System.out.println("Full");
			}
		}
	}	
}	
		