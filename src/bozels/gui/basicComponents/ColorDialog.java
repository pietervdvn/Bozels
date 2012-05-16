package bozels.gui.basicComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.actions.AutoAction;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ColorDialog extends JDialog implements ResourceTrackerListener{
	private static final long serialVersionUID = -2939417657394695818L;

	private final JColorChooser chooser;
	
	private final ResourceTracker tracker;
	private final LocaleConstant title;
	private LocaleConstant extraTitle = LocaleConstant.SOLID;
	
	/**
	 * Little hack to return null if close button is pressed
	 * This value will be set on "ok"-button, and returned with showDialog
	 * It is reset to null with showDialog
	 */
	private Color returnColor = null;
	
	public ColorDialog(JFrame root, ResourceTracker tracker, LocaleConstant title, LocaleConstant button) {
		super(root, true);
		
		this.title = title;
		this.tracker = tracker;
		tracker.addListener(this);
		chooser = new JColorChooser();
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		JButton ok = new JButton(new AutoAction(button, tracker, false) {
			private static final long serialVersionUID = -524489935461337644L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				returnColor = chooser.getColor();
				setVisible(false);
			}
		});
		
		GroupLayout l = new GroupLayout(this.getRootPane());
		this.getRootPane().setLayout(l);
		
		l.setHorizontalGroup(l.createParallelGroup(Alignment.CENTER)
				.addComponent(chooser)
				.addComponent(ok)
				);
		
		l.setVerticalGroup(l.createSequentialGroup()
				.addComponent(chooser)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(ok)
				);
		
		this.pack();
	}
	
	public Color showDialog(Color initColor, LocaleConstant extraTitle){
		chooser.setColor(initColor);
		this.extraTitle = extraTitle;
		onLocaleChanged(tracker);
		returnColor = null;
		this.setVisible(true); //Blocks, because modal
		return returnColor; // will be null if exited with close button, and a color if ok is pressed
	}



	@Override
	public void onLocaleChanged(ResourceTracker source) {
		this.setTitle(source.getU(title)+" "+source.get(extraTitle));
	}

}
