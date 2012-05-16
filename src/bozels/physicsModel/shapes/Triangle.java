package bozels.physicsModel.shapes;

import java.awt.Graphics2D;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Bozels
 * 
 * Door: Pieter Vander Vennet 1ste Bachelor Informatica Universiteit Gent
 * 
 */
public class Triangle extends ShapeWrapper {

	private final double pointPos;
	private final PolygonShape shape;

	private final Vec2[] vect;

	/**
	 * Pointposition is the place of the top. A triangle is always a triangle
	 * with top pointing up, of height h and basis width
	 * 
	 * @param width
	 * @param height
	 * @param pointPosition
	 */
	public Triangle(double width, double height, double pointPosition) {
		super(width, height);
		this.pointPos = pointPosition;
		shape = new PolygonShape();
		vect = new Vec2[3];
		float hHeight = (float) (height / 3);
		vect[0] = new Vec2((float) (-width / 2), -hHeight);
		vect[1] = new Vec2((float) (width / 2), -hHeight);
		vect[2] = new Vec2((float) (pointPosition - (width / 2)), (float) 2
				* hHeight);
		shape.set(vect, 3);
	}

	@Override
	public String toString() {
		return "Triangle: w:" + getWidth() + " h:" + getHeight() + " pp:"
				+ pointPos;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	public double getPointPos() {
		return pointPos;
	}

	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		double x = b.getPosition().x * scale;
		double y = b.getPosition().y * scale;
		drawTriangle(g2d, x, y, scale);

	}

	public void drawTriangle(Graphics2D g2d, double x, double y, int scale) {

		double halfWidth = (getWidth() / 2) * scale;
		double x1 = x - halfWidth;
		double x2 = x + halfWidth;
		double x3 = x + getPointPos() * scale - halfWidth;

		double halfHeight = getHeight() * scale / 3;
		double y1 = y - halfHeight;
		double y2 = y1;
		double y3 = y + 2 * halfHeight;
		g2d.fillPolygon(new int[] { (int) x1, (int) x2, (int) x3 }, new int[] {
				(int) y1, (int) y2, (int) y3 }, 3);
	}

	@Override
	public double getSurfaceArea() {
		return (getWidth() * getHeight()) / 2;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Triangle)) {
			return false;
		}
		Triangle o = (Triangle) obj;
		return super.equals(obj)
				|| (getWidth() == o.getWidth() && getHeight() == o.getHeight() && o
						.getPointPos() == getPointPos());
	}

}
