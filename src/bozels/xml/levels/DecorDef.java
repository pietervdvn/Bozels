package bozels.xml.levels;

import static bozels.physicsModel.material.Materials.CONCRETE;
import static bozels.physicsModel.material.Materials.ICE;
import static bozels.physicsModel.material.Materials.METAL;
import static bozels.physicsModel.material.Materials.SOLID;
import static bozels.physicsModel.material.Materials.WOOD;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.jdom.Element;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
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
abstract class DecorDef extends Parsable {

	private String materialName;
	private Material material;
	
	
	private double width;
	private double height;
	private double angle;
	
	private boolean isSolid;
	
	private static final Map<String, Materials> MATERIALS = new HashMap<String, Materials>();
	private static final Set<Materials> SOLID_MATERIALS = new HashSet<Materials>();
	static {
		MATERIALS.put("wood", WOOD);
		MATERIALS.put("ice", ICE);
		MATERIALS.put("stone", CONCRETE);
		MATERIALS.put("metal", METAL);
	
		MATERIALS.put("solid", SOLID);
		SOLID_MATERIALS.add(SOLID);
	}
	
	public abstract ShapeWrapper getShape();
	
	@Override
	protected void parse(Element el) throws InvalidBozelObjectException {
		super.parse(el);
		materialName = el.getAttributeValue("material");
		Materials materialId = MATERIALS.get(materialName);
		material = materialId.getMaterial();
		if(material == null){
			throw new InvalidBozelObjectException("This is not an material: "+materialName);
		}
		isSolid = SOLID_MATERIALS.contains(materialId);
		
		width = Double.parseDouble(el.getAttributeValue("width"));
		height = Double.parseDouble(el.getAttributeValue("height"));
		angle = Math.toRadians(90 -
				Double.parseDouble(el.getAttributeValue("angle")));
	}
	
	@Override
	public void addToLevel(Level level) {
		level.createGameObject(getMaterial(), getShape(), getPosition(), !isSolid(), getAngle());
	}
	
	public Material getMaterial(){
		return MATERIALS.get(materialName).getMaterial();
	}
	
	public String getMaterialName() {
		return materialName;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getAngle() {
		return angle;
	}

	public boolean isSolid() {
		return isSolid;
	}

}
