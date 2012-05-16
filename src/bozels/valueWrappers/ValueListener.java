package bozels.valueWrappers;

public interface ValueListener<T> {

	void onValueChanged(Value<T> source, T newValue);
	
}
