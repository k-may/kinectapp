package kinectapp.view.home;

import kinectapp.view.MainView;
import kinectapp.view.labels.LabelButton;
import kinectapp.view.scene.Scene;
import processing.core.PApplet;
import FrameWork.scenes.SceneType;
import FrameWork.view.View;

public class HomeScene extends Scene {

	private HomeLabel _homeLabel;
	private LabelButton _canvasButton;

	public HomeScene() {
		super(SceneType.Home);
		
		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;
		
		createChilds();
	}
	
	@Override
	public void draw(PApplet p) {
		p.background(255);
		super.draw(p);
	}

	private void createChilds() {
		_homeLabel = new HomeLabel();
		_homeLabel.set_x((_width - _homeLabel.get_width())/ 2);
		_homeLabel.set_y(100);
		addChild(_homeLabel);
		
		_canvasButton = new LabelButton();
		_canvasButton.setText("Canvas");
		addChild(_canvasButton);
		_canvasButton.set_x(_width - _canvasButton.get_width() - 10);
		_canvasButton.set_y(_height - _canvasButton.get_height() - 10);
	}
}
