package bozels.levelModel.gameObjects.bozel;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.levelModel.gameObjects.GameObject;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.physicsModel.shapes.bozelImageShapes.SplitBozelShape;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SplitBozel extends Bozel {
	

	private final static BozelTypes TYPE = BozelTypes.SPLIT;

	private final static Material MATERIAL = Materials.SPLIT_BOZEL.getMaterial();
	private final static ShapeWrapper SHAPE = new SplitBozelShape();
	private final static double RADIUS = SHAPE.getHeight()/2;
	
	private GameObject bdown;
	private GameObject bup;

//	private final static double splitAngle = Math.PI / 3;
	
	public SplitBozel(Level level) {
		super(level, MATERIAL, SHAPE, TYPE);
	}

	public SplitBozel(Level level, Vec2 startPos) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TYPE);
	}

	public SplitBozel(Level level, Vec2 startPos, double angle) {
		super(level, MATERIAL, SHAPE, startPos, true, angle, TYPE);
	}
		
	@Override
	public void doSpecialAction() {
		Level l = getLevel();
		synchronized (l.getWorld()) {
			double splitAngle = Math.toRadians(30);
			float splitRange = (float) (1+RADIUS);
			
			
			Vec2 pos = this.getBody().getPosition().clone();
			
			Vec2 linVel = this.getBody().getLinearVelocity().clone();
			linVel.normalize();
			int sgn =(int) (Math.signum(linVel.x)*Math.signum(linVel.y));
			Vec2 upper = new Vec2((float)Math.cos(Math.acos(linVel.x)+Math.PI/2)*sgn, 
					(float) Math.sin(Math.asin(linVel.y)+Math.PI/2) );
			upper.mulLocal(splitRange);
			Vec2 downer = new Vec2((float) Math.cos(Math.acos(linVel.x)-Math.PI/2)*sgn, 
					(float) Math.sin(Math.asin(linVel.y)-Math.PI/2) );
			downer.mulLocal(splitRange);
			
			bup = l.createGameObject(
					MATERIAL, this.getShape(),pos.add(upper),
					true, this.getBody().getAngle() + splitAngle);
			
			bdown = l.createGameObject(
					MATERIAL, this.getShape(),pos.add(downer),
					true, this.getBody().getAngle() - splitAngle);
			bup.setBozel(true);
			bdown.setBozel(true);

			Vec2 force = this.getBody().getLinearVelocity();
			
			bup.getBody().setLinearVelocity(
					new Vec2((float) (force.x * Math.cos(splitAngle)),
							(float) (force.y * Math.sin(splitAngle))));
			bdown.getBody().setLinearVelocity(
					new Vec2((float) (force.x * Math.cos(-splitAngle)),
							(float) (force.y *Math.sin(splitAngle))));
		}
	}

	@Override
	public void cleanUp() {	
		Level l = getLevel();
		if(bdown != null){
			l.remove(bdown);
		}
		if(bup != null){
			l.remove(bup);
		}
	}

}
