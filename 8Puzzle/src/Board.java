
public class Board {

	// Length of side of game board
	private int dimension;

	// Game board
	private int[][] blocks;

	// Empty block in game board
	private int[] emptyBlock;

	/*
	 * Constructor receives an N-by-N array containing the N2 integers between 0
	 * and N2 − 1, where 0 represents the blank square. Supports all Board
	 * methods in time proportional to N2 (or better) in the worst case.
	 */
	public Board(int[][] blocks) {
		dimension = blocks.length;
		initializeBlocks(blocks);
	}

	public int dimension() {
		return dimension;
	}

	/*
	 * Hamming priority function: The number of blocks in the wrong position,
	 * plus the number of moves made so far to get to the search node.
	 * Intuitively, a search node with a small number of blocks in the wrong
	 * position is close to the goal, and we prefer a search node that have been
	 * reached using a small number of moves.
	 */
	public int hamming() {
		int count = 0;
		
		for(int column = 0; column < dimension; column++){
			for(int row = 0; row < dimension; row++){
				if(isBlockInPlace(row, column)){
					count++;
				}
			}
		}
		
		return count;
	}

	/*
	 * Manhattan priority function: The sum of the Manhattan distances (sum of
	 * the vertical and horizontal distance) from the blocks to their goal
	 * positions, plus the number of moves made so far to get to the search
	 * node.
	 */
	public int manhattan() {
		int count = 0;

		for(int column = 0; column < dimension; column++){
			for(int row = 0; row < dimension; row++){
					count += getDistance(row, column);
				}
			}
		
		return count;
	}

	public boolean isGoal() {
		boolean isGoal = false;
		
		if(hamming() == 0){
			isGoal = true;
		}
		
		return isGoal;
	}

	//Swap two adjacent boards
	public Board twin() {
		
		for(int column = 0; column < dimension; column++){
			for(int row = 0; row < dimension; row++){
				
				
				}
			}
		
		return null;
	}

	public boolean equals(Object y) {
		return true;
	}

	public Iterable<Board> neighbors() {
		return null;

	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(dimension).append("\n");

		return null;

	}

	public static void main(String[] args) {

	}

	private void initializeBlocks(int[][] blocks) {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				this.blocks[i][j] = blocks[i][j];

				if (this.blocks[i][j] == 0) {
					emptyBlock[0] = i;
					emptyBlock[1] = j;
				}
			}
		}
	}
	
	private boolean isBlockInPlace(int rowIndex, int columnIndex){
		
		boolean isInPlace = false;
		
		if(blocks[columnIndex][rowIndex] != 0){
			if(blocks[columnIndex][rowIndex] != goalValue(rowIndex, columnIndex)){
				isInPlace= true;
			}
		}
		return isInPlace;
	}
	
	private int goalValue(int rowIndex, int columnIndex){
		 return columnIndex * dimension + rowIndex + 1;
	}
	
	private int getDistance(int rowIndex, int columnIndex){
		int distance = 0;
		
		int firstDistance = Math.abs(columnIndex-(blocks[columnIndex][rowIndex]-1)/dimension); 
		int secondDistance = Math.abs(rowIndex-(blocks[columnIndex][rowIndex]-1)%dimension);
		
		distance = firstDistance + secondDistance;
		
		return distance;
	}
	
	private Board swapBoard(int rowIndex, int columnIndex){
		int row1 = rowIndex;
		int row2 = rowIndex;
		
		int column1 = columnIndex;
		int column2 = columnIndex + 1;
		
		int[][] copy = copyBoard();
		
		return null;
	}
	
	private int[][] copyBoard(){
		
		int[][] board = new int[dimension][dimension];
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
					board[i][j] = blocks[i][j];
				}
			}
		
		return null;
	}
}
