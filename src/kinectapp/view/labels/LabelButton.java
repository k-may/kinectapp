package kinectapp.view.labels;
import static processing.core.PApplet.println;
import kinectapp.KinectApp;
import kinectapp.content.ContentManager;
import processing.core.PApplet;
import FrameWork.events.LabelButtonPressed;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

public class LabelButton extends View {

	private LabelView _label;
	private int _overColor;
	private int _outColor;
	private int _color;
	private String _text;

	public LabelButton() {
		super("button");

		_outColor = KinectApp.instance.color(200, 200, 200); // 0x333333;
		_overColor = KinectApp.instance.color(255, 255, 255); // 0xffffff;
		_color = _outColor;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		if (_invalidated) {
			if (_label == null)
				_label = new LabelView(_text, 0x000, ContentManager.GetFont("large"));

			addChild(_label);
			_width = _label.get_width();
			_height = _label.get_height();
			
			_invalidated = false;
		}
		
		p.fill(_color);
		p.stroke(100);
		p.rect(_x, _y, _width, _height);

		super.draw(p);
	}


	public void setText(String text) {
		_text = text;
		_invalidated = true;
	}
	
	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case PressDown:
				new LabelButtonPressed(_label.get_text()).dispatch();
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
	
	@Override
	public Boolean isPressTarget() {
		// TODO Auto-generated method stub
		return true;
	}
}
