package kinectapp.view;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import kinectapp.Controller;
import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.IMainView;
import FrameWork.Rectangle;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.InteractionDispatcher;
import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.data.UserData;
import FrameWork.events.TouchEvent;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.IView;
import FrameWork.view.View;

public class MainView implements IMainView {
	private SceneType _currentScene;
	private PApplet _parent;
	private IInteractionRegion _region;
	private SceneType DefaultScene = SceneType.Home;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	private Controller _controller;
	private InteractionDispatcher _dispatcher;
	private ArrayList<UserData> _users;

	public MainView(PApplet parent) {
		_parent = parent;
		_parent.smooth();
		_currentScene = DefaultScene;
		_controller = Controller.getInstance();
		_dispatcher = new InteractionDispatcher(this);
		_users = new ArrayList<UserData>();

		SCREEN_WIDTH = _parent.width;
		SCREEN_HEIGHT = _parent.height;
	}

	public float get_width() {
		return _parent.width;
	}

	public float get_height() {
		return _parent.height;
	}

	@Override
	public IView getTargetAtLocation(float x, float y) {
		//map values
		x = x * SCREEN_WIDTH;
		y = y * SCREEN_HEIGHT;
		
		View element = SceneManager.getScene(_currentScene);

		for (int i = element.get_numChildren() - 1; i >= 0; i--) {
			View child = element.get_childAt(i);
			Rectangle rect = child.get_rect();
			if (rect.contains(x, y) && child.isTouchEnabled()) {
				if (child.get_numChildren() == 0) {
					element = child;
					break;
				} else {
					x -= element.get_x();
					y -= element.get_y();
					element = child;
					i = element.get_numChildren();
				}
			}
		}

		if (element == SceneManager.getScene(_currentScene))
			element = null;

		return element;
	}

	public IInteractionRegion get_region() {
		return _region;
	}

	public void set_region(IInteractionRegion _region) {
		this._region = _region;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		_region.runInteractions();
		_dispatcher.setStream(_region.getStream());
		_controller.update();

		SceneManager.getScene(_currentScene).draw(p);

	}

	public void start() {
		// TODO Auto-generated method stub
		_parent.loop();
	}

	public IView get_parent() {
		return null;
	}

	@Override
	public void addChild(IView child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeChild(IView child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set_parent(IView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle get_rect() {
		return null;
	}

	@Override
	public void setScene(SceneType scene) {
		//println("set scene : " + scene.toString());
		_currentScene = scene;
	}

	@Override
	public void addPressDownEvent(View target, float x, float y, float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressDown, target, x, y, pressure, id);
	}

	@Override
	public void addPressReleaseEvent(View target, float x, float y, float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressUp, target, x, y, pressure, id);
	}

	@Override
	public void addRollOverEvent(View target, float x, float y, float pressure, int id) {
		addInteractionEvent(InteractionEventType.RollOver, target, x, y, pressure, id);
	}

	@Override
	public void addRollOutEvent(View target, float x, float y, float pressure, int id) {
		addInteractionEvent(InteractionEventType.RollOut, target, x, y, pressure, id);
	}

	public void addMoveEvent(View target, float x, float y, float pressure, int id) {
		addInteractionEvent(InteractionEventType.Move, target, x, y, pressure, id);
	}
	
	private void addInteractionEvent(InteractionEventType type, View target,
			float x, float y, float pressure, int id) {
		PVector pos = target.get_absPos();
		float localX = x*SCREEN_WIDTH - pos.x;
		float localY = y*SCREEN_HEIGHT - pos.y;
		new TouchEvent(type, target, localX, localY, pressure, getUser(id)).dispatch();
	}

	@Override
	public Boolean isTouchEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<UserData> get_users() {
		return _users;
	}
	
	public UserData getUser(int id){
		for (UserData u : _users) {
			if (u.get_id() == id)
				return u;
		}

		return new UserData(id);
	}

}
