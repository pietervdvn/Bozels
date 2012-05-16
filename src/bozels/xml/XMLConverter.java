package bozels.xml;

import org.jdom.Element;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class XMLConverter implements XMLable {

	private final PropertiesList list;

	public XMLConverter(PropertiesList list) {
		this.list = list;
	}

	@Override
	public String getXmlElementName() {
		return list.getName();
	}

	@Override
	public Element createElement() {
		Element el = new Element(getXmlElementName());

		if (list.getProperties() != null) {
			for (PropertiesList sub : list.getProperties()) {
				el.addContent(new XMLConverter(sub).createElement());
			}
		}

		if (list.getXMLables() != null) {
			for (XMLable xmlable : list.getXMLables()) {
				el.addContent(xmlable.createElement());
			}
		}
		return el;
	}

	@Override
	public void restore(Element element) {
		if (!element.getName().equals(getXmlElementName())) {
			element = element.getChild(getXmlElementName());
		}
		if(element == null){
			return;
		}
		if(list.getProperties() != null){
			for (PropertiesList sub : list.getProperties()){
				new XMLConverter(sub).restore(element);
			}
		}
		
		if(list.getXMLables() != null){
		for (XMLable xmlable : list.getXMLables()) {
			xmlable.restore(element);
		}
		}
	}

}
