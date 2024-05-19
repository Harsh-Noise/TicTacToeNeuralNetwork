package tttPackage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class GameTree {
	private BoardNode root;
    //Class and constructor for a tree BoardNode
    public class BoardNode {
		public GameBoard board;
        int xScore;
        int oScore;
        int drawScore;
        //all 9 possible move locations
        BoardNode zz;
        BoardNode zo;
        BoardNode zt;
        BoardNode oz;
        BoardNode oo;
        BoardNode ot;
        BoardNode tz;
        BoardNode to; 
        BoardNode tt;

        BoardNode(GameBoard board) {
            this.board = board;
        }
        
        public BoardNode(BoardNode BoardNode) {
            this.board = BoardNode.board;
            this.xScore = BoardNode.xScore;
            this.oScore = BoardNode.oScore;
            this.drawScore = BoardNode.drawScore;
        }
        
        public double getXScoreRatio(){
        	if(oScore + drawScore == 0) {
        		return(0);
        	}
        	return (((double)xScore)/(oScore + drawScore));
        }
        
        public double getYScoreRatio() {
        	if(getXScoreRatio() == 0) {
        		return(0);
        	}
        	return(1/getXScoreRatio());
        }
        
        public String toString() {
        	//System.out.println();
        	//System.out.println("XScore = " + xScore);
        	//System.out.println("OScore = " + oScore);
        	//System.out.println("DScore = " + drawScore);

        	return(board.toString());
        }
    }
    
    private class Pair {
    	int x;
    	int y;
    	Pair(int x, int y){
    		this.x = x;
    		this.y = y;
    	}
    	
    	public int getX() {
    		return(x);
    	}
    	
    	public int getY() {
    		return(y);
    	}
    	
    	public String toString() {
    		return("[" + x + ", " + y + "]");
    	}
    }
    
    Scanner scan = new Scanner(System.in);
    
    public BoardNode getPos(BoardNode current, int x) {
    	switch(x) {
    	case 1:
    		return(current.zz);
    	case 2:
    		return(current.zo);
    	case 3:
    		return(current.zt);
    	case 4:
    		return(current.oz);
    	case 5:
    		return(current.oo);
    	case 6:
    		return(current.ot);
    	case 7:
    		return(current.tz);
    	case 8:
    		return(current.to);
    	case 9:
    		return(current.tt);
       	default:
    		return(current);
    	}
    }
    
    public void addNodes(GameBoard board) {
    	root = addNodes(root, board);
    }
    
    private BoardNode addNodes(BoardNode current, GameBoard board) {
    	
    	if(current == null) {
    		current = new BoardNode(board);
    	}
    	
    	current.board = new GameBoard(board);
    	
    	if(current.board.isWin() || current.board.isFull()) {
    		return(current);
    	}
    	
    	if(current.board.isUnplayed(0, 0)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(0, 0);
            current.zz = addNodes(current.zz, newBoard);
    	}
    	if(current.board.isUnplayed(0, 1)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(0, 1);
            current.zo = addNodes(current.zo, newBoard);
    	}
    	if(current.board.isUnplayed(0, 2)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(0, 2);
            current.zt = addNodes(current.zt, newBoard);
    	}
    	if(current.board.isUnplayed(1, 0)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(1, 0);
            current.oz = addNodes(current.oz, newBoard);
    	}
    	if(current.board.isUnplayed(1, 1)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(1, 1);
            current.oo = addNodes(current.oo, newBoard);
    	}
    	if(current.board.isUnplayed(1, 2)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(1, 2);
            current.ot = addNodes(current.ot, newBoard);
    	}
    	if(current.board.isUnplayed(2, 0)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(2, 0);
            current.tz = addNodes(current.tz, newBoard);
    	}
    	if(current.board.isUnplayed(2, 1)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(2, 1);
            current.to = addNodes(current.to, newBoard);
    	}
    	if(current.board.isUnplayed(2, 2)) {
    		GameBoard newBoard = new GameBoard(current.board);
            newBoard.play(2, 2);
            current.tt = addNodes(current.tt, newBoard);
    	}
    	return(current);
    }
    
    public void getScores() {
    	root.xScore = getScoresX(root);
    	root.oScore = getScoresY(root);
    	root.drawScore = getScoresDraw(root);

    }
    
    private int getScoresX(BoardNode current) {
    	if(current == null) {
    		return(0);
    	}
    	
    	if(current.board.isWin()) {
    		if(current.board.winTeam() == 1) {
    			current.xScore = 1;
    			return(1);
    		}
    	}
    	
    	if(current.board.isFull()) {
    		return(0);
    	}
    		
    	current.xScore = getScoresX(current.zz) + getScoresX(current.zo) + getScoresX(current.zt) + getScoresX(current.oz) + getScoresX(current.oo) + getScoresX(current.ot) + getScoresX(current.tz) + getScoresX(current.to) + getScoresX(current.tt); 
    	return(current.xScore);
    }    
    
    private int getScoresY(BoardNode current) {
    	if(current == null) {
    		return(0);
    	}
    	
    	if(current.board.isWin()) {
    		if(current.board.winTeam() == 2) {
    			current.oScore = 1;
    			return(1);
    		}
    	}
    	
    	if(current.board.isFull()) {
    		return(0);
    	}
    		
    	current.oScore = getScoresY(current.zz) + getScoresY(current.zo) + getScoresY(current.zt) + getScoresY(current.oz) + getScoresY(current.oo) + getScoresY(current.ot) + getScoresY(current.tz) + getScoresY(current.to) + getScoresY(current.tt); 
    	return(current.oScore);
    }    
    
    private int getScoresDraw(BoardNode current) {
    	if(current == null) {
    		return(0);
    	}
    	
    	if(current.board.isWin()) {
    		return(0);
    	}
    	
    	if(current.board.isFull()) {
    		current.drawScore = 1;
    		return(1);
    	}
    		
    	current.drawScore = getScoresDraw(current.zz) + getScoresDraw(current.zo) + getScoresDraw(current.zt) + getScoresDraw(current.oz) + getScoresDraw(current.oo) + getScoresDraw(current.ot) + getScoresDraw(current.tz) + getScoresDraw(current.to) + getScoresDraw(current.tt); 
    	return(current.drawScore);
    }
    
    public void investigate() {
    	System.out.println("Select which board # to travel to, or type 0 to leave");
    	investigate(root);
    }
    
    private void investigate(BoardNode current) {
    	if(current == null) {
    		System.out.print("Null state reached");
    	}else {
	    	System.out.println("Current board: \nNumber of possible X wins = " + current.xScore + "\nNumber of possible O wins = " + current.oScore + "\nNumber of possible draws = " + current.drawScore);
	    	if (current.oScore == 0) {
	    		System.out.println("X favorability is 100%");
	    	}else {
	    		System.out.printf("X favorability is %.2f\n", (current.getXScoreRatio()));
	    		System.out.printf("O favorability is %.2f\n", (current.getYScoreRatio()));
	    	}
	    	current.board.print();
	    	
	    	//Print what board you're seeing
	    	System.out.print("Board # ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    			System.out.print(x + "    ");
	    		}
	    	}
	    	System.out.println();
	    	
	    	//Print x score
	    	System.out.print("X Ratio ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    			System.out.printf("%.2f ", (getPos(current, x).getXScoreRatio()));
	    	    }
	    	}
	    	System.out.println();
	    	
	    	//Print y score
	    	System.out.print("O Ratio ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    	    	System.out.printf("%.2f ", (getPos(current, x).getYScoreRatio()));
	    		}
	    	}
	    	System.out.println();
	    	
	    	//Print each layer
	    	System.out.print("Preview ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    			getPos(current, x).board.printOne();
	    			System.out.print("  ");
	    		}
	    	}
	    	System.out.println();
	    	
	    	System.out.print("        ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    			getPos(current, x).board.printTwo();
	    			System.out.print("  ");
	    		}
	    	}
	    	System.out.println();
	    	
	    	System.out.print("        ");
	    	for(int x = 1; x < 10; x++) {
	    		if(getPos(current, x) != null) {
	    			getPos(current, x).board.printThree();
	    			System.out.print("  ");
	    		}
	    	}
	    	System.out.println();
	    	
	    	
	    	
	    	int choice = scan.nextInt();
	    	
	    	switch(choice) {
	    	case 0:
	    		break;
	    	case 1:
	    		investigate(current.zz);    	
	    		break;
	    	case 2:
	    		investigate(current.zo);    	
	    		break;
	    	case 3:
	    		investigate(current.zt);    	
	    		break;
	    	case 4:
	    		investigate(current.oz);    	
	    		break;
	    	case 5:
	    		investigate(current.oo);    	
	    		break;
	    	case 6:
	    		investigate(current.ot);    	
	    		break;
	    	case 7:
	    		investigate(current.tz);    	
	    		break;
	    	case 8:
	    		investigate(current.to);    	
	    		break;
	    	case 9:
	    		investigate(current.tt);    	
	    		break;
	    	}
    	}
    }

    public BoardNode getBoardNode(ArrayList<Double> moves) {
    	//ArrayLists representing the coordinates of all Xs and Os
    	ArrayList<Pair> xPlays = new ArrayList<Pair>();
    	ArrayList<Pair> oPlays = new ArrayList<Pair>();
    	
    	//GameBoard just for visualizing & debugging
    	GameBoard board = new GameBoard();
    	
    	//Pointer for scanning through moves list
    	int pointer = 0;
    	
    	//for all x and y coordinates in the board
    	for(int y = 0; y < 3; y++) {
    		for(int x = 0; x < 3; x++) {
    			
    			//if it's an X move, record its coordinates
        		if(moves.get(pointer) > 0.5) {
        			xPlays.add(new Pair(x, y));
        			board.play(x, y, 1);
        			
        			//else, record O coordinates
        		}else if(moves.get(pointer) < -0.5) {
        			oPlays.add(new Pair(x, y));
        			board.play(x, y, 2);

        		}
        		pointer++;
        	}
    	}
    	
    	//print for debug
    	//board.print();
    	//System.out.println(xPlays.toString());
    	//System.out.println(oPlays.toString());
    	
    	//In the below test, calculated BoardNode is compared to root for bugtesting. If calculated BoardNode is actually supposed to be root, return root
    	if(xPlays.size() == 0 && oPlays.size() == 0) {
    		return(root);
    	}
    	//test for stuck in loop
    	while(true) {
        	BoardNode newBoardNode = getBoardNode(root, new ArrayList<Pair>(xPlays), new ArrayList<Pair>(oPlays), true,  0);
        	if(newBoardNode != root) {
        		return(newBoardNode);
        	}
    	}
    }
    
    private BoardNode getBoardNode(BoardNode current, ArrayList<Pair> xPlays, ArrayList<Pair> oPlays, boolean isX, int timer) {
    	//breakout if stuck in loop
    	if(timer > 9) {
    		return(root);
    	}
    	
    	//Random for choosing a random move to make
    	Random rand = new Random();
    	
    	//if there are no moves left, return current BoardNode
    	if(xPlays.size() == 0 && oPlays.size() == 0) {
    		//current.board.print();
    		//System.out.println("Done!");
    		return(current);
    	}else {
    		//current.board.print();
    		
    		
	    	//System.out.println();
    		//System.out.println("X plays & O plays: \n" + xPlays.toString());
			//System.out.println(oPlays.toString());
			
    		//if there are moves, check if it is an even numbered move (X move)
    		if(isX) {
    			//Select a random move to make
    			int randomMove = rand.nextInt(xPlays.size());
    			
    			//turn coordinates to an int
        		int choice = (xPlays.get(randomMove).getX() * 10) + xPlays.get(randomMove).getY();
        		
        		//System.out.println(choice);
        	
        		switch(choice) {
    	    	case 0:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zz.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
    	    		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.zz, xPlays, oPlays, false, timer + 1));    	
    	    	case 1:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zo.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
    	    		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            	
    	    		return(getBoardNode(current.zo, xPlays, oPlays, false, timer + 1));    	
    	    	case 2:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zt.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
    	    		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.zt, xPlays, oPlays, false, timer + 1));    	
    	    	case 10:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.oz.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.oz, xPlays, oPlays, false, timer + 1));    	
    	    	case 11:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.oo.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.oo, xPlays, oPlays, false, timer + 1));    	
    	    	case 12:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.ot.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.ot, xPlays, oPlays, false, timer + 1));    	
    	    	case 20:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.tz.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.tz, xPlays, oPlays, false, timer + 1));    	
    	    	case 21:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.to.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.to, xPlays, oPlays, false, timer + 1));    	
    	    	case 22:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.tt.board.isStop() && xPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, true, timer + 1));
    	    		}
            		
            		//prune selected X move
    	    		xPlays.remove(randomMove);            		
    	    		return(getBoardNode(current.tt, xPlays, oPlays, false, timer + 1));    	
    	    	}
        	}else {
        		//Select a random move to make
    			int randomMove = rand.nextInt(oPlays.size());
    			
        		//turn coordinates to an int
        		int choice = (oPlays.get(randomMove ).getX() * 10) + oPlays.get(randomMove ).getY();
        		
        		//System.out.println(choice);
        		
        		
        		switch(choice) {
    	    	case 0:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zz.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.zz, xPlays, oPlays, true, timer + 1));    	
    	    	case 1:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zo.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.zo, xPlays, oPlays, true, timer + 1));    	
    	    	case 2:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.zt.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.zt, xPlays, oPlays, true, timer + 1));    	
    	    	case 10:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.oz.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.oz, xPlays, oPlays, true, timer + 1));    	
    	    	case 11:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.oo.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.oo, xPlays, oPlays, true, timer + 1));    	
    	    	case 12:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.ot.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.ot, xPlays, oPlays, true, timer + 1));    	
    	    	case 20:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.tz.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.tz, xPlays, oPlays, true, timer + 1));    	
    	    	case 21:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.to.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.to, xPlays, oPlays, true, timer + 1));    	
    	    	case 22:
    	    		//if the move puts the board into a winning state and isn't the last move, get a new random move
    	    		if(current.tt.board.isStop() && oPlays.size() > 1) {
    	    			return(getBoardNode(current, xPlays, oPlays, false, timer + 1));
    	    		}
    	    		
    	    		//prune selected O move
            		oPlays.remove(randomMove);
    	    		return(getBoardNode(current.tt, xPlays, oPlays, true, timer + 1));    	
    	    	}
        	}
    	}
    	//idk it really wants a return statement but I have like 20
		return(root);
    }
    
    public void printBranches(BoardNode current) {
    	/*
    	System.out.print("Board # ");
    	for(int x = 0; x < 9; x++) {
    		if(getPos(current, x) != null) {
    			System.out.print(x + "    ");
    		}
    	}
    	System.out.println();
    	*/
    	//Print each layer
    	System.out.print("Preview ");
    	for(int x = 1; x < 10; x++) {
    		if(getPos(current, x) != null) {
    			getPos(current, x).board.printOne();
    			System.out.print("  ");
    		}
    	}
    	System.out.println();
    	
    	System.out.print("        ");
    	for(int x = 1; x < 10; x++) {
    		if(getPos(current, x) != null) {
    			getPos(current, x).board.printTwo();
    			System.out.print("  ");
    		}
    	}
    	System.out.println();
    	
    	System.out.print("        ");
    	for(int x = 1; x < 10; x++) {
    		if(getPos(current, x) != null) {
    			getPos(current, x).board.printThree();
    			System.out.print("  ");
    		}
    	}
    	System.out.println();
    }

    
    
    
    public static void main(String[] args) {
    	
    	for(int x = 0; x < 9; x++) {
    		System.out.println(x + " " + (int)Math.floor(x/3.0) + " " + x%3);
    	}
    	
    	
    	
		ArrayList<Integer> boardInputs = new ArrayList<Integer>();
		boardInputs.add(1);
		boardInputs.add(1);
		boardInputs.add(1);
		boardInputs.add(1);
		boardInputs.add(-1);
		boardInputs.add(-1);
		boardInputs.add(1);
		boardInputs.add(-1);
		boardInputs.add(-1);
		
		
		System.out.println(boardInputs.toString());
		GameTree tttTree = new GameTree();
		GameBoard board = new GameBoard();
		tttTree.addNodes(board);
		tttTree.getScores();
		//System.out.println(tttTree.getBoardNode(boardInputs));
    }
}



