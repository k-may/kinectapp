package kinectapp;

import static kinectapp.clients.XMLClient.getXMLCLientInstance;
import static processing.core.PApplet.println;

import java.util.ArrayList;

import kinectapp.clients.XMLClient;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.canvas.CanvasScene;
import kinectapp.view.tracks.TrackPlayer;
import processing.core.PApplet;
import stroke.ICanvas;
import FrameWork.data.ImageEntry;
import FrameWork.data.MusicEntry;
import FrameWork.events.Event;
import FrameWork.events.EventType;
import FrameWork.events.InteractionRegionReadyEvent;
import FrameWork.events.LabelButtonPressed;
import FrameWork.events.PlayTrackEvent;
import FrameWork.events.SaveCanvasEvent;
import FrameWork.events.TouchEvent;
import FrameWork.scenes.SceneType;
import FrameWork.view.ICanvasScene;
import FrameWork.view.IGallery;

public class Controller {

	private ArrayList<TouchEvent> _touchEventQueue;
	private ArrayList<Event> _eventQueue;
	private static Controller _instance;
	private MainView _parent;
	private TrackPlayer _player;
	private IGallery _gallery;
	private ICanvas _canvas;
	private ICanvasScene _canvasScene;
	private XMLClient xmlClient;

	private ArrayList<MusicEntry> musicEntries;
	private ArrayList<ImageEntry> imageEntries;

	private Controller() {
		_touchEventQueue = new ArrayList<TouchEvent>();
		_eventQueue = new ArrayList<Event>();
		xmlClient = getXMLCLientInstance();
	}

	public void registerParent(MainView parent) {
		_parent = parent;
	}

	public void registerGallery(IGallery gallery) {
		_gallery = gallery;
		_gallery.setImages(ContentManager.GetGalleyImages());
	}

	public void registerCanvas(ICanvas canvas) {
		_canvas = canvas;
	}

	public void addEvent(Event event) {
		_eventQueue.add(event);
	}

	public void update() {
		processTouches();
		processEvents();
	}

	private void processTouches() {
		ArrayList<TouchEvent> tempList = new ArrayList<TouchEvent>(_touchEventQueue);
		_touchEventQueue.clear();

		for (TouchEvent evt : tempList) {
			handleTouchEvent(evt);
		}
	}

	private void processEvents() {
		ArrayList<Event> tempList = new ArrayList<Event>(_eventQueue);
		_eventQueue.clear();

		for (Event evt : tempList) {
			processEvent(evt);
		}
	}

	private void processEvent(Event event) {
		EventType type = event.get_type();
		println("process event : " + event.toString());
		switch (type) {
			case InteractionReady:
				handleRegionReady((InteractionRegionReadyEvent) event);
				break;
			case SaveCanvas:
				handleSaveCanvas((SaveCanvasEvent) event);
				break;
			case PlayTrack:
				handlePlayTrack((PlayTrackEvent) event);
				break;
			case Exit:
				handleExit();
				break;
			case LabelButtonPressed:
				handleLableButton((LabelButtonPressed) event);
		}
	}

	private void handleLableButton(LabelButtonPressed event) {
		String text = event.get_text();
		println("handleLabelButton : " + text);

		if (text == "Canvas")
			_parent.setScene(SceneType.Canvas);
		else if (text == "Home")
			_parent.setScene(SceneType.Home);
		else if (text == "Save")
			new SaveCanvasEvent().dispatch();
		else if (text == "Clear")
			handleClearCanvas();
	}

	private void handleClearCanvas() {
		// TODO Auto-generated method stub
		_canvas.clear();
	}

	private void handleTouchEvent(TouchEvent event) {
		event.get_target().handleInteraction(event);
	}

	private void handleExit() {
		// TODO Auto-generated method stub
		println("exit!");
		_player.stop();
	}

	private void handlePlayTrack(PlayTrackEvent event) {
		MusicEntry entry = event.get_entry();
		_player.play(entry);
	}

	private void handleSaveCanvas(SaveCanvasEvent event) {
		// TODO Auto-generated method stub
		String date = PApplet.month() + "_" + PApplet.day() + "_"
				+ PApplet.minute() + "_" + PApplet.second();
		String title = PApplet.minute() + "_" + PApplet.second();
		String filePath = "images" + PApplet.minute() + "_" + PApplet.second()
				+ ".jpg";
		ImageEntry entry = new ImageEntry(filePath, "", new String[] { "me" }, date);

		XMLClient.getXMLCLientInstance().writeXML(entry);

		_canvas.save(filePath);
	}

	private void handleRegionReady(InteractionRegionReadyEvent event) {
		// TODO Auto-generated method stub
		_parent.set_region(event.get_region());

		start();
	}

	public void start() {

		ContentManager.loadIcons(KinectApp.instance, xmlClient.readAssetEntries());
		ContentManager.loadFonts(KinectApp.instance, xmlClient.readFontEntries());
		ContentManager.loadGalleryEntries(KinectApp.instance, xmlClient.readImageEntries());

		musicEntries = xmlClient.readMusicEntries();
		_player = new TrackPlayer();
		_player.setEntries(musicEntries);

		_parent.start();

	}

	public static Controller getInstance() {
		if (_instance == null)
			_instance = new Controller();

		return _instance;
	}

	public void addTouchEvent(TouchEvent touchEvent) {
		_touchEventQueue.add(touchEvent);
	}

	public void registerCanvasScene(ICanvasScene canvasScene) {
		_canvasScene = canvasScene;
	}

}
