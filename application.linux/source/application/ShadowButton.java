package application;

import framework.events.TouchEvent;
import framework.view.View;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class ShadowButton extends View {

	protected Image _bg;
	protected Boolean _isOver = false;
	protected int _color = 0xffffffff;
	
	protected int _grey = 0xff333333;

	public ShadowButton() {
		
		_bg = new Image("shadow");
		addChild(_bg);
	}


	@Override
	public void draw(PApplet p) {


		PVector absPos = get_absPos();

		p.noTint();
		super.draw(p);
		
		if (_isOver) {
			p.noFill();
			p.noTint();
			p.stroke(_color);
			p.rect(absPos.x, absPos.y, _width, _height);
		}

	}

	@Override
	protected void onHover(TouchEvent event) {
		_isOver = true;
		_color = event.getUser().getColor();
	}
	
	@Override
	protected void onHoverOut(TouchEvent event) {
		_isOver = false;
		_color = MainView.ICON_COLOR;
	}
	
	
}
