package kinectapp.view;

import static processing.core.PApplet.println;
import kinectapp.view.tracks.TrackView;
import processing.core.PApplet;
import FrameWork.BaseMainView;
import FrameWork.Controller;
import FrameWork.Rectangle;
import FrameWork.audio.IAudioView;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.IView;

public class MainView extends BaseMainView {
	private SceneType DefaultScene = SceneType.Home;
	private Controller _controller;

	private TrackView _trackMenu;

	public MainView(PApplet parent) {
		super(parent);
		init();
		createChilds();
	}

	private void init() {
		_parent.smooth();
		_currentScene = DefaultScene;
		_controller = Controller.getInstance();

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
	public IAudioView get_audioView() {
		// TODO Auto-generated method stub
		return _trackMenu;
	}
	
	@Override
	public IView getTargetAtLocation(float x, float y) {
		// map values
		x = x * SCREEN_WIDTH;
		y = y * SCREEN_HEIGHT;

		IView element = this; // SceneManager.getScene(_currentScene);

		for (int i = element.get_numChildren() - 1; i >= 0; i--) {
			IView child = element.get_childAt(i);
			Rectangle rect = child.get_rect();
			if (rect.contains(x, y)) {
				if (child.get_numChildren() == 0 && child.isTouchEnabled()) {
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

		//println("element : " + element);
		if (element == this)
			element = null;

		return element;
	}



}
