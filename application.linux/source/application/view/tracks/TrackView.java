package application.view.tracks;

import java.util.ArrayList;
import java.util.Observable;

import framework.audio.IAudioView;
import framework.data.MusicEntry;
import framework.events.TouchEvent;
import framework.events.TracksHideEvent;
import framework.view.View;

import application.audio.MinimAudioPlayer;
import application.view.menu.Menu;

import static processing.core.PApplet.println;


public class TrackView extends View implements IAudioView {

	private ArrayList<TrackEntryView> _trackViews;
	private Boolean _isExpanded = false;
	private Boolean _isShowing = false;

	public TrackView() {

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		MinimAudioPlayer player = (MinimAudioPlayer) arg0;
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
				x += view.get_width() + Menu.DividorWidth;
				_trackViews.add(view);
				addChild(view);
			}

			_width = x - Menu.DividorWidth;
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

	}

	@Override
	public void show() {
		_isShowing = true;
		_isHoverEnabled = true;
		
	}

	@Override
	public void hide() {
		_isHoverEnabled = false;
		_isShowing = false;
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
		return _isShowing;
	}
	
	@Override
	protected void onHoverOut(TouchEvent event) {
		new TracksHideEvent().dispatch();
	}


}
