package kinectapp.view.tracks;

import java.util.ArrayList;
import java.util.Observable;

import kinectapp.view.MainView;
import static processing.core.PApplet.println;
import processing.core.PApplet;

import FrameWork.audio.IAudioView;
import FrameWork.data.MusicEntry;
import FrameWork.view.View;

public class TrackView extends View implements IAudioView {

	private ArrayList<TrackEntryView> _trackViews;
	private Boolean _isExpanded = false;
	private Boolean _isShowing = false;

	public static int TrackHeight = 130;

	private TrackSmallView _smallView;

	public TrackView() {

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		// addChild(_smallView);
		// TODO Auto-generated method stub
		TrackPlayer player = (TrackPlayer) arg0;
		MusicEntry currentEntry = (MusicEntry) arg1;
		Boolean isPlaying = player.isPlaying();

		//println("TracksView update : " + currentEntry + " : " + isPlaying);
		if (_trackViews == null) {
			// init views
			_trackViews = new ArrayList<TrackEntryView>();
			float x = 0;
			ArrayList<MusicEntry> entries = player.get_entries();
			for (MusicEntry entry : entries) {
				TrackEntryView view = new TrackEntryView(entry);
				// addChild(view);
				view.set_x(x);
				x += view.get_width();
				_trackViews.add(view);
			}

			_width = x;
			_height = TrackHeight;
		}

		for (TrackEntryView view : _trackViews) {
			if (currentEntry != null && view.get_entry() == currentEntry) {
				view.setActive(true);
				view.setPlaying(isPlaying);
			} else {
				view.setActive(false);
				view.setPlaying(false);
			}
		}

		if (_smallView == null)
			_smallView = new TrackSmallView();

		_smallView.setPlaying(isPlaying);

		if (!_isShowing)
			addChild(_smallView);

		if (currentEntry != null)
			_smallView.setTrackEntry(currentEntry);
	}

	@Override
	public void show() {
		_isShowing = true;

		for (TrackEntryView view : _trackViews) {
			addChild(view);
		}

		removeChild(_smallView);
	}

	@Override
	public void hide() {
		_isShowing = false;
		for (TrackEntryView view : _trackViews) {
			removeChild(view);
		}

		addChild(_smallView);
	}

	@Override
	public void collapse() {
		_isExpanded = false;
	}

	@Override
	public void expand() {
		_isExpanded = true;
	}

	@Override
	public Boolean isPressTarget() {
		return _isExpanded;
	}

	@Override
	public Boolean isTouchEnabled() {
		// TODO Auto-generated method stub
		return _isExpanded;
	}

	@Override
	public void draw(PApplet p) {

		if (_isShowing) {
			// dividors
			p.stroke(MainView.TEXT_COLOR, 140);
			for (TrackEntryView view : _trackViews) {
				float x = view.get_absPos().x + view.get_width();
				float y = view.get_absPos().y + 30;

				p.line(x, y, x, 100);
			}
		}

		super.draw(p);
	}

}
