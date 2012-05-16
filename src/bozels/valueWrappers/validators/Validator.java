package bozels.valueWrappers.validators;

public interface Validator<T> {

	boolean isValidValue(T value);
	
}
