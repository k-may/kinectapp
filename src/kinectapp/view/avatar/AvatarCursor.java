package kinectapp.view.avatar;

import kinectapp.content.ContentManager;
import processing.core.PApplet;
import processing.core.PImage;
import FrameWork.view.View;
import static processing.core.PApplet.println;

public class AvatarCursor extends View {

	private PImage _hand;
	private PImage _highlight;

	public float loadRatio = 0.0f;
	private int _outerRingWeight = 6;
	private int _midRingWeight = 2;
	private int _innerRingWeight = 1;

	private int _greyColor = 0xBA6E6F72;
	private int _lightGreyColor = 0xBADBD7D7;
	private int _color = 0xffffffff;
	private float _pressure = 0.0f;

	private static final int MAX_RADIUS = 25;
	private static final int MIN_RADIUS = 4;
	private Boolean _isPressing = false;
	private Boolean _isOverPressTarget = false;

	private CursorMode _mode;

	public CursorMode get_mode() {
		return _mode;
	}

	public AvatarCursor() {

		_hand = ContentManager.GetIcon("hand");
		_highlight = ContentManager.GetIcon("cursorHighlight");
	}

	@Override
	public void draw(PApplet p) {

		float cX = _x;
		float cY = _y;

		int baseColor = getBaseColor();
		
		if (_isOverPressTarget) {
			if (_isPressing) {
				p.image(_highlight, _x - _highlight.width / 2, _y
						- _highlight.height / 2);
			} else {
				p.tint(baseColor, _pressure * 255);
			}
			p.image(_hand, _x - _hand.width / 2, _y - _hand.height / 2);
		} else {
			p.noStroke();
			p.fill(baseColor);
			p.rect(_x - 2, _y - MAX_RADIUS - 8, 4, 6);
		}

		// outer ring
		p.noFill();
		p.stroke(baseColor);
		p.strokeCap(PApplet.SQUARE);
		p.strokeWeight(_outerRingWeight);
		p.arc(_x, _y, MAX_RADIUS * 2 + 10, MAX_RADIUS * 2 + 10, -(float) Math.PI / 2, get_loadAngle(), PApplet.OPEN);

		// mid ring
		p.stroke(baseColor);
		p.strokeWeight(_midRingWeight);
		p.ellipse(_x, _y, MAX_RADIUS * 2 + 3, MAX_RADIUS * 2 + 3);

		// inner/color ring
		p.strokeWeight(_innerRingWeight);
		p.stroke(_color);
		p.ellipse(_x, _y, MAX_RADIUS * 2, MAX_RADIUS * 2);

		// stroke
		if (!_isOverPressTarget) {
			switch (_mode) {
				case Drawing:
					p.noStroke();
					p.fill(_color);
					break;
				case Navigating:
					p.strokeWeight(1);
					p.stroke(_color);
					p.noFill();
					break;
			}
			float cRadius = GetRadiusForPressure(_pressure);
			p.ellipse(cX, cY, cRadius * 2, cRadius * 2);
		}

		if (loadRatio == 1.0f)
			loadRatio = 0.0f;
		
		p.noTint();

	}

	private float get_loadAngle() {
		// TODO Auto-generated method stub
		return (float) (loadRatio * Math.PI * 2 - Math.PI / 2);
	}

	private int getBaseColor() {
		if (_isOverPressTarget) {
			if (_isPressing)
				return 0xffffffff;
			else
				return _lightGreyColor;
		}

		return _greyColor;

	}

	public void setPressure(float pressure) {
		_pressure = pressure;
	}

	public void setColor(int color) {
		_color = color;
	}

	public static float GetRadiusForPressure(float pressure) {
		return (MAX_RADIUS - MIN_RADIUS) * pressure + MIN_RADIUS;
	}

	public void setPressing(Boolean pressing) {
		_isPressing = pressing;
	}

	public void isOverPressTarget(Boolean isOverPressTarget) {
		_isOverPressTarget = isOverPressTarget;
	}

	public void set_mode(CursorMode _mode) {
		this._mode = _mode;
	}

}
