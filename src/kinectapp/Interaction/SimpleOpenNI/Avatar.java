package kinectapp.Interaction.SimpleOpenNI;

import kinectapp.Content.Assets;
import processing.core.PApplet;
import processing.core.PImage;
import FrameWork.Interaction.Types.HandType;
import FrameWork.View.View;

public class Avatar extends View {

	private int _id;
	private float _pressure;
	private Boolean _isPressing;
	private HandType _type;
	
	private PImage _leftHand;
	private PImage _rightHand;
	
	private PImage _pressingLeft;
	private PImage _pressingRight;

	public Boolean updated;
	
	public Avatar(int id){
		super("avatar_" + id);

		_id = id;
		
		createChilds();
	}
	
	private void createChilds() {
		// TODO Auto-generated method stub
		_pressingLeft = Assets.LeftPressingHand;
		_pressingRight = Assets.RightPressingHand;
		_leftHand = Assets.LeftHand;
		_rightHand = Assets.RightHand;
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public float get_pressure() {
		return _pressure;
	}

	public void set_pressure(float _pressure) {
		this._pressure = _pressure;
	}

	public Boolean get_isPressing() {
		return _isPressing;
	}

	public void set_isPressing(Boolean _isPressing) {
		this._isPressing = _isPressing;
	}
	
	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);
		
		
		if(_isPressing)
			p.image(getPressingImage(), _x, _y);
	}

	private PImage getPressingImage() {
		// TODO Auto-generated method stub
		if(_type == HandType.Left)
			return _pressingLeft;
		else if(_type == HandType.Right)
			return _pressingRight;
		
		return null;
	}

	private PImage getHand(){
		if(_type == HandType.Left)
			return _leftHand;
		else if(_type == HandType.Right)
			return _rightHand;
		
		return null;
	}
	public HandType get_type() {
		return _type;
	}

	public void set_type(HandType _type) {
		this._type = _type;
	}

	public void set_currentTarget(View target) {
		// TODO Auto-generated method stub
		
	}
}
