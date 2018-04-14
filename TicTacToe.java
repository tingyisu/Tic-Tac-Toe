// play TicTacToe against my AI!
// choose what sized board you want to play on and let the fun begin!

import java.util.Scanner; 
import java.util.Random; 

public class TicTacToe {

	public static void main(String[] args) {
		play();
	}
	
	// A. createBoard method
	public static char[][] createBoard(int n) {
		// create a new 2D char array with dimensions n by n
		char[][] emptyBoard = new char[n][n]; 
		// use a loop to initialize an empty space for each element 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				emptyBoard[i][j] = ' ';
			}
		}
		return emptyBoard; 
	}
	
	// B. displayBoard method
	public static void displayBoard(char[][] n) {
		// i and j both go from 0 to n.length*2 in order to make a board with individual cells 
		for (int i = 0; i < n.length*2 + 1; i++) {
			for (int j = 0; j < n.length*2 + 1; j++) {
				// when i is 0 or an even number, print '+'s or '-'s
				if (i % 2 == 0) {
					// if j is 0 or an even number, print '+'
					if (j % 2 == 0) {
						System.out.print('+');	
					// if j is not 0 nor an even number, print '-' 
					} else {
						System.out.print('-');
					}
				// when i is not 0 nor an even number, print '|' or the element in the array
				} else {
					// when j is 0 or an even number, print '|' 
					if (j % 2 == 0) {
						System.out.print('|');
					// when j is not 0 nor an even number, print the element in the array 
					} else {
						// use (i - 1)/2 and (j - 1)/2 to get back the original indices of elements 
						System.out.print(n[(i - 1)/2][(j - 1)/2]);
					}
				}
			}
			System.out.println();
		}
	}
	
	// C. writeOnBoard method
	public static void writeOnBoard(char[][] n, char c, int x, int y) {
		// conditional statements to determine whether the x and y coordinates represent a legal cell on the board
		if (x > n.length - 1 || x < 0 || y > n.length - 1 || y < 0) {
			throw new IllegalArgumentException("This cell does not exist on the board!"); 
		} else if (n[x][y] != ' ') {
			throw new IllegalArgumentException("This cell has already been occupied.");
		// if the coordinates do not represent an illegal cell, update the cell with the character 
		} else {
			n[x][y] = c; 
		}
	}
	
	// D. getUserMove method 
	public static void getUserMove(char[][] n) {
		// variables to represent the coordinates
		int x, y; 
		// Scanner for the coordinates to be entered by the user 
		Scanner coordinates = new Scanner(System.in);
		System.out.print("Please enter your move: ");
		x = coordinates.nextInt();
		y = coordinates.nextInt();
		// if coordinates given represent an illegal cell, ask user for a new input
		while (x > n.length - 1 || x < 0 || y > n.length - 1 || y < 0 || n[x][y] != ' ') {
			System.out.print("Your move is not valid, please enter a new move."); 
			x = coordinates.nextInt();
			y = coordinates.nextInt();
		}
			// once coordinates given represent a legal cell, update the board with the user's chosen move
			writeOnBoard(n, 'x', x, y); 
	}
	
	// E. checkForObviousMove method
	public static boolean checkForObviousMove(char[][] n) { 
		// loop to check for all possible obvious moves on the board through helper methods 
		for (int x = 0; x < n.length; x++) {
			for (int y = 0; y < n.length; y++) {
				// -1 is used to represent no winning row/column 
				// conditional statements to determine if there is a winning/losing row/column/descending diagonal/ascending diagonal
				// if the winning/losing row/column/diagonal contains an empty cell, update the board by adding an 'o' to that cell 
				if (rowWin(n) != -1 && n[rowWin(n)][y] == ' ') {
					writeOnBoard(n, 'o', rowWin(n), y);
					return true; 
				} else if (columnWin(n) != -1 && n[x][columnWin(n)] == ' ') {
					writeOnBoard(n, 'o', x, columnWin(n)); 
					return true; 
				} else if (diagonalDescendingWin(n) && n[y][y] == ' ') {
					writeOnBoard(n, 'o', y, y);
					return true;
				} else if (diagonalAscendingWin(n) && n[y][n.length - 1 - y] == ' ') {
					writeOnBoard(n, 'o', y, n.length - 1 - y); 
					return true;
				} else if (rowLose(n) != -1 && n[rowLose(n)][y] == ' ') {
					writeOnBoard(n, 'o', rowLose(n), y);
					return true;
				} else if (columnLose(n) != -1 && n[x][columnLose(n)] == ' ') {
					writeOnBoard(n, 'o', x, columnLose(n)); 
					return true;
				} else if (diagonalDescendingLose(n) && n[y][y] == ' ') {
					writeOnBoard(n, 'o', y, y);
					return true; 
				} else if (diagonalAscendingLose(n) && n[y][n.length - 1 - y] == ' ') {
					writeOnBoard(n, 'o', y, n.length - 1 - y); 
					return true; 
				}
			}
		}
		return false; 
	}  
	
	// E. helper method 1: checks whether AI is winning on a row 
	// returns x coordinate of row where AI is winning; chose to return x b/c all cells in a row have the same x coordinate
	public static int rowWin(char[][] n) {
		int x = 0; // temporary value 
		int winningRow = -1; // -1 does not exist on the board and will be used to mean no winning row 
		// loops through all possible rows on the board for a possible winning row 
		while (x < n.length) {
			int numberOfOs = 0; // variable to keep track number of 'o's on each row, reset back to 0 when counting a new row
			//loops through each cell in the row and updates number of 'o's in that row
			for (int y = 0; y < n.length; y++) {
				if (n[x][y] == 'o') {
					numberOfOs++;
				}
			}
			// if a row is found to have n.length - 1 (missing 1) 'o's then it is a winning row and return the x coordinate of that row 
			if (numberOfOs == n.length - 1) {
				winningRow = x; 
				return winningRow;
			// if the row does not have n.length - 1 'o's go to the next row 
			} else {
				x++; 
			}
		} 
		return winningRow; 
	}
	
	// E. helper method 2: checks whether AI is losing on a row
	// returns the x coordinate of the row where AI is losing; chose to return x b/c all cells in a row have the same x coordinate
	public static int rowLose(char[][] n) {
		int x = 0; // temporary value 
		int losingRow = -1; // -1 does not exist on the board, used to mean no losing row
		//loops through all rows to find possible losing rows
		while (x < n.length) {
			int numberOfXs = 0; // variable to keep track of number of 'x's in a row; resets to 0 for each new row
			// loops through all cells in the row and updates the number of 'x's in that row
			for (int y = 0; y < n.length; y++) {
				if (n[x][y] == 'x') {
					numberOfXs++;
				}
			}
			// if the row contains n.length - 1 'x's, it is a losing row (for AI) and its x coordinate is returned
			if (numberOfXs == n.length - 1) {
				losingRow = x;
				return losingRow;
			// if the row does not contain n.length - 1 'x's, go the the next row 
			} else {
				x++; 
			}
		}
		return losingRow; 
	}
	
	// E. helper method 3: checks whether AI is winning on a column
	// returns the y coordinate of the column where AI is winning; chose to return y b/c all cells in a column have the same y coordinate 
	public static int columnWin(char[][] n) {
		int y = 0; // temporary value 
		int winningColumn = -1; // -1 does not exist on the board and will be used to mean no winning row 
		// loops through all columns to find possible winning column
		while (y < n.length) {
			int numberOfOs = 0; // variable to keep track of number of 'o's on column; resets to 0 for each new column
			// loops through all cells on the column and updates the number of 'o's in that column 
			for (int x = 0; x < n.length; x++) {
				if (n[x][y] == 'o') {
					numberOfOs++;
				}
			}
			// if the column with n.length - 1 'o's is found, it is a winning column and its y coordinate is returned 
			if (numberOfOs == n.length - 1) {
				winningColumn = y; 
				return winningColumn;
			// if the column does not have n.length - 1 'o's, go to the next column 
			} else {
				y++; 
			}
		} 
		return winningColumn; 
	}
	
	// E. helper method 4: checks whether AI is losing on a column
	// returns the y coordinate of the column where AI is losing; chose to return y b/c all cells in a column have the same y coordinate 
	public static int columnLose(char[][] n) {
		int y = 0; // temporary value 
		int losingColumn = -1; // -1 does not exist on the board, used to mean no losing row
		// loops through all columns for possible losing column
		while (y < n.length) {
			int numberOfXs = 0; // variable to keep track of number of 'x's on a column; resets to 0 for each new column
			// loops through all cells in a column and updates the number of 'x's in that column 
			for (int x = 0; x < n.length; x++) {
				if (n[x][y] == 'x') {
					numberOfXs++;
				}
			}
			// if the column has n.length - 1 'x's then it is a losing column and its y coordinate is returned
			if (numberOfXs == n.length - 1) {
				losingColumn = y;
				return losingColumn;
			// if the column does not have n.length - 1 'x's then go to the next column 
			} else {
				y++; 
			}
		}
		return losingColumn; 
	}
	
	// E. helper method 5: checks whether AI is winning on the descending diagonal (diagonal with negative slope)
	// returns true or false since there is only one descending diagonal on the board 
	public static boolean diagonalDescendingWin(char[][] n) {
		int numberOfOs = 0; // variable to keep track of number of 'o's on descending diagonal
		// loop that finds all cells on descending diagonal and updates numberOfOs when a cell contains an 'o'
		for (int x = 0; x < n.length; x++) {
			for (int y = 0; y < n.length; y++) {
				if (x == y & n[x][x] == 'o') {
					numberOfOs++; 
				}
			}
		// if the descending diagonal has n.length - 1 'o's, it is a winning diagonal and return true
		} if (numberOfOs == n.length - 1) {
			return true; 
		}
		// if there are not n.length - 1 'o's then it is not a winning diagonal 
		return false; 
	}
	
	// E. helper method 6: checks whether AI is losing on the descending diagonal (diagonal with negative slope)
	// returns true or false since there is only one descending diagonal 
	public static boolean diagonalDescendingLose(char[][] n) {
		int numberOfXs = 0; // variable to keep track of number of 'x's on descending diagonal
		// loop that finds all cells on descending diagonal and updates numberOfXs when a cell contains an 'x'
		for (int x = 0; x < n.length; x++) {
			for (int y = 0; y < n.length; y++) {
				if (x == y & n[x][x] == 'x') {
					numberOfXs++; 
				}
			}
		// if the descending diagonal contains n.length - 1 'x's, it is a losing diagonal, return true
		} if (numberOfXs == n.length - 1) {
			return true; 
		} // else return false 
		return false; 
	}
	
	// E. helper method 7: checks whether AI is winning on the ascending diagonal (diagonal with positive slope)
	// returns true or false since there is only one ascending diagonal 
	public static boolean diagonalAscendingWin(char[][] n) {
		int numberOfOs = 0; // variable to keep track of number of 'x's on descending diagonal
		// loop that finds all cells on ascending diagonal and updates numberOfOs when a cell contains an 'o'
		for (int i = 0; i < n.length; i++) {
			if (n[i][n.length - 1 - i] == 'o') {
					numberOfOs++; 
			}
		} 
		// if the ascending diagonal contains n.length - 1 'x', it is a winning diagonal, and return true
		if (numberOfOs == n.length - 1) {
			return true;
		} // otherwise return false 
		return false;
	}
	
	// helper method 8: checks whether AI is losing on the ascending diagonal (negative slope)
	// returns true or false since there is only one ascending diagonal on the board 
	public static boolean diagonalAscendingLose(char[][] n) {
		int numberOfXs = 0; // keeps track of the number of 'x's on the ascending diagonal 
		// loop that finds all cells on ascending diagonal and updates numberOfXs when a cell contains an 'x'
		for (int i = 0; i < n.length; i++) {
			if (n[i][n.length - 1 - i] == 'x') { // i, n.length-1-i represents coordinates of cells on ascending diagonal
					numberOfXs++; 
			}
		} 
		// if the ascending diagonal contains n.length - 1 'x', it is a losing diagonal, and return true
		if (numberOfXs == n.length - 1) {
			return true;
		} // else return false 
		return false;
	}
	
	// F. getAIMove method
	public static void getAIMove(char[][] n) {
		int x = 0; // temp value
		int y = 0; // temp value 
		// if there is an obvious move, then make the move, else generate a random move for AI 
		if (checkForObviousMove(n)) {
		} else {
			Random randomGenerator = new Random(); 
			// until the coordinate represents an available cell, generate as many new random coordinates as needed 
			int i = 0;
			while (i >= 0) {
				x = randomGenerator.nextInt(n.length);
				y = randomGenerator.nextInt(n.length);
				// once have found an available cell, make the move, update the board, and break the loop 
				if (n[x][y] == ' ') {
					writeOnBoard(n, 'o', x, y);
					break; 
				} else {
					i++; 
				}
			}
		}
	}
	
	// G. checkForWinner method
	public static char checkForWinner(char[][] n) {
		char winner = ' '; // temporary value 
		// conditional statements to find the winner through helper method 
		if (rowWinner(n) == 'o') {
			winner = 'o';
			return winner;
		} else if (rowWinner(n) == 'x') {
			winner = 'x';
			return winner;
		} else if (columnWinner(n) == 'o') {
			winner = 'o';
			return winner;
		} else if (columnWinner(n) == 'x') {
			winner = 'x';
			return winner;
		} else if (diagonalDescendingWinner(n) == 'o') {
			winner = 'o';
			return winner;
		} else if (diagonalDescendingWinner(n) == 'x') {
			winner = 'x';
			return winner;
		} else if (diagonalAscendingWinner(n) == 'o') {
			winner = 'o';
			return winner;
		} else if (diagonalAscendingWinner(n) == 'x') {
			winner = 'x';
			return winner;
		// if there is no winner, return ' ' to represent no winner
		} else {
			return winner; 
		}
	} 
	
	// G. helper method 1: check every row for a winner and return the winner 
	public static char rowWinner(char[][] n) {
		int x = 0; // variable to represent the x coordinate
		char winner = ' '; // temporary value; will stay ' ' if there is not row winner 
		// loop to determine if someone has won on a row 
		while (x < n.length) {
			int numberOfOs = 0; // variable to keep track of the number of 'o's on a row; resets to 0 for each new row
			int numberOfXs = 0; // variable to keep track of the number of 'x's on a row; resets to 0 for each new row
			// loop through each cell on a row and updates the number of 'o'/'x' on the row
			for (int y = 0; y < n.length; y++) {
				if (n[x][y] == 'o') {
					numberOfOs++;
				} else if (n[x][y] == 'x') {
					numberOfXs++; 
				}
			}
			// if a row is found to contain n.length number of 'o's, the winner is 'o'
			if (numberOfOs == n.length) {
				winner = 'o'; // 'o' represents AI 
				return winner;
			// if a row is found to contain n.length number of 'x's, the winner is 'x'
			} else if (numberOfXs == n.length) {
				winner = 'x'; // 'x' represents the user 
				return winner; 
			// if a row does not contain n.length number of 'o's/'x's, go to the next row 
			} else {
				x++; 
			}
		} 
		return winner; 
	}
	
	// G. helper method 2: check every column for a winner and return the winner 
	public static char columnWinner(char[][] n) {
		int y = 0; // variable to represent the y coordinate 
		char winner = ' '; // temporary value, will stay ' '  if there is no column winner 
		// loop to find all possible cells on a column 
		while (y < n.length) {
			int numberOfOs = 0; // variable to keep track of the number of 'o's on a column
			int numberOfXs = 0; // variable to keep track of the number of 'x's on a column 
			// if there is a 'o'/'x' in a cell of the column, update the number of 'o's/'x's 
			for (int x = 0; x < n.length; x++) {
				if (n[x][y] == 'o') {
					numberOfOs++;
				} else if (n[x][y] == 'x') {
					numberOfXs++; 
				}
			}
			// if the column has n.length number of 'o's, the winner is 'o', and return the winner 
			if (numberOfOs == n.length) {
				winner = 'o'; 
				return winner; 
			// if the column has n.length number of 'x's, the winner is 'x', and return the winner
			} else if (numberOfXs == n.length){
				winner = 'x';
				return winner; 
			// if the column does not contain n.length number of 'o's/'x's, go to the next column
			} else {
				y++; 
			}
		} 
		return winner; 
	}
	
	// G. helper method 3: check descending diagonal (diagonal with negative slope) for a winner and returns the winner
	public static char diagonalDescendingWinner(char[][] n) {
		int numberOfOs = 0; // variable to keep track of the number of 'o's on the descending diagonal 
		int numberOfXs = 0; // variable to keep track of the number of 'x's on the descending diagonal  
		char winner = ' '; // temporary value, if no winner is ' ' 
		// loop through all the cells on the board to find the cells on the descending diagonal 
		for (int x = 0; x < n.length; x++) {
			for (int y = 0; y < n.length; y++) {
				// if is a cell on the descending diagonal and it contains an 'o'/'x', update the number of 'o'x/'x's
				if (x == y && n[x][x] == 'o') {
					numberOfOs++; 
				} else if (x == y && n[x][x] == 'x') {
					numberOfXs++; 
				}
			}
		// if the descending diagonal contains n.length number of 'o's, winner is 'o' and return the winner
		} if (numberOfOs == n.length) {
			winner = 'o';
			return winner; 
		// if the descending diagonal contains n.length number of 'x's, winner is 'x' and return the winner
		} else if (numberOfXs == n.length) {
			winner = 'x';
			return winner; 
		}
		return winner; 
	}
	
	// helper method 4: check ascending diagonal (diagonal with positive slope) for a winner and returns the winner
	public static char diagonalAscendingWinner(char[][] n) {
		int numberOfOs = 0; // variable to keep track of the number of 'o's on the ascending diagonal 
		int numberOfXs = 0; // variable to keep track of the number of 'o's on the ascending diagonal
		char winner = ' '; // temporary value; if no winner is ' ' 
		// loops through all possible cells to find the cells on the ascending diagonal 
		for (int i = 0; i < n.length; i++) {
			// if a cell on the ascending diagonal contains 'o'/'x', update the number of 'o's/'x's 
			if (n[i][n.length - 1 - i] == 'o') {
				numberOfOs++; 
			} else if (n[i][n.length - 1 - i] == 'x') {
				numberOfXs++; 
			}
		} 
		// if the ascending diagonal contains n.length number of 'o's, winner is 'o' and return the winner 
		if (numberOfOs == n.length) {
			winner = 'o'; 
			return winner; 
		// if the ascending diagonal contains n.length number of 'x's, winner is 'x' and return the winner
		} else if (numberOfXs == n.length) {
			winner = 'x'; 
			return winner; 
		}
		return winner; 
	}
	
	// H. play method
	public static void play() {
		// Scanner to find get the user to input his/her name 
		String nameUser; 
		Scanner name = new Scanner(System.in);
		System.out.println("Please enter your name: ");
		nameUser = name.nextLine(); 
		System.out.println("Welcome " + nameUser + "!" + " Are you ready to play?");
		// Scanner to get the user to input what sized board he/she would like 
		int dimension;
		Scanner n = new Scanner(System.in);
		System.out.println("Please choose the dimension of your board: "); 
		// if the dimension entered is not an integer, ask for a new input until get an integer 
		while (!n.hasNextInt()) {
			System.out.println("Please enter an integer: ");
			n.nextLine(); 
		}
		// when the dimension given is an integer, store it 
		dimension = n.nextInt();
		// use Math.random to toss a coin and find out who makes the first move
		int coinToss = (int) (Math.random()*(2)); 
		// prints out the result of the coin toss and who gets to go first; user = 0, AI = 1 
		System.out.println("The result of the coin toss is: " + coinToss);
		String winner = " "; 
		if (coinToss == 0) {
			winner = "You";
			System.out.println(winner + " have the first move.");
		} else {
			winner = "AI"; 
			System.out.println(winner + " has the first move.");
		}
		int numberOfTotalMoves = 0; // variable to keep track of the total number of moves
		// create the board using the dimension given by the user 
		char[][] board = createBoard(dimension); 
		// if the winner of the coin toss is the user, go through the following block of code to play tictactoe
		if (winner == "You") {
			while (numberOfTotalMoves <= Math.pow(dimension, 2)) {
				// user gets the first move and the total number of moves is updated
				getUserMove(board); 
				numberOfTotalMoves++; 
				// display the board once the user has made his/her move 
				displayBoard(board); 
				// since user goes first, he/she makes a winning move, print game over and terminate the loop/game 
				if (checkForWinner(board) == 'x') {
					System.out.println("GAME OVER!");
					System.out.println("You won!");
					break; 
				// if instead the board is now filled up and no one won, terminate the loop/game and print no winner
				} else if (numberOfTotalMoves == Math.pow(dimension, 2) && checkForWinner(board) == ' ') {
					System.out.println("GAME OVER!");
					System.out.println("There was no winner.");
					break; 
				// if instead the user didn't win and the board is still not filled, AI gets to make a move 
				} else {
					getAIMove(board); 
					numberOfTotalMoves++; // update the total number of moves
					System.out.println("AI made its move: "); 
					displayBoard(board); // display the board once AI has made its move 
					// if AI makes a winning move, terminate the loop/game and tell the user he/she has lost 
					if (checkForWinner(board) == 'o') {
						System.out.println("GAME OVER!");
						System.out.println("You lost."); 
						break; 
					// if instead the board is now filled up and no one won, terminate the loop/game and print no winner
					} else if (numberOfTotalMoves == Math.pow(dimension, 2) && checkForWinner(board) == ' ') {
						System.out.println("GAME OVER!");
						System.out.println("There was no winner.");
						break; 
					} 	
				} 
			}
		// if the winner of the coin toss is AI, go through the following block of code to play tictactoe 
		} else {
			while (numberOfTotalMoves <= Math.pow(dimension, 2)) {
				// AI gets the first move 
				getAIMove(board); 
				numberOfTotalMoves++; // update the number of total moves 
				System.out.println("AI made its move: ");
				displayBoard(board); // display the board once AI has made its move 
				// since AI gets to go first, if AI makes a winning move, terminate the loop/game and tell the user that he/she has lost
				if (checkForWinner(board) == 'o') {
					System.out.println("GAME OVER!");
					System.out.println("You lost.");
					break; 
				// if instead the board is filled up and there is no winner, terminate the loop/game and tell the user that no one won
				} else if (numberOfTotalMoves == Math.pow(dimension, 2) && checkForWinner(board) == ' ') {
					System.out.println("GAME OVER!");
					System.out.println("There was no winner."); 
					break;
				// if AI did not win and the board is not filled up yet, user gets to make a move 
				} else {
					getUserMove(board); 
					numberOfTotalMoves++; // update the total number of moves once the user has made his/her move
					displayBoard(board); // display the move 
					// if the user makes a winning move, terminate the loop/game and tell the user that he/she has won
					if (checkForWinner(board) == 'x') {
						System.out.println("GAME OVER!");
						System.out.println("You won!");
						break; 
					// if instead the board is filled and there is no winner, terminate the loop/game and print no winner 
					} else if (numberOfTotalMoves == Math.pow(dimension, 2) && checkForWinner(board) == ' ') {
						System.out.println("GAME OVER!");
						System.out.println("There was no winner.");
						break; 
					}
				}	
			}
		}	
	} 
}
