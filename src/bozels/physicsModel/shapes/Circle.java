package bozels.physicsModel.shapes;

import java.awt.Graphics2D;

import org.jbox2d.collision.shapes.CircleShape;
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
public class Circle extends ShapeWrapper{

	private CircleShape shape = new CircleShape();
	
	public Circle(double width, double height) {
		super(width, width);
		if(width != height){
			throw new IllegalArgumentException("Not a circle");
		}
		shape.m_radius = (float) (width/2);
	}
	
	@Override
	public String toString() {
		return "Circle: r:"+shape.m_radius;
	}
	
	public Circle(double radius){
		this(2*radius, 2*radius);
	}
	

	@Override
	public Shape getShape() {
		return shape;
	}
	
	public double getRadius(){
		return getWidth()/2;
	}
	
	@Override
	public void drawShape(Graphics2D g, Body b, int scale, boolean useImage) {
		double x = (b.getPosition().x - shape.m_radius)*scale;
		double y = (b.getPosition().y - shape.m_radius)*scale;
		g.fillOval((int) (x), 
				(int) (y),
				(int) (shape.m_radius*2*scale), (int) (shape.m_radius*2*scale));
	}

	@Override
	public double getSurfaceArea() {
		return shape.m_radius*shape.m_radius*Math.PI;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Circle)){
			return false;
		}
		Circle o = (Circle) obj;
		return super.equals(obj) || 
				(getWidth() == o.getWidth());
	}

}
