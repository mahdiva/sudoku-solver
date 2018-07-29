package codes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board {

	private Node root;
	private int size;
	
	public Board(int size) throws IOException {
		
		this.size = size;
		root = new Node(); // Make the first Node and set it to root
		Node rowPointer = root; // This pointer will be used for iteration, start at root
		
		
		for(int y=0; y<size; y++){
			if(y != 0){
				// Add new Node to the bottom of the list
				Node temp = new Node();
				rowPointer.setDown(temp);
				temp.setUp(rowPointer);
				rowPointer = temp;
			}
			
			Node colPointer = rowPointer; // Pointing to first(1st column) Node in the row
			// Add all of the Nodes in that row
			for(int x=1; x<size; x++){
				Node colTemp = new Node();
				colPointer.setRight(colTemp);
				colTemp.setLeft(colPointer);
				if(y != 0){
					// Set up and down pointers
					colTemp.setUp(colTemp.getLeft().getUp().getRight());
					colTemp.getLeft().getUp().getRight().setDown(colTemp);
				}
				colPointer = colTemp;
			}
		}
		
		// Set box IDs of each cell
		setBoxIDs();
		
	}
	
	// Method for printing the Sudoku board
	public void printBoard(){
		Node rowPointer = root;
		int rowCount = 1;
		int colCount = 1;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			
			while(colPointer != null){
				System.out.print(colPointer.getData() + " ");
				if((colCount%3)==0){
					System.out.print("| ");
				}
				colPointer = colPointer.getRight();
				colCount++;
			}
			System.out.println();
			if((rowCount%3)==0){
				System.out.println("-----------------------");
			}
			rowPointer = rowPointer.getDown();
			rowCount++;
		}
		
		System.out.println();
		System.out.println();
		
	}
	
	// Method for printing the possibilities of each cell, useful for debugging
	public void printPossibilities(){
		Node rowPointer = root;
		int rowCount = 1;
		int colCount = 1;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			
			while(colPointer != null){
				boolean[] poss = colPointer.getPossibilities();
				for(int i=0; i<poss.length; i++){
					if(poss[i] == true){
						System.out.print(i + " ");
					}else{
						System.out.print("  ");
					}
				}
				if((colCount%3)==0){
					System.out.print("  ||  ");
				}else{
					System.out.print("| ");
				}
				colPointer = colPointer.getRight();
				colCount++;
			}
			System.out.println();
			if((rowCount%3)==0){
				System.out.println();
			}
			rowPointer = rowPointer.getDown();
			rowCount++;
		}
		
		System.out.println();
		System.out.println();
		
	}
	
	
	// Method for loading the board from a file
	public void loadFromFile(String filepath) throws IOException {
		
		File file = new File(filepath);
		Scanner input = new Scanner(file);
		
		Node rowPointer = root;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				colPointer.setData(input.nextInt());
				colPointer = colPointer.getRight();
			}
			rowPointer = rowPointer.getDown();
		}
		
		input.close();
	}
	
	// Method for setting the boxIDs of each cell loaded from a file
	public void setBoxIDs() throws IOException {
		
		File file = new File("resources/boxids.txt");
		Scanner input = new Scanner(file);
		
		Node rowPointer = root;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				colPointer.setBoxID(input.nextInt());
				colPointer = colPointer.getRight();
			}
			rowPointer = rowPointer.getDown();
		}
		
		input.close();
	}
	
	// Iterate through each cell, if a cell has only 1 possibility true, set its value to that
	public boolean solveCells(){
		
		// To keep track if any cells were solved. Used for knowing when to stop looping this method
		boolean solvedAnyCells = false;
		
		Node rowPointer = root;
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				if( colPointer.solveNode() ){
					solvedAnyCells = true;
				}
				colPointer = colPointer.getRight();
			}
			rowPointer = rowPointer.getDown();
		}
		
		
		//System.out.println(solvedAnyCells);
		return solvedAnyCells;
	}
	
	
	// Iterate through the board and eliminate every possibility of unsolved cells based on the solved cells
	public void eliminate(){
		
		Node rowPointer = root;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				
				int cell = colPointer.getData();
				
				// If the cell is already set/solved eliminate all the other cells in it's section
				if(cell != 0){
					
					// Set all possibilities of that box to false
					for(int i=0; i<10; i++){
						colPointer.setImpossible(i);
					}
					
					// Eliminate all the cells to the right
					Node cellPointer = colPointer;
					while(cellPointer != null){
						cellPointer.setImpossible(cell);
						cellPointer = cellPointer.getRight();
					}
					
					// Eliminate all the cells to the left
					cellPointer = colPointer;
					while(cellPointer != null){
						cellPointer.setImpossible(cell);
						cellPointer = cellPointer.getLeft();
					}
					
					// Eliminate all the cells upwards
					cellPointer = colPointer;
					while(cellPointer != null){
						cellPointer.setImpossible(cell);
						cellPointer = cellPointer.getUp();
					}
					
					// Eliminate all the cells downwards
					cellPointer = colPointer;
					while(cellPointer != null){
						cellPointer.setImpossible(cell);
						cellPointer = cellPointer.getDown();
					}
					
					// Eliminate all the cells in that box section (with the same boxID)
					int boxID = colPointer.getBoxID();
					Node cellRowPointer = root;
					
					while(cellRowPointer != null){
						Node cellColPointer = cellRowPointer;
						while(cellColPointer != null){
							if(cellColPointer.getBoxID() == boxID){
								cellColPointer.setImpossible(cell);
							}
							cellColPointer = cellColPointer.getRight();
						}
						cellRowPointer = cellRowPointer.getDown();
					}
					
				}
				
				colPointer = colPointer.getRight();
			}
			
			rowPointer = rowPointer.getDown();
		}
		
	}
	
	// Check to see if the board is valid; Check if there are any conflicting cells with the same value
	public boolean isValid() {
		
		Node rowPointer = root;
		
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				
				int cell = colPointer.getData();
				
				if(cell != 0){
					
					// Check all the cells to the right
					Node cellPointer = colPointer.getRight();
					while(cellPointer != null){
						if(cell == cellPointer.getData()) {
							return false;
						}
						cellPointer = cellPointer.getRight();
					}
					
					
					// Check all the cells to the left
					cellPointer = colPointer.getLeft();
					while(cellPointer != null){
						if(cell == cellPointer.getData()) {
							return false;
						}
						cellPointer = cellPointer.getLeft();
					}
					
					// Check all the cells upwards
					cellPointer = colPointer.getUp();
					while(cellPointer != null){
						if(cell == cellPointer.getData()) {
							return false;
						}
						cellPointer = cellPointer.getUp();
					}
					
					// Check all the cells downwards
					cellPointer = colPointer.getDown();
					while(cellPointer != null){
						if(cell == cellPointer.getData()) {
							return false;
						}
						cellPointer = cellPointer.getDown();
					}
					
					
					// Check all the cells in that box section (with the same boxID)
					cellPointer = colPointer;
					int boxID = colPointer.getBoxID();
					Node cellRowPointer = root;
					
					while(cellRowPointer != null){
						Node cellColPointer = cellRowPointer;
						while(cellColPointer != null){
							if(cellColPointer.getBoxID() == boxID && cellColPointer != cellPointer){
								if(cell == cellColPointer.getData()) {
									return false;
								}
							}
							cellColPointer = cellColPointer.getRight();
						}
						cellRowPointer = cellRowPointer.getDown();
					}
					
				}
				
				colPointer = colPointer.getRight();
			}
			
			rowPointer = rowPointer.getDown();
		}
		
		return true;
	}
	
	// Check if the board is solved by iterating through each cell, if there are any unsolved cells, then the board is not solved
	public boolean isSolved(){
		
		Node rowPointer = root;
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				if( colPointer.getData() == 0 ){
					return false;
				}
				colPointer = colPointer.getRight();
			}
			rowPointer = rowPointer.getDown();
		}
		
		return true;
	}
	
	// Find the next(first) unsolved cell in the board. Useful for taking the next guess of the next unsolved cell
	public Node getNextUnsolved(){
		
		Node rowPointer = root;
		while(rowPointer != null){
			Node colPointer = rowPointer;
			while(colPointer != null){
				if( colPointer.getData() == 0 ){
					return colPointer;
				}
				colPointer = colPointer.getRight();
			}
			rowPointer = rowPointer.getDown();
		}
		
		return null;
	}
	
	
	// Recursive method for trying each possibility of each cell
	public void guess(Node cell){
		
		boolean[] possibilities = cell.getPossibilities();
		
		// Iterate through and try each possibility of the cell 
		for(int i = 1; i < 10; i++) {
			if(possibilities[i] == true) {
				
				// If the board is solved, stop the guessing recursion
				if(isSolved()){
					return;
				}
				
				cell.setData(i);
				
				// If the possibility would results in a conflict with other solved cells
				if(!isValid()){
					cell.setData(0);
					continue;
				}
				
				cell.setData(i);
				
				// If the board is still valid, move to the next unsolved cell in the board
				
				Node nextUnsolved = getNextUnsolved();
				
				if(getNextUnsolved() == null) {
					return;
				}
				
				// Recursive function call
				guess(nextUnsolved);
				
			}
		}
		
		if(!isSolved()) {
			cell.setData(0); // Set the cell value back to 0
		}
		
	}
	
	// The main method for solving the Sudoku board using the previous utility methods
	// Eliminate and Solve the board until no more cells can be eliminated
	// Start trying each possibility and solve the board by taking guesses
	
	public void solveBoard(){
		
		eliminate();
		// Eliminate-Solve each cell until no more cells can be eliminated-solved
		while(solveCells()){
			eliminate();
		}
		
		// If no more cells can be eliminated-solved then start guessing
		
		// Find first unsolved cell and start guessing from there
		Node start = getNextUnsolved();
		
		guess(start);
		
	}
	
	
}
