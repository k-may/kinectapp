package FrameWork.scenes;

import java.util.HashMap;
import java.util.Map;

import FrameWork.view.IView;

public class SceneManager {

	private static Map<SceneType, Scene> _sceneMap;

	public static void registerScene(Scene scene, SceneType type) {
		if (_sceneMap == null)
			_sceneMap = new HashMap<SceneType, Scene>();

		_sceneMap.put(type, scene);
	}

	public static Scene getScene(SceneType type) {
		return _sceneMap.get(type);
	}
	
}
