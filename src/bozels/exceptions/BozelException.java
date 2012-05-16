package bozels.exceptions;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class BozelException extends Exception{

	private static final long serialVersionUID = 6711295732915081349L;

	public BozelException(){}
	
	public BozelException(String message) {
		super(message);
	}
}
