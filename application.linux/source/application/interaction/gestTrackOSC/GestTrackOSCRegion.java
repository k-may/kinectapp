package application.interaction.gestTrackOSC;

import java.util.ArrayList;

import framework.interaction.InteractionStreamData;

import application.interaction.Adapter;
import application.interaction.HandData;
import application.interaction.KinectRegion;
import application.interaction.RegionType;


import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PVector;

import static processing.core.PApplet.println;



public class GestTrackOSCRegion extends KinectRegion<OscP5> implements OscEventListener {

	private GestTrackingType _trackingType;

	public GestTrackOSCRegion(OscP5 source) {
		super(source);
		_adapter = new Adapter();
		_trackingType = GestTrackingType.Normalized;

		source.addListener(this);
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
		onHand(1, x, y, z);
	}

	public void onHand3(float x, float y, float z) {
		//println("hand1 : " + x + " : " + y + " : " + z);
		onHand(2, x, y, z);
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

	@Override
	public void oscEvent(OscMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oscStatus(OscStatus arg0) {
		// TODO Auto-generated method stub
		println("status : " + arg0);
	}

	@Override
	public RegionType getType() {
		// TODO Auto-generated method stub
		return RegionType.GestTrackOSC;
	}

	

}
