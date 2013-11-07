package FrameWork.data;

import FrameWork.Interaction.Types.HandType;

public class UserData {
	private int _id;
	private int _color = 0xff000000;
	private int _previousColor;
	private HandType _primaryHand;
	private Boolean _updated = false;

	public UserData(int id) {
		_id = id;
	}

	public int get_id(){
		return _id;
	}
	
	public void setColor(int color) {
		_previousColor = _color;
		_color = color;
	}
	
	public int getColor(){
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
}
