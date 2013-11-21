package kinectapp.view.tracks;

import java.util.ArrayList;
import java.util.Observable;

import kinectapp.view.menu.Menu;
import static processing.core.PApplet.println;

import FrameWork.audio.IAudioView;
import FrameWork.data.MusicEntry;
import FrameWork.view.View;

public class TrackView extends View implements IAudioView {

	private ArrayList<TrackEntryView> _trackViews;
	private Boolean _isExpanded = false;
	private Boolean _isShowing = false;

	private TrackSmallView _smallView;

	public TrackView() {

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		TrackPlayer player = (TrackPlayer) arg0;
		MusicEntry currentEntry = (MusicEntry) arg1;
		Boolean isPlaying = player.isPlaying();

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
			_height = Menu.OpenHeight;
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


}
