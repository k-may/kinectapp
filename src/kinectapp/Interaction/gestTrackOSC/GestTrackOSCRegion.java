package kinectapp.Interaction.gestTrackOSC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.PressHandle;
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
	private ArrayList<PressHandle> _pressHandles;

	public GestTrackOSCRegion(OscP5 source) {
		super(source);
		_adapter = new Adapter();
		_pressHandles = new ArrayList<PressHandle>();
		_trackingType = TrackingType.Normalized;

		source.plug(this, "onHand1", _trackingType.toString() + "/hand0");
		source.plug(this, "onHand2", _trackingType.toString() + "/hand1");
		source.plug(this, "onHand3", _trackingType.toString() + "/hand2");
	}

	@Override
	public void runInteractions() {
		_stream = new ArrayList<InteractionStreamData>();

		if (_handData == null)
			return;

		for (HandData handData : _handData.values()) {
			processInput(handData);
		}

		// update users
		_adapter.handleStreamData(_stream);
	}

	private void processInput(HandData handData) {

		PVector position = handData.getPosition();
		float mX = position.x;// / CAM_WIDTH;
		float mY = position.y;// / CAM_HEIGHT;
		float mZ = position.z;
		Boolean isPressing = false;
		Boolean isPressTarget = false;
		PressHandle pressHandle = getPressHandle(handData.get_id());
		PVector tendency = handData.getTendency();

		//println("z : " + mZ);
		//println("tendency : " + tendency.z);
		
		if (pressHandle != null) {
			// update pressing
			pressHandle.updateTendency(tendency);
			pressHandle.updatePosition(position);
			// check tendency
			if (pressHandle.isPressing()) {
				println("pressing: " + mZ);
				PVector pressPosition = pressHandle.getUpdatedPosition();
				mX = pressPosition.x;
				mY = pressPosition.y;
				isPressing = pressHandle.isPressAction();
				println("isPressing : " + isPressing);
			} else
				removePressHandle(pressHandle);

		} else {

			InteractionTargetInfo info = _adapter.getInteractionInfoAtLocation(mX, mY, mZ, handData.get_id(), _type);

			isPressTarget = info.get_isPressTarget();

			if (isPressTarget && tendency.z > 0) {
				PVector attrV = new PVector(info.get_pressAttractionX(), info.get_pressAttractionY());
				addPressHandle(handData.get_id(), attrV, new PVector(mX, mY, mZ));
			}else{
				isPressing = mZ > 0.1f;
				//println("isPressing : " + isPressing + " : " + mZ);
			}
		}

		InteractionStreamData data = new InteractionStreamData(mX, mY, mZ, handData.get_id(), _type, isPressing);

		data.set_isOverPressTarget(isPressTarget);

		_stream.add(data);
	}

	@Override
	public ArrayList getStream() {
		return _stream;
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
		if (_trackingType == TrackingType.Normalized)
			getHand(id, x, y, z);
		else
			getHand(id, new PVector(x, y, z));
	}

	private void getHand(int id, float x, float y, float z) {
		getHand(id, new PVector(x * CAM_WIDTH, y * CAM_HEIGHT, z * 1000));
	}

	private void addPressHandle(int id, PVector attr, PVector pos) {
		println("add press handle");
		PressHandle handle = new PressHandle(id, attr, pos);
		_pressHandles.add(handle);
	}

	private PressHandle getPressHandle(int userId) {
		for (PressHandle pressHandle : _pressHandles) {
			if (pressHandle.get_id() == userId)
				return pressHandle;
		}
		return null;
	}

	private void removePressHandle(PressHandle pressHandle) {
		println("remove handle");
		if (_pressHandles.contains(pressHandle))
			_pressHandles.remove(pressHandle);
	}
}
