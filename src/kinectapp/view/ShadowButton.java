package kinectapp.view;

import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

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

		super.draw(p);
		
		if (_isOver) {
			p.stroke(_color);
			p.rect(absPos.x, absPos.y, _width, _height);
		}

	}

	protected abstract void dispatchPress(TouchEvent event);
	
	protected void dispatchHover(TouchEvent event){
		
	}
	
	protected void dispatchHoverOut(TouchEvent event){
		
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case PressDown:
				dispatchPress(event);
				break;
			case PressUp:
				break;
			case Cancel:
				_isOver = false;
				_color = _grey;
				dispatchHoverOut(event);
				break;
			case RollOver:
				_isOver = true;
				_color = event.getUser().getColor();
				dispatchHover(event);
				break;
		}
	}
	
	
}
