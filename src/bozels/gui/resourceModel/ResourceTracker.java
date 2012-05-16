package bozels.gui.resourceModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import bozels.gui.mainWindow.MainWindow;
import bozels.gui.resourceModel.actionHub.ActionHub;
import bozels.gui.resourceModel.guiColorModel.GUIColorModel;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.gui.sound.SoundsPool;
import bozels.superModel.SuperModel;
import bozels.valueWrappers.BooleanValue;
import bozels.valueWrappers.StringValue;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;
import bozels.xml.PropertiesList;
import bozels.xml.XMLable;

/**
 * This class is designed to keep track of external resources: locale strings and files
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
public class ResourceTracker extends ResourceTrackerEventSource implements PropertiesList, ValueListener<String>{
	
	public static final String RESOURCES_BASE="bozels/resources/";
	
	public static final String LOCALE_BASE = "bozels";
	public static final String IMAGE_BASE = "images/";
	/**
	 * These are string constants, to load these standard images with the getImage method
	 */
	public static final String MUTED_VOLUME = "Vol0.png";
	public static final String FULL_VOLUME = "Vol3.png";
	
	public static final String BACKGROUND_UPPER = "Background_up.png";
	public static final String BACKGROUND_DOWN = "Background_down.png";
	
	public static final String LEVELS = "levels";
	
	public static final String DEFAULT_SETTINGS = "DefaultSettings.xml";
	
	private final static List<String> SUPPORTED_LANGUAGES = new ArrayList<String>();
	static{
		SUPPORTED_LANGUAGES.add("en");
		SUPPORTED_LANGUAGES.add("nl");
	}
	
	private final StringValue language = new StringValue(SUPPORTED_LANGUAGES.get(1), "language");
	private Locale locale = new Locale(language.get());
	private ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES_BASE+LOCALE_BASE, locale);
	
	// \\//\\//\\//\\ RESOURCE OBJECTS //\\//\\//\\//\\

	
	/**
	 * The main window
	 */
	private MainWindow window;
	
	/**
	 * An class to centralize all the actions
	 */
	private final ActionHub actionHub;
	
	/**
	 * An colormodel for the components. eg: unsaved should be yellow
	 */
	private final GUIColorModel guiColorModel = new GUIColorModel();

	
	/**
	 * The soundpool
	 */
	
	private final SoundsPool soundPool;
	private BooleanValue muteAll = new BooleanValue(false, "muteAll");
	
	public ResourceTracker(SuperModel supM) {
		setSource(this);
		actionHub = new ActionHub(supM, this);
		soundPool = new SoundsPool(supM, muteAll);
		language.addListener(this);
	}
	
	public BooleanValue getMuteAll(){
		return muteAll;
	}
	
	public MainWindow getWindow() {
		return window;
	}

	public void setWindow(MainWindow window) {
		if(this.window == window){
			return;
		}
		this.window = window;
	}
	
	public ActionHub getActionHub() {
		return actionHub;
	}

	public GUIColorModel getGuiColorModel() {
		return guiColorModel;
	}

	public SoundsPool getSoundPool() {
		return soundPool;
	}
	
	// \\//\\//\\//\\ Loading from images //\\//\\//\\//\\

	
	public ImageIcon getIcon(String key, int maxHeight){
		ImageIcon icon = getIcon(key);
			icon = new ImageIcon(
				icon.getImage().getScaledInstance(maxHeight, -1, java.awt.Image.SCALE_SMOOTH)
				);
		return icon;
	}
	
	public ImageIcon getIcon(String key){
		ImageIcon icon = new ImageIcon(getResource(IMAGE_BASE+key));
		return icon;
	}
	
	public URL getResource(String name){
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
		URL url = ResourceTracker.class.getResource("/"+RESOURCES_BASE+name);
		return url;
	}
	
	// \\//\\//\\//\\ LOCALE STUFF //\\//\\//\\//\\

	
	public void setLocale(Locale newLocale){
		if(this.locale.getLanguage().equals(newLocale.getLanguage())){
			return;
		}
		this.locale = newLocale;
		language.set(newLocale.getLanguage());
		bundle = ResourceBundle.getBundle(RESOURCES_BASE+LOCALE_BASE, locale);
		JComponent.setDefaultLocale(locale);
		throwLocaleChanged();
	}
	
	public Locale getLocale(){
		return locale;
	}
	
	/**
	 * Same as get, but with first char as uppercase
	 * @param key
	 * @return
	 */
	public String getU(String key){
		return firstToUppercase(get(key));
	}
	
	public String getU(LocaleConstant key){
		return getU(key.toString());
	}
	
	public String get(LocaleConstant key){
		return get(key.toString());
	}
	
	public String get(String key){
		if(key.equals("")){
			return key;
		}
		key = key.toLowerCase();
		if(bundle.containsKey(key)){
			return bundle.getString(key);
		}
		
		if(SUPPORTED_LANGUAGES.contains(key.toLowerCase())){
			return (new Locale(key)).getDisplayLanguage(locale);
		}
		
		try{
			Integer.parseInt(key);
			return key;
		}catch(Exception e){
		}
		
		
		System.out.println("WARNING: missing key "+key+" for locale "+locale.getLanguage());
		return key;
	}
	
	public String firstToUppercase(String toUppercaseFirstChar){
		if(toUppercaseFirstChar == null || toUppercaseFirstChar.length()<1){
			return toUppercaseFirstChar;
		}
		toUppercaseFirstChar = toUppercaseFirstChar.trim();
		toUppercaseFirstChar = (toUppercaseFirstChar.charAt(0)+"").toUpperCase()+toUppercaseFirstChar.substring(1);
		return toUppercaseFirstChar;
	}
	
	public List<String> getSupportedLanguages(){
		return SUPPORTED_LANGUAGES;
	}



	public void checkLocales(){
		for(String lang : SUPPORTED_LANGUAGES){
			checkLocale(new Locale(lang));
		}
	}

	public void checkLocale(Locale locale){
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCES_BASE+LOCALE_BASE, locale);
		for(LocaleConstant name : LocaleConstant.getAllConstants()){
			if(!bundle.containsKey(name.toString().toLowerCase())){
				System.out.println("Localecheck: key "+name.toString().toLowerCase()
						+" is missing for language "+locale.getLanguage()); 
			}
		}
	}

	@Override
	public String getName() {
		return "resourceSettings";
	}
	
	@Override
	public List<? extends PropertiesList> getProperties() {
		List<PropertiesList> list = new ArrayList<PropertiesList>();
		list.add(guiColorModel);
		return list;
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		List<XMLable> list = new ArrayList<XMLable>();
		list.add(language);
		return list;
	}

	@Override
	public void onValueChanged(Value<String> source, String newValue) {
		setLocale(new Locale(newValue));
	}
	
}
