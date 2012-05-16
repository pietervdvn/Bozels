package bozels.gui.panels;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.basicComponents.AutoJLabel;
import bozels.gui.basicComponents.coloringTextField.DoubleValueTextField;
import bozels.gui.basicComponents.coloringTextField.IntegerValueTextField;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.guiColorModel.GUIColorModel;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.physicsModel.explosions.ExplosionSettingsModel;
import bozels.superModel.SuperModel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ExplosionSettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ExplosionSettingsPanel(SuperModel superModel) {
		ResourceTracker tracker = superModel.getResourceModel();

		JLabel force = new AutoJLabel(tracker, LocaleConstant.EXPLOSION_FORCE);
		JLabel range = new AutoJLabel(tracker, LocaleConstant.EXPLOSION_RANGE);
		JLabel rays = new AutoJLabel(tracker,
				LocaleConstant.EXPLOSION_NUMBER_OF_RAYS);

		ExplosionSettingsModel exp = superModel.getGameSettingsModel()
				.getExpSettings();
		GUIColorModel colorSettings = superModel.getResourceModel()
				.getGuiColorModel();

		JTextField forceField = new DoubleValueTextField(colorSettings,
				exp.getMaxForceValue(), -1000, 1000, 100);

		JTextField rangeField = new DoubleValueTextField(colorSettings,
				exp.getRangeValue(), 0, 10000, 100);

		JTextField raysField = new IntegerValueTextField(colorSettings,
				exp.getNumberOfRaysValue(), 1, 1440);
		
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);
		
		l.setHorizontalGroup(l.createSequentialGroup()
				.addContainerGap()
				.addGroup(createPar(l, Alignment.TRAILING, force, range, rays))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(createPar(l, Alignment.LEADING, forceField, rangeField, raysField))
				.addContainerGap()
				);
		
		l.setVerticalGroup(l.createSequentialGroup()
				.addContainerGap()
				.addGroup(createPar(l, Alignment.BASELINE, force, forceField))
				.addGroup(createPar(l, Alignment.BASELINE, range, rangeField))
				.addGroup(createPar(l, Alignment.BASELINE, rays, raysField))
				.addContainerGap()
				);

	}
	
	private ParallelGroup createPar(GroupLayout l, Alignment al, JComponent... components){
		ParallelGroup group = l.createParallelGroup(al);
		for (JComponent jComponent : components) {
			group.addComponent(jComponent);
		}
		return group;
	}

}
