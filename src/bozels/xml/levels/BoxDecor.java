package bozels.xml.levels;

import org.jdom.Element;

import bozels.physicsModel.shapes.Box;
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
class BoxDecor extends DecorDef {
	
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
	private BoxDecor(){}

	private static final BoxDecor FACTORY = new BoxDecor();
	public static BoxDecor getFactory(){
		return FACTORY;
	}
	
	@Override
	protected Parsable create(Element el) throws InvalidBozelObjectException {
		BoxDecor bd = new BoxDecor();
		bd.parse(el);
		return bd;
	}
	
	@Override
	public ShapeWrapper getShape() {
		if(shape == null){
			shape = new Box(getHeight(), getWidth());
		}
		return shape;
	}

}
