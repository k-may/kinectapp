package FrameWork.data;

import FrameWork.Interaction.Types.HandType;

public class UserData {
	private int _id;
	private int _color = 0xff000000;
	private int _previousColor;
	private HandType _primaryHand;
	private Boolean _updated = false;
	private float _localX;
	private float _localY;

	public UserData(int id) {
		_id = id;
	}

	public int get_id() {
		return _id;
	}

	public void setColor(int color) {
		_previousColor = _color;
		_color = color;
	}

	public int getColor() {
		return _color;
	}

	public void setPrimaryHand(HandType type) {
		_primaryHand = type;
	}

	public Boolean isUpdated() {
		return _updated;
	}

	public void set_updated(Boolean _updated) {
		this._updated = _updated;
	}
	
	public float get_localX() {
		return _localX;
	}

	public void set_localX(float _localX) {
		this._localX = _localX;
	}

	public float get_localY() {
		return _localY;
	}

	public void set_localY(float _localY) {
		this._localY = _localY;
	}

}
