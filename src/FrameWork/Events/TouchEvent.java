package framework.events;

import framework.Controller;
import framework.data.UserData;
import framework.interaction.Types.InteractionEventType;
import framework.view.IView;
import framework.view.View;

public class TouchEvent extends Event {

	private InteractionEventType _type;
	private IView _target;
	private float _localX;
	private float _localY;
	private float _pressure;
	private UserData _user;
	private int _time;

	public TouchEvent(InteractionEventType type, IView target, float localX,
			float localY, float pressure, UserData user, int time) {
		super(EventType.Touch);
		_type = type;
		_target = target;
		_localX = localX;
		_localY = localY;
		_pressure = pressure;
		_user = user;
		_time = time;
	}
	

	public void dispatch() {
		Controller.getInstance().addTouchEvent(this);
	}

	public InteractionEventType get_interactionType() {
		return _type;
	}

	public IView get_target() {
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
		return _type == InteractionEventType.Cancel;
	}

	public float get_pressure() {
		return _pressure;
	}

	public UserData getUser() {
		return _user;
	}

	public int get_time() {
		return _time;
	}




}
