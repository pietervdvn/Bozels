package bozels.physicsModel.shapes;

import java.awt.Graphics2D;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class Box extends ShapeWrapper{

	PolygonShape shape = new PolygonShape();
	
	public Box(double width, double height) {
		super(width, height);
		shape.setAsBox((float) (width/2), (float) (height/2));
	}

	@Override
	public Shape getShape() {
		return shape;
	}
	
	@Override
	public String toString() {
		return "Box: w: "+getWidth()+" h:"+getHeight();
	}

	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		int x = (int) ((b.getPosition().x)*scale);
		int y = (int) ((b.getPosition().y)*scale);
		g2d.fillRect((int) (x - (getWidth()/2)*scale),
				(int) (y - (getHeight()/2)*scale),
				(int) (getWidth()*scale), (int) (getHeight()*scale));
	}

	@Override
	public double getSurfaceArea() {
		return getWidth()*getHeight();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Box)){
			return false;
		}
		Box o = (Box) obj;
		return super.equals(obj) || 
				(getWidth() == o.getWidth() && getHeight() == o.getHeight());
	}

}
