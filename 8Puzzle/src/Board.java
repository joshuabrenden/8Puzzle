
public class Board {
	
	//Length of side of game board
	private int dimension;
	private int[][] blocks;
	private int[]emptyBlock;
	
	public Board(int[][] blocks) {
		dimension = blocks.length;
		initializeBlocks(blocks);
	}

	public int dimension() {
		return dimension;
	}

	public int hamming() {
		return 0;
	}

	public int manhattan(){
		return 0;
	}

	public boolean isGoal(){
		return true;
	}

	public Board twin(){
		return null;
	}

	public boolean equals(Object y){
		return true;
	}

	public Iterable<Board> neighbors(){
		return null;
		
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(dimension).append("\n");
		
		return null;
		
	}

	public static void main(String[] args) {
		
	}

	public void initializeBlocks(int[][] blocks){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				this.blocks[i][j] = blocks[i][j];
				
				if (this.blocks[i][j] == 0){
					emptyBlock[0] = i;
					emptyBlock[1] = j;
				}
			}
		}
	}
}
