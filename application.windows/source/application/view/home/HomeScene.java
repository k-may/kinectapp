package application.view.home;

import framework.scenes.IHomeScene;
import framework.scenes.SceneType;
import application.MainView;
import application.view.labels.LabelButton;
import application.view.scene.Scene;
import processing.core.PApplet;

public class HomeScene extends Scene implements IHomeScene{

	private LabelButton _canvasButton;
	private WelcomeLabel _welcomeLabel;

	public HomeScene() {
		super(SceneType.Home);

		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;

		
		createChilds();
	}

	@Override
	public void draw(PApplet p) {
		p.background(10);

		_canvasButton.set_x((_width - _canvasButton.get_width())/2);
		_canvasButton.set_y(300);
		
		float welcomeWidth = _welcomeLabel.get_width();
		_welcomeLabel.set_x((_width - welcomeWidth)/2);
		
		super.draw(p);
	}

	private void createChilds() {
		_canvasButton = new LabelButton();
		_canvasButton.setText("START");
		addChild(_canvasButton);

		_welcomeLabel = new WelcomeLabel();
		addChild(_welcomeLabel);

	}

	@Override
	public void setReady() {
		removeChild(_welcomeLabel);
	}
}
