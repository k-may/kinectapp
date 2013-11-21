package FrameWork.Interaction;

import processing.core.PVector;
import static processing.core.PApplet.println;
public class PressHandle {

	private int _userId;
	private float _dX;
	private float _dY;
	private float _dZ;
	private PVector _target;
	private PVector _tendency;
	private PVector _position;
	private final float EASING = 0.1f;

	public PressHandle(int userId, PVector target, PVector startPos) {
		_userId = userId;
		_target = target;
		_position = startPos;
	}

	public int get_id() {
		return _userId;
	}

	public PVector getUpdatedPosition() {
		easePosition(EASING);
		return _position;
	}

	private void easePosition(float percentage) {
		_position.x = _position.x + (_target.x - _position.x) * percentage;
		_position.y = _position.y + (_target.y - _position.y) * percentage;
	}

	public void updateTendency(PVector tendency) {
		_tendency = tendency;
	}
	
	public void updatePosition(PVector position){
		_dX = position.x - _position.x;
		_dY = position.y - _position.y;
		_dZ = position.z - _position.z;
	}

	public Boolean isPressing() {
		return _tendency.z > 0;
	}

	public Boolean isPulling() {
		return _tendency.z < 0;
	}
	
	public Boolean isPressAction(){
		return _dZ > 0.7f;
	}

	public float get_pressure() {
		return Math.min(1.0f,_dZ/0.7f);
	}
}
