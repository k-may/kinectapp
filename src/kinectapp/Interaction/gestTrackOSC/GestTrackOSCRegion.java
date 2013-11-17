package kinectapp.Interaction.gestTrackOSC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.events.HandDetectedEvent;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PVector;

import static processing.core.PApplet.println;

import kinectapp.Interaction.Adapter;
import kinectapp.Interaction.HandData;
import kinectapp.Interaction.Region;

public class GestTrackOSCRegion extends Region<OscP5> {
	private static final int CAM_WIDTH = 640;
	private static final int CAM_HEIGHT = 480;
	private TrackingType _trackingType;

	public GestTrackOSCRegion(OscP5 source) {
		super(source);
		_adapter = new Adapter();

		_trackingType = TrackingType.Normalized;
		
		source.plug(this, "onHand1", _trackingType.toString() + "/hand0");
		//source.plug(this, "onHand2", _dataType + "/hand1");
		//source.plug(this, "onHand3", _dataType + "/hand2");
	}

	@Override
	public void runInteractions() {
		//println("run");
		_interactions = new ArrayList<InteractionStreamData>();

		if (_handData == null)
			return;

		for (HandData handData : _handData.values()) {

			PVector position = handData.getPosition();
			float mX = position.x;// / CAM_WIDTH;
			float mY = position.y;// / CAM_HEIGHT;
			float mZ = position.z;

			
			PVector tendency = handData.getTendency();
			float pressRatio = Math.min(Math.max(tendency.z / 20, 0), 1);

			println(position.z + " / " +pressRatio);
			InteractionTargetInfo info = _adapter.getInteractionInfoAtLocation(mX, mY, mZ, handData.get_id(), _type);

			Boolean isPressTarget = info.get_isPressTarget();

			if (isPressTarget) {
				//println("z: " + mZ);
				PVector attrV = new PVector(info.get_pressAttractionX(), info.get_pressAttractionY());
				// attrV.normalize();
				// attrV.mult(mZ);
				mX = mX + (attrV.x - mX) * pressRatio;
				mY = mY + (attrV.y - mY) * pressRatio;
			}

			Boolean isPressing = tendency.z > 20;

			InteractionStreamData data = new InteractionStreamData(mX, mY, mZ, handData.get_id(), _type, isPressing);

			data.set_isOverPressTarget(isPressTarget);

			_interactions.add(data);
		}

		// update users
		_adapter.handleStreamData(_interactions);
	}

	@Override
	public ArrayList getStream() {
		return _interactions;
	}

	public void onHand1(float x, float y, float z) {
		//println("hand1 : " + x + " : " + y + " : " + z);
		onHand(0, x, y, z);
	}

	public void onHand2(float x, float y, float z) {
		// println("hand1 : " + x + " : " + y + " : " + z);
		onHand(1, x, y, z);
	}

	public void onHand3(float x, float y, float z) {
		// println("hand1 : " + x + " : " + y + " : " + z);
		onHand(2, x, y, z);
	}

	private void onHand(int id, float x, float y, float z) {
		if (_trackingType == TrackingType.Normalized)
			getHand(id, x, y, z);
		else
			getHand(id, new PVector(x, y, z));
	}

	private void getHand(int id, float x, float y, float z) {
		getHand(id, new PVector(x * CAM_WIDTH, y * CAM_HEIGHT, z * 1000));
	}
}
