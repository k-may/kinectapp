package kinectapp.Interaction;

import java.util.ArrayList;

import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionType;

public abstract class Region<T> implements IInteractionRegion {

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
	 * 
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
}
