package bozels.gui.panels;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.actions.BooleanValueSelectAction;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.actionHub.ActionHub;
import bozels.gui.resourceModel.actionHub.ActionKey;
import bozels.levelModel.core.Level;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.xml.levels.LevelDefinition;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ButtonPanel extends JPanel implements SuperModelListener {
	private static final long serialVersionUID = 1L;

	private final ActionHub hub;
	private final JButton restart;

	public ButtonPanel(SuperModel supM) {
		ResourceTracker tracker = supM.getResourceModel();
		supM.addListener(this);
		hub = tracker.getActionHub();
		JToggleButton pause = new JToggleButton(hub.getAction(ActionKey.PAUSE));
		restart = new JButton(hub.getAction(ActionKey.OPEN_FILE));
		
		Action a = new BooleanValueSelectAction(tracker.getMuteAll(), ResourceTracker.MUTED_VOLUME, ResourceTracker.FULL_VOLUME,
				32, tracker);
			
		 JButton mute = new JButton(a);
		
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);
		l.linkSize(pause, restart);
		l.setHorizontalGroup(l
				.createParallelGroup(Alignment.CENTER)
				.addComponent(pause)
				.addComponent(restart)
				.addGroup(Alignment.TRAILING, l.createSequentialGroup()
						.addComponent(mute)
						)
				);
		l.setVerticalGroup(l.createSequentialGroup()
				
				.addComponent(pause)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(restart)
				.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(mute)
						
				);

	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
		if (newLevelDef != null
				&& restart.getAction().equals(
						hub.getAction(ActionKey.OPEN_FILE))) {
			restart.setAction(hub.getAction(ActionKey.RESTART));
		}
	}

	@Override
	public void doSlowInit(SuperModel source) {
	}

}
