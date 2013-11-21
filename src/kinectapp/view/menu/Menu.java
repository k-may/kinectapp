package kinectapp.view.menu;

import processing.core.PApplet;
import processing.core.PImage;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.tracks.TrackView;
import FrameWork.view.View;
import static processing.core.PApplet.println;

public class Menu extends View {

	private Boolean _tracksShowing = false;
	
	private TrackView _tracks;
	public static final int ClosedHeight = 82;
	public static final int OpenHeight = 202; 

	private PImage _bg;
	
	private NowPlayingButton _nowPlayingBtn;

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
		//_bg = ContentManager.GetIcon("shadow");
		
		_tracks = new TrackView();
		//addChild(_tracks);
		_tracks.set_width(_width);

		_nowPlayingBtn = new NowPlayingButton();
		addChild(_nowPlayingBtn);
	}
	
	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);
		
		if(_bg == null)
			_bg = ContentManager.GetIcon("shadow");
		
		float bgX = _tracksShowing ? _tracks.get_width() : _nowPlayingBtn.get_width();
		p.image(_bg, bgX, 0, _width - bgX, ClosedHeight);
	}

	public TrackView get_trackView() {
		return _tracks;
	}
	
	public void showTracks(){
		addChild(_tracks);
		removeChild(_nowPlayingBtn);
		_tracksShowing = true;
	}

	@Override
	public Boolean isPressTarget() {
		return false;
	}

	public void hideTracks() {
		_tracksShowing = false;
		removeChild(_tracks);
		addChild(_nowPlayingBtn);
	}
}
