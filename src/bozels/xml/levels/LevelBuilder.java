package bozels.xml.levels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

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
public class LevelBuilder {
	
	private final static String ROOT_ELEMENT_NAME = "level";
	private final SAXBuilder builder = new SAXBuilder();
	
	public void buildLevel(Level l, File f) throws FileNotFoundException, JDOMException, IOException, InvalidBozelObjectException{
		buildLevel(l, new FileInputStream(f));
	}
	
	public void buildLevel(Level l, InputStream in) throws JDOMException, IOException, InvalidBozelObjectException{
		getLevelDefinition(in).addToLevel(l);
	}
	
	public LevelDefinition getLevelDefinition(File f) throws JDOMException, IOException, InvalidBozelObjectException{
		return getLevelDefinition(new FileInputStream(f));
	}
	
	public LevelDefinition getLevelDefinition(InputStream in) throws JDOMException, IOException, InvalidBozelObjectException{
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		if(root.getName() != ROOT_ELEMENT_NAME){
			throw new JDOMException("This is not a valid BozelLevelFile");
		}
		LevelDefinition ld = (LevelDefinition) LevelDefinition.getFactory().create(root);
		return ld;
	}

}
