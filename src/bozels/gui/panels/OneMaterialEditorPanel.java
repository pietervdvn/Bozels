package bozels.gui.panels;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.actions.BooleanValueSelectAction;
import bozels.gui.basicComponents.AutoJLabel;
import bozels.gui.basicComponents.ColorButton;
import bozels.gui.basicComponents.coloringTextField.DoubleValueTextField;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.guiColorModel.GUIColorModel;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.physicsModel.material.Material;
import bozels.superModel.SuperModel;
import bozels.valueWrappers.Value;
import bozels.visualisatie.gameColorModel.GameColorModel;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class OneMaterialEditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final GUIColorModel colorSettings;
	
	public OneMaterialEditorPanel(final SuperModel supM, final Material mat, boolean showBreakable) {
		final ResourceTracker tracker = supM.getResourceModel();
		colorSettings = supM.getResourceModel().getGuiColorModel();
		
		// \\//\\//\\//\\ LABELS //\\//\\//\\//\\
		JLabel density = new AutoJLabel(tracker, LocaleConstant.DENSITY);
		JLabel restit = new AutoJLabel(tracker, LocaleConstant.RESTITUTION);
		JLabel friction = new AutoJLabel(tracker, LocaleConstant.FRICTION);
		JLabel color = new AutoJLabel(tracker, LocaleConstant.COLOR);
		
		JLabel empty = new JLabel();
		JLabel powerThrs = new AutoJLabel(tracker, LocaleConstant.POWER_THRESHOLD);
		JLabel strength = new AutoJLabel(tracker, LocaleConstant.STRENGTH);
		JLabel sleepingColor = new AutoJLabel(tracker, LocaleConstant.SLEEPING_COLOR);
		
		// \\//\\//\\//\\ COlor chooser //\\//\\//\\//\\
		GameColorModel cm = supM.getGameColorModel();
		LocaleConstant name = mat.getMaterialName();
		int key = mat.getColorKey();
		JButton colorPicker = new ColorButton(supM, name, null, cm.getColorValue(key));
		JButton sleepingColorPicker = new ColorButton(supM, name, null , cm.getSleepingColorValue(key));
		
		
		// \\//\\//\\//\\ FIELDS //\\//\\//\\//\\
		JTextField dens = createField(mat.getDensitValue());
		JTextField rest = createField(mat.getRestitutionValue());
		JTextField frict = createField(mat.getFrictionValue());
		JTextField powThr = createField(mat.getPowerThresholdValue());
		JTextField str = createField(mat.getStrengthValue());
		 
		// \\//\\//\\//\\ Checkbox //\\//\\//\\//\\
		BooleanValueSelectAction sw = new BooleanValueSelectAction(mat.getCanBreak(),
				LocaleConstant.BREAKABLE, tracker);
		sw.getSwitchesWith().add(powThr);
		sw.getSwitchesWith().add(str);
		sw.revalidate();
		JCheckBox breakable = new JCheckBox(sw);

		
		// \\//\\//\\//\\ LAYOUT //\\//\\//\\//\\
		GroupLayout l = new GroupLayout(this);
		 this.setLayout(l);
		 
		 
		 /*
		  * VERANTWOORDING:
		  * 
		  * Hier een if-else voor de layout is inderdaad lelijk.
		  * Ik heb echter gekozen om deze hier te gebruiken,
		  *  omdat op deze manier de layout van het linker- en rechterstuk dezelfde layout kunnen gebruiken.
		  *  
		  * Op deze manier zullen de layouts altijd mooi samenblijven, hoewel dit minder elegant is naar 
		  * klassenstructuur toe.
		  * 
		  */
		 if(showBreakable){
		l.setHorizontalGroup(l.createSequentialGroup()
				.addGroup(createPar(l, Alignment.TRAILING, 
						density,restit, friction, color))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(createPar(l, Alignment.LEADING, 
						dens, rest, frict, colorPicker))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(createPar(l, Alignment.TRAILING, 
						empty, powerThrs, strength, sleepingColor))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(createPar(l, Alignment.LEADING, 
						breakable, powThr, str, sleepingColorPicker))
				);
		
		l.setVerticalGroup(l.createSequentialGroup()
				.addGroup(createPar(l, Alignment.BASELINE, density, dens, empty, breakable))
				.addGroup(createPar(l, Alignment.BASELINE, restit, rest, powerThrs, powThr))
				.addGroup(createPar(l, Alignment.BASELINE, friction, frict, strength, str))
				.addGroup(createPar(l, Alignment.BASELINE, color, colorPicker, sleepingColor, sleepingColorPicker)
				)
				.addContainerGap()
				);
		 }else{
				l.setHorizontalGroup(l.createSequentialGroup()
						.addGroup(createPar(l, Alignment.TRAILING, 
								density,restit, friction, color))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(createPar(l, Alignment.LEADING, 
								dens, rest, frict, colorPicker))
						);
				
				l.setVerticalGroup(l.createSequentialGroup()
						.addGroup(createPar(l, Alignment.BASELINE, density, dens))
						.addGroup(createPar(l, Alignment.BASELINE, restit, rest))
						.addGroup(createPar(l, Alignment.BASELINE, friction, frict))
						.addGroup(createPar(l, Alignment.BASELINE, color, colorPicker))
						.addContainerGap());
			 
		 }
		 
	}
	
	private ParallelGroup createPar(GroupLayout l, Alignment al, JComponent... components){
		ParallelGroup group = l.createParallelGroup(al);
		for (JComponent jComponent : components) {
			group.addComponent(jComponent);
		}
		return group;
	}

	private DoubleValueTextField createField(Value<Double> val){
		return new DoubleValueTextField(colorSettings, val, 0, 10000, 10000);
	}



}
