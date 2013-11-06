package FrameWork.Events;

import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.View.View;

public class TouchEvent extends Event {

	private InteractionEventType _type;
	private View _target;
	private float _localX;
	private float _localY;

	public TouchEvent(InteractionEventType type, View target, float localX,
			float localY) {
		super(EventType.Touch);
		_type = type;
		_target = target;
		_localX = localX;
		_localY = localY;
	}

	public InteractionEventType get_interactionType() {
		return _type;
	}

	public View get_target() {
		return _target;
	}

	public float get_localX() {
		return _localX;
	}

	public float get_localY() {
		return _localY;
	}

	public Boolean isPressDown() {
		return _type == InteractionEventType.PressDown;
	}

	public Boolean isPressRelease() {
		return _type == InteractionEventType.PressUp;
	}

	public Boolean isRollOver() {
		return _type == InteractionEventType.RollOver;
	}

	public Boolean isRollOut() {
		return _type == InteractionEventType.RollOut;
	}

}
