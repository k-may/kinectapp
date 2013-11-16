package kinectapp.Interaction;

import static processing.core.PApplet.println;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PVector;

import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionType;
import FrameWork.events.HandDetectedEvent;

public abstract class Region<T> implements IInteractionRegion {

	protected Map<Integer, HandData> _handData;

	protected IAdapter _adapter;
	protected int[] _users;
	protected T _source;
	protected ArrayList<InteractionStreamData> _interactions;
	protected InteractionType _type;

	public Region(T source) {
		_source = source;
	}

	/*
	 * runInteractions() begins the processing of the interaction streams
	 * 
	 * Translate 2D UI to 3D interactions, apply pressTargetAttractions to
	 * location, map z values to user, get id from stream
	 */
	@Override
	public abstract void runInteractions();

	@Override
	public abstract ArrayList<InteractionStreamData> getStream();

	@Override
	public IAdapter get_adapter() {
		// TODO Auto-generated method stub
		return _adapter;
	}

	@Override
	public String get_name() {
		// TODO Auto-generated method stub
		return _source.getClass().getName();
	}

	@Override
	public Object get_source() {
		// TODO Auto-generated method stub
		return _source;
	}

	protected HandData getHand(int id, PVector pos) {
		// println("get hand : " + id);
		println(id + " / " + pos);
		if (_handData == null) {
			_handData = new HashMap<Integer, HandData>();
			new HandDetectedEvent().dispatch();
		}

		HandData data = null;

		if (_handData.containsKey(id))
			data = _handData.get(id);
		else {
			println("--> new hand : " + id + " : " + pos);
			data = new HandData(id);
			_handData.put(id, data);
		}

		data.addPosition(pos);

		return data;

	}
}
