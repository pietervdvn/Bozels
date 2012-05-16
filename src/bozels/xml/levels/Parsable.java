package bozels.xml.levels;


import org.jbox2d.common.Vec2;
import org.jdom.Element;

import bozels.levelModel.core.Level;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class Parsable {
	
	protected Parsable(){}
	
	private double x;
	private double y;
	
	protected abstract Parsable create(Element el) throws InvalidBozelObjectException;
	
	protected void parse(Element el) throws InvalidBozelObjectException{
		x = Double.parseDouble(el.getAttributeValue("x"));
		y = Double.parseDouble(el.getAttributeValue("y"));
	}


	public abstract void addToLevel(Level level);
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public Vec2 getPosition(){
		return new Vec2((float) x, (float) y);
	}

}
