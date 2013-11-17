package FrameWork.events;

public class HoverEndEvent extends Event {

	private int _id;

	public int get_userId() {
		return _id;
	}

	public HoverEndEvent(int id) {
		super(EventType.HoverEnd);
		_id = id;
	}

}
