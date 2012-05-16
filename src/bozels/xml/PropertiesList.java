package bozels.xml;

import java.util.List;

/**
 * This interface is an interface that tells that the object has got
 * an list of properties and/or an list of xmlables/ValueWrappers.
 * This way, models gan pass their children-models and properties, which can be serialise
 * (e.g. to xml afterwards.)
 * 
 * This way, xmling code can be avoiden in the models.
 * 
 * Note: both methods may return null, except getName
 */
public interface PropertiesList {
	
	String getName();
	List<? extends PropertiesList> getProperties();
	List<? extends XMLable> getXMLables();

}
