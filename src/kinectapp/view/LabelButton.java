package kinectapp.view;

import kinectapp.KinectApp;
import kinectapp.content.Assets;
import processing.core.PApplet;
import FrameWork.events.LabelButtonPressed;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

public class LabelButton extends View {

	private LabelView _label;
	private int _overColor;
	private int _outColor;
	private int _color;

	public LabelButton() {
		super("button");
	}

	public void setText(String text) {
		_label = new LabelView(text, 0x000, Assets.Font_48);
		addChild(_label);
		_width = _label.get_width();
		_height = _label.get_height();
		_outColor = KinectApp.instance.color(200, 200, 200); // 0x333333;
		// KinectApp.instance.color(200,200,200);
		_overColor = KinectApp.instance.color(255, 255, 255); // 0xffffff;
		_color = _outColor;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub

		p.fill(_color);
		p.stroke(100);
		p.rect(_x, _y, _width, _height);

		super.draw(p);
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case PressDown:
				new LabelButtonPressed(_label.get_text()).dispatch();
				break;
			case PressUp:
				break;
			case RollOut:
				_color = _outColor;
				break;
			case RollOver:
				_color = _overColor;
				break;
		}
	}
}
