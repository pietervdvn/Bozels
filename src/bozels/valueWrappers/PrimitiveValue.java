package bozels.valueWrappers;

import org.jdom.Element;

import bozels.valueWrappers.validators.Validator;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public abstract class PrimitiveValue<T> extends Value<T>{

	public PrimitiveValue(T value, String name) {
		super(value, name);
	}

	public PrimitiveValue(T value, String name, Validator<T> v) {
		super(value, name, v);
	}

	@Override
	public Element createElement() {
		Element el = new Element(getXmlElementName());
		el.setText(get().toString());
		return el;
	}
	
	@Override
	public void restore(Element element) {
		if(!element.getName().equals(getXmlElementName())){
			element = element.getChild(getXmlElementName());
		}
		if(element == null){
			return;
		}
		set(parse(element.getText()));
	}
	
	public abstract T parse(String text);
}
