package driver;

import java.util.ArrayList;
import java.util.Random;

import botPackage.*;
import tttPackage.*;

public class GamePlayer {
	//Define properties of the bot
	int inputs;
	int outputs;
	int[] layers; 
	
	//Define properties of the experiment
	int creatureNumber;
	double mutationRate;
	double swapRate;
	int timer;
	
	//Prepare list of tic tac toe moves
	GameTree tttTree = new GameTree();
	GameBoard board = new GameBoard();
	
	//List of bots
	ArrayList<Bot> botList = new ArrayList<Bot>();

	Random rand = new Random();
	
	public GamePlayer(int inputs, int outputs, int[] layers, int creatureNumber, double mutationRate, double swapRate, int timer) {	
		this.inputs = inputs;
		this.outputs = outputs;
		this.layers = layers;
		this.creatureNumber = creatureNumber;
		this.mutationRate = mutationRate;
		this.swapRate = swapRate;
		this.timer = timer;
		
		tttTree.addNodes(board);
		tttTree.getScores();
	}
	
	public ArrayList<Bot> getBots(){
		return(botList);
	}
	
	public void botsFill() {
		//make sure botList is empty for filling
		botList.clear();
		
		//Add new bots
		for(int x = 0; x < creatureNumber; x++) {
			botList.add(new Bot(inputs, outputs, layers));
			//ystem.out.println("Added bot " + botList.get(x));
		}
	}
	
	public void botsCompete() {
		//compete all bots against each other
		for(int a = 0; a < creatureNumber; a++) {
			for(int b = 0; b < creatureNumber; b++) {
				//if the bots are the same, don't compete them against each other
				if(a == b) {
					continue;
				}else {
					botsCompete(botList.get(a), botList.get(b));
				}
			}
		}
	}
	
	public void botsCompete(int start, int end) {
		//compete all bots against each other
		for(int a = start; a < end; a++) {
			for(int b = 0; b < creatureNumber; b++) {
				//if the bots are the same, don't compete them against each other
				if(a == b) {
					continue;
				}else {
					botsCompete(botList.get(a), botList.get(b));
				}
			}
		}
	}
	
	public void botsRefill() {
		//Clones bots from first to last until the list is full
		int pointer = 0;
		while(botList.size() < creatureNumber) {
			botList.add(new Bot(botList.get(pointer)));
			//System.out.println("Refilled with bot " + botList.get(pointer));
			pointer++;
		}
	}
	
	public void botsSort(){
		//Sorts bots by rank
		ArrayList<Bot> tempList = new ArrayList<Bot>();
		
		//for every bot
		for(int x = 0; x < creatureNumber; x++) {
			//check if it is the highest ranked bot
			int highest = 0;
			for(int y = 0; y < botList.size(); y++) {
				//if the highest's rank is lest than the new's rank, new becomes highest
				if(botList.get(highest).getRank() < botList.get(y).getRank()) {
					highest = y;
				}	
			}
			//Add the next highest to templist, and remove it from botlist
			tempList.add(botList.get(highest));
			botList.remove(highest);
			//System.out.println(botList.toString());
		}
		//overwrite botList with the ordered templist
		botList = new ArrayList<Bot>(tempList);
		//System.out.println("Sorted botList = " + botList.toString());
	}
	
	public void botsDebugRank() {
		//Set the rank for debugging
		for(int x = 0; x < creatureNumber; x++) {
			botList.get(x).setRank(x);
		}
	}
	
	public double getAverageRank() {
		double average = 0.0;
		for(int x = 0; x < creatureNumber; x++) {
			average += botList.get(x).getRank();
			//System.out.println("Added " + botList.get(x).getRank() + " to get " + average);
		}
		//System.out.println("dividing by " + creatureNumber + " to get " + average/creatureNumber);
		return(average/creatureNumber);
	}
	
	public double getNormalizedRank(){
		double average = 0.0;
		for(int x = 0; x < creatureNumber; x++) {
			average += botList.get(x).getRank();
			//System.out.println("Added " + botList.get(x).getRank() + " to get " + average);
		}
		//System.out.println("dividing " + average + " by " + Math.pow(creatureNumber, 2) + " to get " + average/Math.pow(creatureNumber, 2));
		return(average/(Math.pow(creatureNumber, 2)));
	}
	
