package kinectapp.view;

import static processing.core.PApplet.println;
import kinectapp.content.ContentManager;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import FrameWork.view.View;

public class Image extends View {

	protected float _scaleX = 1.0f;
	protected float _scaleY = 1.0f;
	private int _color = 0xffffffff;

	public Image(String name) {
		super(name);
		
		_isTouchEnabled = false;
	}

	@Override
	public void draw(PApplet p) {

		if (_invalidated) {

			if (_width != -1)
				_scaleX = _width / getImage().width;
			else
				_width = getImage().width;

			if (_height != -1)
				_scaleY = _height / getImage().height;
			else
				_height = getImage().height;

			_invalidated = false;

		}
		PVector absPos = get_absPos();

		p.tint(_color);

		float width = get_width();
		float height = get_height();

		p.image(getImage(), absPos.x, absPos.y, width, height);

		p.noTint();
	}

	@Override
	public float get_width() {
		try {
			return getImage().width * _scaleX;
		} catch (NullPointerException e) {
			println("image name null : " + _name);
		}
		return 0.0f;
	}

	@Override
	public void set_width(float width) {
		try {
			_scaleX = width / getImage().width;
			_width = get_width();
		} catch (NullPointerException e) {
			_width = width;
			_invalidated = true;
		}
	}

	@Override
	public float get_height() {
		if (getImage() != null)
			return getImage().height * _scaleY;
		else
			return -1;
	}

	@Override
	public void set_height(float height) {
		try {
			_scaleY = height / getImage().height;
			_height = get_height();
		} catch (NullPointerException e) {
			_height = height;
			_invalidated = true;
		}
	}

	protected PImage getImage() {
		return ContentManager.GetIcon(_name);
	}

	public int get_color() {
		return _color;
	}

	public void set_color(int _color) {
		this._color = _color;
	}

}
