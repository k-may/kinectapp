package kinectapp;

import static kinectapp.clients.XMLClient.getXMLCLientInstance;
import static processing.core.PApplet.println;
import java.util.ArrayList;

import processing.core.PApplet;

import stroke.ICanvas;

import kinectapp.clients.XMLClient;
import kinectapp.view.MainView;
import FrameWork.IMainView;
import FrameWork.Interaction.InteractionDispatcher;
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
import FrameWork.view.IGallery;
import FrameWork.view.View;
import ddf.minim.Minim;

public class Controller {

	private ArrayList<TouchEvent> _touchEventQueue;
	private ArrayList<Event> _eventQueue;
	private static Controller _instance;
	private MainView _parent;
	private TrackPlayer _player;
	private IGallery _gallery;
	private ICanvas _canvas;

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
		else if (text == "Gallery")
			_parent.setScene(SceneType.Gallery);
		else if (text == "Home")
			_parent.setScene(SceneType.Home);
		else if (text == "Save")
			handleSaveImage();
		else if(text == "Clear")
			handleClearCanvas();
	}

	private void handleClearCanvas() {
		// TODO Auto-generated method stub
		_canvas.clear();
	}

	private void handleSaveImage() {

		String filePath = PApplet.minute() + ".jpg";
		ImageEntry entry = new ImageEntry(filePath, "", new String[]{"me"});

		XMLClient.getXMLCLientInstance().writeXML(entry);

		_canvas.save(filePath);
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
		String fileName = KinectApp.instance.millis() + ".jpg";
	}

	private void handleRegionReady(InteractionRegionReadyEvent event) {
		// TODO Auto-generated method stub
		_parent.set_region(event.get_region());

		start();
	}

	public void start() {
		_parent.start();

		musicEntries = xmlClient.readMusicEntries();
		_player = new TrackPlayer();
		_player.setEntries(musicEntries);
		// new
		// PlayTrackEvent(musicEntries.get(0)).dispatch();//_player.play(musicEntries.get(0));

		imageEntries = xmlClient.readImageEntries();
		_gallery.setImages(imageEntries);

	}

	public static Controller getInstance() {
		if (_instance == null)
			_instance = new Controller();

		return _instance;
	}

	public void addTouchEvent(TouchEvent touchEvent) {
		_touchEventQueue.add(touchEvent);
	}

}
