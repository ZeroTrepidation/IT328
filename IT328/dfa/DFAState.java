import java.util.HashMap;
import java.util.Map;

public class DFAState {

	private boolean isInitialState;
	private boolean isFinalState;
	private int stateNumber;
	private Map<String, Integer> transitionMap;
	public DFAState(int stateNumber) {
		
		this.stateNumber = stateNumber;
		this.transitionMap = new HashMap<>();
	}
	

	public Map<String, Integer> getTransitionMap() {
		return transitionMap;
	}

	public void addTransition(String key, Integer state) {
		transitionMap.put(key, state);
	}
	
	public Integer getKey(String key) {
		return transitionMap.get(key);
	}


	public boolean isInitialState() {
		return isInitialState;
	}


	public void setInitialState(boolean isInitialState) {
		this.isInitialState = isInitialState;
	}


	public boolean isFinalState() {
		return isFinalState;
	}


	public void setFinalState(boolean isFinalState) {
		this.isFinalState = isFinalState;
	}


	public int getStateNumber() {
		return stateNumber;
	}


	public void setStateNumber(int stateNumber) {
		this.stateNumber = stateNumber;
	}
	
	public boolean containsSigma(String key) {
		return transitionMap.containsKey(key);
	}
	
	public void printDfaMap() {
		transitionMap.forEach((key, value) -> System.out.print("\t" + value));
		System.out.println();
	}
}
