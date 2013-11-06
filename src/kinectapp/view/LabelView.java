package kinectapp.view;

import kinectapp.KinectApp;
import kinectapp.Content.FontInfo;
import kinectapp.Content.Utils;
import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.View.View;


public class LabelView extends View {

	protected int _size;
	protected int _color;
	protected String _text;
	protected FontInfo _font;
	protected int _alignment = KinectApp.instance.LEFT;

	public LabelView(String text,int color, FontInfo font) {
		_text = text;
		_color = color;
		_font = font;
		_invalidated = true;
	}
	

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		if(_invalidated){
			_invalidated = false;
			
			get_width();
			get_height();
		}
		
		p.textAlign(_alignment);
		p.textFont(_font.font);
		p.fill(_color);
		PVector pos = get_absPos();
		p.text(_text, pos.x, pos.y + _height);

		super.draw(p);
	}

	public int get_size() {
		return _size;
	}

	public void set_size(int _size) {
		this._size = _size;
		_invalidated = true;
	}

	public int get_color() {
		return _color;
	}

	public void set_color(int _color) {
		this._color = _color;
		_invalidated = true;
	}

	public String get_text() {
		return _text;
	}

	public void set_text(String _text) {
		this._text = _text;
		_invalidated = true;
	}


	public int get_alignment() {
		return _alignment;
	}

	public void set_alignment(int _alignment) {
		this._alignment = _alignment;
		_invalidated = true;
	}
	
	@Override
	public float get_width() {
		// TODO Auto-generated method stub
		_width = Utils.MeasureStringWidth(_font.font, _text);
		return _width;
	}
	
	@Override
	public float get_height() {
		// TODO Auto-generated method stub
		_height = _font.size;
		log("set height: " + _height);
		return _height;
	}

}
