package kinectapp.view.tracks;

import de.looksgood.ani.Ani;
import kinectapp.content.ContentManager;
import kinectapp.view.labels.LabelView;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import FrameWork.data.MusicEntry;
import FrameWork.events.PlayTrackEvent;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

import static processing.core.PApplet.println;

public class TrackEntryView extends View {
	private MusicEntry _entry;

	private LabelView _trackLabel;
	private LabelView _artistLabel;

	private PImage _playButton;
	private PImage _playButtonOver;
	private PImage _pauseButton;
	private PImage _pauseButtonOver;

	private Boolean _isActive = false;
	private Boolean _isPlaying = false;
	private Boolean _isOver = false;

	public int alpha = 0x00;

	public TrackEntryView(MusicEntry entry) {
		_entry = entry;

		createChilds();
	}

	private void createChilds() {

		_width = 120;
		_height = TrackView.TrackHeight;

		_playButton = ContentManager.GetIcon("playOut");
		_playButtonOver = ContentManager.GetIcon("playOver");
		_pauseButton = ContentManager.GetIcon("pauseOut");
		_pauseButtonOver = ContentManager.GetIcon("pauseOver");

		_invalidated = true;
	}

	public MusicEntry get_entry() {
		// TODO Auto-generated method stub
		return _entry;
	}

	public void setActive(boolean b) {
		_isActive = b;
	}

	public void setPlaying(Boolean isPlaying) {
		_isPlaying = isPlaying;
	}

	@Override
	public void draw(PApplet p) {
		if (_invalidated) {
			_trackLabel = new LabelView(_entry.trackName, 0xff000000, ContentManager.GetFont("smallItalic"));
			addChild(_trackLabel);
			_trackLabel.set_y(100);
			_trackLabel.set_x(5);

			_artistLabel = new LabelView(_entry.artist, 0xff333333, ContentManager.GetFont("small"));
			addChild(_artistLabel);
			_artistLabel.set_x(5);
			_artistLabel.set_y(115);
			_invalidated = false;
		}
		PVector absPos = get_absPos();

		p.fill(0, 0, 0, alpha);
		p.stroke(0xffeeeeee);
		p.strokeWeight(1f);
		p.rect(absPos.x, absPos.y, _width, _height);
		PImage button = _isPlaying ? !_isOver ? _playButton : _playButtonOver
				: !_isOver ? _pauseButton : _pauseButtonOver;
		p.image(button, absPos.x + (_width - button.width) / 2, absPos.y
				+ (_height - button.height) / 2);

		super.draw(p);
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case RollOver:
				if (!_isOver) {
					_isOver = true;
					Ani.to(this, 0.5f, "alpha", 0x33, Ani.SINE_IN);
				}
				break;
			case RollOut:
				_isOver = false;
				Ani.to(this, 0.25f, "alpha", 0x00, Ani.SINE_IN);
				break;
			case PressDown:
				new PlayTrackEvent(_entry).dispatch();
				break;
		}
	}

	@Override
	public Boolean isPressTarget() {
		return true;
	}
}
