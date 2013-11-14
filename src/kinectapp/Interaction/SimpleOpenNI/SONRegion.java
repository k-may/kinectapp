package kinectapp.Interaction.SimpleOpenNI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PVector;

import kinectapp.Interaction.Adapter;
import kinectapp.Interaction.Region;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import SimpleOpenNI.SimpleOpenNI;

import static processing.core.PApplet.println;

public class SONRegion extends Region<SimpleOpenNI> {

	private Map<Integer, SONHandData> _handData;

	private static final int CAM_WIDTH = 640;
	private static final int CAM_HEIGHT = 480;

	public SONRegion(SimpleOpenNI source) {
		super(source);

		_adapter = new Adapter();
		// enable depthMap generation
		source.enableDepth();
		source.enableHand();
		source.startGesture(SimpleOpenNI.GESTURE_WAVE);

		_handData = new HashMap<Integer, SONHandData>();
	}

	@Override
	public void runInteractions() {
		_source.update();
		_interactions = new ArrayList<InteractionStreamData>();

		for (SONHandData handData : _handData.values()) {

			PVector position = handData.getPosition();
			float mX = position.x;// / CAM_WIDTH;
			float mY = position.y;// / CAM_HEIGHT;
			float mZ = position.z;
			
			//println(mX + " : " + mY + " : " + mZ);

			InteractionTargetInfo info = _adapter.getInteractionInfoAtLocation(mX, mY, mZ, 1, _type);

			InteractionStreamData data = new InteractionStreamData(mX, mY, mZ, 1, _type);
			data.set_isOverPressTarget(info.get_isPressTarget());

			_interactions.add(data);
		}

		// update users
		_adapter.handleStreamData(_interactions);
	}

	@Override
	public ArrayList<InteractionStreamData> getStream() {
		// TODO Auto-generated method stub
		return _interactions;
	}

	public void onCompletedGesture(int gestureType, PVector pos) {
		_source.startTrackingHand(pos);
	}

	public void onNewHand(int id, PVector pos) {
		println("new : " + id + " : " + pos);
		PVector lastPos = new PVector();
		_source.convertRealWorldToProjective(pos, lastPos);
		getHand(id, lastPos);
	}

	public void onTrackedHand(int id, PVector pos) {
		println("z : " + pos.z);
		PVector lastPos = new PVector();
		_source.convertRealWorldToProjective(pos, lastPos);
		getHand(id, lastPos);
	}

	public void onLostHand(int id) {
		if (_handData.containsKey(id))
			_handData.remove(id);
	}

	private SONHandData getHand(int id, PVector pos) {
		SONHandData data = null;

		if (_handData.containsKey(id))
			data = _handData.get(id);
		else {
			data = new SONHandData(id);
			_handData.put(id, data);
		}

		data.addPosition(pos);

		return data;
	}
}
