package FrameWork.Scenes;

import FrameWork.View.View;

public class Scene extends View {
	public Scene(SceneType type) {
		SceneManager.registerScene(this, type);
		_isTouchEnabled = false;
	}
}
