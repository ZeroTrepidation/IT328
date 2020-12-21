import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DFAState {
	
	private Map<String,ArrayList<String>> nfaValueMap;
	private Map<String, Integer> dfaStateMap;
	
	private ArrayList<String> NFAValue;
	private boolean initialState;
	private boolean finalState;
	private int stateNumber;
	
	
	public DFAState(ArrayList<String> NFAValue) {
		this.NFAValue = NFAValue;
		nfaValueMap = new HashMap<>();
		dfaStateMap = new HashMap<>();
	}
	

	public void addTransition(String key, ArrayList<String> code) {
		nfaValueMap.put(key, code);
	}
	
	public ArrayList<String> getKeyValue(String key) {
		return nfaValueMap.get(key);
	}
	
	public Integer getDfaKeyValue(String key) {
		return dfaStateMap.get(key);
	}
	
	public boolean containsSigma(String key) {
		return dfaStateMap.containsKey(key);
	}
	

	public Map<String, Integer> getDfaStateMap() {
		return dfaStateMap;
	}

	public void addToDFAStateMap(String key, Integer state) {
		dfaStateMap.put(key, state);
	}
	
	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
	}

	public ArrayList<String> getNFAValue() {
		return NFAValue;
	}
	public void setNFAValue(ArrayList<String> nFAValue) {
		NFAValue = nFAValue;
	}
	
	public boolean isInitialState() {
		return initialState;
	}
	public void setInitialState(boolean initialState) {
		this.initialState = initialState;
	}
	public boolean isFinalState() {
		return finalState;
	}
	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
	
	public void printNfaMap() {
		nfaValueMap.forEach((key, value) -> System.out.print(key + ":" + value + " "));
		System.out.println();
		}
	
	public void printDfaMap() {
		System.out.print("\t" + stateNumber + ":");
		dfaStateMap.forEach((key, value) -> System.out.print("\t" + value));
		System.out.println();
	}
	
	
	

}
