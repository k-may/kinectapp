package kinectapp.view;

import java.util.ArrayList;

import kinectapp.Controller;
import kinectapp.view.tracks.TrackView;
import processing.core.PApplet;
import processing.core.PVector;
import FrameWork.IMainView;
import FrameWork.Rectangle;
import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.IInteractionView;
import FrameWork.Interaction.InteractionDispatcher;
import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.audio.IAudioView;
import FrameWork.events.TouchEvent;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.IView;
import FrameWork.view.View;
import static processing.core.PApplet.println;

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

	private TrackView _trackMenu;

	private ArrayList<IView> _childs;

	public MainView(PApplet parent) {
		_parent = parent;

		init();
		createChilds();
	}

	private void init() {
		_parent.smooth();
		_currentScene = DefaultScene;
		_controller = Controller.getInstance();
		_dispatcher = new InteractionDispatcher(this);
		_childs = new ArrayList<IView>();
		SCREEN_WIDTH = _parent.width;
		SCREEN_HEIGHT = _parent.height;
	}

	private void createChilds() {
		_trackMenu = new TrackView();
		addChild(_trackMenu);
	}

	public void start() {
		_parent.loop();
	}

	@Override
	public IView getTargetAtLocation(float x, float y) {
		// map values
		x = x * SCREEN_WIDTH;
		y = y * SCREEN_HEIGHT;

		IView element = this; //SceneManager.getScene(_currentScene);

		for (int i = element.get_numChildren() - 1; i >= 0; i--) {
			IView child = element.get_childAt(i);
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

		println("element : " + element);
		if (element == SceneManager.getScene(_currentScene))
			element = null;

		return element;
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

		for (IView child : _childs)
			child.draw(p);

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

		// local data accessible from user data? but only if user exists.
		// Perhaps
		// we should concolidate and initiate the users in a separate area (ie.
		// Adapter)
		float localX = x * SCREEN_WIDTH - pos.x;
		float localY = y * SCREEN_HEIGHT - pos.y;
		new TouchEvent(type, target, localX, localY, pressure, _interactionView.getUser(id)).dispatch();
	}

	@Override
	public Boolean isTouchEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addInteractionView(IInteractionView view) {
		_interactionView = view;
		addChild(_interactionView);
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

	public float get_width() {
		return _parent.width;
	}

	public float get_height() {
		return _parent.height;
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public IAudioView get_audioView() {
		// TODO Auto-generated method stub
		return _trackMenu;
	}

	@Override
	public int get_numChildren() {
		// TODO Auto-generated method stub
		return _childs.size() + 1;
	}

	@Override
	public IView get_childAt(int index) {
		// TODO Auto-generated method stub
		if(index == 0)
			return SceneManager.getScene(_currentScene);
		else
			return _childs.get(index - 1);
	}

	@Override
	public float get_x() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float get_y() {
		// TODO Auto-generated method stub
		return 0;
	}

}
