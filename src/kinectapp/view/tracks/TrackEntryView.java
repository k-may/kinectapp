package kinectapp.view.tracks;

import de.looksgood.ani.Ani;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.Image;
import kinectapp.view.ShadowButton;
import kinectapp.view.labels.LabelView;
import kinectapp.view.menu.Menu;
import processing.core.PApplet;
import FrameWork.data.MusicEntry;
import FrameWork.events.PauseTrackEvent;
import FrameWork.events.PlayTrackEvent;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

import static processing.core.PApplet.println;

public class TrackEntryView extends ShadowButton {
	private MusicEntry _entry;

	private Boolean _isActive = false;

	private LabelView _trackLabel;
	private LabelView _artistLabel;

	private Image _playIcon;
	private Image _pauseIcon;
	private Image _playText;
	private Image _pauseText;

	private Boolean _isPlaying = false;
	private Boolean _isOver = false;

	private int _textPaddingTop = 127;

	public int alpha = 0x00;

	public TrackEntryView(MusicEntry entry) {
		super();
		_entry = entry;

		createChilds();
	}

	private void createChilds() {

		_width = Menu.OpenHeight;
		_height = Menu.OpenHeight;

		_bg.set_width(_width);
		_bg.set_height(_height);

		_playIcon = new Image("playIcon");
		_playIcon.set_color(MainView.ICON_COLOR);
		_playIcon.set_x((_width - _playIcon.get_width()) / 2);
		_playIcon.set_y((_height - _playIcon.get_height()) / 2);

		_pauseIcon = new Image("pauseIcon");
		_pauseIcon.set_color(MainView.ICON_COLOR);
		_pauseIcon.set_x((_width - _pauseIcon.get_width()) / 2);
		_pauseIcon.set_y((_height - _pauseIcon.get_height()) / 2);

		_playText = new Image("playText");
		_playText.set_color(0xff000000);
		_pauseText = new Image("pauseText");
		_pauseText.set_color(0xff000000);

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

		if (_isPlaying) {
			addChild(_playText);
			addChild(_playIcon);

			removeChild(_pauseIcon);
			removeChild(_pauseText);
		} else {
			removeChild(_playIcon);
			removeChild(_playText);

			addChild(_pauseText);
			addChild(_pauseIcon);
		}
	}

	@Override
	public void draw(PApplet p) {
		if (_invalidated) {
			_trackLabel = new LabelView(_entry.trackName, MainView.TEXT_COLOR, ContentManager.GetFont("smallItalic"));
			addChild(_trackLabel);
			_trackLabel.set_y(130);
			_trackLabel.set_x(5);

			_artistLabel = new LabelView(_entry.artist, MainView.TEXT_COLOR, ContentManager.GetFont("small"));
			addChild(_artistLabel);
			_artistLabel.set_x(5);
			_artistLabel.set_y(145);
			_invalidated = false;
		}

		super.draw(p);
	}

	@Override
	public Boolean isPressTarget() {
		return true;
	}

	@Override
	protected void dispatchHover(TouchEvent event) {
		_playIcon.set_color(_color);
		_pauseIcon.set_color(_color);
	}
	
	@Override
	protected void dispatchHoverOut(TouchEvent event) {
		_playIcon.set_color(_color);
		_pauseIcon.set_color(_color);
	}

	@Override
	protected void dispatchPress(TouchEvent event) {
		if (!_isPlaying)
			new PlayTrackEvent(_entry).dispatch();
		else
			new PauseTrackEvent(_entry).dispatch();
	}
}
