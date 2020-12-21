

import java.io.IOException;
import java.util.ArrayList;

public class solveClique {
	
	//variable to hold time elapsed for each maxClique method call
	private static long ms;

	// Driver code to find the max clique in each graph from the input file
	public static void main(String[] args) throws NumberFormatException, IOException {

		//set filename with input from command line
		String fileName = args[0];
		
        //create new graph list object with the given input file
		GraphList allGraphs = new GraphList(fileName);
		
		System.out.println("* Max Cliques in graphs in graphs2019.txt\n" + 
				           "   (|V|,|E|) Cliques (size, ms used)");
		
		//iterate through the graphs in the graph list and call the findMaxClique on each graph
		for (int i = 0; i < allGraphs.getGraphList().size(); i++) {

			int graphSize = allGraphs.getGraphList().get(i).getSize();

			// create an integer list to hold the set of vertices found in the max clique for this graph
			ArrayList<Integer> cliqueList = new ArrayList<Integer>();
			
			//start timer
			long startTime = System.currentTimeMillis();

			//call method to find max clique in graph and return the list of vertices
			cliqueList = findMaxClique(allGraphs.getGraphList().get(i));
			
			//end timer
			ms = System.currentTimeMillis() - startTime;

			// print the max clique results for this graph
			System.out.println("G" + (i + 1) + " (" + graphSize + ", " + allGraphs.getGraphList().get(i).countEdges() + ") "
					+ cliqueList.toString() + "(size=" + cliqueList.size() + ", " + ms + " ms)");
		} // end of for loop
		System.out.println("***");
	}// end of main method
	
	//take graph and pass into recursive algorithm to return array list of max clique
	public static ArrayList<Integer> findMaxClique(Graph graph){
		
	//pass in a new array list to hold clique (this argument is needed for recursion), initialize second arg to 0 (needed for recursion as well)
		return findMaxCliqueAlgorithm(new ArrayList<Integer>(),0, graph);
		
	}//end of helper method
	
	//overloaded method that is used to specify the max clique to search for (useful for reducing CNF)
		public static ArrayList<Integer> findMaxClique(Graph graph, int k){
			
			//set k so that we can access it later in recursive method
			graph.setK(k);
		//pass in a new array list to hold clique (this argument is needed for recursion), initialize second arg to 0 (needed for recursion as well)
			return findMaxCliqueAlgorithm(new ArrayList<Integer>(),0, graph);
			
		}//end of helper m

	//recursive method to find max clique in a given graph
	public static ArrayList<Integer> findMaxCliqueAlgorithm(ArrayList<Integer> clique, int row, Graph graph) {
		
		//get the size of the graph to iterate through the columns of the adjacency matrix
        int dimension = graph.getSize();
        
        
        //first check to see if the graph object has a specified clique to search for before starting the algorithm
       int k = graph.getK();
       
 
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		
		maxClique = clique;
		 

		for (int i = row; i < dimension; i++) {
			boolean isClique = true;
			
			for (int j = 0; j < clique.size(); j++) {
				if (graph.getMatrix()[clique.get(j)][i] != 1) {
					isClique = false;
				}
			}//end of inner for loop

			if (isClique) {
				ArrayList<Integer> currentClique = new ArrayList<Integer>(clique);
				currentClique.add(i);
				temp = findMaxCliqueAlgorithm(currentClique, i +1, graph);
				

				if (temp.size() > maxClique.size()) { maxClique = temp;}
				
				//if k is specified for the graph and the current max clique is equal to k.. no need to keep search. end loop
		        if(k >= 0 && k == maxClique.size()) { break;}
			}//end of outer if statement
		} // end of outer loop
		
		return maxClique;
	}//end of findMaxClique method

}// end of class
