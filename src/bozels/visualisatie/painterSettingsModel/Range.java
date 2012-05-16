package bozels.visualisatie.painterSettingsModel;

/**
 * An range, with an value
 * Min and max are inclusive
 */
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class Range<T extends Comparable<T>> {
	
	private final T min;
	private final T max;
	
	private T current;
	
	public Range(T min, T current, T max){
		this.min = min;
		this.max = max;
		
		this.setCurrent(current);
	}
	
	public Range(T min, T max) {
		this(min, min, max);
	}

	public T getMax() {
		return max;
	}

	public T getMin() {
		return min;
	}

	public T getCurrent() {
		return current;
	}
	
	public boolean isInRange(T testVal){
		return !(testVal.compareTo(min)<0 || testVal.compareTo(max)>0);
	}

	public void setCurrent(T newValue) {
		if(newValue.compareTo(min)<0 || newValue.compareTo(max)>0){
			throw new IllegalArgumentException("Invalid value: "+newValue+": should be in: ["+min+","+max+"]");
		}
		this.current = newValue;
	}

}
