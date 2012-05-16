package bozels.gui.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;

import bozels.gui.basicComponents.ColorDialog;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.valueWrappers.Value;

public class ColorChooserAction extends AutoAction{
	private static final long serialVersionUID = 7716957899445864095L;

	private final Value<Color> color;
	private final ResourceTracker tracker;
	private final LocaleConstant extraTitle;
	
	public ColorChooserAction(Value<Color> toEdit, LocaleConstant extraTitle, LocaleConstant name, ResourceTracker tracker, boolean useMnemomic) {
		super(name, tracker, true, useMnemomic);
		this.color = toEdit;
		this.tracker = tracker;
		this.extraTitle = extraTitle;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorDialog dialog = tracker.getActionHub().getColorDialog();
		Color c = dialog.showDialog(color.get(), extraTitle);
		if(c!=null){
			color.set(c);
		}
	}

}
