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
	private float _pressure;
	private float _maxPressure = Integer.MIN_VALUE;
	private float _minPressure = Integer.MAX_VALUE;
	private Boolean _isPressing = false;
	private Boolean _isOverPressTarget = false;


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

	public void set_pressure(float z) {
		_pressure = z;
		
		if(_pressure > _maxPressure)
			_maxPressure = _pressure;
		
		if(_pressure < _minPressure)
			_minPressure = _minPressure;
	}
	
	public float get_pressure(){
		return _pressure;
	}
	
	public float get_maxPressure() {
		return _maxPressure;
	}

	public float get_minPressure() {
		return _minPressure;
	}

	public Boolean isPressing() {
		return _isPressing;
	}

	public void set_isPressing(Boolean _isPressing) {
		this._isPressing = _isPressing;
	}

	public void set_isOverPressTarget(Boolean isOverPressTarget) {
		// TODO Auto-generated method stub
		_isOverPressTarget = isOverPressTarget;
	}
	
	public Boolean isOverPressTarget(){
		return _isOverPressTarget;
	}

}
