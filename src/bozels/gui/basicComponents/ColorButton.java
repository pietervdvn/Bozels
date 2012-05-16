package bozels.gui.basicComponents;

import java.awt.Color;

import javax.swing.JButton;

import bozels.gui.actions.ColorChooserAction;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.superModel.SuperModel;
import bozels.valueWrappers.Value;
import bozels.visualisatie.gameColorModel.GameColorModel;
import bozels.visualisatie.gameColorModel.GameColorModelListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ColorButton extends JButton implements GameColorModelListener,
		ResourceTrackerListener {
	private static final long serialVersionUID = -6679427643849371791L;

	private final LocaleConstant name;
	
	private final Value<Color> color;

	public ColorButton(final SuperModel supM, LocaleConstant colorChooserTitle,
			LocaleConstant buttonText, Value<Color> colorValue) {
		
		this.name = buttonText;
		this.color = colorValue;
		
		GameColorModel gameColorModel = supM.getGameColorModel();
		gameColorModel.addListener(this);
		onColorChanged(gameColorModel);

		ResourceTracker tracker = supM.getResourceModel();
		tracker.addListener(this);
		this.setAction(new ColorChooserAction(colorValue, colorChooserTitle,
				null, tracker, false));
		onLocaleChanged(tracker);
	}

	@Override
	public void onColorChanged(GameColorModel source) {
		this.setBackground(color.get());
		this.repaint();
	}

	@Override
	public void onLocaleChanged(ResourceTracker source) {
		if (name != null) {
			this.setText(source.getU(name));
		} else {
			this.setText("      ");
		}
	}

}
