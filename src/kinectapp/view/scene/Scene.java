package kinectapp.view.scene;

import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.View;

public class Scene extends View {
	public Scene(SceneType type) {
		SceneManager.registerScene(this, type);
		_isTouchEnabled = false;
	}

	@Override
	public Boolean isTouchEnabled() {
		return false;
	}
}
