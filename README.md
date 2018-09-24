# **Sudoku Solver**
A Sudoku solver written in Java	A Sudoku Solver written in Java using Linked Grids, which is able to solve Sudoku puzzles at any difficulty level. This program uses an elimination and recursive guessing algorithm to solve a given Sudoku board.
 ## **Installation**
To initialize a 9x9 Sudoku board:  
`Board puzzle = new Board(9);`  
This creates a new empty board with all values set to 0.  
  
 You can load a Sudoku board from an external .txt file in the format similar to the following:  
 ![board](https://user-images.githubusercontent.com/35560951/45935544-ab3a5c00-bf69-11e8-8855-aa6fd58f40e0.jpg)  
 Where a 0 denotes an empty and unsolved cell.
  
 Use the following method to load your Sudoku board into the Board object:  
`puzzle.loadFromFile("resources/board.txt");`  
  
To attempt to solve a Board object use:  
`puzzle.solveBoard();`  
  
To display a solved board in the console use the following:  
`puzzle.printBoard();`  
  
## **License**
 This project is licensed under the MIT License - see the [LICENSE.md](https://raw.githubusercontent.com/MahdiVarposhti/sudoku-solver/master/LICENSE) file for details
