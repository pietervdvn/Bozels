package bozels.levelModel.gameObjects;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.shapes.ShapeWrapper;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class DecorObject extends GameObject{

	public DecorObject(Level l, Material material, ShapeWrapper shape,
			Vec2 startPos, boolean dynamic, double angle) {
		super(l, material, shape, startPos, dynamic, angle, false, false);
	}
	
	public DecorObject(Level l, Material material, ShapeWrapper shape) {
		super(l, material, shape, false, false);
	}

}
