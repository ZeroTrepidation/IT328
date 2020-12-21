

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class solveISet {

	// main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = args[0];
		try {
			// initiates new object
			GraphList graphList = new GraphList(filename);

			// invert graph
			List<Graph> inverseGraphs = graphList.invertGraphs();


			System.out.println("\n* Max Independent Sets in graphs in graphs2019.txt : (reduced to K-Clique) *\r\n" + 
					"      (|V|,|E|) Independent Set (size, ms used)");
			for (int i = 0; i<inverseGraphs.size(); i++) {
				
				long ms;
				
				int graphSize = inverseGraphs.get(i).getSize();

				// create an integer list to hold the set of vertices found in the max clique for this graph
				ArrayList<Integer> cliqueList;
				
				//start timer
				long startTime = System.currentTimeMillis();

				//call method to find max clique in graph and return the list of vertices
				cliqueList = solveClique.findMaxClique(inverseGraphs.get(i));
				
				//end timer
				ms = System.currentTimeMillis() - startTime;

				// print the max clique results for this graph
				System.out.println("G" + (i + 1) + " (" + graphSize + ", " + inverseGraphs.get(i).countEdges() + ") "
						+ cliqueList.toString() + "(size=" + cliqueList.size() + ", " + ms + " ms)");
				
				}


		} catch (IOException e) {
			System.out.println("file error");
		}
	}

}
