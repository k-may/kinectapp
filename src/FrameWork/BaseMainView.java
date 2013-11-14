package FrameWork;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
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

public class BaseMainView implements IMainView {
	protected InteractionDispatcher _dispatcher;
	protected ArrayList<IView> _childs;
	protected PApplet _parent;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	protected SceneType _currentScene;
	protected IInteractionRegion _region;
	private IInteractionView _interactionView;


	public BaseMainView(PApplet parent) {
		_parent = parent;

		init();
	}

	private void init() {
		_dispatcher = new InteractionDispatcher(this);
		_childs = new ArrayList<IView>();
	}

	@Override
	public void setScene(SceneType scene) {
		_currentScene = scene;
	}

	@Override
	public ArrayList<IView> getTargetsAtLocation(float x, float y) {
		return null;
	}

	@Override
	public void addPressDownEvent(IView target, float x, float y,
			float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressDown, target, x, y, pressure, id);
	}

	@Override
	public void addPressReleaseEvent(IView target, float x, float y,
			float pressure, int id) {
		addInteractionEvent(InteractionEventType.PressUp, target, x, y, pressure, id);
	}

	@Override
	public void addRollOverEvent(IView target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.RollOver, target, x, y, pressure, id);
	}

	@Override
	public void addRollOutEvent(IView target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.RollOut, target, x, y, pressure, id);
	}

	public void addMoveEvent(View target, float x, float y, float pressure,
			int id) {
		addInteractionEvent(InteractionEventType.Move, target, x, y, pressure, id);
	}

	protected void addInteractionEvent(InteractionEventType type, IView target,
			float x, float y, float pressure, int id) {
		PVector pos = target.get_absPos();

		// local data accessible from user data? but only if user exists.
		// Perhaps
		// we should concolidate and initiate the users in a separate area (ie.
		// Adapter)
		float localX = x * SCREEN_WIDTH - pos.x;
		float localY = y * SCREEN_HEIGHT - pos.y;
		new TouchEvent(type, target, localX, localY, pressure, _interactionView.getUser(id), _parent.millis()).dispatch();
	}
	
	@Override
	public Boolean isTouchEnabled() {
		// TODO Auto-generated method stub
		return false;
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
		return null;
	}

	@Override
	public int get_numChildren() {
		// TODO Auto-generated method stub
		return _childs.size() + 1;
	}

	@Override
	public IView get_childAt(int index) {
		// TODO Auto-generated method stub
		if (index == 0)
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

	@Override
	public Boolean isPressTarget() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_region(IInteractionRegion _region) {
		this._region = _region;
	}

	@Override
	public void addInteractionView(IInteractionView view) {
		_interactionView = view;
		addChild(_interactionView);
	}

	@Override
	public PVector get_absPos() {
		// TODO Auto-generated method stub
		return new PVector(0,0);
	}

	@Override
	public void addMoveEvent(IView target, float x, float y, float pressure,
			int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IInteractionRegion get_region() {
		// TODO Auto-generated method stub
		return _region;
	}

}
