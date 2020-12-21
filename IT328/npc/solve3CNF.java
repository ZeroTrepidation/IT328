
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class solve3CNF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String filename = args[0];
		BufferedReader fin;
		String currentLine;
		int count = 1;

		try {

			// create file reader object with input file

			FileReader reader = new FileReader(new File(filename));

			// use buffered reader object to help efficiently read in characters and lines
			// from input file
			fin = new BufferedReader(reader);

			// while loop to perform conversion for each given boolean formula

			System.out.println("\n* Solve 3CNF in cnfs2019.txt: (reduced to K-Clique) *\r\n"
					+ "                    x: can be either T or F");

			whileloop: while ((currentLine = fin.readLine()) != null) {
				long time = System.currentTimeMillis();

				// read line and split into an array
				String[] line = currentLine.split("\\s");

				// Initialize an arraylist and take first value from line to know number of
				// variables
				ArrayList<Integer> valueArray = new ArrayList<Integer>();
				int n = Integer.parseInt(line[0]);

				ArrayList<String> variableValue = new ArrayList<String>();
				for (int i = 0; i < n; i++) {
					variableValue.add("X");
				}

				// stop reading if you hit a 0
				if (n == 0) {
					System.out.println("***\nDone!");
					break whileloop;
				}
				// makes sure that value is not 0 just in case
				if (n != 0) {

					// parses string to an array of ints
					for (int i = 1; i < line.length; i++) {
						valueArray.add(Integer.parseInt(line[i]));
					}

					// determines number of clauses
					int numClauses = valueArray.size() / 3;

					// convert 3CNF to graph and assign to variable
					Graph graph = CNFtoClique(valueArray);

					// return our satisfied clique from our graph that we obtained
					ArrayList<Integer> cliqueList;

					//if statement checks to see if there are greater than 19 clauses.
					//if so then it will run the second if statement which uses a different method
					if (numClauses < 20) {
						cliqueList = solveClique.findMaxClique(graph, numClauses);
					} else {

						cliqueList = denseGraphCliqueFinder(numClauses, graph);
					}

					time = System.currentTimeMillis() - time;

					System.out.print("3CNF No." + count + ": [n=" + n + " k=" + numClauses + "] ");

					// if statement will print out no solution if you cannot find a k-clique where k
					// is number
					// of clauses. Otherwise will set the variables to the proper one and print them
					if (cliqueList.size() < numClauses) {
						System.out.print("No " + numClauses + "-clique; No solution. ");
						System.out.println("(" + time + "ms)");

					} else {
						for (int i = 0; i < cliqueList.size(); i++) {
							if (valueArray.get(cliqueList.get(i)) > 0) {
								variableValue.set(Math.abs(valueArray.get(cliqueList.get(i))) - 1, "T");
							} else {
								variableValue.set(Math.abs(valueArray.get(cliqueList.get(i))) - 1, "F");

							}
						}
						for (int i = 0; i < variableValue.size(); i++) {
							System.out.print(variableValue.get(i) + " ");
						}
						System.out.println("(" + time + "ms)");
					}

				}

				count++;
			}

		} catch (IOException e) {

			System.out.println("there was an error");
		}

	}

	// method for converting 3CNF to clique
	public static Graph CNFtoClique(ArrayList<Integer> valueArray) {
		Graph graph;
		int size = valueArray.size();

		int[][] matrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j)
					matrix[i][j] = 1;
				else if (i / 3 == j / 3)
					matrix[i][j] = 0;
				else if ((valueArray.get(i) * -1) == valueArray.get(j))
					matrix[i][j] = 0;
				else
					matrix[i][j] = 1;
			}
		}

		graph = new Graph(size, matrix);

		return graph;
	}

	
	
	/*
	 * method for finding a clique in a densely packed graph
	 * **will run infinitely if no clique exists**
	 * 
	 * @param k: number of clauses
	 * @param graph: the graph you want to check for clique
	 * 
	 * returns an int list which are the vertices of the clique
	 */
	public static ArrayList<Integer> denseGraphCliqueFinder(int k, Graph graph) {

		//get the matrix
		int[][] matrix = graph.getMatrix();

		//infinite for loop
		for (;;) {
			ArrayList<Integer> vertexArray = new ArrayList<Integer>();

			//generate array of random integers to represent vertex from each clause
			for (int i = 2; i <= k * 3; i = i + 3) {
				vertexArray.add(generateRandomIntIntRange(i - 2, i));
			}
			

			//outer loop which gets broken if it encounters a 0 (not a clique)
			//	but returns the list of vertices otherwise (graph contains all 1's)
			//	which means it is a clique
			GRAPH_CHECK: for (;;) {
				for (int i = 0; i < vertexArray.size(); i++) {
					for (int j = 0; j < vertexArray.size(); j++) {
						if (matrix[vertexArray.get(i)][vertexArray.get(j)] == 0)
							break GRAPH_CHECK;
					}
				}
				return vertexArray;
			}

		}

	}

	//generates random integer
	public static int generateRandomIntIntRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
