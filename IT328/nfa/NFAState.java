import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NFAState {

	private Map<String,ArrayList<String>> multiMap;
	private boolean initialState;
	private boolean finalState;
	
	
	
	
	
	public NFAState() {
		this.multiMap = new HashMap<>();
		initialState = false;
		finalState = false;
	}
	
	public void addTransition(String symbol, String transitions) {
		ArrayList<String> temp = new ArrayList<String>();
		
		String[] x = transitions.split(",", -1);
		for (int i = 0; i < x.length; i++) {
			temp.add(x[i]);
		}
		
				
		multiMap.put(symbol, temp);
		
	}

	public Map<String, ArrayList<String>> getMultiMap() {
		return multiMap;
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

	public ArrayList<String> getKeyValue(String key) {
		return multiMap.get(key);
	}
	
	public void print() {
		multiMap.forEach((key, value) -> System.out.print(key + ":" + value + " "));
		System.out.println();
			}
	
	
	
	
	
	
}
