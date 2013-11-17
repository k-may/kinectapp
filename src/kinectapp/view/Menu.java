package kinectapp.view;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import kinectapp.content.ContentManager;
import kinectapp.view.tracks.TrackView;
import FrameWork.events.HoverEndEvent;
import FrameWork.events.HoverStartEvent;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;
import static processing.core.PApplet.println;

public class Menu extends View {

	public float height;
	private Boolean _isOver = false;
	private int _overTime;
	private int _outTime;

	private int _hoverUserID;
	private int _openInterval = 1000;
	private int _closeInterval = 500;

	private Boolean _isOpen = false;
	private TrackView _tracks;

	private final int _closedHeight = 38;
	private final int _openHeight = 160;

	private PImage _bg;

	public Menu() {
		init();
		createChilds();
	}

	private void init() {
		_name = "menu";
		_isTouchEnabled = true;
		_width = MainView.SCREEN_WIDTH;
		height = get_height();

	}

	private void createChilds() {
		_tracks = new TrackView();
		addChild(_tracks);
		_tracks.set_width(_width);

	}

	@Override
	public void draw(PApplet p) {
		if (_isOver) {
			if (!_isOpen) {
				int diff = p.millis() - _overTime;
				// println("diff : " + diff);
				if (diff > _openInterval)
					open();
			}
		} else {
			if (_isOpen) {
				if (p.millis() - _outTime > _closeInterval)
					close();
			}
		}

		// background
		/*
		 * p.fill(0xddeeeeee); p.noStroke(); p.rect(0, 0, _width, height);
		 */

		if (_bg == null)
			_bg = ContentManager.GetIcon("menuBg");

		PVector absPos = get_absPos();
		p.image(_bg, absPos.x, absPos.y, _width, height);

		super.draw(p);
	}

	private void open() {
		println("menu is open!");
		_isOpen = true;
		println("animate to : " + get_height());
		Ani.to(this, 1, "height", get_height(), Ani.EXPO_OUT, "onEnd:openAniEnd");
	}

	public void openAniEnd() {
		_tracks.show();
	}

	public void close() {
		println("menu is closed");
		// height = get_height();
		_isOpen = false;
		Ani.to(this, 1, "height", get_height(), Ani.EXPO_OUT);
		_tracks.hide();
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case RollOver:
				_isOver = true;
				_hoverUserID = event.getUser().get_id();
				new HoverStartEvent(_hoverUserID, _openInterval).dispatch();
				_overTime = event.get_time();
				// println("over menu  : " + _overTime);
				break;
			case RollOut:
				if (!_isOpen)
					new HoverEndEvent(_hoverUserID).dispatch();

				_isOver = false;
				_outTime = event.get_time();
				break;
		}
	}

	@Override
	public float get_height() {
		// TODO Auto-generated method stub
		_height = _isOpen ? _openHeight : _closedHeight;
		return _height;
	}

	public TrackView get_trackView() {
		return _tracks;
	}

	@Override
	public int get_numChildren() {
		// TODO Auto-generated method stub
		return _isOpen ? super.get_numChildren() : 0;
	}

	@Override
	public Boolean isPressTarget() {
		// TODO Auto-generated method stub
		return _isOpen;
	}
}
