package kinectapp;

import static processing.core.PApplet.println;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import kinectapp.Interaction.Processing.PRegion;
import kinectapp.Interaction.SimpleOpenNI.SONRegion;
import kinectapp.view.MainView;
import kinectapp.view.canvas.CanvasScene;
import kinectapp.view.gallery.GalleryView;
import kinectapp.view.home.HomeScene;
import kinectapp.view.tracks.TrackPlayer;
import FrameWork.IMainView;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.audio.IAudioPlayer;
import FrameWork.events.InteractionRegionReadyEvent;
import SimpleOpenNI.SimpleOpenNI;

public class AppBuilder {


	private IInteractionRegion _region;
	KinectApp _parent;
	IMainView _root;
	Controller _controller;
	CanvasScene _canvas;
	HomeScene _home;

	public AppBuilder(PApplet parent) {
		_parent = (KinectApp) parent;

		init();
		
		start();
	}

	private void start() {
		_controller.start();
	}

	private void init() {
		
		_controller = Controller.getInstance();
		
		initMainView();
		initAnimationEngine();
		initInteraction();
		initScenes();
		initPlayer();
	}

	private void initMainView() {
		_root = new MainView(_parent);
		_controller.registerParent(_root);
		_parent.setRoot(_root);
	}

	private void initPlayer() {
		IAudioPlayer player = new TrackPlayer();
		_controller.registerTrackPlayer(player);
		player.set_view(_root.get_audioView());
	}

	private void initAnimationEngine(){
		Ani.init(_parent);
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
			_region = new PRegion(_parent);

		_region.get_adapter().set_canvas(_root);
		_root.set_region(_region);

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
