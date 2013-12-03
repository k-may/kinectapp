package application;

import java.net.MalformedURLException;

import application.audio.MinimAudioPlayer;
import application.clients.XMLClient;
import application.content.ContentManager;
import application.interaction.RegionType;
import application.interaction.gestTrackOSC.GestTrackOSCRegion;
import application.interaction.processing.PRegion;
import application.interaction.soni.SONRegion;
import application.view.canvas.CanvasScene;
import application.view.home.HomeScene;
import oscP5.OscP5;
import kinectapp.KinectApp;
import processing.core.PApplet;
import SimpleOpenNI.SimpleOpenNI;
import de.looksgood.ani.Ani;
import framework.Controller;
import framework.ErrorType;
import framework.IMainView;
import framework.audio.IAudioPlayer;
import framework.events.ErrorEvent;
import framework.events.LogEvent;
import framework.interaction.IInteractionRegion;
import framework.scenes.SceneManager;
import framework.scenes.SceneType;

public class AppBuilder {

	public static String xmlPath = "c:\\work\\gesturetek\\KinectApp\\KinectApp\\bin\\data\\";
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
		XMLClient xmlClient;

		xmlClient = XMLClient.getXMLCLientInstance(xmlPath);

		_controller.registerXMLClient(xmlClient);

		try {
			String imagesPath = xmlClient.getImagesPath();
			String assetsPath = xmlClient.getAssetsPath();
			String tracksPath = xmlClient.getTracksPath();

			ContentManager contentManager = ContentManager.getInstance(_parent, imagesPath, assetsPath, tracksPath);

			contentManager.loadAssets(_parent, xmlClient);
			contentManager.loadGalleryEntries(_parent, xmlClient.readImageEntries());
			_canvas.getGallery().setImages(ContentManager.GetGalleyImages());

			_player.setEntries(xmlClient.readMusicEntries());
		} catch (NullPointerException e) {
			new ErrorEvent(ErrorType.AssetError, "XMLClient null").dispatch();
			// _parent.exit();
		}

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

		SceneManager.getInstance().addObserver((MainView) _root);
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
				} else {
					new ErrorEvent(ErrorType.KinectError, "Unable to initate SimpleOpenNI, make sure all KinectAPI drivers are properly installed");
					_region = new PRegion(_parent);
				}

				break;
			default:
				_region = new PRegion(_parent);

		}

		new LogEvent("Region created : " + _region.getType().toString()).dispatch();
		
		_region.get_adapter().set_canvas(_root);
		_root.set_region(_region);

	}

}
