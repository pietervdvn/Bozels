package bozels.gui.panels.stats;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.EventSource;
import bozels.gui.basicComponents.AutoJLabel;
import bozels.gui.basicComponents.coloringTextField.IntegerValueTextField;
import bozels.gui.mainWindow.MainWindow;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListener;
import bozels.levelModel.core.LevelListenerAdapter;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.valueWrappers.Value;
import bozels.xml.levels.LevelDefinition;

/**
 * Bozels
 * 
 * Door: Pieter Vander Vennet 1ste Bachelor Informatica Universiteit Gent
 * 
 */
public class StatisticsPanel extends JPanel implements SuperModelListener {
	private static final long serialVersionUID = 1L;

	private Level previousLevel;
	private final LevelListener levelListener;

	public StatisticsPanel(SuperModel supM, MainWindow window) {
		supM.addListener(this);
		ResourceTracker tracker = supM.getResourceModel();
		JTextField sttf = new SimulationThreadStatusTextfield(tracker);
		JLabel stl = new AutoJLabel(tracker,
				LocaleConstant.SIMULATION_THREAD_STATUS, "");

		JTextField autoPanel = new FPSTextField(window);
		JLabel fpsl = new AutoJLabel(tracker, LocaleConstant.FPS);

		JLabel tol = new AutoJLabel(tracker, LocaleConstant.TOTAL_OBJECTS);
		JLabel tbj = new AutoJLabel(tracker, LocaleConstant.TOTAL_BOZELS);
		JLabel ttj = new AutoJLabel(tracker, LocaleConstant.TOTAL_TARGETS);
		final JTextField totalObj = new JTextField();
		final JTextField totalBozels = new JTextField();
		final JTextField totalTargets = new JTextField();
		totalObj.setEditable(false);
		totalBozels.setEditable(false);
		totalTargets.setEditable(false);
		
		levelListener = new LevelListenerAdapter() {
			@Override
			public void onObjectsChanged(Level source) {
				totalObj.setText("" + source.getGameObjects().size());
			}

			@Override
			public void onNumberOfBozelsChanged(Level source) {
				totalBozels.setText("" + source.getNumberOfBozelsInQueue());
			}

			@Override
			public void onNumberOfTargetsChanged(Level source) {
				totalTargets.setText("" + source.getNumberOfRemainingTargets());
			}
		};

		
		JLabel esl = new AutoJLabel(tracker, LocaleConstant.EVENTSOURCES_MADE);
		JTextField eventSources = new IntegerValueTextField
				(supM.getResourceModel().getGuiColorModel(), 
						EventSource.getNumberofeventsources(), 0, Integer.MAX_VALUE);
		eventSources.setEditable(false);
		
		JLabel vwl = new AutoJLabel(tracker, LocaleConstant.VALUEWRAPPERS_MADE);
		JTextField valueWrappers = new IntegerValueTextField
				(supM.getResourceModel().getGuiColorModel(), 
						Value.getNumberOfValues(), 0, Integer.MAX_VALUE);
		valueWrappers.setEditable(false);
		
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);

		l.setHorizontalGroup(l
				.createSequentialGroup()
				.addGroup(
						createPar(l, Alignment.TRAILING, stl, fpsl, tol, tbj,
								ttj, esl, vwl))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(
						createPar(l, Alignment.LEADING, sttf, autoPanel,
								totalObj, totalBozels, totalTargets, eventSources, valueWrappers)));

		l.setVerticalGroup(l.createSequentialGroup()
				.addGroup(createPar(l, Alignment.BASELINE, stl, sttf))
				.addGroup(createPar(l, Alignment.BASELINE, fpsl, autoPanel))
				.addGroup(createPar(l, Alignment.BASELINE, tol, totalObj))
				.addGroup(createPar(l, Alignment.BASELINE, tbj, totalBozels))
				.addGroup(createPar(l, Alignment.BASELINE, ttj, totalTargets))
				.addGroup(createPar(l, Alignment.BASELINE, esl, eventSources))
				.addGroup(createPar(l, Alignment.BASELINE, vwl, valueWrappers))

				.addContainerGap());

	}

	private ParallelGroup createPar(GroupLayout l, Alignment al,
			JComponent... components) {
		ParallelGroup group = l.createParallelGroup(al);
		for (JComponent jComponent : components) {
			group.addComponent(jComponent);
		}
		return group;
	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
		if (previousLevel != null) {
			previousLevel.removeListener(levelListener);
		}
		newLevel.addListener(levelListener);
		previousLevel = newLevel;
		levelListener.onObjectsChanged(newLevel);
		levelListener.onNumberOfBozelsChanged(newLevel);
		levelListener.onNumberOfTargetsChanged(newLevel);

	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
	}

	@Override
	public void doSlowInit(SuperModel source) {
	}

}
