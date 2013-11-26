package kinectapp.clients;

import FrameWork.events.ErrorEvent;

public class ErrorLogClient extends LogClient{

	public ErrorLogClient() {
		super("bin/logs/error.txt");
	}

	public void addError(ErrorEvent evt) {
		String type = evt.get_errorType().toString();
		String time = evt.get_time();
		String msg = evt.get_msg();

		append(time + " " + type + " : " + msg);
	}
}
