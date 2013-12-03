package application;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import application.audio.MinimAudioPlayer;
import application.clients.DataXMLClient;
import application.clients.XMLClient;
import application.content.ContentManager;
import application.interaction.RegionType;
import application.interaction.RegionTypeHelper;
import application.interaction.gestTrackOSC.GestTrackOSCRegion;
import application.interaction.processing.PRegion;
import application.interaction.soni.SONRegion;
import application.view.MainView;
import application.view.canvas.CanvasScene;
import application.view.home.HomeScene;
import oscP5.OscP5;
import kinectapp.KinectApp;
import processing.core.PApplet;
import processing.core.PImage;
import SimpleOpenNI.SimpleOpenNI;
import de.looksgood.ani.Ani;
import framework.Controller;
import framework.ErrorType;
import framework.IMainView;
import framework.audio.IAudioPlayer;
import framework.data.AssetEntry;
import framework.data.FontEntry;
import framework.data.GalleryEntry;
import framework.events.ErrorEvent;
import framework.events.LogEvent;
import framework.interaction.IInteractionRegion;
import framework.scenes.SceneManager;
import framework.scenes.SceneType;

public class AppBuilder {

	public static String logPath;

	private RegionType REGION_TYPE;
	private IInteractionRegion _region;
	KinectApp _parent;
	IMainView _root;
	IAudioPlayer _player;
	Controller _controller;
	CanvasScene _canvasScene;
	HomeScene _home;
	PCanvasController _canvasController;

	public AppBuilder(PApplet parent) {
		_parent = (KinectApp) parent;

		init();
		load();
		start();
	}

	private void load() {
		DataXMLClient dataClient;
		dataClient = DataXMLClient.getInstance();
		_controller.registerDataClient(dataClient);

		XMLClient assetClient = new XMLClient();

		try {
			ContentManager contentManager = ContentManager.getInstance();

			// load assets
			ArrayList<AssetEntry> readAssetEntries = assetClient.readAssetEntries();
			ArrayList<FontEntry> fontEntries = assetClient.readFontEntries();

			// load gallery images
			contentManager.loadAssets(_parent, readAssetEntries, fontEntries);
			contentManager.loadGalleryEntries(_parent, dataClient.readImageEntries());

			ArrayList<GalleryEntry<PImage>> images = ContentManager.GetGalleyImages();

			_canvasScene.setImages(images);
			_home.setImages(images);

			_player.setEntries(dataClient.readMusicEntries());
		} catch (NullPointerException e) {
			new ErrorEvent(ErrorType.AssetError, "XMLClient null").dispatch();
			// _parent.exit();
		}

	}

	private void start() {
		_controller.start();
	}

	private void init() {

		initControllers();
		initMainView();
		initAnimationEngine();
		initInteraction();
		initScenes();
		initPlayer();
	}

	private void initControllers() {
		_controller = Controller.getInstance();

		String path = AppBuilder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			path = URLDecoder.decode(path, "UTF-8");
			Controller.DATA_PATH = path ;//"C:\\work\\gesturetek\\KinectApp\\KinectApp\\bin\\data\\"; //URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			new ErrorEvent(ErrorType.Decode, "Couldn't decode path, '" + path
					+ "' : " + e1.getMessage()).dispatch();

			e1.printStackTrace();
		}
		_canvasController = new PCanvasController();
		_controller.registerController(_canvasController);
		
	}

	private void initMainView() {
		_root = new MainView(_parent);
		_controller.registerParent(_root);
		_parent.setRoot(_root);
	}

	private void initPlayer() {
		_player = new MinimAudioPlayer();
		_controller.registerTrackPlayer(_player);
		_player.set_view(_canvasScene.get_audioView());
	}

	private void initAnimationEngine() {
		Ani.init(_parent);
	}

	private void initScenes() {
		_canvasScene = new CanvasScene();
		_home = new HomeScene();

		_canvasController.registerCanvasScene(_canvasScene);
		_controller.registerHomeScene(_home);

		SceneManager.getInstance().addObserver((MainView) _root);
		// SceneManager.setScene(SceneType.Canvas);

	}

	private void initInteraction() {

		DataXMLClient dataClient;
		dataClient = DataXMLClient.getInstance();

		_controller.registerDataClient(dataClient);

		REGION_TYPE = RegionTypeHelper.GetTypeForString(dataClient.getInputType());

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
