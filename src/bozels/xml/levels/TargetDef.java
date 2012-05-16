package bozels.xml.levels;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;

import bozels.levelModel.core.Level;
import bozels.levelModel.gameObjects.target.TargetType;

import static bozels.levelModel.gameObjects.target.TargetType.*;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class TargetDef extends Parsable{
	
	private String type;
	private TargetType targType;
	
	private final static Map<String, TargetType> TYPES = new HashMap<String, TargetType>();
	static{
		TYPES.put("small", LITTLE);
		TYPES.put("big", BIG);
	}
	
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
	private TargetDef(){}

	private static final TargetDef FACTORY = new TargetDef();
	public static TargetDef getFactory(){
		return FACTORY;
	}
	
	@Override
	protected Parsable create(Element el) throws InvalidBozelObjectException {
		TargetDef tarD = new TargetDef();
		tarD.parse(el);
		return tarD;
	}
	
	@Override
	public void addToLevel(Level level) {
		level.createTargetObject(targType, getPosition());
	}
	
	@Override
	protected void parse(Element el) throws InvalidBozelObjectException {
		super.parse(el);
		type = el.getAttributeValue("type");
		targType = TYPES.get(type);
		if(targType == null){
			throw new InvalidBozelObjectException("This is not a valid target type: "+type);
		}
	}

	public TargetType getTargetType(){
		return TYPES.get(type);
	}
	
	public String getType() {
		return type;
	}

}
