package kinectapp.view;

import processing.core.PApplet;
import processing.core.PImage;
import FrameWork.data.UserData;
import FrameWork.view.View;

public class AvatarView extends View {

	private UserData _user;
	private Boolean _isColorWheelVisible = false;
	private float _colorWheelX;
	private float _colorWheelY;
	private PImage _colorWheel;

	private PImage _icon;

	public AvatarView(UserData user) {
		_user = user;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		p.image(_icon, _user.get_localX(), _user.get_localY());

		if (_isColorWheelVisible) {
			p.image(_colorWheel, _colorWheelX, _colorWheelY);

			updateColor();

		}
		super.draw(p);
	}

	private void updateColor() {
		// TODO Auto-generated method stub

		// if(isOverWheel(_user.get_localX(), _user.get_localY(), _colorWheelX,
		// _colorWheelY))
		// _user.setColor(getColor());
	}

	public void showColorWheel(float x, float y) {
		_isColorWheelVisible = true;
		_colorWheelX = x;
		_colorWheelY = y;
	}

	public void hideColorWheel() {
		_isColorWheelVisible = false;
	}

	public int getColor(float x, float y) {
		return 0xffffffff;
	}
}
