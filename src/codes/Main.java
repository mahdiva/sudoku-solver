package codes;

import java.io.IOException;

public class Main {
	
	public static String boardPath = "resources/board.txt"; // <==== Sudoku Board File Path

	public static void main(String[] args) throws IOException {
		
		Board puzzle = new Board(9);
		puzzle.loadFromFile(boardPath);
		
		// Display the initial board
		puzzle.printBoard();
		
		System.out.println("Starting to solve the board...\n");
		
		// Solve the board using elimination and guessing
		puzzle.solveBoard();
		
		// Display the solved board
		puzzle.printBoard();

	}

}
