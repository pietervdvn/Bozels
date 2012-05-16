package bozels.visualisatie.gameColorModel;

import static java.awt.Color.black;
import static java.awt.Color.blue;
import static java.awt.Color.gray;
import static java.awt.Color.magenta;
import static java.awt.Color.white;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import bozels.EventSource;
import bozels.valueWrappers.BooleanValue;
import bozels.valueWrappers.ColorValue;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;
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
public class GameColorModel extends EventSource<GameColorModelListener> implements ValueListener<Boolean>, PropertiesList{

	private BooleanValue showSleepingColor = new BooleanValue(false, "showsleepingcolors");
	{showSleepingColor.addListener(this);}
	
	public static final int SOLID = 0;
	public static final int BETON = 1;
	public static final int WOOD = 2;
	public static final int METAL = 3;
	public static final int ICE = 4;
	
	public static final int NORMAL_BOZEL = 5;
	public static final int NORMAL_BOZEL_INTERIOR = 6;
	public static final int SPLIT_BOZEL = 7;
	public static final int EXPLODING_BOZEL = 8;
	public static final int ACCELERATING_BOZEL = 9;
	
	public static final int LITTLE_TARGET = 10;
	public static final int BIG_TARGET = 11;
	
	public static final int KATAPULT_ELASTIC = 12;
	public static final int KATAPULT_DOT = 13;
	
	public static final int DEBUG_DIRECTION = 14;
	
	public static final int TRANSPARENT = 15;
	
	public static final int EXPLOSION_RANGE = 16;
	public static final int EXPLOSION_RAYCAST = 17;
	
	
	private static final Color[] DEFAULT_NORMAL_COLORS = {
			//materials
			new Color(0, 0, 0),
			new Color(100, 100, 100),
			new Color(224,116,27),
			new Color(0, 255, 255),
			new Color(0, 0, 255),
			
			//bozels/angry birds
			new Color(255,0,0),
			new Color(255,255,255),
			new Color(0, 0, 255),
			new Color(128,128,128),
			new Color(255,255,0),
			
			//targets
			
			new Color(70, 255, 70),
			new Color(255, 170, 170),
			
			//katapult
			blue,
			blue,
			
			//debug
			black,
			
			new Color(0,0,0, 0),
			
			//Explosions
			gray,
			magenta,
			
	};
	
	private static final Color[] DEFAULT_SLEEPING_COLORS = {
			//decor
			new Color(0, 0, 0, 100),
			new Color(100, 100, 100, 100),
			new Color(224,116,27, 100),
			new Color(0, 255, 255, 100),
			new Color(0, 0, 255, 100),
			
			//bozels/angry birds
			new Color(255,0,0 ),
			new Color(255,255,255),
			new Color(0, 0, 255),
			new Color(128,128,128),
			new Color(255,255,0),
			
			//targets
			
			new Color(70, 255, 70,100),
			new Color(255, 170, 170,100),
			
			//katapult
			blue,
			blue,
			
			//debug
			black,
			
			new Color(0,0,0, 0),
			
			gray,
			magenta

	};
	
	private final List<ColorValue> normalList = new ArrayList<ColorValue>();
	private final List<ColorValue> sleepingList = new ArrayList<ColorValue>();
	
	private final List<XMLable> xmlables = new ArrayList<XMLable>();
	
	public GameColorModel() {
		ValueListener<Color> listener = new ValueListener<Color>() {

			@Override
			public void onValueChanged(Value<Color> source, Color newValue) {
				throwEvent();
			}
		};
		for (int i = 0; i < DEFAULT_NORMAL_COLORS.length; i++) {
			ColorValue v = new ColorValue(DEFAULT_NORMAL_COLORS[i], "normalColor"+i);
			normalList.add(v);
			v.addHardListener(listener);
		}
		for(int i = 0; i < DEFAULT_SLEEPING_COLORS.length; i++){
			ColorValue v = new ColorValue(DEFAULT_SLEEPING_COLORS[i], "sleepingColor"+i);
			sleepingList.add(v);
			v.addHardListener(listener);
		}
		
		xmlables.add(showSleepingColor);
		xmlables.addAll(normalList);
		xmlables.addAll(sleepingList);
	}
	
	public Color getContrastColorFor(Color color){
		int sum = color.getRed()+color.getBlue()+color.getGreen();
		if(sum>255 && sum<382){
			return black;
		}else if(sum>=382 && sum<510){
			return white;
		}
		
		return getNegative(color);
	}
	
	public Color getNegative(Color color){
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
	}
	
	public Color getColor(int key){
		return normalList.get(key).get();
	}
	
	public Value<Color> getColorValue(int key){
		return normalList.get(key);
	}
	
	public Color getSleepingColor(int key){
		return sleepingList.get(key).get();
	}
	
	public Value<Color> getSleepingColorValue(int key){
		return sleepingList.get(key);
	}
	
	public Color getDynamicSleepingColor(int key){
		return showSleepingColor.get() ? getSleepingColor(key) : getColor(key);
	}
	
	public int numberOfColors(){
		return DEFAULT_NORMAL_COLORS.length;
	}
	
	public void setColor(int key, Color newColor){
		normalList.get(key).set(newColor);
	}
	
	public void setSleepingColor(int key, Color newColor){
		sleepingList.get(key).set(newColor);
	}
	
	private void throwEvent(){
		synchronized (getListeners()) {
			getListeners().resetCounter();
			while(getListeners().hasNext()){
				getListeners().next().onColorChanged(this);
			}
		}
	}

	public boolean showSleepingColor() {
		return showSleepingColor.get();
	}

	public void showSleepingColor(boolean showSleepingColor) {
		this.showSleepingColor.set(showSleepingColor);
	}

	@Override
	public void onValueChanged(Value<Boolean> source, Boolean curVal) {
		throwEvent();
	}
	
	public BooleanValue getShowSleepingColor(){
		return showSleepingColor;
	}

	@Override
	public String getName() {
		return "gamecolorSettings";
	}

	@Override
	public List<? extends PropertiesList> getProperties() {
		return null;
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		return xmlables;
	}



}
