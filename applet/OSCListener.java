package kinectapp;

import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscStatus;

import static processing.core.PApplet.println;

public class OSCListener implements OscEventListener {

	public OSCListener(){
		println("hello new listener");
	}
	
	@Override
	public void oscEvent(OscMessage arg0) {
		println("--->>>osc event : " + arg0);
	}

	@Override
	public void oscStatus(OscStatus arg0) {
		println("--->>>osc statuc : " + arg0);
	}

}
