package bozels.visualisatie.painterSettingsModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import bozels.EventSource;
import bozels.SafeList;
import bozels.physicsModel.shapes.Circle;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.valueWrappers.BooleanValue;
import bozels.valueWrappers.ColorValue;
import bozels.valueWrappers.IntegerValue;
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
public class PainterSettingsModel extends
		EventSource<PainterSettingsModelListener> implements
		ValueListener<Boolean>, PropertiesList {

	private final static int NORMAL_FACTOR = 3;
	private final static int NORMAL_SCALE = 7;

	private final static int NORMAL_WIDTH = 1024;
	private final static int NORMAL_HEIGHT = 450;

	private int width = NORMAL_WIDTH;
	private int height = NORMAL_HEIGHT;
	
	BooleanValue useBackgroundImage = new BooleanValue(true, "usebackgroundimage");
	private ColorValue backgroundColor = new ColorValue(Color.white, "backgroundcolor");

	private Range<Integer> scale = new Range<Integer>(NORMAL_SCALE, NORMAL_SCALE
			* NORMAL_FACTOR);

	private Range<Integer> xTrans = new Range<Integer>(0, NORMAL_WIDTH
			* (NORMAL_FACTOR - 1));
	private Range<Integer> yTrans = new Range<Integer>(-20, NORMAL_HEIGHT
			* (NORMAL_FACTOR - 1));

	private BooleanValue drawGravityPoint = new BooleanValue(false, "drawgravitypoint");
	private BooleanValue drawRotation = drawGravityPoint; // Will work together
	private ShapeWrapper gravPointShape = new Circle(0.25);

	private BooleanValue drawExplosions = new BooleanValue(false,"drawexplosions");
	private BooleanValue drawSpeeds = new BooleanValue(false, "drawspeeds");
	private BooleanValue drawImages = new BooleanValue(true, "drawimages");

	private IntegerValue catapultDotSize = new IntegerValue(1, "catapultdotsize");
	
	private final List<XMLable> xmlables = new ArrayList<XMLable>();

	public PainterSettingsModel() {
		drawGravityPoint.addListener(this);
		drawExplosions.addListener(this);
		drawSpeeds.addListener(this);
		useBackgroundImage.addListener(this);
		drawImages.addListener(this);
		
		ValueListener<Integer> catapultListener = new ValueListener<Integer>() {
			@Override
			public void onValueChanged(Value<Integer> source, Integer newValue) {
				throwEvent();
			}
		};
		catapultDotSize.addHardListener(catapultListener);
		
		backgroundColor.addHardListener(new ValueListener<Color>() {
			
			@Override
			public void onValueChanged(Value<Color> source, Color newValue) {
				throwEvent();
			}
		});
		
		xmlables.add(getUseBackgroundImage());
		xmlables.add(getDrawExplosions());
		xmlables.add(getDrawGravityPoint());
		xmlables.add(getDrawRotation());
		xmlables.add(getDrawSpeeds());
		xmlables.add(getDrawsImages());
		
		xmlables.add(getCatapultDotSizeValue());
		xmlables.add(backgroundColor);
	}
	
	public void setUseBackground(boolean useBack){
		useBackgroundImage.set(useBack);
	}
	
	public boolean usesBackground(){
		return useBackgroundImage.get();
	}
	
	public BooleanValue getUseBackgroundImage() {
		return useBackgroundImage;
	}

	public int getScale() {
		return scale.getCurrent();
	}

	public void setScale(int newVal) {
		changeSetting(scale, newVal);
	}

	public void setXtranslation(int newX) {
		changeSetting(xTrans, newX);
	}

	public void setYTranslation(int newY) {
		changeSetting(yTrans, newY);
	}

	private void changeSetting(Range<Integer> r, int i) {
		if (r.getCurrent() != i && r.isInRange(i)) {
			r.setCurrent(i);
			throwEvent();
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (this.width == width) {
			return;
		}
		this.width = width;
		throwEvent();
	}

	public int getHeight() {
		return height;
	}

	public int getXTranslation() {
		return xTrans.getCurrent();
	}

	public int getYTranslation() {
		return yTrans.getCurrent();
	}

	public void setHeight(int height) {
		if (this.height == height) {
			return;
		}
		this.height = height;
		throwEvent();
	}

	private void throwEvent() {
		SafeList<PainterSettingsModelListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().onSettingsChanged(this);
			}
		}
	}

	public boolean drawsExplosions() {
		return drawExplosions.get();
	}

	public void setDrawExplosions(boolean drawExplosions) {
		this.drawExplosions.set(drawExplosions);
	}

	public BooleanValue getDrawExplosions() {
		return drawExplosions;
	}
	
	public void setDrawsImages(boolean b){
		drawImages.set(b);
	}
	
	public BooleanValue getDrawsImages(){
		return drawImages;
	}

	public boolean drawsImage() {
		return drawImages.get();
	}


	public boolean drawsGravityPoint() {
		return drawGravityPoint.get();
	}

	public void setDrawGravityPoint(boolean drawGravityPoint) {
		this.drawGravityPoint.set(drawGravityPoint);
	}

	public BooleanValue getDrawGravityPoint() {
		return drawGravityPoint;
	}

	public boolean drawsSpeeds() {
		return drawSpeeds.get();
	}

	public void setDrawSpeeds(boolean drawSpeeds) {
		this.drawSpeeds.set(drawSpeeds);
	}

	public BooleanValue getDrawSpeeds() {
		return drawSpeeds;
	}

	public boolean drawRotation() {
		return drawRotation.get();
	}

	public void setDrawRotation(boolean drawRotation) {
		this.drawRotation.set(drawRotation);
	}

	public BooleanValue getDrawRotation() {
		return drawRotation;
	}

	public ShapeWrapper getGravPointShape() {
		return gravPointShape;
	}

	public void setGravPointShape(ShapeWrapper gravPointShape) {
		if (this.gravPointShape == gravPointShape) {
			return;
		}
		this.gravPointShape = gravPointShape;
		throwEvent();
	}

	public int getCatapultDotSize() {
		return catapultDotSize.get();
	}
	
	public Value<Integer> getCatapultDotSizeValue(){
		return catapultDotSize;
	}

	public void setCatapultDotSize(int catapultDotSize) {
		this.catapultDotSize.set(catapultDotSize);
	}

	@Override
	public void onValueChanged(Value<Boolean> source, Boolean curVal) {
		throwEvent();
	}

	public Color getBackgroundColor() {
		return backgroundColor.get();
	}

	public void setBackgroundColor(Color backgroundColor) {
		if(backgroundColor == null){
		}
		this.backgroundColor.set(backgroundColor);
	}

	@Override
	public String getName() {
		return "painterSettings";
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
