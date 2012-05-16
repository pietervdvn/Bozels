package bozels.gui.panels;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.actions.BooleanValueSelectAction;
import bozels.gui.basicComponents.AutoJLabel;
import bozels.gui.basicComponents.coloringTextField.DoubleValueTextField;
import bozels.gui.basicComponents.coloringTextField.IntegerValueTextField;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.guiColorModel.GUIColorModel;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.settingsModel.SettingsModel;
import bozels.superModel.SuperModel;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class GeneralSettingsEditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public GeneralSettingsEditorPanel(final SuperModel superModel) {
		
		final ResourceTracker tracker = superModel.getResourceModel();
		final SettingsModel sets = superModel.getGameSettingsModel();
		final GUIColorModel cm = tracker.getGuiColorModel();
		final PainterSettingsModel psm = superModel.getPainterSettingsModel();
		
		// \\//\\//\\//\\ INIT LABELS //\\//\\//\\//\\

		
		JLabel gravL = new AutoJLabel(tracker, LocaleConstant.GRAVITY);
		JLabel timeStepL = new AutoJLabel(tracker, LocaleConstant.TIMESTEP_IN_S);
		JLabel worldTimeStepL = new AutoJLabel(tracker, LocaleConstant.TIMESTEP_FOR_WORLD);
		JLabel velStepL = new AutoJLabel(tracker, LocaleConstant.VEL_ITER_PER_CYCLE);
		JLabel posStepL = new AutoJLabel(tracker, LocaleConstant.SPEED_ITER_PER_CYCLE);
		JLabel launchForceL = new AutoJLabel(tracker, LocaleConstant.LAUNCH_FORCE);
		
		// \\//\\//\\//\\ INIT TEXTFIELDS //\\//\\//\\//\\

		
		
		JTextField grav = new DoubleValueTextField(cm, sets.getGravityYValue(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 100);
		JTextField timeStep = new IntegerValueTextField(cm, sets.getSleepDtValue(), 0, 1000, 1000);
		JTextField worldTimeStep = new DoubleValueTextField(cm, sets.getWorldDtValue(), 0, 1000, 100000);
		JTextField posIt = new IntegerValueTextField(cm, sets.getPosIterationValue(), 0, 100);
		JTextField velIt = new IntegerValueTextField(cm, sets.getVelIterationsValue(), 1, 100);
		JTextField launchForce = new DoubleValueTextField(cm, sets.getKatapultForceValue(), 0.0, Double.POSITIVE_INFINITY, 1);
		
		
		// \\//\\//\\//\\ INIT CHECKBOXES //\\//\\//\\//\\

		
		
		JCheckBox showCenter = new JCheckBox
				(new BooleanValueSelectAction(psm.getDrawGravityPoint(),
						LocaleConstant.SHOW_MASS_POINT_ROTATION, tracker));
		
		JCheckBox showSpeed = new JCheckBox(new BooleanValueSelectAction(psm.getDrawSpeeds(), 
				LocaleConstant.SHOW_SPEED, tracker));

		JCheckBox showSleepingObj = new JCheckBox(
				new BooleanValueSelectAction(superModel.getGameColorModel().getShowSleepingColor(), 
						LocaleConstant.SHOW_SLEEPING, tracker));
		
		JCheckBox showRays = new JCheckBox(
				new BooleanValueSelectAction(psm.getDrawExplosions(), 
						LocaleConstant.SHOW_RAYS, tracker));
		
		JCheckBox showImages = new JCheckBox(
				new BooleanValueSelectAction(psm.getDrawsImages(),
						LocaleConstant.SHOW_IMAGES, tracker));
		
		
		// \\//\\//\\//\\ INIT LAYOUT //\\//\\//\\//\\
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);
		l.setHorizontalGroup(l.createSequentialGroup()
				.addContainerGap()
				.addGroup(createPar(l, Alignment.TRAILING, gravL, timeStepL, worldTimeStepL, velStepL, posStepL, launchForceL))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(createPar(l, Alignment.LEADING, grav, timeStep, worldTimeStep, posIt, velIt, launchForce))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(createPar(l, Alignment.LEADING, showCenter, showSpeed, showSleepingObj, showRays, showImages))
				.addContainerGap());
		
		l.setVerticalGroup(l.createSequentialGroup()
				.addContainerGap()
				.addGroup(createPar(l, gravL, grav, showCenter))
				.addGroup(createPar(l, timeStepL, timeStep, showSpeed))
				.addGroup(createPar(l, worldTimeStepL, worldTimeStep, showSleepingObj))
				.addGroup(createPar(l, velStepL, velIt, showRays))
				
				.addGroup(createPar(l, posStepL, posIt, showImages))
				.addGroup(createPar(l, launchForceL, launchForce))
				.addContainerGap());
		
	}
	
	private ParallelGroup createPar(GroupLayout l, JComponent... components){
		return createPar(l, Alignment.BASELINE, components);
	}
		
	
	private ParallelGroup createPar(GroupLayout l, Alignment al, JComponent... components){
		ParallelGroup group = l.createParallelGroup(al);
		for (JComponent jComponent : components) {
			group.addComponent(jComponent);
		}
		return group;
	}

}
