package FrameWork.scenes;

import FrameWork.view.View;

public class Scene extends View {
	public Scene(SceneType type) {
		SceneManager.registerScene(this, type);
		_isTouchEnabled = false;
	}
}
