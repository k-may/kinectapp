package FrameWork.Interaction;

public class InteractionStreamData {
	
	private float _pressure;
	private float _x;
	private float _y;
	private int _userId;
	private InteractionType _type;
	
	public InteractionStreamData(float pressure, float x, float y, int userId, InteractionType type){
		_pressure = (pressure);
		_x = (x);
		_y = (y);
		_type = (type);
	}

	public Boolean isPressing(){
		return _pressure == 1;
	}
	
	public float get_pressure() {
		return _pressure;
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

}
