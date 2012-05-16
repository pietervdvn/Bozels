package bozels.xml.levels;

import bozels.exceptions.BozelException;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class InvalidBozelObjectException extends BozelException{
	private static final long serialVersionUID = 3129850374269600993L;
	
	public InvalidBozelObjectException() {}
	public InvalidBozelObjectException(String message){
		super(message);
	}
	
	
}