	public void botsPrune() {
		//find the average score
		double average = getAverageRank();
		//System.out.println("Avg = " + average);
		//if a bot's rank is below average, delete it
		for(int x = 0; x < botList.size(); x++) {
			//System.out.println("Testing rank: " + botList.get(x).getRank());
			if(average/botList.get(x).getRank() > 1.05) {
				//System.out.println("Pruned");
				botList.remove(x);
				//go back one so none are skipped
				x--;
			}else {
				//reset rank
				botList.get(x).setRank(0);
			}
		}
	}
	
	public void botsMutate() {
		//System.out.println("Unmutated list = " + botList.toString());

		//mutates all bots
		for(int x = 0; x < creatureNumber; x++) {
			botList.get(x).genes().mutate(mutationRate);
		}
		
		//System.out.println("Mutated list = " + botList.toString());
	}
	
	public void botsScramble() {
		//Scramble the list
		ArrayList<Bot> tempList = new ArrayList<Bot>();
		
		//For all bots left in the list
		while(botList.size() > 0) {
			//System.out.println(botList.toString());
			//get a random bot
			int nextRand = rand.nextInt(botList.size());
			//add it to the new list
			//System.out.println("Adding " + botList.get(nextRand));
			tempList.add(botList.get(nextRand));
			//remove it from the old list
			botList.remove(nextRand);
		}
		//Make sure botlist is clear, and overwrite it with the randomized templist
		botList = new ArrayList<Bot>(tempList);
		//System.out.println("randomized botList = " + botList.toString());
	}
	
	public void botsSex() {
		//System.out.println(botList.toString());
		for(int x = 0; x < creatureNumber; x+=2) {
			if(x+1 > creatureNumber) {
				//System.out.println("Sex break");
				break;
			}
			//System.out.println(x + " " + creatureNumber);
			//System.out.println("\nPre-sex:\n" + botList.get(x).toString() + "\n" + botList.get(x+1).toString());
			botList.get(x).genes().sex(botList.get(x + 1).genes(), swapRate);
			//System.out.println("Post-sex:\n" + botList.get(x).toString() + "\n" + botList.get(x+1).toString());
		}
	}
	
	public static double botReaction(ArrayList<Double> inputs, NodeMatrix matrix, double timer) {
		//break if bord will be full
		if(timer > 0.7) {
			//System.out.println("full");
			return(0);
		}
		
		double reactions = 0;
		int previousMove = -1;
		//System.out.println(timer + "Testing board " + inputs.toString());

		//for all places on the board
		for(int x = 0; x < 9; x++) {
			//System.out.println("Testing board pos " + x);
			//check if move is already played
			if(Math.abs(inputs.get(x)) < 0.5) {
				//System.out.println("Unplayed square on " + x);
				//get burner list
				ArrayList<Double> tempList = new ArrayList<Double>();
				for(int y = 0; y < inputs.size(); y++) {
					tempList.add(inputs.get(y));
				}
				//set an enemy move at the pos
				tempList.set(x, -1.0);
				//System.out.println("Updated tempList " + tempList.toString());

				//get next move
				ArrayList<Double> newTempList = new ArrayList<Double>(matrixTurn(tempList, matrix));
				//System.out.println("Response board " + newTempList.toString());
				
				//find the difference
				for(int y = 0; y < newTempList.size(); y++) {
					//System.out.println("Checking " + y + " = " + newTempList.get(y) + " " + tempList.get(y));
					if(Math.abs(newTempList.get(y) - tempList.get(y)) > 0.5) {
						
						//System.out.println("Response detected on " + y + " value = " + Math.abs(newTempList.get(y) - tempList.get(y)));
						//check if this is the first move tested
						if(previousMove == -1) {
							//System.out.println("First move, breaking");
							previousMove = y;
							break;
						}else {
							//if it's reacted by playing a new move
							if(previousMove != y) {
								//System.out.println("Incrementing rxns, breaking");
								previousMove = y;
								reactions++;
								break;
							}
						}
					}
				}
			}
		}
		//Return score
		//System.out.println("Reactions = " + reactions);
		//System.out.println("Returned score = " + (reactions / (9 - (timer * 10))) * 3.0);
		return((reactions / (9-timer)) * 9);
	}
	public static ArrayList<Double> matrixTurn(ArrayList<Double> inputs, NodeMatrix matrix){
		//Updates the board given an input
		//run inputs through the matrix and get the move preferences
		//System.out.println(inputs.toString());
		matrix.setInputs(inputs);
		matrix.calculate();
		ArrayList<Double> returnList = new ArrayList<Double>(inputs);
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
		//System.out.println(highest + ", " + returnList.toString());
		//update inputs with new move
		returnList.set(highest, 1.0);
		
		//trim timer and team off the end to be updated
		returnList.remove(returnList.size() - 1);
		returnList.remove(returnList.size() - 1);

		//return updated inputs
		return(returnList);
	}
	
