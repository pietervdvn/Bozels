package bozels.xml;

import org.jdom.Element;

public interface XMLable {
	
	String getXmlElementName();
	Element createElement();
	void restore(Element element);

}
