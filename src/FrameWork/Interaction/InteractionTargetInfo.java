package framework.interaction;

import framework.data.UserData;

public class InteractionTargetInfo {

	private float _x;
	private float _y;
	private Boolean _isHoverTarget;
	private Boolean _isPressTarget;
	private float _pressAttractionX;
	private float _pressAttractionY;
	private UserData _user;

	public InteractionTargetInfo() {
	}

	public float get_x() {
		return _x;
	}

	public void set_x(float _x) {
		this._x = _x;
	}

	public float get_y() {
		return _y;
	}

	public void set_y(float _y) {
		this._y = _y;
	}

	public Boolean get_isPressTarget() {
		return _isPressTarget;
	}

	public void set_isPressTarget(Boolean _isPressTarget) {
		this._isPressTarget = _isPressTarget;
	}

	public float get_pressAttractionX() {
		return _pressAttractionX;
	}

	public void set_pressAttractionX(float _pressAttractionX) {
		this._pressAttractionX = _pressAttractionX;
	}

	public float get_pressAttractionY() {
		return _pressAttractionY;
	}

	public void set_pressAttractionY(float _pressAttractionY) {
		this._pressAttractionY = _pressAttractionY;
	}

	public UserData get_user() {
		return _user;
	}

	public void set_user(UserData _user) {
		this._user = _user;
	}

	public Boolean get_isHoverTarget() {
		return _isHoverTarget;
	}

	public void set_isHoverTarget(Boolean _isHoverTarget) {
		this._isHoverTarget = _isHoverTarget;
	}

}
