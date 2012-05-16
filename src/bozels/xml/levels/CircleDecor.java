package bozels.xml.levels;

import org.jdom.Element;

import bozels.physicsModel.shapes.Circle;
import bozels.physicsModel.shapes.ShapeWrapper;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class CircleDecor extends DecorDef {
	
	private ShapeWrapper shape;
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	//singleton implementation: only the class itself can create an instance
	//This static instance can be used to ask as factory
	private CircleDecor(){}

	private static final CircleDecor FACTORY = new CircleDecor();
	public static CircleDecor getFactory(){
		return FACTORY;
	}
	
	
	@Override
	protected Parsable create(Element el) throws InvalidBozelObjectException {
		CircleDecor dec = new CircleDecor();
		dec.parse(el);
		return dec;
	}
	
	@Override
	public ShapeWrapper getShape() {
		if(shape == null){
			shape = new Circle(getHeight()/2);
		}
		return shape;
	}

}
