package kinectapp.view.home;

import kinectapp.view.LabelButton;
import kinectapp.view.MainView;
import processing.core.PApplet;
import FrameWork.scenes.Scene;
import FrameWork.scenes.SceneType;
import FrameWork.view.View;

public class HomeScene extends Scene {

	private HomeLabel _homeLabel;
	private LabelButton _canvasButton;
	private LabelButton _galleryButton;

	public HomeScene() {
		super(SceneType.Home);
		
		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;
		
		createChilds();
	}
	
	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		p.background(255);
		super.draw(p);
	}

	private void createChilds() {
		// TODO Auto-generated method stub
		_homeLabel = new HomeLabel("homeLabel");
		_homeLabel.set_x((_width - _homeLabel.get_width())/ 2);
		_homeLabel.set_y(100);
		addChild(_homeLabel);
		
		_canvasButton = new LabelButton();
		_canvasButton.setText("Canvas");
		addChild(_canvasButton);
		_canvasButton.set_x(_width - _canvasButton.get_width() - 10);
		_canvasButton.set_y(_height - _canvasButton.get_height() - 10);
		
		_galleryButton = new LabelButton();
		_galleryButton.setText("Gallery");
		addChild(_galleryButton);
		_galleryButton.set_x(10);
		_galleryButton.set_y(_height - _galleryButton.get_height() - 10);
	}
}
