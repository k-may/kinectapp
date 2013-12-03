package application.view.menu;

import framework.view.View;
import application.content.ContentManager;
import application.view.MainView;
import application.view.tracks.TrackView;
import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PApplet.println;

public class Menu extends View {

	private Boolean _tracksShowing = false;

	private TrackView _tracks;
	public static final int ClosedHeight = 82;
	public static final int OpenHeight = 202;

	public static final int DividorWidth = 6;

	private PImage _bg;

	private NowPlayingButton _nowPlayingBtn;
	private GalleryButton _galleryButton;
	private SaveButton _saveButton;

	public Menu() {
		init();
		createChilds();
	}

	private void init() {
		_name = "menu";
		_isTouchEnabled = true;
		_width = MainView.SCREEN_WIDTH;
		_height = ClosedHeight;

	}

	private void createChilds() {
		// _bg = ContentManager.GetIcon("shadow");

		_tracks = new TrackView();
		// addChild(_tracks);
		_tracks.set_width(_width);

		_nowPlayingBtn = new NowPlayingButton();
		addChild(_nowPlayingBtn);

		_galleryButton = new GalleryButton();
		addChild(_galleryButton);

		_saveButton = new SaveButton();
		addChild(_saveButton);
		_saveButton.set_x(_width - _saveButton.get_width());

		_galleryButton.set_x(_saveButton.get_x() - _galleryButton.get_width()
				- DividorWidth);
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);

		if (_bg == null)
			_bg = ContentManager.GetIcon("shadow");

		float bgX = getBgX();
		p.image(_bg, bgX, 0, getBgWidth(), ClosedHeight);
	}

	private float getBgX() {
		if (_tracksShowing) {
			return _tracks.get_width() + DividorWidth;
		} else
			return _nowPlayingBtn.get_width() + DividorWidth;
	}

	private float getBgWidth() {
		float startX = getBgX();
		float endX = _galleryButton.get_x() - DividorWidth;
		return endX - startX;
	}

	public TrackView get_trackView() {
		return _tracks;
	}

	public void showTracks() {
		addChild(_tracks);
		removeChild(_nowPlayingBtn);
		_tracksShowing = true;
		_height = OpenHeight;
	}

	@Override
	public Boolean isPressTarget() {
		return false;
	}

	public void hideTracks() {
		_tracksShowing = false;
		removeChild(_tracks);
		addChild(_nowPlayingBtn);
		_height = ClosedHeight;
	}
}
