package FrameWork.data;

import FrameWork.Interaction.Types.HandType;
import FrameWork.pressing.PressState;
import FrameWork.pressing.PressStateData;

public class UserData {
	private int _id;
	private int _color = 0xff000000;
	private int _previousColor;
	private HandType _primaryHand;
	private Boolean _updated = false;
	private float _localX;
	private float _localY;
	private float _pressure = 0.0f;
	private Boolean _isPressing = false;
	private Boolean _isOverPressTarget = false;
	private Boolean _isOverHoverTarget = false;
	private PressStateData _pressStateData;

	public UserData(int id) {
		// println("new user : " + id);
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

	public Boolean isOverPressTarget() {
		return _isOverPressTarget;
	}

	public PressStateData get_pressStateData() {
		return _pressStateData;
	}

	public void set_pressStateData(PressStateData _pressStateData) {
		this._pressStateData = _pressStateData;
	}

	public void set_pressPressure(float pressure) {
		_pressure = pressure;
	}
	
	public float get_pressPressure(){
		return _isOverPressTarget ? _pressure : 0.0f;
	}
	
	public float get_strokePressure(){
		return _pressStateData.get_state() == PressState.Drawing ? 
				_pressStateData.get_pressure() : 0.0f;
	}
	
	public float get_navigationPressure(){
		return _pressStateData.get_state() == PressState.Start ? 
				_pressStateData.get_pressure() : 0.0f;
	}

	public Boolean isOverTouchTarget(){
		return _isOverHoverTarget || _isOverPressTarget;
	}
	
	public Boolean isOverHoverTarget() {
		return _isOverHoverTarget;
	}

	public void set_isOverHoverTarget(Boolean _isOverHoverTarget) {
		this._isOverHoverTarget = _isOverHoverTarget;
	}
}
