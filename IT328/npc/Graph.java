

//class to create graph objects
public class Graph {

	private int size;
	private int numEdges;
	private int k; //member that helps to specify if you want to search for a k clique instead of max

	private int[][] matrix; // symmetric adjacency matrix n*n

	// constructor with size and adjacency matrix as arguments
	public Graph(int size, int[][] matrix) {

		this.size = size;
		this.matrix = matrix;
		this.k = -1;
	}// end of constructor

	// method for printing the matrix in the graph object
	public void printMatrix() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		} // end of for loop
	}

	// method to count the total number of edges in a graph
	public int countEdges() {

		int numEdges = 0;

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				if (matrix[i][j] == 1)
					numEdges++;
			} // end of inner loop
		} // end of outer loop

		return numEdges;
	}

	// getters and setters
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getMatrix() {
		return matrix;
	}
	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

}// end of class
