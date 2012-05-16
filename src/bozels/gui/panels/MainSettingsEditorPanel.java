package bozels.gui.panels;


import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import bozels.gui.mainWindow.MainWindow;
import bozels.gui.panels.stats.StatisticsPanel;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.physicsModel.material.Materials;
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
public class MainSettingsEditorPanel extends JPanel implements ResourceTrackerListener{
	private static final long serialVersionUID = 1L;
	
	private final JTabbedPane tabs;
	private final MultipleMaterialEditor materials;
	private final MultipleMaterialEditor bozels;
	private final MultipleMaterialEditor targets;
	
	
	private final LocaleConstant[] titles = {
			LocaleConstant.GENERAL,
			LocaleConstant.EXPLOSION_SETTINGS,
			LocaleConstant.MATERIALS,
			LocaleConstant.BOZELS,
			LocaleConstant.TARGETS,
			LocaleConstant.STATS,
	};
	
	public MainSettingsEditorPanel(SuperModel supM, MainWindow window) {
		supM.getResourceModel().addListener(this);
		
		JPanel buttons = new ButtonPanel(supM);
		tabs = new JTabbedPane();
		tabs.add(new GeneralSettingsEditorPanel(supM));
		tabs.add(new ExplosionSettingsPanel(supM));
		
		materials = 
		new MultipleMaterialEditor(supM, true, 
				Materials.SOLID_TRANSPARENT.getMaterial(),
				Materials.SOLID.getMaterial(),
				Materials.CONCRETE.getMaterial(),
				Materials.WOOD.getMaterial(),
				Materials.METAL.getMaterial(),
				Materials.ICE.getMaterial()
				);
		tabs.add(materials);
		
		bozels = new MultipleMaterialEditor(supM, false, 
				Materials.NORMAL_BOZEL.getMaterial(),
				Materials.SPLIT_BOZEL.getMaterial(),
				Materials.EXPLODING_BOZEL.getMaterial(),
				Materials.ACCELERATING_BOZEL.getMaterial()
				);
		tabs.add(bozels);
		
		targets = 
		new MultipleMaterialEditor(supM, true, 
				Materials.LITTLE_TARGET.getMaterial(),
				Materials.BIG_TARGET.getMaterial()
				);
		tabs.add(targets);
		
		tabs.add(new StatisticsPanel(supM, window));
		
		
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);
		
		l.linkSize(SwingConstants.VERTICAL, tabs, buttons);
		
		l.setHorizontalGroup(l.createSequentialGroup()
				.addComponent(buttons)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(tabs)
				);
		
		l.setVerticalGroup(l.createParallelGroup()
				.addComponent(buttons)
				.addComponent(tabs)
				);
		
		onLocaleChanged(supM.getResourceModel());
		
	}

	@Override
	public void onLocaleChanged(ResourceTracker source) {
		for (int i = 0; i < titles.length; i++) {
			tabs.setTitleAt(i, source.getU(titles[i]));
		}
		this.revalidate();
	}

}
