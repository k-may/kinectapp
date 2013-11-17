package FrameWork;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import processing.core.PApplet;
import stroke.ICanvas;
import FrameWork.audio.IAudioPlayer;
import FrameWork.data.IXMLClient;
import FrameWork.data.ImageEntry;
import FrameWork.data.MusicEntry;
import FrameWork.events.Event;
import FrameWork.events.EventType;
import FrameWork.events.HandDetectedEvent;
import FrameWork.events.HoverEndEvent;
import FrameWork.events.HoverStartEvent;
import FrameWork.events.InteractionRegionReadyEvent;
import FrameWork.events.LabelButtonPressed;
import FrameWork.events.PauseTrackEvent;
import FrameWork.events.PlayTrackEvent;
import FrameWork.events.SaveCanvasEvent;
import FrameWork.events.TouchEvent;
import FrameWork.scenes.IHomeScene;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.CanvasState;
import FrameWork.view.ICanvasScene;
import FrameWork.view.IGallery;

public class Controller {

	private ArrayList<TouchEvent> _touchEventQueue;
	private ArrayList<Event> _eventQueue;
	private static Controller _instance;
	private IMainView _mainView;
	private IAudioPlayer _player;
	private IGallery _gallery;
	private ICanvas _canvas;
	private ICanvasScene _canvasScene;
	private IXMLClient xmlClient;
	private IHomeScene _homeScene;

	private Controller() {
		_touchEventQueue = new ArrayList<TouchEvent>();
		_eventQueue = new ArrayList<Event>();
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
		// println("process event : " + event.toString());
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
			case PauseTrack:
				handlePauseTrack((PauseTrackEvent) event);
				break;
			case Exit:
				handleExit();
				break;
			case LabelButtonPressed:
				handleLableButton((LabelButtonPressed) event);
				break;
			case HandDetected:
				handleHandDetected((HandDetectedEvent) event);
				break;
			case HoverStart:
				handleHoverStart((HoverStartEvent) event);
				break;
			case HoverEnd:
				handleHoverEnd((HoverEndEvent) event);
		}
	}

	private void handleHoverEnd(HoverEndEvent event) {
		_mainView.endHover(event.get_userId());
	}

	private void handleHoverStart(HoverStartEvent event) {
		//println("controller : hover start : " + event.get_userId());
		_mainView.startHover(event.get_userId(), event.get_interval());
	}

	private void handleHandDetected(HandDetectedEvent event) {
		switch (SceneManager.GetSceneType()) {
			case Home:
				_homeScene.setReady();
				break;
		}

	}

	private void handleLableButton(LabelButtonPressed event) {
		String text = event.get_text();

		if (text == "START")
			navigateToCanvas();
		else if (text == "Home")
			naviagateToHome();
		else if (text == "Save")
			new SaveCanvasEvent().dispatch();
		else if (text == "Clear")
			handleClearCanvas();
	}

	private void naviagateToHome() {
		// _mainView.setScene(SceneType.Home);
		SceneManager.setScene(SceneType.Home);
	}

	private void navigateToCanvas() {
		// _mainView.setScene(SceneType.Canvas);
		SceneManager.setScene(SceneType.Canvas);
	}

	private void handleClearCanvas() {
		_canvas.clear();
	}

	private void handleTouchEvent(TouchEvent event) {
		event.get_target().handleInteraction(event);
	}

	private void handleExit() {
		_player.stop();
	}

	//--------tracks----------
	
	private void handlePlayTrack(PlayTrackEvent event) {
		MusicEntry entry = event.get_entry();
		_player.play(entry);
	}


	private void handlePauseTrack(PauseTrackEvent event) {
		MusicEntry entry = event.get_entry();
		_player.pause();
	}

	private void handleSaveCanvas(SaveCanvasEvent event) {
		String date = PApplet.month() + "_" + PApplet.day() + "_"
				+ PApplet.minute() + "_" + PApplet.second();
		String title = PApplet.minute() + "_" + PApplet.second();
		String filePath = "images" + PApplet.minute() + "_" + PApplet.second()
				+ ".jpg";
		ImageEntry entry = new ImageEntry(filePath, "", new String[] { "me" }, date);

		xmlClient.writeXML(entry);

		_canvas.save(filePath);
	}

	private void handleRegionReady(InteractionRegionReadyEvent event) {
		_mainView.set_region(event.get_region());
		start();
	}

	public void start() {
		_mainView.start();
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

	public void registerXMLClient(IXMLClient client) {
		xmlClient = client;
	}

	public void registerParent(IMainView parent) {
		_mainView = parent;
	}

	public void registerGallery(IGallery gallery) {
		_gallery = gallery;
	}

	public void registerCanvas(ICanvas canvas) {
		_canvas = canvas;
	}

	public void registerTrackPlayer(IAudioPlayer player) {
		_player = player;
	}

	public void registerHomeScene(IHomeScene scene) {
		_homeScene = scene;
	}

}
