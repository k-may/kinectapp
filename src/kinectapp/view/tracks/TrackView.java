package kinectapp.view.tracks;

import processing.core.PImage;
import kinectapp.view.LabelView;
import FrameWork.data.MusicEntry;
import FrameWork.view.View;

public class TrackView extends View {
	private MusicEntry _entry;

	private LabelView _trackLabel;
	private LabelView _artistLabel;
	
	private PImage _playButton;
	private PImage _pauseButton;
	
	public TrackView(MusicEntry entry) {
		_entry = entry;
	}
}
