package bozels.gui.basicComponents;


import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bozels.EventSource;
import bozels.SafeList;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.physicsModel.material.Material;
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
public class MaterialList extends JList<Material> implements ResourceTrackerListener, ListSelectionListener, GameColorModelListener{
	private static final long serialVersionUID = 1L;

	private final ResourceTracker tracker;
	private final GameColorModel cm;
	
	private Material selectedMaterial;
	private final EventSource<MaterialListListener> eventSource = new EventSource<MaterialListListener>();
	
	private final int defaultIconSize = (int) new JLabel("hi!").getPreferredSize().getHeight();
	
	public MaterialList(GameColorModel cm, ResourceTracker tracker, Material... materials) {
		super(materials);
		selectedMaterial = materials[0];
		this.setSelectedIndex(0);
		this.cm = cm;
		this.tracker = tracker;
		tracker.addListener(this);
		cm.addListener(this);
		this.setCellRenderer(new MaterialCellRenderer());
		this.addListSelectionListener(this);
		this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class MaterialCellRenderer extends DefaultListCellRenderer{
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Material mat = (Material) value;
			Component comp = super.getListCellRendererComponent(list, 
					tracker.getU(mat.getMaterialName())+"   ", index, isSelected,
					cellHasFocus);
			
			if(!(comp instanceof JLabel)){
				return comp;
			}
			JLabel label = (JLabel) comp;
			label.setIcon(new AutoColorIcon(defaultIconSize, mat.getColorKey(), cm));
			
			return label;
		}
	}

	@Override
	public void onLocaleChanged(ResourceTracker source) {
		repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(this.getSelectedValue() != selectedMaterial){
			selectedMaterial = this.getSelectedValue();
			throwEvent();
		}
	}
	
	public void addListener(MaterialListListener l){
		eventSource.addListener(l);
	}
	
	public void removeListener(MaterialListListener l){
		eventSource.removeListener(l);
	}
	
	private void throwEvent() {
		SafeList<MaterialListListener> l = eventSource.getListeners();
		synchronized (l) {
			l.resetCounter();
			while(l.hasNext()){
				l.next().onMaterialChanged(this, selectedMaterial);
			}
		}
		
	}

	@Override
	public void onColorChanged(GameColorModel source) {
		repaint();
	}

}
