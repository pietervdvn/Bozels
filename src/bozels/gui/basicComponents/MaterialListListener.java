package bozels.gui.basicComponents;

import bozels.physicsModel.material.Material;

public interface MaterialListListener {

	void onMaterialChanged(MaterialList source, Material newMaterial);
	
}
