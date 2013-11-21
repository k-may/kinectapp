package kinectapp.Interaction;

import static processing.core.PApplet.println;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PVector;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.PressHandle;
import FrameWork.events.HandDetectedEvent;

public class KinectRegion<T> extends Region<T> {
	protected static final int CAM_WIDTH = 640;
	protected static final int CAM_HEIGHT = 480;
	protected ArrayList<PressHandle> _pressHandles;

	public KinectRegion(T source) {
		super(source);
		_pressHandles = new ArrayList<PressHandle>();
	}

	protected void processInput(HandData handData) {

		PVector position = handData.getPosition();
		float mX = position.x;// / CAM_WIDTH;
		float mY = position.y;// / CAM_HEIGHT;
		float mZ = position.z;
		float pressPressure = 0.0f;
		Boolean isPressing = false;
		PressHandle pressHandle = getPressHandle(handData.get_id());
		PVector tendency = handData.getTendency();

		InteractionTargetInfo info = _adapter.getInteractionInfoAtLocation(mX, mY, mZ, handData.get_id(), _type);
		Boolean isPressTarget = info.get_isPressTarget();
		Boolean isHoverTarget = info.get_isHoverTarget();
		
		if (pressHandle != null) {
			// update pressing
			pressHandle.updateTendency(tendency);
			pressHandle.updatePosition(position);
			// check tendency
			if (pressHandle.isPressAction() || pressHandle.isPressing()) {
				PVector pressPosition = pressHandle.getUpdatedPosition();
				mX = pressPosition.x;
				mY = pressPosition.y;
				isPressTarget = true;
				pressPressure = pressHandle.get_pressure();
				isPressing = pressHandle.isPressAction();
			} else if(!isPressTarget){
				removePressHandle(pressHandle);
			}

		} else {
			if (isPressTarget && tendency.z > 0) {
				PVector attrV = new PVector(info.get_pressAttractionX(), info.get_pressAttractionY());
				addPressHandle(handData.get_id(), attrV, new PVector(mX, mY, mZ));
			} else {
				isPressing = mZ > 0.1f;
			}
		}

		InteractionStreamData data = new InteractionStreamData(mX, mY, mZ, handData.get_id(), _type, isHoverTarget, isPressTarget, isPressing, pressPressure);

		_stream.add(data);
	}

	private void addPressHandle(int id, PVector attr, PVector pos) {
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
		if (_pressHandles.contains(pressHandle))
			_pressHandles.remove(pressHandle);
	}

	protected HandData getHand(int id, PVector pos) {
		if (_handData == null) {
			_handData = new HashMap<Integer, HandData>();
			new HandDetectedEvent().dispatch();
		}

		HandData data = null;

		if (_handData.containsKey(id))
			data = _handData.get(id);
		else {
			data = new HandData(id);
			_handData.put(id, data);
		}

		data.addPosition(pos);

		return data;

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

	@Override
	public ArrayList<InteractionStreamData> getStream() {
		return _stream;
	}

}
