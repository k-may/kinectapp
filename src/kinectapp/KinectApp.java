package kinectapp;

import kinectapp.view.MainView;
import processing.core.PApplet;
import FrameWork.IMainView;
import FrameWork.events.ExitEvent;

public class KinectApp extends PApplet {

	private IMainView _root;
	private AppBuilder _appBuilder;
	public static PApplet instance;
	private Boolean isFullScreen  =	false;

	public void setup() {
		noLoop();
		if(!isFullScreen){
			size(1024, 768);
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
	
	public void setRoot(IMainView root){
		_root = root;
	}
}
