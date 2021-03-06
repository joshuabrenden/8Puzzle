import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Board {

	// Length of side of game board
	private int dimension;

	// Game board
	private int[][] blocks;

	// Empty block in game board, 0 index is row, 1 index is column
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
	
	//The length of the sides of the square game board
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

		//Iterates through the entire matrix, increments the count if the block is in place
		for (int column = 0; column < dimension; column++) {
			for (int row = 0; row < dimension; row++) {
				if (isBlockInPlace(row, column)) {
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

		//Iterates through the entire matrix, increments the count by the distance from the goal
		for (int column = 0; column < dimension; column++) {
			for (int row = 0; row < dimension; row++) {
				count += getDistance(row, column);
			}
		}

		return count;
	}

	//If hamming is zero, we are at our goal position.
	public boolean isGoal() {
		boolean isGoal = false;

		if (hamming() == 0) {
			isGoal = true;
		}

		return isGoal;
	}

	// Returns the game board's twin from exchanging tiles
	public Board twin() {
		Board twin = null;
		for (int column = 0; column < dimension - 1; column++) {
			for (int row = 0; row < dimension; row++) {
				
				//if the block is not an empty space, it swaps the values.
				if (blocks[row][column] != 0 && blocks[row][column + 1] != 0) {
					int nextColumn = column + 1;
					int nextRow = row;

					twin = swapBoard(row, column, nextRow, nextColumn);
				}
			}
		}

		return twin;
	}

	//Uses the Java.Util method for comparing two dimensional arrays.
	public boolean equals(Object y) {
		return Arrays.deepEquals(blocks, (int[][]) y);
	}

	//Returns all the neighboring tiles from the board.
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new LinkedList<Board>();

		//Ensures the emptyBlock is fully updated
		refreshEmptyBlock();
		
		int emptyRow = emptyBlock[0];
		int emptyColumn = emptyBlock[1];

		if (notTopRow()) {
			neighbors.add(swapBoard(emptyRow, emptyColumn, emptyRow - 1, emptyColumn ));
		}

		if (notBottomRow()) {
			neighbors.add(swapBoard(emptyRow, emptyColumn, emptyRow + 1, emptyColumn));
		}

		if (notRightColumn()) {
			neighbors.add(swapBoard(emptyRow, emptyColumn, emptyRow, emptyColumn - 1));
		}

		if (notLeftColumn()) {
			neighbors.add(swapBoard(emptyRow, emptyColumn, emptyRow, emptyColumn + 1));
		}

		return neighbors;
	}

	//Human readable matrix for printing results.
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(dimension).append("\n");

		for (int column = 0; column < dimension; column++) {
			for (int row = 0; row < dimension; row++) {
				builder.append(String.format("%2d", blocks[row][column]));
			}
			builder.append("\n");
		}

		return builder.toString();
	}
	
	//Initializes the gameboard with all of the values from the text file.
	private void initializeBlocks(int[][] newBlocks) {
		blocks = new int[dimension][dimension];
		emptyBlock = new int[2];
		for (int column = 0; column < dimension; column++) {
			for (int row = 0; row < dimension; row++) {
				blocks[column][row] = newBlocks[column][row];

				if (blocks[column][row] == 0) {
					emptyBlock[0] = column;
					emptyBlock[1] = row;
				}
			}
		}
	}

	private boolean isBlockInPlace(int rowIndex, int columnIndex) {

		boolean isInPlace = false;

		if (blocks[columnIndex][rowIndex] != 0) {
			if (blocks[columnIndex][rowIndex] != goalValue(rowIndex, columnIndex)) {
				isInPlace = true;
			}
		}
		return isInPlace;
	}

	private int goalValue(int rowIndex, int columnIndex) {
		return columnIndex * dimension + rowIndex + 1;
	}

	private int getDistance(int rowIndex, int columnIndex) {
		int distance = 0;

		int firstDistance = Math.abs(columnIndex - (blocks[columnIndex][rowIndex] - 1) / dimension);
		int secondDistance = Math.abs(rowIndex - (blocks[columnIndex][rowIndex] - 1) % dimension);

		distance = firstDistance + secondDistance;

		return distance;
	}

	private Board swapBoard(int rowIndex, int columnIndex, int nextRowIndex, int nextColumnIndex) {
		int[][] twin = copyBoard();

		int placeHolder = twin[rowIndex][columnIndex];

		twin[rowIndex][columnIndex] = twin[nextRowIndex][nextColumnIndex];
		twin[nextRowIndex][nextColumnIndex] = placeHolder;

		return new Board(twin);
	}

	//Creates a copy of the game board for use during the swap
	private int[][] copyBoard() {

		int[][] board = new int[dimension][dimension];

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				board[i][j] = blocks[i][j];
			}
		}

		return board;
	}

	private void refreshEmptyBlock() {
		for (int column = 0; column < dimension; column++) {
			for (int row = 0; row < dimension; row++) {
				if (blocks[column][row] == 0) {
					emptyBlock[0] = column;
					emptyBlock[1] = row;
				}
			}
		}
	}

	private boolean notTopRow() {
		return emptyBlock[0] > 0;
	}

	private boolean notBottomRow() {
		return emptyBlock[0] < dimension - 1;
	}

	private boolean notRightColumn() {
		return emptyBlock[1] > 0;
	}

	private boolean notLeftColumn() {
		return emptyBlock[1] < dimension - 1;
	}

}
