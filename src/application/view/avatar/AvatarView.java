package application.view.avatar;

import application.content.ContentManager;
import application.view.MainView;
import de.looksgood.ani.Ani;
import framework.IMainView;
import framework.data.UserData;
import framework.events.TouchEvent;
import framework.interaction.Types.InteractionEventType;
import framework.pressing.PressState;
import framework.view.IView;
import framework.view.View;

import kinectapp.KinectApp;
import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PApplet.println;

public class AvatarView extends View implements Comparable<AvatarView> {

	private IView _hoverTarget;
	private UserData _user;
	private Boolean _isColorWheelVisible = false;
	private float _colorWheelX;
	private float _colorWheelY;
	private PImage _colorWheel;
	private Ani _hoverAnimation;

	private PressState _currentState;

	private AvatarCursor _cursor;

	public AvatarView(UserData user) {
		println("new AvatarView : " + user.get_id());
		_user = user;

		createChilds();
	}

	private void createChilds() {
		_colorWheel = ContentManager.GetIcon("colorWheel");
		_cursor = new AvatarCursor();
		addChild(_cursor);
	}

	@Override
	public void draw(PApplet p) {
		Boolean isPressing = _user.isPressing();

		float x = _user.get_localX();
		float y = _user.get_localY();

		PressState state = _user.get_pressStateData().get_state();
		_cursor.set_pressing(isPressing, _user.get_pressPressure());

		switch (state) {
			case Start:
				drawStart(p);
				break;
			case ColorSelection:

				if (!_isColorWheelVisible)
					showColorWheel(x, y);

				drawColorSelection(p);
				break;
			case PreDrawing:
				drawPreDrawing(p);
				break;
			case Drawing:
				drawDrawing(p);
				break;
		}

		if (state != PressState.ColorSelection && _isColorWheelVisible)
			hideColorWheel();

		_cursor.set_mode(getCursorMode());
		_cursor.set_navPressure(_user.get_navigationPressure());
		_cursor.set_strokePressure(_user.get_strokePressure());
		_cursor.set_x(x);
		_cursor.set_y(y);
		_cursor.setColor(_user.getColor());

		_currentState = state;

		super.draw(p);
	}

	public CursorMode getCursorMode() {
		PressState state = _user.get_pressStateData().get_state();
		CursorMode mode = CursorMode.Navigating;

		switch (state) {
			case Drawing:
				mode = CursorMode.Drawing;
				break;
		}

		mode = _user.isOverPressTarget() ? mode.Pressing : mode;

		return mode;
	}

	private void drawDrawing(PApplet p) {
	}

	private void drawPreDrawing(PApplet p) {
	}

	private void drawColorSelection(PApplet p) {
		// hide color wheel slightly if over press target
		if (_user.isOverTouchTarget())
			p.tint(255, 120);
		else
			p.noTint();

		p.image(_colorWheel, _colorWheelX, _colorWheelY);

		updateColor();
	}

	private void drawStart(PApplet p) {
	}

	private void updateColor() {
		float localX = _user.get_localX();
		float localY = _user.get_localY();
		if (isOverWheel(localX, localY)) {
			float x = localX - _colorWheelX;
			float y = localY - _colorWheelY;
			int color = GetColor((int) x, (int) y);
			_user.setColor(color);
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

	public int get_userId() {
		return _user.get_id();
	}

	@Override
	public int compareTo(AvatarView o) {
		return get_userId() - o.get_userId();
	}

	private static int GetColor(int x, int y) {
		PImage wheel = ContentManager.GetIcon("colorWheel");
		return wheel.get(x, y);
	}

	public void startLoad(int interval, float value, IView target) {
		_hoverTarget = target;
		_hoverAnimation = new Ani(_cursor, interval / 1000, "loadRatio", value, Ani.EXPO_OUT, "onEnd:onHoverEnd");
	}

	public void cancelHover() {
		if (_hoverTarget != null)
			_hoverTarget = null;

		if (_hoverAnimation != null)
			_hoverAnimation.end();

		_cursor.loadRatio = 0.0f;
	}

}
