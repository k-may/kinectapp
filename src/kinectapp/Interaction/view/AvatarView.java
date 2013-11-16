package kinectapp.Interaction.view;

import java.util.Observable;
import java.util.Observer;

import kinectapp.content.ContentManager;
import processing.core.PApplet;
import processing.core.PImage;
import FrameWork.data.UserData;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.View;
import static processing.core.PApplet.println;

public class AvatarView extends View implements Comparable<AvatarView>,
		Observer {

	private UserData _user;
	private Boolean _isColorWheelVisible = false;
	private float _colorWheelX;
	private float _colorWheelY;
	private PImage _colorWheel;

	private PImage _iconPressing;
	private PImage _iconOut;

	private final float WHEEL_MIN_THRESHOLD = 0.0f;
	private final float WHEEL_MAX_THRESHOLD = 0.1f;

	private AvatarCursor _cursor;

	public AvatarView(UserData user) {
		println("new AvatarView : " + user.get_id());
		_user = user;

		createChilds();
	}

	private void createChilds() {
		_iconPressing = ContentManager.GetIcon("handOver");
		_iconOut = ContentManager.GetIcon("handOut");
		_colorWheel = ContentManager.GetIcon("colorWheel");

		_cursor = new AvatarCursor();
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		Boolean isOverPressTarget = _user.isOverPressTarget();
		Boolean isInPressureRange = isInWheelPressureRange();

		// println("isOverPressTarget : " + isOverPressTarget);
		float x = _user.get_localX();
		float y = _user.get_localY();
		int color = 0xff000000;

		if (SceneManager.GetSceneType() == SceneType.Canvas) {
			if (_isColorWheelVisible) {
				if (!isOverPressTarget && isInPressureRange) {
					p.image(_colorWheel, _colorWheelX, _colorWheelY);
					updateColor();
				} else
					hideColorWheel();
			} else {
				if (isInPressureRange)
					showColorWheel(x, y);
			}
			color = _user.getColor();
		}

		_cursor.set_x(x);
		_cursor.set_y(y);
		_cursor.setColor(color);
		_cursor.setPressure(_user.get_pressure());
		_cursor.draw(p);
		_cursor.setPressing(_user.isPressing());
		_cursor.isOverPressTarget(isOverPressTarget);

	}

	private Boolean isInWheelPressureRange() {
		float pressure = _user.get_pressure();
		return (pressure >= WHEEL_MIN_THRESHOLD && pressure < WHEEL_MAX_THRESHOLD);
	}

	private void updateColor() {
		// TODO Auto-generated method stub
		float localX = _user.get_localX();
		float localY = _user.get_localY();
		if (isOverWheel(localX, localY)) {
			float x = localX - _colorWheelX;
			float y = localY - _colorWheelY;
			_user.setColor(GetColor((int) x, (int) y));
		}
	}

	private Boolean isOverWheel(float x, float y) {
		float radius = _colorWheel.width / 2;
		float cX = _colorWheelX + radius;
		float cY = _colorWheelY + radius;

		double dist = Math.sqrt(Math.pow(cX - x, 2) + Math.pow(cY - y, 2));

		return dist <= radius;
	}

	public void showColorWheel(float x, float y) {
		_isColorWheelVisible = true;
		_colorWheelX = x - _colorWheel.width / 2;
		_colorWheelY = y - _colorWheel.height / 2;
	}

	public void hideColorWheel() {
		_isColorWheelVisible = false;
	}

	public int getColor(float x, float y) {
		return 0xffffffff;
	}

	public int get_userId() {
		return _user.get_id();
	}

	public float get_pressure() {
		return _user.get_pressure();
	}

	@Override
	public int compareTo(AvatarView o) {
		// TODO Auto-generated method stub
		return get_userId() - o.get_userId();
	}

	private static int GetColor(int x, int y) {
		PImage wheel = ContentManager.GetIcon("colorWheel");
		return wheel.get(x, y);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
