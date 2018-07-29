package codes;

public class Node {
	
	private int data;
	private int boxID;
	private Node up;
	private Node down;
	private Node right;
	private Node left;
	private boolean[] possible = new boolean[10];
	
	// Initialize the variables
	public Node(){
		this.data = 0;
		this.boxID = 0;
		this.up = null;
		this.down = null;
		this.right = null;
		this.left = null;
		for(int i=0; i<possible.length; i++){
			// Set the possibility for all 1-9 to true initially
			possible[i] = true;
		}
		possible[0] = false;
	}
	
	// Constructor overload
	public Node(int data){
		this.data = data;
	}
	
	// Getters and setters for the Node value
	public int getData(){
		return this.data;
	}
	
	public void setData(int data){
		this.data = data;
	}
	
	// Returns ID of the box the cell belongs to
	public int getBoxID(){
		return this.boxID;
	}
	
	// Sets ID of the box the cell belongs to
	public void setBoxID(int id){
		this.boxID = id;
	}
	
	// Getters for the adjacent nodes
	public Node getUp(){
		return this.up;
	}
	
	public Node getDown(){
		return this.down;
	}
	
	public Node getRight(){
		return this.right;
	}
	
	public Node getLeft(){
		return this.left;
	}
	
	// Setters for the adjacent nodes
	public void setUp(Node up){
		this.up = up;
	}
	
	public void setDown(Node down){
		this.down = down;
	}
	
	public void setRight(Node right){
		this.right = right;
	}

	public void setLeft(Node left){
		this.left = left;
	}
	
	// Set the possibility of a number to impossible 
	public void setImpossible(int poss){
		possible[poss] = false;
	}
	
	// Getter for the possibilities array
	public boolean[] getPossibilities(){
		return this.possible;
	}
	
	// Method for solving the node when there is only 1 possibility 
	public boolean solveNode(){
		if(this.data==0){ // If not already solved
			
			int poss = 0; // Number of possibilities
			int solvedVal = 0; 
			for(int i=1; i<possible.length; i++){ // Iterate through each possibility
				if(this.possible[i]==true){
					poss++;
					solvedVal = i;
				}
			}
			if(poss == 1){ // If there was only 1 possibility found
				this.data = solvedVal; 
				return true; // Return true if the cell was solved
			}
		}
		
		return false;
	}
	
	
	
}
