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
public enum LocaleConstant {
	
	/*
	 * DO NOT CHANGE NAME DEFENITIONS HERE!
	 * Translations should happen in the locale, via the resourcetracker.
	 * 
	 * These values should be used AS KEYS for the locale.
	 */

	/*
	 * Key policy: everyting in lowercase
	 */
	HELLO,
	WORLD,
	WINDOW_TITLE,
	
	WON_MESSAGE, 
	LOST_MESSAGE,
	GAME_OVER_TITLE,
	
	CONFIRM_EXIT_QUESTION,
	CONFIRM_EXIT_TITLE,
	
	COLOR_PICKER_TITLE_FOR,
	SLEEPING_COLOR,

	ERROR,
	INVALID_FILE_MESSAGE,
	FILE_ERROR,
	NO_LEVEL, 
	
	LOAD_SETTINGS,
	SAVE_SETTINGS,
	RESET_SETTINGS,
	INVALID_SETTINGS_FILE_MESSAGE,
	FILE_SAVE_ERROR, 
	
	// \\//\\//\\//\\ About-window //\\//\\//\\//\\

	ABOUT_MENU_ITEM,
	ABOUT_TITLE,
	
	ABOUT_PROGRAM_TITLE,
	ABOUT_AUTHOR,
	ABOUT_TEXT,
	
	// \\//\\//\\//\\ Menu bar //\\//\\//\\//\\

	FILE,
	GAME,
	EXTRA,
	LANGUAGES,
	LOOK_AND_FEEL,
	
	BACKGROUND, 
	BACKGROUND_IMAGE,
	BACKGROUND_COLOR_MENU_ITEM, 
	BACKGROUND_COLOR,

	
	OPEN,
	QUIT,
	OK, 
	DOTS, 
	
	STATIC_PAUSE,
	PAUSE,
	UNPAUSE,
	
	SOUND, 
	MUTE_ALL,
	MUTE_BACKGROUND,
	MUTE_FOREGROUND,
	
	
	RESTART,
	ACTIVE, 
	PAUSED,
	SLEEPING,
	
	// \\//\\//\\//\\ ALGEMEEN/General //\\//\\//\\//\\
	GENERAL,

	GRAVITY,
	TIMESTEP_IN_S,
	TIMESTEP_FOR_WORLD,
	VEL_ITER_PER_CYCLE,
	SPEED_ITER_PER_CYCLE,
	LAUNCH_FORCE,
	
	SHOW_MASS_POINT_ROTATION,
	SHOW_SPEED,
	SHOW_SLEEPING,
	SHOW_RAYS,
	SHOW_IMAGES,
	
	EXPLOSION_SETTINGS,
	EXPLOSION_FORCE,
	EXPLOSION_RANGE,
	EXPLOSION_NUMBER_OF_RAYS,

	STATS,
	SIMULATION_THREAD_STATUS,
	FPS, 
	TOTAL_OBJECTS,
	TOTAL_TARGETS,
	TOTAL_BOZELS,
	EVENTSOURCES_MADE,
	VALUEWRAPPERS_MADE,
	
	// \\//\\//\\//\\ MATERIALS:pane //\\//\\//\\//\\

	MATERIALS,
	BOZELS,
	TARGETS,
	
	DENSITY, 
	RESTITUTION,
	FRICTION,
	COLOR,
	POWER_THRESHOLD,
	STRENGTH,
	BREAKABLE, 
	
	// \\//\\//\\//\\ Material names //\\//\\//\\//\\

	ACCELERATING_BOZEL,
	EXPLODING_BOZEL,
	NORMAL_BOZEL,
	SPLIT_BOZEL,
	LITTLE_TARGET,
	BIG_TARGET,
	CONCRETE,
	ICE,
	METAL,
	SOLID,
	SOLID_TRANSPARENT,
	WOOD, 
	;
	
	private LocaleConstant(){
		LocaleConstantList.getList().add(this);
	}
	
	public static LocaleConstant parse(String name){
		for(LocaleConstant cons : LocaleConstantList.getList()){
			if(cons.toString().equals(name)){
				return cons;
			}
		}
		return null;
	}
	
	public static List<LocaleConstant> getAllConstants(){
		return LocaleConstantList.getList();
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
	private static class LocaleConstantList {
		
		private static final List<LocaleConstant> STATIC_LIST = new ArrayList<LocaleConstant>();

		public static List<LocaleConstant> getList(){
			return STATIC_LIST;
		}
		
	}

}
