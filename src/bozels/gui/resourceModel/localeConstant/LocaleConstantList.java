package bozels.gui.resourceModel.localeConstant;

import java.util.ArrayList;
import java.util.List;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class LocaleConstantList {
	
	private static final List<LocaleConstant> STATIC_LIST = new ArrayList<LocaleConstant>();

	public static List<LocaleConstant> getList(){
		return STATIC_LIST;
	}
	
}
