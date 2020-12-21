
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphList {

	// list that contains all the graphs
	private List<Graph> graphList;

	// constructor to create graph list object with given input file
	public GraphList(String filename) throws NumberFormatException, IOException {
		this.graphList = new ArrayList<Graph>();
		parseGraphs(filename);

	}// end of constructor

	// method to parse input file into an array list of graphs
	public void parseGraphs(String filename) throws NumberFormatException, IOException {
		BufferedReader bufferedReader;
		String firstChar;// first char read in to tell us the number of rows/cols in graph/adjacency
							// matrix

		try {
			// create file reader object with input file
			FileReader reader = new FileReader(new File(filename));

			// use buffered reader object to help efficiently read in characters and lines
			// from input file
			bufferedReader = new BufferedReader(reader);

			// while loop to read in first number that lets us know how many rows in graph
			// and then reads lines to set values in graph
			while ((firstChar = bufferedReader.readLine()) != null) {

				// first character read in should represent the size of the rows & cols for the
				// symmetric adjacency matrix
				int matrixSize = Integer.parseInt(firstChar);

				// if there is 0 rows.. then we do not need to read in any lines
				if (matrixSize == 0) {

					break;
				} // end of if statement

				// create array of adjacency matrix to help create graph
				int[][] adjacencyMatrix = new int[matrixSize][matrixSize];

				// for loop to read input file and parse into graph
				for (int j = 0; j < matrixSize; j++) {
					String line = bufferedReader.readLine().replaceAll("\\s+", "");

					for (int i = 0; i < matrixSize; i++) {

						adjacencyMatrix[j][i] = Integer.parseInt(line.charAt(i) + "");

					} // end of inner for loop
				} // end of outer for loop

				// create new graph with adjacency matrix parsed from input file and add to list
				// of graphs
				this.graphList.add(new Graph(matrixSize, adjacencyMatrix));

			} // end of while loop

			reader.close();
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} // end of try/catch block
	}// end of parseGraphs method

	// static method which will invert all graphs in the list returns message if
	// list is empty
	public List<Graph> invertGraphs() {

		// lets user know no graphs were inverted
		if (graphList.size() == 0) {
			System.out.println("No graphs to invert");
		}

		// for loop that goes through each graph of the list
		for (int i = 0; i < graphList.size(); i++) {
			Graph temp = graphList.get(i);

			int[][] matrix = temp.getMatrix();
			int size = temp.getSize();

			// for loop that inverts matrix
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					if (j == k)
						matrix[j][k] = 1;
					else if (matrix[j][k] == 0)
						matrix[j][k] = 1;
					else
						matrix[j][k] = 0;
				}
			}

			graphList.get(i).setMatrix(matrix);

		}

		return graphList;
	}// end of invertGraphs method

	// getters and setters
	public List<Graph> getGraphList() {
		return graphList;
	}

	public void setGraphList(List<Graph> graphList) {
		this.graphList = graphList;
	}

}// end of class
