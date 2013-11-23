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
	private final float TENDENCY = 0.02f;

	public PressHandle(int userId, PVector target, PVector startPos) {
		_userId = userId;
		_target = target;
		_position = startPos;
	}

	public int get_id() {
		return _userId;
	}

	private PVector getTendencyVector(){
		PVector tendencyNorm = new PVector(_tendency.x, _tendency.y); 
		tendencyNorm.normalize();
		tendencyNorm.mult(EASING);
		return tendencyNorm;
	}
	
	public PVector getUpdatedPosition() {
		easePosition(getTendencyVector());
		return _position;
	}

	private void easePosition(PVector tendency) {
		_position.x = _position.x + ((_target.x - _position.x)*EASING);// + TENDENCY*tendency.x;
		_position.y = _position.y + ((_target.y - _position.y)*EASING);// + TENDENCY*tendency.y;
	}

	public void updateTendency(PVector tendency) {
		_tendency = tendency;
	}
	
	public void updatePosition(PVector position){
		_dX = position.x - _position.x;
		_dY = position.y - _position.y;
		_dZ = position.z - _position.z;
		
		_position.z = position.z;
	}

	public Boolean isPressing() {
		println("tendency z : " + _tendency.z + " : " + _position.z);
		return _tendency.z > 0 && _position.z > 0.3f;
	}

	public Boolean isPulling() {
		return _tendency.z < 0;
	}
	
	public Boolean isPressAction(){
		return _position.z > 0.7f;
	}

	public float get_pressure() {
		return Math.min(1.0f,_dZ/0.7f);
	}
	
	public PVector getTargetPos(){
		return _target;
	}
}
