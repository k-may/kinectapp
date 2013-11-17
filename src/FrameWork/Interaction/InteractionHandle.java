package FrameWork.Interaction;

import java.util.ArrayList;
import static processing.core.PApplet.println;
import FrameWork.view.IView;
import FrameWork.view.View;

public class InteractionHandle {
	private IView _target;
	private int _id;
	private ArrayList<InteractionStreamData> _data;
	private Boolean _isCancelled = false;
	private Boolean _isPressing = false;
	private Boolean _updated = false;

	public InteractionHandle(int id, IView target) {
		_target = target;
		_id = id;
	}

	public void add(InteractionStreamData data) {
		if (_data == null)
			_data = new ArrayList<InteractionStreamData>();

		_data.add(data);

		_updated = true;
	}

	public IView get_target() {
		return _target;
	}

	public int get_id() {
		return _id;
	}

	public void cancel() {
		//println("handle cancelled");
		_isCancelled = true;
	}

	public Boolean isUpdated() {
		return _updated;
	}

	public float get_currentX() {
		return _data.get(_data.size() - 1).get_x();
	}

	public float get_currentY() {
		return _data.get(_data.size() - 1).get_y();
	}

	public InteractionStreamData get_lastInteraction() {
		if (_data.size() >= 2)
			return _data.get(_data.size() - 2);

		else
			return null;
	}
	
	public InteractionStreamData get_currentInteraction(){
		return _data.get(_data.size() - 1);
	}

	public Boolean isPressing() {
		return _isPressing;
	}

	public void reset(){
		//only update press state after initial processing
		_isPressing = get_currentInteraction().isPressing();
		_updated = false;
	}
	
	public float getCurrentPressure(){
		return get_currentInteraction().get_z();
	}
}
