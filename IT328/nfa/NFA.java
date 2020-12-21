import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NFA {

	public static void main(String[] args) {
		BufferedReader br;

		int noOfStates;
		String[] sigma;
		ArrayList<NFAState> NFAStates = new ArrayList<NFAState>();
		String initialState;
		String[] finalState;

		if (args.length != 2) {
			System.out.println("Invalid amount of arguments");
		} else {
			try {

				FileReader reader = new FileReader(new File(args[0]));
				br = new BufferedReader(reader);

				noOfStates = Integer.parseInt(br.readLine().trim());
				sigma = br.readLine().replaceAll("\\s+", " ").trim().concat(" la").split("\\s");

				// create the NFA arraylist here
				for (int i = 0; i < noOfStates; i++) {

					NFAStates.add(new NFAState());

					String[] transitions = br.readLine().replaceAll("\\s|:|\\}", "").split("\\{", -1);

					// loop which adds trasitions to the state
					for (int j = 1; j < transitions.length; j++) {

						// System.out.println(symbols[j-1] + " transition: " + transitions[j]);

						NFAStates.get(i).addTransition(sigma[j - 1], transitions[j]);
					}

					// transitions.add(br.readLine());
					// loop to next state until all states have been created
				}

				initialState = br.readLine();
				finalState = br.readLine().replaceAll("\\{|\\}", "").split(",");

				// System.out.println(noOfStates);
				// System.out.println(symbols);
//			for (int i = 0; i < transitions.size(); i++) {
//				System.out.println(transitions.get(i));
//			}

				// set the initial states of the NFA
				NFAStates.get(Integer.parseInt(initialState)).setInitialState(true);

				// test stuff
//			System.out.println(initialState);
//			System.out.println(finalState);

				// set the final states of the NFA
				for (int i = 0; i < finalState.length; i++) {
					NFAStates.get(Integer.parseInt(finalState[i])).setFinalState(true);
				}

				// more test stuff
				System.out.print("Sigma: ");
				for (int i = 0; i < sigma.length - 1; i++) {
					System.out.print(sigma[i] + " ");
				}
				System.out.println();
				System.out.println("--------------------");
				for (int i = 0; i < NFAStates.size(); i++) {
					System.out.print(i + ":" + '\t');
					NFAStates.get(i).print();

				}
				System.out.println("--------------------");
				System.out.println("Initial States: " + initialState);
				System.out.print("Accepting States: ");
				for (int i = 0; i < finalState.length; i++) {
					System.out.print(finalState[i] + " ");
				}
				System.out.println();

				// Start Creating the DFA

				ArrayList<DFAState> DFA = new ArrayList<DFAState>();

				ArrayList<String> initialCode = new ArrayList<String>();

				initialCode.add(initialState);

				for (int i = 0; i < initialCode.size(); i++) {
					ArrayList<String> lamda = NFAStates.get(Integer.parseInt(initialCode.get(i))).getKeyValue("la");
					// System.out.println("Getting lambda for: " + initialCode.get(i));
					for (int j = 0; j < lamda.size(); j++) {
						String temp = lamda.get(j);
						// System.out.println("Attempting to add this to initial state: " + temp);
						if (!temp.equals("")) {
							if (!initialCode.contains(temp)) {
								initialCode.add(temp);
							}
						}
					}
				}

//			for (int i = 0; i < initialCode.size(); i++) {
//				System.out.println(initialCode.get(i));
//			}
				Collections.sort(initialCode);
				DFA.add(new DFAState(initialCode));
				DFA.get(0).setInitialState(true);
				// outer loop for each new state in DFA
				for (int i = 0; i < DFA.size(); i++) {
					ArrayList<String> currentState = DFA.get(i).getNFAValue();
					// for loop
					for (int j = 0; j < sigma.length - 1; j++) {
						String key = sigma[j];
						ArrayList<String> newCode = new ArrayList<String>();

						for (int k = 0; k < currentState.size(); k++) {
							ArrayList<String> keyTransitions = NFAStates.get(Integer.parseInt(currentState.get(k)))
									.getKeyValue(key);
							for (int l = 0; l < keyTransitions.size(); l++) {
								String temp = keyTransitions.get(l);
								if (!temp.equals("")) {
									if (!newCode.contains(temp)) {
										newCode.add(temp);
									}
								}
							}
						}
						for (int k = 0; k < newCode.size(); k++) {
							ArrayList<String> lamda = NFAStates.get(Integer.parseInt(newCode.get(k))).getKeyValue("la");
							for (int l = 0; l < lamda.size(); l++) {
								String temp = lamda.get(l);
								if (!temp.equals("")) {
									if (!newCode.contains(temp)) {
										newCode.add(temp);
									}
								}
							}
						}
						Collections.sort(newCode);
						DFA.get(i).addTransition(key, newCode);
						if (!newStateCheck(DFA, newCode) && newCode.size() != 0) {
							DFA.add(new DFAState(newCode));
						}
					}

				}
				for (int i = 0; i < DFA.size(); i++) {
					for (int j = 0; j < finalState.length; j++) {
						if (DFA.get(i).getNFAValue().contains(finalState[j]))
							DFA.get(i).setFinalState(true);

					}
				}
				generateDfaMap(DFA, sigma);

				System.out.println();
				System.out.println("To DFA:");
				System.out.print("Sigma:\t");
				for (int i = 0; i < sigma.length - 1; i++) {
					System.out.print("\t" + sigma[i]);
				}
				System.out.println("\n--------------------");
				for (int i = 0; i < DFA.size(); i++) {
					// System.out.print(DFA.get(i).get + "\t");
					DFA.get(i).printDfaMap();
				}
				System.out.println("--------------------");
				System.out.print("Initial States: ");
				for (int i = 0; i < DFA.size(); i++) {
					if (DFA.get(i).isInitialState())
						System.out.print(" " + i);
				}
				System.out.print("\nAccepting States: ");
				for (int i = 0; i < DFA.size(); i++) {
					if (DFA.get(i).isFinalState())
						System.out.print(" " + i);
				}
				System.out.println();

				testStrings(DFA, args[1]);

			} catch (FileNotFoundException e) {
				// TODO: handle exception
			} catch (IOException e) {

			}
		}
	}

	// static method to check if state exists, returns true if it already does and
	// false if it does not
	public static boolean newStateCheck(ArrayList<DFAState> DFA, ArrayList<String> newValue) {
		for (int i = 0; i < DFA.size(); i++) {
			if (DFA.get(i).getNFAValue().equals(newValue))
				return true;

		}
		return false;

	}

	public static void generateDfaMap(ArrayList<DFAState> DFA, String[] sigma) {
		for (int i = 0; i < DFA.size(); i++) {
			DFA.get(i).setStateNumber(i);
			for (int j = 0; j < sigma.length - 1; j++) {
				ArrayList<String> nfaValue = DFA.get(i).getKeyValue(sigma[j]);
				for (int k = 0; k < DFA.size(); k++) {
					ArrayList<String> currentState = DFA.get(k).getNFAValue();
					if (currentState.equals(nfaValue)) {
						DFA.get(i).addToDFAStateMap(sigma[j], k);
					}
				}

			}
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
					currentState = DFA.get(currentState).getDfaKeyValue(Character.toString(testString[i]));
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

}
