package FrameWork.Interaction;

public class InteractionStreamData {

	private float _z;
	private float _x;
	private float _y;
	private int _userId;
	private InteractionType _type;
	private Boolean _isOverPressTarget = false;
	private Boolean _isPressing = false;

	public InteractionStreamData(float x, float y, float z, int userId,
			InteractionType type) {
		_z = (z);
		_x = (x);
		_y = (y);
		_type = (type);
		_isPressing = _z == 1;
	}

	public InteractionStreamData(float x, float y, float z, int userId,
			InteractionType type, Boolean isPressing) {
		this(x, y, z, userId, type);
		_isPressing = isPressing;
	}

	public Boolean isPressing() {
		return _isPressing;
	}

	public float get_z() {
		return _z;
	}

	public float get_x() {
		return _x;
	}

	public float get_y() {
		return _y;
	}

	public int get_userId() {
		return _userId;
	}

	public InteractionType get_type() {
		return _type;
	}

	public Boolean isOverPressTarget() {
		return _isOverPressTarget;
	}

	public void set_isOverPressTarget(Boolean _isOverPressTarget) {
		this._isOverPressTarget = _isOverPressTarget;
	}

}
