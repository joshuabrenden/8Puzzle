import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private MinPQ<Node> priorityQueue;
	private MinPQ<Node> twinPriorityQueue;
	private int moves;
	private boolean isSolvable;
	private boolean isTwinSolvable;
	private Node finalNode;
	
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
	
	public Solver(Board initial) {
		//Need two priority queues, one for the board and one for it's twin
		priorityQueue = new MinPQ<Node>();
		twinPriorityQueue = new MinPQ<Node>();
		
		//Insert the initiail nodes
		priorityQueue.insert(new Node(initial, null, 0));
		twinPriorityQueue.insert(new Node(initial.twin(), null, 0));
		
		//We continue to execute through the priority queue while the board has not yet been solved
		while(!isSolvable() && !isTwinSolvable()){
			isSolvable = execute(priorityQueue);
			isTwinSolvable = execute(twinPriorityQueue);
		}
		
	}

	private boolean execute(MinPQ<Node> currentPriorityQueue){
		
		Node minimumNode = currentPriorityQueue.delMin();
		boolean solvable = minimumNode.board.isGoal();
		
		//If the board is not yet solved, it goes through the neighboring tiles
		if(!solvable){
			for(Board board : minimumNode.board.neighbors()){
				if(minimumNode.previous == null || board.equals(minimumNode.previous.board)){
					moves++;
					Node newNode = new Node(board, minimumNode.previous, moves);
					currentPriorityQueue.insert(newNode);
				}
			}
		} else {
			finalNode = minimumNode; 
		}
		
		return solvable;
	}

	public boolean isSolvable() {
		return isSolvable;
	}
	
	public boolean isTwinSolvable(){
		return isTwinSolvable;
	}

	public int moves() {
		return moves;
	}

	public Iterable<Board> solution() {
		Stack<Board> solution = new Stack<Board>();
		
		if(isSolvable()){
			
	        while (finalNode != null) {
	            
	        	solution.push(finalNode.board);
	        	finalNode = finalNode.previous;
	        }
	        
	        return solution;
		}
		
		return null;
	}

	//Needed a separate class for the node object to properly use the priorityqueue implementation
	private class Node implements Comparable<Node> {

		private Board board;
		private Node previous;
		private int moves;

		public Node(Board board, Node previous, int moves) {
			this.board = board;
			this.previous = previous;
			this.moves = moves;
		}

		@Override
		public int compareTo(Node otherNode) {
			int thisCompareValue = this.moves + board.manhattan();
			int otherCompareValue = otherNode.moves + otherNode.board.manhattan();
			return thisCompareValue - otherCompareValue;
		}
	}

}
