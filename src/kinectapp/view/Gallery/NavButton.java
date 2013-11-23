package kinectapp.view.gallery;

import processing.core.PApplet;
import processing.core.PVector;
import kinectapp.view.Image;
import kinectapp.view.MainView;
import FrameWork.events.GalleryNavigationEvent;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

public class NavButton extends View {

	private Boolean _isOver = false;

	private Image _arrow;
	private Image _bg;
	private int _color = 0xff000000;
	private String _direction;

	public NavButton(String direction) {
		_direction = direction;
		createChilds();
	}

	private void createChilds() {
		if (_direction == "right") {
			_arrow = new Image("navigationRightIcon");
			_bg = new Image("shadowRight");
		} else {
			_arrow = new Image("navigationLeftIcon");
			_bg = new Image("shadowLeft");
		}

		_arrow.set_color(_color);

		addChild(_bg);
		addChild(_arrow);

	}

	@Override
	public void draw(PApplet p) {
		super.draw(p);

		_width = _bg.get_width();
		_height = _bg.get_height();

		if (_isOver) {
			PVector absPos = get_absPos();
			p.noFill();
			p.stroke(_color);
			p.rect(absPos.x, absPos.y, _width, _height);
		}
	}

	@Override
	protected void onPress(TouchEvent event) {
		new GalleryNavigationEvent(_direction).dispatch();
	}

	@Override
	protected void onHover(TouchEvent event) {
		_isOver = true;
		_color = event.getUser().getColor();
		_arrow.set_color(_color);
	}

	@Override
	protected void onHoverOut(TouchEvent event) {
		_isOver = false;
		_color = 0xff000000;
		_arrow.set_color(_color);
	}
}
