package kinectapp;

import FrameWork.events.ExitEvent;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import processing.core.PApplet;


public class KinectApp extends PApplet {

	private MainView _root;
	private AppBuilder _appBuilder;
	private Boolean isFullScreen  =	false;
	
	public static PApplet instance;
	
	public void setup() {
		instance = this;
		
		this.noLoop();

		if(!isFullScreen){
			size(1024, 768);
		}

		createChilds();
	}


	private void createChilds() {
		_root = new MainView(this);

		_appBuilder = new AppBuilder(_root);
		_appBuilder.init();

	}

	public void draw() {
		_root.draw(this);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { kinectapp.KinectApp.class.getName() });
	}

	@Override
	public boolean sketchFullScreen() {
		return isFullScreen;
	}
	
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		new ExitEvent().dispatch();
		
		super.exit();
		
	}
	
}
