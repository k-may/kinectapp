package kinectapp;

import framework.IMainView;
import framework.events.ExitEvent;
import application.AppBuilder;
import application.interaction.soni.SONRegion;
import oscP5.OscMessage;
import processing.core.PApplet;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class KinectApp extends PApplet {

	private IMainView _root;
	public static PApplet instance;
	private Boolean isFullScreen = true;

	public void setup() {
		noLoop();
		if (!isFullScreen) {
			size(1024, 768, PApplet.P2D);
		}else{
			size(displayWidth, displayHeight, PApplet.P2D);
		}

		instance = this;
		AppBuilder appBuilder = new AppBuilder(this);
	}

	public void draw() {
		_root.draw(this);
	}


	@Override
	public void exit() {
		// TODO Auto-generated method stub
		new ExitEvent().dispatch();
		super.exit();
	}

	@Override
	public boolean sketchFullScreen() {
		return isFullScreen;
	}

	public void setRoot(IMainView root) {
		_root = root;
	}

	// -----------------------------------------------------------------
	// hand events

	public void onNewHand(SimpleOpenNI curContext, int handId, PVector pos) {
		SONRegion region = (SONRegion) _root.get_region();
		region.onNewHand(handId, pos);
	}

	public void onTrackedHand(SimpleOpenNI curContext, int handId, PVector pos) {
		SONRegion region = (SONRegion) _root.get_region();
		region.onTrackedHand(handId, pos);
	}

	public void onLostHand(SimpleOpenNI curContext, int handId) {
		SONRegion region = (SONRegion) _root.get_region();
		region.onLostHand(handId);
	}

	// -----------------------------------------------------------------
	// gesture events
	public void onCompletedGesture(SimpleOpenNI curContext, int gestureType,
			PVector pos) {
		SONRegion region = (SONRegion) _root.get_region();
		region.onCompletedGesture(gestureType, pos);
	}


	public static void main(String _args[]) {
		PApplet.main(new String[] { kinectapp.KinectApp.class.getName() });
	}
}
