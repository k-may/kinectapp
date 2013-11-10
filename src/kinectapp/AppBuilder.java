package kinectapp;

import kinectapp.Interaction.Processing.PRegion;
import kinectapp.Interaction.SimpleOpenNI.SONRegion;
import kinectapp.clients.XMLClient;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.canvas.CanvasScene;
import kinectapp.view.home.HomeScene;
import kinectapp.view.tracks.TrackPlayer;
import processing.core.PApplet;
import FrameWork.Controller;
import FrameWork.IMainView;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.audio.IAudioPlayer;
import SimpleOpenNI.SimpleOpenNI;
import de.looksgood.ani.Ani;

public class AppBuilder {

	private IInteractionRegion _region;
	KinectApp _parent;
	IMainView _root;
	IAudioPlayer _player;
	Controller _controller;
	CanvasScene _canvas;
	HomeScene _home;

	public AppBuilder(PApplet parent) {
		_parent = (KinectApp) parent;

		init();
		load();
		start();
	}

	private void load() {
		XMLClient xmlClient = XMLClient.getXMLCLientInstance();
		ContentManager.loadIcons(KinectApp.instance, xmlClient.readAssetEntries());
		ContentManager.loadFonts(KinectApp.instance, xmlClient.readFontEntries());
		ContentManager.loadGalleryEntries(KinectApp.instance, xmlClient.readImageEntries());

		_canvas.getGallery().setImages(ContentManager.GetGalleyImages());
		
		_player.setEntries(xmlClient.readMusicEntries());
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
		_player = new TrackPlayer();
		_controller.registerTrackPlayer(_player);
		_player.set_view(_root.get_audioView());
	}

	private void initAnimationEngine() {
		Ani.init(_parent);
	}

	private void initScenes() {
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
