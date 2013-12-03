package application.interaction.soni;

import application.interaction.Adapter;
import application.interaction.KinectRegion;
import application.interaction.RegionType;
import processing.core.PVector;


import SimpleOpenNI.SimpleOpenNI;

import static processing.core.PApplet.println;

public class SONRegion extends KinectRegion<SimpleOpenNI> {

	public SONRegion(SimpleOpenNI source) {
		super(source);

		_adapter = new Adapter();
		// enable depthMap generation
		source.enableDepth();
		source.enableHand();
		source.startGesture(SimpleOpenNI.GESTURE_WAVE);

	}

	@Override
	public void runInteractions() {
		_source.update();
		super.runInteractions();
	}

	public void onCompletedGesture(int gestureType, PVector pos) {
		_source.startTrackingHand(pos);
	}

	public void onNewHand(int id, PVector pos) {
		PVector lastPos = new PVector();
		_source.convertRealWorldToProjective(pos, lastPos);
		getHand(id, lastPos);
	}

	public void onTrackedHand(int id, PVector pos) {
		PVector lastPos = new PVector();
		_source.convertRealWorldToProjective(pos, lastPos);
		getHand(id, lastPos);
	}

	public void onLostHand(int id) {
		if (_handData.containsKey(id))
			_handData.remove(id);
	}

	@Override
	public RegionType getType() {
		// TODO Auto-generated method stub
		return RegionType.SimpleOpenNI;
	}

}
