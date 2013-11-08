package kinectapp;

import static processing.core.PApplet.println;
import kinectapp.Interaction.Processing.PRegion;
import kinectapp.Interaction.SimpleOpenNI.SONRegion;
import kinectapp.view.MainView;
import kinectapp.view.canvas.CanvasScene;
import kinectapp.view.gallery.GalleryView;
import kinectapp.view.home.HomeScene;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.events.InteractionRegionReadyEvent;
import SimpleOpenNI.SimpleOpenNI;

public class AppBuilder {

	private IInteractionRegion _region;
	MainView _parent;
	Controller _controller;
	CanvasScene _canvas;
	HomeScene _home;

	public AppBuilder(MainView parent) {
		_parent = parent;
		_controller = Controller.getInstance();
		_controller.registerParent(_parent);
	}

	public void init() {
		initInteraction();
		initController();
		initScenes();
	}

	private void initController() {
		_controller.start();
	}

	private void initScenes() {
		// TODO Auto-generated method stub
		_canvas = new CanvasScene();
		_home = new HomeScene();
		
		_controller.registerCanvasScene(_canvas);
		_controller.registerGallery(_canvas.getGallery());
		_controller.registerCanvas(_canvas.getCanvas());
	}

	private void initInteraction() {
		// try SimpleOpenNI
		if (!initSONContext())
			_region = new PRegion(KinectApp.instance);

		_region.get_adapter().set_canvas(_parent);
		_parent.set_region(_region);

	}

	private Boolean initSONContext() {
		SimpleOpenNI context = new SimpleOpenNI(KinectApp.instance);
		if (context.init()) {
			_region = new SONRegion(context);
			return true;
		}

		return false;
	}
}
