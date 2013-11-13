package kinectapp.view;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import kinectapp.view.tracks.TrackView;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;
import static processing.core.PApplet.println;
public class Menu extends View {

	public float height;
	private Boolean _isOver = false;
	private int _overTime;
	private int _outTime;

	private Boolean _isOpen = false;
	private TrackView _tracks;

	private final int _closedHeight = 38;
	private final int _openHeight = 154;

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
	}

	@Override
	public void draw(PApplet p) {
		if (_isOver) {
			if (!_isOpen) {
				int diff = p.millis() - _overTime;
				//println("diff : " + diff);
				if (diff > 1000)
					open();
			}
		}else{
			if(_isOpen){
				if(p.millis() - _outTime > 1000)
					close();
			}
		}

		// background
		p.fill(0xffeeeeee);
		p.rect(0, 0, _width, height);
	}

	private void open() {
		println("menu is open!");
		_isOpen = true;
		println("animate to : " + get_height());
		//Ani.from(this, 1, "height", get_height());
height = get_height();
	}

	public void close() {
		println("menu is closed");
		//height = get_height();
		_isOpen = false;
		//Ani.from(this, 1, "height", get_height());
height = get_height();
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case RollOver:
				_isOver = true;
				
				_overTime = event.get_time();
				println("over menu  : " + _overTime);
				break;
			case RollOut:
				println("out menu : " + _outTime);
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
