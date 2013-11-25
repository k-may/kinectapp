package kinectapp;

import oscP5.OscP5;
import kinectapp.Interaction.RegionType;
import kinectapp.Interaction.Processing.PRegion;
import kinectapp.Interaction.SimpleOpenNI.SONRegion;
import kinectapp.Interaction.gestTrackOSC.GestTrackOSCRegion;
import kinectapp.audio.MinimAudioPlayer;
import kinectapp.clients.XMLClient;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.canvas.CanvasScene;
import kinectapp.view.home.HomeScene;
import processing.core.PApplet;
import FrameWork.Controller;
import FrameWork.IMainView;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.audio.IAudioPlayer;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import SimpleOpenNI.SimpleOpenNI;
import de.looksgood.ani.Ani;

public class AppBuilder {

	private RegionType REGION_TYPE = RegionType.GestTrackOSC;
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
		
		_controller.registerXMLClient(xmlClient);
		
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
		_player = new MinimAudioPlayer();
		_controller.registerTrackPlayer(_player);
		_player.set_view(_canvas.get_audioView());
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
		_controller.registerHomeScene(_home);
		
		SceneManager.getInstance().addObserver((MainView)_root);
		SceneManager.setScene(SceneType.Canvas);
		
	}

	private void initInteraction() {
		switch (REGION_TYPE) {
			case GestTrackOSC:
				OscP5 osc = new OscP5(KinectApp.instance, 12345);
				_region = new GestTrackOSCRegion(osc);
				break;
			case SimpleOpenNI:

				SimpleOpenNI context = new SimpleOpenNI(KinectApp.instance);
				if (context.init()) {
					_region = new SONRegion(context);
				} else
					_region = new PRegion(_parent);

				break;
			default:
				_region = new PRegion(_parent);

		}

		_region.get_adapter().set_canvas(_root);
		_root.set_region(_region);

	}

}
