package codes;

import java.io.IOException;

public class Main {
	
	// Sudoku Board File Path
	public static String boardPath = "resources/board.txt";

	public static void main(String[] args) throws IOException {
		
		// Create a 9*9 board
		Board puzzle = new Board(9);
		puzzle.loadFromFile(boardPath);
		
		// Display the initial board
		puzzle.printBoard();
		
		System.out.println("Starting to solve the board...\n");
		
		// Solve the board using elimination and recursive guessing
		puzzle.solveBoard();
		
		// Display the solved board
		puzzle.printBoard();

	}

}
