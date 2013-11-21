package kinectapp.Interaction.gestTrackOSC;

import java.util.ArrayList;

import FrameWork.Interaction.InteractionStreamData;

import oscP5.OscP5;
import processing.core.PVector;

import static processing.core.PApplet.println;

import kinectapp.Interaction.Adapter;
import kinectapp.Interaction.HandData;
import kinectapp.Interaction.KinectRegion;


public class GestTrackOSCRegion extends KinectRegion<OscP5> {

	private GestTrackingType _trackingType;

	public GestTrackOSCRegion(OscP5 source) {
		super(source);
		_adapter = new Adapter();
		_trackingType = GestTrackingType.Normalized;

		source.plug(this, "onHand1", _trackingType.toString() + "/hand0");
		source.plug(this, "onHand2", _trackingType.toString() + "/hand1");
		source.plug(this, "onHand3", _trackingType.toString() + "/hand2");
	}

	public void onHand1(float x, float y, float z) {
		//println("hand1 : " + x + " : " + y + " : " + z);
		onHand(0, x, y, z);
	}

	public void onHand2(float x, float y, float z) {
		//println("hand1 : " + x + " : " + y + " : " + z);
		// onHand(1, x, y, z);
	}

	public void onHand3(float x, float y, float z) {
		//println("hand1 : " + x + " : " + y + " : " + z);
		// onHand(2, x, y, z);
	}

	private void onHand(int id, float x, float y, float z) {
		if (_trackingType == GestTrackingType.Normalized)
			getHand(id, x, y, z);
		else
			getHand(id, new PVector(x, y, z));
	}

	private void getHand(int id, float x, float y, float z) {
		getHand(id, new PVector(x * CAM_WIDTH, y * CAM_HEIGHT, z * 1000));
	}

	

}
