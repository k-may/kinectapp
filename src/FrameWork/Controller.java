package framework;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import processing.core.PApplet;
import framework.audio.IAudioPlayer;
import framework.clients.ErrorLogClient;
import framework.clients.IDataClient;
import framework.clients.LogClient;
import framework.data.ImageEntry;
import framework.data.MusicEntry;
import framework.events.ActionEvent;
import framework.events.BackEvent;
import framework.events.ErrorEvent;
import framework.events.Event;
import framework.events.EventType;
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
import framework.view.CanvasState;
import framework.view.IView;

public class Controller implements IController {

	private ArrayList<TouchEvent> _touchEventQueue;
	private ArrayList<Event> _eventQueue;
	private static Controller _instance;
	private IMainView _mainView;
	private IAudioPlayer _player;
	private IDataClient xmlClient;
	private IHomeScene<?> _homeScene;
	private ErrorLogClient _errorLogClient;
	private LogClient _logClient;
	private int _actionTime;

	private ArrayList<IController> _controllers;

	public static String DATA_PATH;

	private Controller() {
		_touchEventQueue = new ArrayList<TouchEvent>();
		_eventQueue = new ArrayList<Event>();

		_errorLogClient = new ErrorLogClient(Controller.DATA_PATH + "logs/error.txt");
		_logClient = new LogClient(Controller.DATA_PATH + "logs/log.txt");

		registerController(this);
		
	}

	public void addEvent(Event event) {
		_eventQueue.add(event);
	}

	public void update(int time) {
		processTouches();
		processEvents();

		if (SceneManager.GetSceneType() == SceneType.Canvas) {
			int elapsed = time - _actionTime;
			if (elapsed > 60000)
				navigateToHome();
		}
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
			for (IController controller : _controllers)
				controller.processEvent(evt);
		}
	}

	public void processEvent(Event event) {
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
			case Action:
				handleAction((ActionEvent) event);
				break;
			case CanvasStateUpdate:
				handleCanvasState((CanvasStateUpdateEvent) event);
				break;
		}
	}

	private void handleCanvasState(CanvasStateUpdateEvent event) {
		CanvasState state = event.getState();
		_mainView.set_currentState(state);
	}

	private void handleAction(ActionEvent event) {
		_actionTime = event.get_time();
	}

	private void handleLog(LogEvent event) {
		_logClient.addLog(event.get_time(), event.get_msg());
	}

	private void handleError(ErrorEvent event) {
		_errorLogClient.addError(event);
	}

	private void handleGallerySelected(GallerySelectedEvent event) {
		// setCanvasState(CanvasState.Gallery);
		new CanvasStateUpdateEvent(CanvasState.Gallery).dispatch();

		SceneManager.setScene(SceneType.Canvas);
	}

	private void handleBack(BackEvent event) {
		switch (SceneManager.GetSceneType()) {
			case Canvas:
				new CanvasStateUpdateEvent(CanvasState.Canvas).dispatch(); // (CanvasState.Canvas);
				break;
		}
	}

	private void handleHandDetected(HandDetectedEvent event) {
		switch (SceneManager.GetSceneType()) {
			case Home:
				_homeScene.setReady(true);
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

	}

	private void navigateToHome() {
		new CanvasStateUpdateEvent(CanvasState.Canvas).dispatch(); // setCanvasState(CanvasState.Canvas);
		SceneManager.setScene(SceneType.Home);
		_homeScene.setReady(false);
	}

	private void navigateToCanvas() {
		new CanvasStateUpdateEvent(CanvasState.Canvas).dispatch(); // setCanvasState(CanvasState.Gallery);
		SceneManager.setScene(SceneType.Canvas);
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

		xmlClient.writeImageEntry(entry);

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

	public void registerDataClient(IDataClient client) {
		xmlClient = client;
	}

	public void registerParent(IMainView parent) {
		_mainView = parent;
	}

	public void registerTrackPlayer(IAudioPlayer player) {
		_player = player;
	}

	public void registerHomeScene(IHomeScene scene) {
		_homeScene = scene;
	}

	public void registerController(IController controller) {
		if (_controllers == null)
			_controllers = new ArrayList<IController>();

		_controllers.add(controller);
	}

}
