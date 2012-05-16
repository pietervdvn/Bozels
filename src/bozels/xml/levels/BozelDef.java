package bozels.xml.levels;

import static bozels.levelModel.gameObjects.bozel.BozelTypes.ACCELERATING;
import static bozels.levelModel.gameObjects.bozel.BozelTypes.EXPLODING;
import static bozels.levelModel.gameObjects.bozel.BozelTypes.NORMAL;
import static bozels.levelModel.gameObjects.bozel.BozelTypes.SPLIT;

import java.util.HashMap;
import java.util.Map;


import org.jdom.Element;

import bozels.levelModel.core.Level;
import bozels.levelModel.gameObjects.bozel.BozelTypes;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class BozelDef extends Parsable implements Comparable<BozelDef>{

	private String type;
	private BozelTypes bozelTypes;
	private int id;
	
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
	private BozelDef(){}

	private static final BozelDef FACTORY = new BozelDef();
	public static BozelDef getFactory(){
		return FACTORY;
	}
	

	private final static Map<String, BozelTypes> TYPES = new HashMap<String, BozelTypes>();
	static{
		TYPES.put("red", NORMAL);
		TYPES.put("blue", SPLIT);
		TYPES.put("yellow", ACCELERATING);
		TYPES.put("white", EXPLODING);
		TYPES.put("black", EXPLODING);
	}
	
	@Override
	protected Parsable create(Element el) throws InvalidBozelObjectException {
		BozelDef def = new BozelDef();
		def.parse(el);
		return def;
	}
	
	@Override
	protected void parse(Element el) throws InvalidBozelObjectException {
		super.parse(el);
		id = Integer.parseInt(el.getAttributeValue("id"));
		type = el.getAttributeValue("type");
		bozelTypes = TYPES.get(type);
		if(bozelTypes == null){
			throw new InvalidBozelObjectException("Not a valid bozel! Type:"+type);
		}
	}
	
	@Override
	public void addToLevel(Level level){
		level.createBozelObject(bozelTypes, getPosition());
	}
	
	public BozelTypes getBozelType(){
		return bozelTypes;
	}

	@Override
	public String toString() {
		return super.toString()+" BozelDef: type:"+type+" id:"+id;
	}
	
	@Override
	public int compareTo(BozelDef o) {
		return getId() - o.getId();
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}
}
