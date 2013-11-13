package kinectapp.view.tracks;

import java.util.ArrayList;
import java.util.Observable;

import FrameWork.audio.IAudioView;
import FrameWork.data.MusicEntry;
import FrameWork.view.View;

public class TrackView extends View implements IAudioView {

	private ArrayList<TrackEntryView> _trackViews;
	private Boolean _isExpanded = false;
	private Boolean _isShowing = false;
	
	public static int TrackHeight = 130;

	public TrackView() {
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		TrackPlayer player = (TrackPlayer) arg0;
		MusicEntry currentEntry = (MusicEntry) arg1;
		Boolean isPlaying = player.isPlaying();

		if(_trackViews == null){
			//init views
			_trackViews = new ArrayList<TrackEntryView>();
			float x = 0;
			ArrayList<MusicEntry> entries = player.get_entries();
			for(MusicEntry entry : entries){
				TrackEntryView view =new TrackEntryView(entry);
				addChild(view);
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
			}
			view.setActive(false);
			view.setPlaying(false);
		}
	}

	@Override
	public void show() {
		_isShowing = true;
	}

	@Override
	public void hide() {
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
		return _isExpanded;
	}
}
