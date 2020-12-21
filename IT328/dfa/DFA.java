import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DFA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length == 2) {
			try {
				BufferedReader br;
				int noOfStates;
				String[] sigma;
				ArrayList<DFAState> DFA = new ArrayList<DFAState>();
				ArrayList<Integer> initialStates = new ArrayList<Integer>();
				ArrayList<Integer> finalStates = new ArrayList<Integer>();
				Integer[][] optiMatrix;
				boolean changesMade;

				FileReader reader = new FileReader(new File(args[0]));
				br = new BufferedReader(reader);

				// read number of states
				noOfStates = Integer.parseInt(br.readLine().trim());

				// read each sigma
				sigma = br.readLine().substring(7).replaceAll("\\s+", " ").trim().split("\\s+");

				// trash line
				br.readLine();

				// read each transition
				for (int i = 0; i < noOfStates; i++) {
					String[] temp = br.readLine().strip().replaceAll("\\s+|:", " ").split("\\s+");
					// System.out.println(temp[0]);
					DFA.add(new DFAState(Integer.parseInt(temp[0])));
					for (int j = 0; j < sigma.length; j++) {
						DFA.get(i).addTransition(sigma[j], Integer.parseInt(temp[j + 1]));
					}

				}
				// trash line
				br.readLine();

				// read initial states
				String[] temp = br.readLine().split(":");
				temp = temp[0].split(",");
				for (int i = 0; i < temp.length; i++) {
					initialStates.add(Integer.parseInt(temp[i]));
					// System.out.println(initialStates.get(i));
				}

				// read final states
				temp = br.readLine().split(":");
				temp = temp[0].split(",");
				for (int i = 0; i < temp.length; i++) {
					finalStates.add(Integer.parseInt(temp[i]));
					// System.out.println(finalStates.get(i));
				}
				for (int i = 0; i < initialStates.size(); i++) {
					DFA.get(initialStates.get(i)).setInitialState(true);
				}
				for (int i = 0; i < finalStates.size(); i++) {
					DFA.get(finalStates.get(i)).setFinalState(true);
				}

				optiMatrix = new Integer[noOfStates][noOfStates];

				// fill in all immediately obvious indistinguishable and distinguishable states
				// 1 = distinguishable 0 = indistinguishable
				for (int i = 0; i < optiMatrix.length; i++) {
					for (int j = 0; j < optiMatrix.length; j++) {
						if (i == j) {
							optiMatrix[i][j] = 0;
						} else if (DFA.get(i).isFinalState() != DFA.get(j).isFinalState()) {
							optiMatrix[i][j] = 1;
						}

					}
				}
				do {
					changesMade = false;
					int x, y;
					for (int i = 0; i < optiMatrix.length; i++) {
						for (int j = 0; j < optiMatrix.length; j++) {
							for (int k = 0; k < sigma.length; k++) {
								if (optiMatrix[i][j] == null) {
									x = DFA.get(i).getKey(sigma[k]);
									y = DFA.get(j).getKey(sigma[k]);
									if (optiMatrix[x][y] != null && optiMatrix[x][y] == 1) {
										optiMatrix[i][j] = 1;
										changesMade = true;
									}
								}
							}
						}
					}
				} while (changesMade);

				boolean contains = false;
				ArrayList<HashSet<Integer>> uniqueStates = new ArrayList<HashSet<Integer>>();

				for (int i = 0; i < optiMatrix.length; i++) {

					for (int k = 0; k < uniqueStates.size(); k++) {
						if (uniqueStates.get(k).contains(i))
							contains = true;
					}
					if (!contains) {
						HashSet<Integer> tempSet = new HashSet<Integer>();
						tempSet.add(i);
						for (int j = i; j < optiMatrix.length; j++) {
							if (optiMatrix[i][j] == null) {
								tempSet.add(j);
							}
						}
						uniqueStates.add(tempSet);
					}
					contains = false;
				}

				ArrayList<DFAState> minimizedDFA = new ArrayList<DFAState>();

				for (int i = 0; i < uniqueStates.size(); i++) {
					minimizedDFA.add(new DFAState(i));

				}
				for (int i = 0; i < minimizedDFA.size(); i++) {
					if (i == 0) {
						minimizedDFA.get(i).setInitialState(true);
					}
					for (int j = 0; j < sigma.length; j++) {

						DFAState tempState = DFA.get(uniqueStates.get(i).iterator().next());
						Integer originalTransition = tempState.getKey(sigma[j]);
						Integer newTransition = null;
						boolean isAccepting = tempState.isFinalState();
						for (int k = 0; k < uniqueStates.size(); k++) {
							if (uniqueStates.get(k).contains(originalTransition)) {
								newTransition = k;

							}
						}
						minimizedDFA.get(i).addTransition(sigma[j], newTransition);
						minimizedDFA.get(i).setFinalState(isAccepting);

					}
				}
				System.out.println("Minimized " + args[0] + ": ");
				System.out.println("Number of States: " + minimizedDFA.size());
				System.out.print("Sigma: ");
				for (int i = 0; i < sigma.length; i++) {

					System.out.print("\t" + sigma[i]);
				}
				System.out.println();
				printLine(sigma.length);
				for (int i = 0; i < minimizedDFA.size(); i++) {
					System.out.print(i + ":");
					minimizedDFA.get(i).printDfaMap();
				}
				printLine(sigma.length);
				System.out.print("Initial States: ");
				for (int i = 0; i < minimizedDFA.size(); i++) {
					if (minimizedDFA.get(i).isInitialState())
						System.out.print(" " + i);
				}
				System.out.print("\nAccepting States: ");
				for (int i = 0; i < minimizedDFA.size(); i++) {
					if (minimizedDFA.get(i).isFinalState())
						System.out.print(" " + i);
				}
				System.out.println("\n");
				

				testStrings(minimizedDFA, args[1]);
				System.out.println("\n");

			} catch (FileNotFoundException e) {
				System.out.println("error");
			} catch (IOException e) {

			}
		} else {
			System.out.println("Wrong Number of Arguments");
		}
	}

	public static void printMatrix(Integer[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void testStrings(ArrayList<DFAState> DFA, String filename) {
		try {
			FileReader reader = new FileReader(new File(filename));
			BufferedReader br = new BufferedReader(reader);
			int outputSpacer = 0;

			while (br.ready()) {
				int currentState = 0;
				char[] testString = br.readLine().toCharArray();
				int i;
				FOR_LOOP: for (i = 0; i < testString.length; i++) {
					if (!DFA.get(currentState).containsSigma(Character.toString(testString[i])))
						break FOR_LOOP;
					currentState = DFA.get(currentState).getKey((Character.toString(testString[i])));
				}

				if (i < testString.length)
					System.out.print("No   ");
				else if (DFA.get(currentState).isFinalState())
					System.out.print("Yes  ");
				else {
					System.out.print("No   ");
				}
				outputSpacer++;
				if (outputSpacer >= 15) {
					System.out.println();
					outputSpacer = 0;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {

		}
	}

	public static void printLine(int length) {

		System.out.print("-------");
		for (int i = 0; i < length; i++) {
			System.out.print("-------");
		}
		System.out.println();

	}

}
