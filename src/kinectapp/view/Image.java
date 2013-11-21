package kinectapp.view;

import kinectapp.content.ContentManager;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import FrameWork.view.View;

import static processing.core.PApplet.println;

public class Image extends View {

	private float _scaleX = 1.0f;
	private float _scaleY = 1.0f;
	private int _color;

	public Image(String name) {
		super(name);
	}

	@Override
	public void draw(PApplet p) {
		PVector absPos = get_absPos();

		p.tint(_color);

		p.image(ContentManager.GetIcon(_name), absPos.x, absPos.y, get_width(), get_height());
	}

	@Override
	public float get_width() {
		try {
			return ContentManager.GetIcon(_name).width * _scaleX;
		} catch (NullPointerException e) {
			println("image name null : " + _name);
		}
		return 0.0f;
	}

	@Override
	public void set_width(float width) {
		// TODO Auto-generated method stub
		_scaleX = getImage().width / width;
	}

	@Override
	public float get_height() {
		return ContentManager.GetIcon(_name).height * _scaleY;
	}

	@Override
	public void set_height(float height) {
		_scaleY = getImage().height / height;
	}

	private PImage getImage() {
		return ContentManager.GetIcon(_name);
	}

	public int get_color() {
		return _color;
	}

	public void set_color(int _color) {
		this._color = _color;
	}

}
