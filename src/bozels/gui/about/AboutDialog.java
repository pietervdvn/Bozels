package bozels.gui.about;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.actions.AutoAction;
import bozels.gui.basicComponents.AutoJLabel;
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
public class AboutDialog extends JDialog implements ResourceTrackerListener {
	private static final long serialVersionUID = 1L;

	private final JPanel text = new JPanel();

	public AboutDialog(ResourceTracker tracker) {
		super(tracker.getWindow(), tracker.getU(LocaleConstant.ABOUT_TITLE),
				true);

		JPanel info = new JPanel();
		JLabel title = new AutoJLabel(tracker,
				LocaleConstant.ABOUT_PROGRAM_TITLE, "");
		Font normalFont = title.getFont();
		Font titleFont = normalFont.deriveFont(normalFont.getSize()+2).deriveFont(Font.BOLD);
		title.setFont(titleFont);
		JLabel by = new AutoJLabel(tracker, LocaleConstant.ABOUT_AUTHOR, "");

		GroupLayout l = new GroupLayout(info);
		info.setLayout(l);

		tracker.addListener(this);
		onLocaleChanged(tracker);
		
		l.setHorizontalGroup(l
				.createSequentialGroup()
				.addContainerGap()
				.addGroup(
						l.createParallelGroup()
								.addComponent(title)
								.addGroup(
										l.createSequentialGroup()
												.addPreferredGap(
														ComponentPlacement.RELATED,
														25, 25)
												.addComponent(by))
								.addComponent(text)).addContainerGap());

		l.setVerticalGroup(l
				.createSequentialGroup()
				.addContainerGap()
				.addComponent(title)
				.addComponent(by)
				.addGap(45)
				.addPreferredGap(ComponentPlacement.UNRELATED,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(text).addContainerGap());

		JButton ok = new JButton(new AutoAction(LocaleConstant.OK, tracker,
				false) {
			private static final long serialVersionUID = 3471537361540760560L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		GroupLayout lay = new GroupLayout(this.getRootPane());
		this.getRootPane().setLayout(lay);
		lay.setHorizontalGroup(lay
				.createSequentialGroup()
				.addContainerGap()
				.addGroup(
						lay.createParallelGroup()
								.addComponent(info)
								.addGroup(
										lay.createSequentialGroup()
												.addPreferredGap(
														ComponentPlacement.UNRELATED,
														GroupLayout.PREFERRED_SIZE,
														Short.MAX_VALUE)
												.addComponent(ok)))
				.addContainerGap());

		lay.setVerticalGroup(lay.createSequentialGroup().addContainerGap()
				.addComponent(info)
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(ok)
				.addContainerGap());

		this.pack();
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}

	public void onLocaleChanged(ResourceTracker tracker) {
		text.removeAll();
		String[] texts = tracker.getU(LocaleConstant.ABOUT_TEXT).split("\n");
		text.setLayout(new GridLayout(texts.length, 1));
		for (int i = 0; i < texts.length; i++) {
			text.add(new JLabel(texts[i]));
		}
	}

}
