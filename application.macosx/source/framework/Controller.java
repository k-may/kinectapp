package framework;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import framework.audio.IAudioPlayer;
import framework.clients.ErrorLogClient;
import framework.clients.LogClient;
import framework.data.GalleryEntry;
import framework.data.IXMLClient;
import framework.data.ImageEntry;
import framework.data.MusicEntry;
import framework.events.BackEvent;
import framework.events.ErrorEvent;
import framework.events.Event;
import framework.events.EventType;
import framework.events.GalleryNavigationEvent;
import framework.events.GallerySelectedEvent;
import framework.events.HandDetectedEvent;
import framework.events.InteractionRegionReadyEvent;
import framework.events.LabelButtonPressed;
import framework.events.LogEvent;
import framework.events.PauseTrackEvent;
import framework.events.PlayTrackEvent;
import framework.events.SaveCanvasEvent;
import framework.events.TouchEvent;
import framework.scenes.IHomeScene;
import framework.scenes.SceneManager;
import framework.scenes.SceneType;
import framework.stroke.ICanvas;
import framework.view.CanvasState;
import framework.view.ICanvasScene;
import framework.view.IGallery;
import framework.view.IView;

import processing.core.PApplet;

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
	private ErrorLogClient _errorLogClient;
	private LogClient _logClient;

	private Controller() {
		_touchEventQueue = new ArrayList<TouchEvent>();
		_eventQueue = new ArrayList<Event>();

		_errorLogClient = new ErrorLogClient();
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
			case OpenTracks:
				handleOpenTracks();
				break;
			case CloseTracks:
				handleCloseTracks();
				break;
			case GalleryNavigation:
				handleGalleryNavigation((GalleryNavigationEvent) event);
				break;
			case Back:
				handleBack((BackEvent) event);
				break;
			case GallerySelected:
				handleGallerySelected((GallerySelectedEvent) event);
				break;
			case Error:
				handleError((ErrorEvent) event);
				break;
			case Log:
				handleLog((LogEvent) event);
				break;
		}
	}

	private void handleLog(LogEvent event) {
		// TODO Auto-generated method stub

	}

	private void handleError(ErrorEvent event) {
		_errorLogClient.addError(event);
	}

	private void handleGallerySelected(GallerySelectedEvent event) {
		setCanvasState(CanvasState.Gallery);
		SceneManager.setScene(SceneType.Canvas);
	}

	private void handleBack(BackEvent event) {
		switch (SceneManager.GetSceneType()) {
			case Canvas:
				setCanvasState(CanvasState.Canvas);
				break;
		}
	}

	private void handleGalleryNavigation(GalleryNavigationEvent event) {
		String direction = event.get_direction();
		_gallery.navigate(direction);
	}

	private void handleCloseTracks() {
		_canvasScene.hideTracks();
	}

	private void handleOpenTracks() {
		_canvasScene.showTracks();
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
			navigateToHome();
		else if (text == "Save")
			new SaveCanvasEvent().dispatch();
		else if (text == "Clear")
			handleClearCanvas();
	}

	private void navigateToHome() {
		setCanvasState(CanvasState.Canvas);
		SceneManager.setScene(SceneType.Home);
	}

	private void navigateToCanvas() {
		setCanvasState(CanvasState.Gallery);
		SceneManager.setScene(SceneType.Canvas);
	}

	private void setCanvasState(CanvasState state) {
		switch (state) {
			case Canvas:
				_canvasScene.hideGallery();
				break;
			case Gallery:
				_canvasScene.showGallery();
				break;
			default:
				// home
				break;
		}

		_mainView.set_currentState(state);
	}

	private void handleClearCanvas() {
		_canvas.clear();
	}

	private void handleTouchEvent(TouchEvent event) {
		IView parent = event.get_target();
		parent.handleInteraction(event);
	}

	private void handleExit() {
		_player.stop();
	}

	// --------tracks----------

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
		String filePath = "images/" + PApplet.minute() + "_" + PApplet.second()
				+ ".jpg";
		ImageEntry entry = new ImageEntry(filePath, "", new String[] { "me" }, date);

		xmlClient.writeXML(entry);

		_canvas.save(filePath);

		GalleryEntry galleryEntry = new GalleryEntry(entry, _canvas.getImage());// KinectApp.instance.loadImage(filePath));
		_gallery.addImage(galleryEntry);
	}

	private void handleRegionReady(InteractionRegionReadyEvent event) {
		_mainView.set_region(event.get_region());
		start();
	}

	public void start() {
		navigateToHome();
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
