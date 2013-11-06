package kinectapp;

import static kinectapp.Data.XMLClient.getXMLCLientInstance;
import static processing.core.PApplet.println;
import java.util.ArrayList;

import kinectapp.Data.XMLClient;
import kinectapp.view.Canvas;
import FrameWork.ICanvas;
import FrameWork.Data.ImageEntry;
import FrameWork.Data.MusicEntry;
import FrameWork.Events.Event;
import FrameWork.Events.EventType;
import FrameWork.Events.InteractionRegionReadyEvent;
import FrameWork.Events.PlayTrackEvent;
import FrameWork.Events.SaveCanvasEvent;
import FrameWork.Events.TouchEvent;
import FrameWork.Interaction.InteractionDispatcher;
import FrameWork.View.IGallery;
import FrameWork.View.View;
import ddf.minim.Minim;

public class Controller {

	private ArrayList<Event> _eventQueue;
	private static Controller _instance;
	private Canvas _parent;
	private TrackPlayer _player;
	private IGallery _gallery;

	private XMLClient xmlClient;

	private ArrayList<MusicEntry> musicEntries;
	private ArrayList<ImageEntry> imageEntries;

	private Controller() {
		_eventQueue = new ArrayList<Event>();
		xmlClient = getXMLCLientInstance();
	}

	public void registerParent(Canvas parent) {
		_parent = parent;
	}

	public void registerGallery(IGallery gallery) {
		_gallery = gallery;
	}

	public void addEvent(Event event) {
		_eventQueue.add(event);
	}

	public void update() {
		processEvents();
	}

	private void processEvents() {
		for (Event evt : _eventQueue) {
			processEvent(evt);
		}
		_eventQueue.clear();
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
			case Touch:
				handleTouchEvent((TouchEvent) event);
				break;
		}
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

}
