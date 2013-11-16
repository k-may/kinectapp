package kinectapp;

import oscP5.OscMessage;
import kinectapp.Interaction.SimpleOpenNI.SONRegion;
import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.IMainView;
import FrameWork.events.ExitEvent;
import SimpleOpenNI.SimpleOpenNI;

public class KinectApp extends PApplet {

	private IMainView _root;
	private AppBuilder _appBuilder;
	public static PApplet instance;
	private Boolean isFullScreen = false;

	public void setup() {
		noLoop();
		if (!isFullScreen) {
			size(1024, 768);
		}else{
			size(displayWidth, displayHeight);
		}

		instance = this;
		_appBuilder = new AppBuilder(this);
	}

	public void draw() {
		_root.draw(this);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { kinectapp.KinectApp.class.getName() });
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

}
