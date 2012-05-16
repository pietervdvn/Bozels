package bozels.physicsModel.material;

import java.util.ArrayList;
import java.util.List;

import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.xml.PropertiesList;
import bozels.xml.XMLable;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public enum Materials implements PropertiesList{
	
	// \\//\\//\\//\\ BOZELS //\\//\\//\\//\\
	ACCELERATING_BOZEL(10.0, 0.1, 0.9, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.ACCELERATING_BOZEL, LocaleConstant.ACCELERATING_BOZEL),
	EXPLODING_BOZEL(5.0, 0.0, 0.2, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.EXPLODING_BOZEL, LocaleConstant.EXPLODING_BOZEL),
	NORMAL_BOZEL(10.0, 0.3, 0.9, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.NORMAL_BOZEL, LocaleConstant.NORMAL_BOZEL),
	SPLIT_BOZEL(8.0, 0.7, 1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.SPLIT_BOZEL, LocaleConstant.SPLIT_BOZEL),
	
	// \\//\\//\\//\\ TARGETS //\\//\\//\\//\\

	LITTLE_TARGET(1.0, 0.1, 0.9, 7.0, 10.0, GameColorModel.LITTLE_TARGET, LocaleConstant.LITTLE_TARGET),
	BIG_TARGET(1.0, 0.1, 0.9, 5.0, 10.0, GameColorModel.BIG_TARGET, LocaleConstant.BIG_TARGET),
	
	// \\//\\//\\//\\ DECOR //\\//\\//\\//\\

	CONCRETE(4.0, 0.0, 0.9, 20.0, 50.0, GameColorModel.BETON, LocaleConstant.CONCRETE),
	ICE(1.0, 0.0, 0.1, 15.0, 10.0, GameColorModel.ICE, LocaleConstant.ICE),
	METAL(3.0, 0.2, 0.3, 18.0, 52.0, GameColorModel.METAL, LocaleConstant.METAL),
	SOLID(1, 0.1, 1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.SOLID, LocaleConstant.SOLID),
	SOLID_TRANSPARENT(1, 0.1, 1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, GameColorModel.TRANSPARENT, LocaleConstant.SOLID_TRANSPARENT),
	WOOD(0.75, 0.3, 0.8, 10.0, 12.0, GameColorModel.WOOD, LocaleConstant.WOOD),
	;
	private final Material material;
	
	private Materials(double density, 
			double restitution, 
			double friction, 
			double powerThreshold, 
			double strength, int colorKey, LocaleConstant name){
		this(new Material(density, restitution, friction, powerThreshold, strength, colorKey, name));
	}
	
	private Materials(Material material) {
		this.material = material;
		MaterialsList.getList().add(this.getMaterial());
	}

	public Material getMaterial() {
		return material;
	}
	
	public static List<Material> getList(){
		return MaterialsList.getList();
	}
	
	public static Material getMaterial(LocaleConstant name){
		for(Material mat : MaterialsList.getList()){
			if(mat.getMaterialName() == name){
				return mat;
			}
		}
		return null;
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
	private static class MaterialsList {
		
		private static final List<Material> ALL_MATS = new ArrayList<Material>();
		public static List<Material> getList(){
			return ALL_MATS;
		}

		public static String getXmlElementName() {
			return "materialSettings";
		}
	}

	@Override
	public String getName() {
		return MaterialsList.getXmlElementName();
	}

	@Override
	public List<? extends PropertiesList> getProperties() {
		return getList();
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		return null;
	}
}
