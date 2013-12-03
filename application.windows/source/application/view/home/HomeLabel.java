package application.view.home;

import framework.events.TouchEvent;
import framework.interaction.InteractionStreamData;
import framework.view.View;
import application.content.ContentManager;
import application.view.labels.LabelView;
import kinectapp.KinectApp;
import processing.core.PApplet;

public class HomeLabel extends View {

	private LabelView _label;
	private int _paddingLeft = 10;
	private int _paddingBottom = 10;
	private int _overColor;
	private int _outColor;
	private int _color;

	public HomeLabel() {

		_outColor = KinectApp.instance.color(200, 200, 200); // 0x333333;
		_overColor = KinectApp.instance.color(255, 255, 255); // 0xffffff;
		_color = _outColor;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub

		if (_invalidated) {
			if (_label == null)
				_label = new LabelView("Welcome", KinectApp.instance.color(100, 100, 100), ContentManager.GetFont("large"));
			
			addChild(_label);
			_label.set_x(_paddingLeft);
			_height = _label.get_height() + _paddingBottom;
			_width = _label.get_width() + _paddingLeft * 2;
			_invalidated = false;
		}

		p.fill(_color);
		p.stroke(100);
		p.rect(_x, _y, _width, _height);

		super.draw(p);
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case PressDown:
				break;
			case PressUp:
				break;
			case Cancel:
				_color = _outColor;
				break;
			case RollOver:
				_color = _overColor;
				break;
		}
	}

}
