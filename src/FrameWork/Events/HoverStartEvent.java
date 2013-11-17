package FrameWork.events;

public class HoverStartEvent extends Event {

	private int _id;
	private int _interval;

	public int get_userId() {
		return _id;
	}

	public int get_interval() {
		return _interval;
	}

	public HoverStartEvent(int id, int interval) {
		super(EventType.HoverStart);
		_id = id;
		_interval = interval;
	}

}
