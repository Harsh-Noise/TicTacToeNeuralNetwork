package tttPackage;

import java.util.ArrayList;

public class GameBoard {
	int[][] board = new int[3][3];
	boolean isX = true;
	int timer = 0;
	
	public GameBoard() {
		////System.out.println("Setting");
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				board[x][y] = 0;
			}
		}
	}
	
	public GameBoard(GameBoard boardCopy) {
		////System.out.println("Setting");
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				board[x][y] = boardCopy.board[x][y];
			}
		}
	    isX = boardCopy.isX; // Copy the boolean field
	}
	
	public boolean isUnplayed(int x, int y) {
		return(board[x][y] == 0);
	}
	
	public void play(int x, int y) {
		if(isX) {
			isX = false;
			board[x][y] = 1;
		}else {
			isX = true;
			board[x][y] = 2;
		}
		timer++;
	}
	
	public void play(int x, int y, int z) {
		board[x][y] = z;
		timer++;
	}
	
	public int getTimer() {
		return(timer);
	}
	
	public void print() {
		/*
		for(int x = 2; x >= 0; x--) {
			for(int y = 0; y < 3; y++) {
				switch(board[y][x]) {
					case 0:
						System.out.print(".|");
						break;
					case 1:
						System.out.print("X|");
						break;
					case 2:
						System.out.print("O|");
						break;
				}
			}
			System.out.println("\n------");
		}
		*/
		
		printOne();
		System.out.println();
		printTwo();
		System.out.println();
		printThree();
		System.out.println();
	}
	
	public void printOne() {
		for(int x = 0; x < 3; x++) {
			switch(board[x][2]) {
				case 0:
					System.out.print(".");
					break;
				case 1:
					System.out.print("X");
					break;
				case 2:
					System.out.print("O");
					break;
			}
		}
	}
	
	public void printTwo() {
		for(int x = 0; x < 3; x++) {
			switch(board[x][1]) {
				case 0:
					System.out.print(".");
					break;
				case 1:
					System.out.print("X");
					break;
				case 2:
					System.out.print("O");
					break;
			}
		}
	}
	
	public void printThree() {
		for(int x = 0; x < 3; x++) {
			switch(board[x][0]) {
				case 0:
					System.out.print(".");
					break;
				case 1:
					System.out.print("X");
					break;
				case 2:
					System.out.print("O");
					break;
			}
		}
	}
	
	public boolean isWin() {
		boolean temp = false;
		
		for(int a = 0; a < 3; a ++) {
			for(int b = 0; b < 3; b ++) {
				
				//skip middle square because it will always be false
				if(a == 1 && b == 1) {
					continue;
				}
				
				//if reference square is unplayed, skip because it will be false
				int ab = board[a][b];
				if(ab == 0) {
					continue;
				}
				
				//Determine direction which you check for a win
				for(int c = -1; c < 2; c++) {
					for(int d = -1; d < 2; d++) {
						
						//skip 0 0 because pointer won't move
						if(c == 0 && d == 0) {
							continue;
						}
						
						//break if out of bounds
						if((a + (1*c)) < 0 || (b + (1*d)) < 0 || (a + (1*c)) > 2 || (b + (1*d)) > 2) {
							continue;
						}
						
						//check the first number in a row
						if(board[a + (1*c)][b + (1*d)] == ab) {
							
							//break if out of bounds
							if((a + (2*c)) < 0 || (b + (2*d)) < 0 || (a + (2*c)) > 2 || (b + (2*d)) > 2) {
								continue;
							}
							
							//check the second number in a row
							if(board[a + (2*c)][b + (2*d)] == ab) {
								temp = true;
							}
						}
					}
				}
			}
		}
		return(temp);
	}
	
	public int winTeam() {
		for(int a = 0; a < 3; a ++) {
			for(int b = 0; b < 3; b ++) {
				
				//skip middle square because it will always be false
				if(a == 1 && b == 1) {
					continue;
				}
				
				//if reference square is unplayed, skip because it will be false
				if(board[a][b] == 0) {
					continue;
				}
				
				//Determine direction which you check for a win
				for(int c = -1; c < 2; c++) {
					for(int d = -1; d < 2; d++) {
						
						//skip 0 0 because pointer won't move
						if(c == 0 && d == 0) {
							continue;
						}
						
						//break if out of bounds
						if((a + (1*c)) < 0 || (b + (1*d)) < 0 || (a + (1*c)) > 2 || (b + (1*d)) > 2) {
							continue;
						}
						
						//check the first number in a row
						if(board[a + (1*c)][b + (1*d)] == board[a][b]) {
							
							//break if out of bounds
							if((a + (2*c)) < 0 || (b + (2*d)) < 0 || (a + (2*c)) > 2 || (b + (2*d)) > 2) {
								continue;
							}
							
							//check the second number in a row
							if(board[a + (2*c)][b + (2*d)] == board[a][b]) {
								return(board[a][b]);
							}
						}
					}
				}
			}
		}
		return(-1);
	}
	
	public boolean isFull() {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(board[x][y] == 0) {
					return(false);				
				}
			}
		}
		return(true);
	}
	
	public boolean isStop() {
		return(isFull() || isWin());
	}
	
	public boolean isMoveBlock(GameBoard previous) {
		//find the move played
		for(int a = 0; a < 3; a++) {
			for(int b = 0; b < 3; b++) {
				if(previous.board[a][b] != board[a][b]) {
					//Determine direction which you check for a block
					for(int c = -1; c < 2; c++) {
						for(int d = -1; d < 2; d++) {
							
							//if it's a center move
							if(a == 1 && b == 1) {
								//the sum of a block's teams can either be 4 (1, 1, 2) or 5 (2, 2, 1)
								int sum = board[a + (1*c)][b + (1*d)] + board[a - (1*c)][b - (1*d)];
								if(sum == 4 || sum == 5) {
									return(true);
								}
							}else {
								//break if out of bounds
								if((a + (1*c)) < 0 || (b + (1*d)) < 0 || (a + (1*c)) > 2 || (b + (1*d)) > 2) {
									continue;
								}
								if((a + (2*c)) < 0 || (b + (2*d)) < 0 || (a + (2*c)) > 2 || (b + (2*d)) > 2) {
									continue;
								}
								
								//the sum of a block's teams can either be 4 (1, 1, 2) or 5 (2, 2, 1)
								int sum = board[a + (1*c)][b + (1*d)] + board[a + (2*c)][b + (2*d)];
								if(sum == 4 || sum == 5) {
									return(true);
								}
							}
						}
					}
				}
			}
		}
		//else return false
		return(false);
	}
	
	public String toString() {
		String boardString = new String();
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++){
				boardString += (board[y][x] + ", "); 
			}
		}
		return(boardString);
	}
	
	public ArrayList<Double> toArray(){
		ArrayList<Double> returnList = new ArrayList<Double>(); 
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++){
				if(board[y][x] == 2) {
					returnList.add(-1.0);
				}else {
					returnList.add((double)board[y][x]);
				}
			}
		}
		return(returnList);
	}
	
	public static void main(String[] args) {
	
	}
}
