package kinectapp.Interaction;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import processing.core.PApplet;

import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
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

}