	public ArrayList<Double> listInvert(ArrayList<Double> inputs){
		//inverts the inputs, specifically for when it is O's move
		ArrayList<Double> outputs = new ArrayList<Double>();
		for(int x = 0; x < inputs.size(); x++) {
			outputs.add(inputs.get(x) * -1);
		}
		return(outputs);
	}
	
	public void botsCompete(Bot a, Bot b) {
		
		//runs a game between two bots, and updates their rank
		NodeMatrix one = new NodeMatrix(a);
		NodeMatrix two = new NodeMatrix(b);
		
		//create scores for both bots
		double scoreOne = 0.0;
		double scoreTwo = 0.0;
		
		//timer for measuring time passed
		double timer = -10.0;
		
		//ArrayList representing the current state of the board
		ArrayList<Double> boardInputs = new ArrayList<Double>();

		//Initialize the board with all zeros (minus two because timer and team will be added and updated later)
		for(int x = 0; x < inputs-2; x++) {
			boardInputs.add(0.0);
		}
		
		//boolean to determine who's turn it is
		boolean isXTurn = true; 
		//previous board used for comparing and scoring
		GameBoard previousBoard = new GameBoard();
		
		//loop as long as the board isn't full or in a victory state
		while(!tttTree.getBoardNode(boardInputs).board.isStop()) {

			//System.out.println("Start of turn " + boardInputs.toString());
			//System.out.println("X turn status: " + isXTurn);
			if(isXTurn) {
				//if it is X's turn
				//System.out.println("Xturn");

				//test the reaction of the enemy board
				scoreTwo += botReaction(boardInputs, two, timer);
				
				//append timer and team to the end as the last input node
				boardInputs.add(timer);
				boardInputs.add(1.0);
				
				//given the board state, X makes a move
				boardInputs = new ArrayList<Double>(matrixTurn(boardInputs, one));				

				//Get and store the current node
				GameTree.BoardNode originalNode = tttTree.getBoardNode(boardInputs);
				GameTree.BoardNode heldNode = tttTree.new BoardNode(originalNode);
				
				//Add the score of the board
				scoreOne += (heldNode.getXScoreRatio() * 1);
				
				//check if the move is a block
				if(heldNode.board.isMoveBlock(previousBoard)) {
					scoreOne += 3;
				}
				
				//scoreOne += botReaction(boardInputs, two, timer);
				
				//signal that it is now O's turn, set previousBoard, and increment timer
				isXTurn = false;
				timer += 2;
				//System.out.println(tttTree.getBoardNode(boardInputs).board.toString());
				//System.out.println(previousBoard.toString());
				previousBoard = new GameBoard(heldNode.board);
			}else {
				//if it is O's turn
				//System.out.println("Oturn");

				//test the reaction of the enemy board
				scoreOne += botReaction(boardInputs, one, timer);
				
				//Invert board state
				boardInputs = new ArrayList<Double>(listInvert(boardInputs));
				
				//append timer and team to the end as the last input node
				boardInputs.add(timer);
				boardInputs.add(-1.0);

				//given the board state, O makes a move
				boardInputs = new ArrayList<Double>(matrixTurn(boardInputs, two));
				
				//Invert board state again
				boardInputs = new ArrayList<Double>(listInvert(boardInputs));
				
				//Get and store the current node
				GameTree.BoardNode originalNode = tttTree.getBoardNode(boardInputs);
				GameTree.BoardNode heldNode = tttTree.new BoardNode(originalNode);
				
				//Add the score of the board
				scoreTwo += (heldNode.getYScoreRatio() * 1);
				
				//check if the move is a block
				if(heldNode.board.isMoveBlock(previousBoard)) {
					scoreTwo += 3;
				}
				
				
				
				//signal that it is now X's turn, set previousBoard, and increment timer
				isXTurn = true;
				timer += 2;
				
				//System.out.println(tttTree.getBoardNode(boardInputs).board.toString());
				//System.out.println(previousBoard.toString());
				previousBoard = new GameBoard(heldNode.board);
			}
			
			//System.out.println("End of turn " + boardInputs.toString());
			//System.out.println("Stop status:" + tttTree.getBoardNode(boardInputs).board.isStop() + "\n");
		}
		//System.out.println("Ended");
		
		//Get and store the current node
		GameTree.BoardNode originalNode = tttTree.getBoardNode(boardInputs);
		GameTree.BoardNode heldNode = tttTree.new BoardNode(originalNode);
		
		//check if the board is in a winning state
		if(heldNode.board.isWin()) {
			//if the winner is X
			if(heldNode.board.winTeam() == 1) {
				//System.out.println("X win");
				//double X's score
				scoreOne *= 2;
			//if the winner is O
			}else {
				//System.out.println("O win");
				//double O's score
				scoreTwo *= 2;
			}
		}else {
			//System.out.println("Full");
			scoreTwo *= 2;
		}
		
		//add the scores to each bot's rank
		a.setRank(a.getRank() + scoreOne);
		b.setRank(b.getRank() + scoreTwo);
	}
	
