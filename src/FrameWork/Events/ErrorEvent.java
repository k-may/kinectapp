package FrameWork.events;

import processing.core.PApplet;
import FrameWork.ErrorType;

public class ErrorEvent extends Event {

	private ErrorType _errorType;
	private String _msg;
	private String _time;

	public ErrorEvent(ErrorType errorType, String msg) {
		super(EventType.Error);

		_errorType = errorType;
		_msg = msg;
		
		String minute = String.valueOf(PApplet.minute());
		minute = minute.length() == 1 ? "0" + minute : minute;
		
		String second = String.valueOf(PApplet.second());
		second = second.length() == 1 ? "0" + second : second;
		
		_time = PApplet.month() + "/" + PApplet.day() + " " + PApplet.hour()
				+ ":" + minute + ":" + second;
	}
	
	public ErrorType get_errorType() {
		return _errorType;
	}

	public String get_msg() {
		return _msg;
	}

	public String get_time() {
		return _time;
	}
}
