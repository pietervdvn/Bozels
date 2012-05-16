package bozels.physicsModel;

import java.awt.Graphics2D;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class AbstractObject extends BreakableObject{
	
	private final World world;
	private final Body body;
	
	private final ShapeWrapper shape;
	private final Material material;
	
	private final boolean isSolid;
	private int colorkey;
	
	private Level level;
	
	/**
	 * User data is a plain and global accessible string, used for debug
	 */
	protected AbstractObject(World world, Material material, ShapeWrapper shape, boolean bullet){
		this(world, material, shape, new Vec2(0f,0f), true, bullet, 0.0);
	}
		
	
	protected AbstractObject(World world, Material material, ShapeWrapper shape, 
			Vec2 startPos, boolean dynamic, boolean bullet, double angle){
		super(material.getStrength(), material.getPowerThreshold(), shape.getSurfaceArea()*material.getDensity(), material.canBreak());
		this.world = world;
		this.shape = shape;
		this.material = material;
		this.isSolid = !dynamic;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(startPos);
		bodyDef.angle = (float) angle;
		bodyDef.bullet = bullet;
		bodyDef.type = dynamic ? BodyType.DYNAMIC : BodyType.STATIC;
		synchronized (world) {
			this.body = world.createBody(bodyDef);
			body.m_userData = this;
		}
		applyFixt(material);
		material.addListener(this);
	}
	
	private void applyFixt(Material mat){
		Fixture oldFixture = body.getFixtureList();
		while(oldFixture != null){
			body.destroyFixture(oldFixture);
			oldFixture = oldFixture.getNext();
		}
			
			
		FixtureDef fixt = material.createFixtureDefinition();
		fixt.shape = shape.getShape();
		
		colorkey = material.getColorKey();

		synchronized (world) {
			Fixture fixture = body.createFixture(fixt);
			fixture.m_userData = material;
		}
	}
	
	@Override
	public void onFixtureSettingChanged(Material source) {
		applyFixt(source);
	}
	
	@Override
	public String toString() {
		return "Physical object: "+shape.toString()+" "+material.toString();
	}
	
	public void paintOn(Graphics2D g2d, int scale, int xTrans, int yTrans, GameColorModel colorModel, PainterSettingsModel psm){
		if(body.isAwake()){
			g2d.setColor(colorModel.getColor(colorkey));
		}else{
			g2d.setColor(colorModel.getDynamicSleepingColor(colorkey));
		}
		int x = (int) (body.getPosition().x*scale);
		int y = (int) (body.getPosition().y*scale);
		double angle = body.getAngle();
		g2d.rotate(angle, x, y);
				
		paintShape(g2d, scale, psm.drawsImage(), colorModel);
		
		if(psm.drawRotation()){
			g2d.setColor(colorModel.getContrastColorFor(colorModel.getColor(colorkey)));
			
			g2d.drawLine(x,
					y,
					(int) ((body.getPosition().x+shape.getWidth()/2)*scale),
					y);
		}
		g2d.rotate(-angle, x, y);
		if(psm.drawsSpeeds()){
			g2d.setColor(colorModel.getColor(GameColorModel.DEBUG_DIRECTION));
			g2d.drawLine(x, y, (int) (x+body.getLinearVelocity().x*scale), (int) (y+body.getLinearVelocity().y*scale));
		}
		if(psm.drawsGravityPoint()){
			g2d.setColor(colorModel.getContrastColorFor(colorModel.getColor(colorkey)));
			psm.getGravPointShape().drawShape(g2d, body, scale, false);
		}
	}
	
	protected void paintShape(Graphics2D g2d, int scale, boolean useImage, GameColorModel cm){
		shape.drawShape(g2d, body, scale, useImage);
		
	}

	public float getRotation(){
		return body.getAngle();
	}
	
	public float getX(){
		return body.getPosition().x;
	}
	
	public float getY(){
		return body.getPosition().y;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Body getBody(){
		return body;
	}

	public ShapeWrapper getShape() {
		return shape;
	}

	public Material getMaterial() {
		return material;
	}
	
	public int getColorkey() {
		return colorkey;
	}
	
	public void setColorkey(int colorkey) {
		this.colorkey = colorkey;
	}
	
	public void resetColorkey(){
		this.colorkey = material.getColorKey();
	}


	public boolean isSolid() {
		return isSolid;
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}

}
