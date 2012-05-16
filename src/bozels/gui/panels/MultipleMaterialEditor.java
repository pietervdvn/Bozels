package bozels.gui.panels;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import bozels.gui.basicComponents.MaterialList;
import bozels.gui.basicComponents.MaterialListListener;
import bozels.physicsModel.material.Material;
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
public class MultipleMaterialEditor extends JPanel implements MaterialListListener{
	private static final long serialVersionUID = 1L;

	private final SuperModel supM;
	private final boolean showBreakable;
	
	private final MaterialList list;
	private OneMaterialEditorPanel mep;
	
	public MultipleMaterialEditor(SuperModel supM, boolean showBreakable, Material... materials) {
		this.supM = supM;
		this.showBreakable = showBreakable;
		list = new MaterialList(supM.getGameColorModel(), supM.getResourceModel(), materials);
		list.addListener(this);
		onMaterialChanged(list, list.getSelectedValue());
	}
	
	private void createLay(){
		GroupLayout l = new GroupLayout(this);
		this.setLayout(l);
		
		l.setHorizontalGroup(l.createSequentialGroup()
				.addContainerGap()
				.addComponent(list)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(mep)
				.addContainerGap());

		l.setVerticalGroup(
				l.createSequentialGroup()
				.addContainerGap()
				.addGroup(l.createParallelGroup()
				.addComponent(list, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(mep))
				.addContainerGap());

	}
	
	public MaterialList getList() {
		return list;
	}

	@Override
	public void onMaterialChanged(MaterialList source, Material newMaterial) {
		if(mep != null){
			this.remove(mep);
		}
		mep = new OneMaterialEditorPanel(supM, newMaterial, showBreakable);
		createLay();
	}
	
}