	public Bot getBestBot() {
		//compete all bots against each other
		for(int a = 0; a < creatureNumber; a++) {
			for(int b = 0; b < creatureNumber; b++) {
				
				//if the bots are the same, don't compete them against each other
				if(a == b) {
					break;
				}else {
					botsCompete(botList.get(a), botList.get(b));
				}
			}
		}
		
		botsSort();
		
		return(botList.get(0));
	}
	
	public void fullRun() {
		
		System.out.println("Filling");
		
		//Fill the bot list
		botsFill();
		
		BotRecorder recorder = new BotRecorder(botList.get(0).genes().sequence().size(), timer);
		
		ArrayList<Double> scores = new ArrayList<Double>();
		//System.out.println(botList.toString());
		
		//Repeat for the length of the timer
		for(int x = 0; x < timer; x++) {
			//System.out.println("Competeing");
			botsCompete();
			
			//System.out.println("Cycle " + x + ", average rank = " + ((getAverageRank())));
			System.out.println("Cycle " + x + ", normalized improvement = " + ((getNormalizedRank())));
			scores.add(getNormalizedRank());
			//average += (getNormalizedRank());
			
			//sort list
			botsSort();
			
			recorder.record(botList.get(0).genes().sequence());
			System.out.println("Best bot with a score of " + botList.get(0).getRank() + " = " + botList.get(0).genes().sequence().toString());

			//prune list
			botsPrune();
			
			//refill from best
			botsRefill();
			
			//scramble the list to get random pairs
			botsScramble();
			
			//kinky time :3
			botsSex();
			
			//mutate genes
			botsMutate();
			
		}
		
		/*
		System.out.print("Average for {");
		for(int x = 0; x < layers.length; x++) {
			System.out.print(layers[x] + ", ");
		}
		System.out.println("} = " + average/timer);
		
		double max = 0;
		for(int x = 0; x < timer; x++) {
			if(scores.get(x) > max) {
				max = scores.get(x);
			}
		}
		System.out.println("Max average = " + max);
		*/
	}
	
	
	public static void main(String[] args) {
		//Define properties of the bot
		int inputs = 11;
		int outputs = 9;
		int[] layers = {8};
		
		//Define properties of the experiment
		int creatureNumber = 100;
		double mutationRate = 0.005;
		double swapRate = 0.5;
		int timer = 5;
		GamePlayer gamePlayer = new GamePlayer(inputs, outputs, layers, creatureNumber, mutationRate, swapRate, timer);
		gamePlayer.fullRun();
		Bot bestBot = gamePlayer.getBestBot();
		System.out.println("Best bot with a score of " + bestBot.getRank() + " = " + bestBot.genes().sequence().toString());
	}
}
