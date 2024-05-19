package tttPackage;

public class TreeInvestigator {
	public static void main(String[] args) {
		GameTree TTT = new GameTree();
		
		GameBoard board = new GameBoard();
		
		TTT.addNodes(board);
		
		TTT.getScores();
		
		TTT.investigate();
	} 
}
