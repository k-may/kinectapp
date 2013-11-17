package kinectapp.view.tracks;

import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.labels.LabelView;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import FrameWork.data.MusicEntry;
import FrameWork.view.View;

public class TrackSmallView extends View {

	private PImage _noteIcon;
	private PImage _playIcon;
	private PImage _pauseIcon;

	private LabelView _nowPlayingLabel;
	private LabelView _trackLabel;

	private Boolean _isPlaying = true;

	private int _notePaddingLeft = 12;
	private int _notePaddingTop = 7;

	private int _playPaddingLeft = 50;
	private int _playPaddingTop = 12;

	private int _dividor1PaddingLeft = 38;
	private int _dividor2PaddingLeft = 73;
	private int _dividorPaddingTop = 7;
	private int _dividorHeight = 23;

	private int _textPaddingLeft = 82;

	public TrackSmallView() {
		createChilds();
	}

	private void createChilds() {
		_noteIcon = ContentManager.GetIcon("smallNote");
		_pauseIcon = ContentManager.GetIcon("smallPause");
		_playIcon = ContentManager.GetIcon("smallPlay");
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);

		PVector absPos = get_absPos();

		p.tint(MainView.TEXT_COLOR, 140);
		p.image(_noteIcon, absPos.x + _notePaddingLeft, absPos.y
				+ _notePaddingTop);

		p.strokeWeight(1);
		p.stroke(MainView.TEXT_COLOR, 140);
		p.line(absPos.x + _dividor1PaddingLeft, absPos.y + _dividorPaddingTop, absPos.x
				+ _dividor1PaddingLeft, absPos.y + _dividorPaddingTop
				+ _dividorHeight);

		if (_isPlaying)
			p.image(_playIcon, absPos.x + _playPaddingLeft, absPos.y
					+ _playPaddingTop);
		else
			p.image(_pauseIcon, absPos.x + _playPaddingLeft, absPos.y
					+ _playPaddingTop);

		p.strokeWeight(1);
		p.stroke(MainView.TEXT_COLOR, 140);
		p.line(absPos.x + _dividor2PaddingLeft, absPos.y + _dividorPaddingTop, absPos.x
				+ _dividor2PaddingLeft, absPos.y + _dividorPaddingTop
				+ _dividorHeight);

	}

	public void setPlaying(Boolean isPlaying) {
		_isPlaying = isPlaying;
	}

	public void setTrackEntry(MusicEntry entry) {

		String text = entry.artist + ",\"" + entry.trackName + "\"";
		
		if (_trackLabel == null)
			_trackLabel = new LabelView(text, MainView.TEXT_COLOR, ContentManager.GetFont("small"));
		else
			_trackLabel.set_text(text);

		_trackLabel.set_x(_textPaddingLeft);
		_trackLabel.set_y(15);
		addChild(_trackLabel);

		if (_nowPlayingLabel == null) {
			_nowPlayingLabel = new LabelView("now playing:", MainView.TEXT_COLOR, ContentManager.GetFont("smallItalic"));
			_nowPlayingLabel.set_x(_textPaddingLeft);
			_nowPlayingLabel.set_y(2);
			addChild(_nowPlayingLabel);
		}
	}
}
