package bozels.xml.levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class LevelDefinition extends Parsable{
	
	private final static LevelDefinition FACTORY = new LevelDefinition();
	public static LevelDefinition getFactory(){
		return FACTORY;
	}
	private LevelDefinition(){}
	
	private List<BozelDef> bozelDefs;
	private List<Parsable> otherObjects;

	private final static Map<String, Parsable> FACTORIES = new HashMap<String, Parsable>();
	private final static List<String> TO_PARSE = new ArrayList<String>();
	static{
		FACTORIES.put("bozel", BozelDef.getFactory());
		
		FACTORIES.put("target", TargetDef.getFactory());
		TO_PARSE.add("target");
		
		FACTORIES.put("ellipse", CircleDecor.getFactory());
		TO_PARSE.add("ellipse");

		FACTORIES.put("block", BoxDecor.getFactory());
		TO_PARSE.add("block");
	}
	
	
	@Override
	protected Parsable create(Element root) throws InvalidBozelObjectException {
		LevelDefinition ld = new LevelDefinition();
		ld.parseLevel(root);
		return ld;
	}
	
	private void parseLevel(Element root) throws InvalidBozelObjectException{
		bozelDefs = extractBozels(root);
		if(bozelDefs.size()==0){
			throw new InvalidBozelObjectException("This is not a valid bozel file! It contains no Bozels.");
		}
		otherObjects = new ArrayList<Parsable>();
		
		for(String parseType : TO_PARSE){
			@SuppressWarnings("unchecked")
			List<Element> elements = root.getChildren(parseType);
			otherObjects.addAll(
					parseAll(elements));
		}
	}

	@Override
	public void addToLevel(Level level) {
		addAll(bozelDefs, level);
		addAll(otherObjects, level);
	}
	
	private List<BozelDef> extractBozels(Element root) throws InvalidBozelObjectException{
		@SuppressWarnings("unchecked")
		List<Element> bozelsE = root.getChildren("bozel");
		List<Parsable> bozelsP = parseAll(bozelsE);
		List<BozelDef> bozels = new ArrayList<BozelDef>();
		for(Parsable bozel : bozelsP){
			bozels.add((BozelDef) bozel);
		}
		Collections.sort(bozels);
		return bozels;
	}
	
	
	private List<Parsable> parseAll(List<Element> elements) throws InvalidBozelObjectException{
		List<Parsable> parsed = new ArrayList<Parsable>();
		for(Element element : elements){
			parsed.add(parseElement(element));
		}
		return parsed;
	}

	private Parsable parseElement(Element element) throws InvalidBozelObjectException{
		
		Parsable factory = FACTORIES.get(element.getName());
		if(factory == null){
			throw new InvalidBozelObjectException("No such kind of bozel: "+element.getName());
		}
		Parsable object = factory.create(element);
		return object;
	}
	
	private void addAll(List<? extends Parsable> toAdd, Level level){
		synchronized (level) {
			for(Parsable p : toAdd){
				p.addToLevel(level);
			}
		}
	}

}
