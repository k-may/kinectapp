package kinectapp.view.home;

import kinectapp.view.Canvas;
import processing.core.PApplet;
import FrameWork.Scenes.Scene;
import FrameWork.Scenes.SceneType;
import FrameWork.View.View;

public class HomeScene extends Scene {

	private HomeLabel _homeLabel;

	public HomeScene() {
		super(SceneType.Home);
		
		_width = Canvas.SCREEN_WIDTH;
		_height = Canvas.SCREEN_HEIGHT;
		
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
	}
}
