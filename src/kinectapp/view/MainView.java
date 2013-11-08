package kinectapp.view;

import static processing.core.PApplet.println;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kinectapp.Controller;
import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.IMainView;
import FrameWork.Rectangle;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.IInteractionView;
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
	private IInteractionView _interactionView;
	// private ArrayList<UserData> _users;
	// private Map<Integer, AvatarView> _avatarViews;

	private ArrayList<IView> _childs;

	public MainView(PApplet parent) {
		_parent = parent;
		_parent.smooth();
		_currentScene = DefaultScene;
		_controller = Controller.getInstance();
		_dispatcher = new InteractionDispatcher(this);
		// _users = new ArrayList<UserData>();
		_childs = new ArrayList<IView>();

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
		// map values
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

		for (IView child : _childs)
			child.draw(p);

		SceneManager.getScene(_currentScene).draw(p);

	}

	private void arrangeUsers() {
		// TODO Auto-generated method stub

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
		_childs.add(child);
	}

	@Override
	public void removeChild(IView child) {
		_childs.remove(child);
	}

	@Override
	public void set_parent(IView view) {
	}

	@Override
	public Rectangle get_rect() {
		return null;
	}

	@Override
	public void setScene(SceneType scene) {
		_currentScene = scene;
	}

	@Override
	public void addPressDownEvent(View target, float x, float y,
			float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressDown, target, x, y, pressure, id);
	}

	@Override
	public void addPressReleaseEvent(View target, float x, float y,
			float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressUp, target, x, y, pressure, id);
	}

	@Override
	public void addRollOverEvent(View target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.RollOver, target, x, y, pressure, id);
	}

	@Override
	public void addRollOutEvent(View target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.RollOut, target, x, y, pressure, id);
	}

	public void addMoveEvent(View target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.Move, target, x, y, pressure, id);
	}

	private void addInteractionEvent(InteractionEventType type, View target,
			float x, float y, float pressure, int id) {
		PVector pos = target.get_absPos();
		
		//local data accessible from user data? but only if user exists. Perhaps
		//we should concolidate and initiate the users in a separate area (ie. Adapter)
		float localX = x * SCREEN_WIDTH - pos.x;
		float localY = y * SCREEN_HEIGHT - pos.y;
		new TouchEvent(type, target, localX, localY, pressure, _interactionView.getUser(id)).dispatch();
	}

	@Override
	public Boolean isTouchEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addInteractionView(IInteractionView view){
		_interactionView = view;
		addChild(_interactionView);
	}

}
