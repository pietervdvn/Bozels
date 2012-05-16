package bozels.superModel;

import java.util.ArrayList;
import java.util.List;

import bozels.EventSource;
import bozels.SafeList;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.levelModel.core.Level;
import bozels.levelModel.settingsModel.SettingsModel;
import bozels.physicsModel.material.Materials;
import bozels.simulationThread.SimulationThread;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;
import bozels.xml.PropertiesList;
import bozels.xml.XMLable;
import bozels.xml.levels.LevelDefinition;

/** The supermodel keeps track of all the other models (and the level) in the
 * application.
 * 
 * This class is built so that models can be swapped during runtime, however,
 * these changes won't apply immediately, and will often be totally ignored.
 * 
 * It is safer to get the model, and change the settings there
 * 
 * @author pieter
 * 
 */
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SuperModel extends EventSource<SuperModelListener> implements PropertiesList{

	/**
	 * This; an model to gather all the different models scattered around the project.
	 * As it is an model itself, it should be in the model
	 */
	private final SuperModel superModel = this;

	/**
	 * The level, where it all starts
	 */
	private Level level = null;
	/**
	 * An leveldefinition, how they come from XML
	 */
	private LevelDefinition levelDefinition = null;
	
	// \\//\\//\\//\\ LEVEL models //\\//\\//\\//\\

	
	/**
	 * The colors of the game, eg: 
	 * ice is blue, 
	 * bozels are red,
	 * sugar is sweet,
	 * and tastes good in coffee
	 */
	private GameColorModel gameColorModel = new GameColorModel();
	/**
	 * The gamesettings: settings about the timesteps, ...
	 */
	private SettingsModel gameSettingsModel = new SettingsModel();
	/**
	 * Settings about zoom, viewport, ...
	 */
	private PainterSettingsModel painterSettingsModel = new PainterSettingsModel();

	// \\//\\//\\//\\ GUI-models //\\//\\//\\//\\

	/**
	 * An class to centralize all resources and GUI-stuff
	 */
	private ResourceTracker resourceModel = new ResourceTracker(this);
	
	private final List<PropertiesList> properties = new ArrayList<PropertiesList>();
	{
		properties.add(gameColorModel);
		properties.add(gameSettingsModel);
		properties.add(painterSettingsModel);
		properties.add(resourceModel);
		properties.add(Materials.ACCELERATING_BOZEL);
	}
	
	public SuperModel getSuperModel() {
		return superModel;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		if(this.level == level){
			return;
		}
		this.level = level;
		SimulationThread st = SimulationThread.getTheSimulationThread();
		st.setLevel(level);
		
		throwLevelChanged(level);
	}
	
	public LevelDefinition getLevelDefinition() {
		return levelDefinition;
	}
	
	public void setLevelDefinition(LevelDefinition levelDefinition) {
		if(levelDefinition == this.levelDefinition){
			return;
		}
		this.levelDefinition = levelDefinition;
		throwLevelDefChanged(levelDefinition);
	}

	public GameColorModel getGameColorModel() {
		return gameColorModel;
	}

	public void setGameColorModel(GameColorModel gameColorModel) {
		if (this.gameColorModel == gameColorModel) {
			return;
		}
		this.gameColorModel = gameColorModel;
		throwModelChanged();
	}
	
	public ResourceTracker getResourceModel() {
		return resourceModel;
	}
	
	public void setResourceModel(ResourceTracker resourceModel) {
		if(this.resourceModel == resourceModel){
			return;
		}
		this.resourceModel = resourceModel;
		throwModelChanged();
	}
	
	public SettingsModel getGameSettingsModel() {
		return gameSettingsModel;
	}
	
	public void setGameSettingsModel(SettingsModel gameSettingsModel) {
		if(this.gameSettingsModel == gameSettingsModel){
			return;
		}
		this.gameSettingsModel = gameSettingsModel;
		throwModelChanged();
	}
	
	public PainterSettingsModel getPainterSettingsModel() {
		return painterSettingsModel;
	}
	
	public void setPainterSettingsModel(
			PainterSettingsModel painterSettingsModel) {
		if(this.painterSettingsModel == painterSettingsModel){
			return;
		}
		this.painterSettingsModel = painterSettingsModel;
		throwModelChanged();
	}
	
	private void throwLevelChanged(Level level) {
		SafeList<SuperModelListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().levelChanged(this, level);
			}
		}
	}
	
	private void throwLevelDefChanged(LevelDefinition levelDef) {
		SafeList<SuperModelListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().levelDefinitionChanged(this, levelDef);
			}
		}
	}

	private void throwModelChanged() {
		SafeList<SuperModelListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().modelChanged(this);
			}
		}
	}
	
	public void throwDoInit(){
		SafeList<SuperModelListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while (l.hasNext()) {
				l.next().doSlowInit(this);
			}
		}
	}

	@Override
	public String getName() {
		return "bozelSettings";
	}

	@Override
	public List<? extends PropertiesList> getProperties() {
		return properties;
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		return null;
	}

}
