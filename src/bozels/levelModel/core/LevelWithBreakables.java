package bozels.levelModel.core;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.settingsModel.SettingsModel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class LevelWithBreakables extends AbstractLevel implements ContactListener{
	
	
	public LevelWithBreakables(SettingsModel settings) {
		super(settings);
	}

	@Override
	public void beginContact(Contact contact) {}

	@Override
	public void endContact(Contact contact) {}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		GameObject obj1 = (GameObject) contact.getFixtureA().getBody().m_userData;
		GameObject obj2 = (GameObject) contact.getFixtureB().getBody().m_userData;
		
		double impact = impulse.normalImpulses[0];
		if(impulse.normalImpulses[1]>0){
			impact+=impulse.normalImpulses[1];
			impact/=2;
		}
		
		impact*=getSettingsModel().getVelIterations()-getSettingsModel().getPosIterations();
		impact/=25;
		if((obj1.isBozel() && obj2.isTarget())){
			remove(obj2);
		}
		if((obj2.isBozel() && obj1.isTarget())){
			remove(obj1);
		}
		
		if(!obj1.onImpact(impact)){
			remove(obj1);
		}
		if(!obj2.onImpact(impact)){
			remove(obj2);
		}
		throwObjectContact(obj1, obj2, (int) impact);
	}
	

}
