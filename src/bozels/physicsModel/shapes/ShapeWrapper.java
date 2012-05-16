	package bozels.physicsModel.shapes;

import java.awt.Graphics2D;

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
public abstract class ShapeWrapper {

	private final double width;
	private final double height;

	public ShapeWrapper(double width, double height){
		this.width = width;
		this.height = height;
	}

	/**
	 * Get's the shape. This is one shape per shapewrapper, 
	 * and does not generate a new (JBox2d)shape object
	 * @return
	 */
	public abstract Shape getShape();
	
	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}
	
	public abstract void drawShape(Graphics2D g, Body b, int scale, boolean useImages);

	public abstract double getSurfaceArea();
	
}
