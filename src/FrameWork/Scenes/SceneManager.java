package FrameWork.scenes;

import java.util.HashMap;
import java.util.Map;

import kinectapp.view.scene.Scene;

import FrameWork.view.IView;

public class SceneManager {

	private static Map<SceneType, IView> _sceneMap;

	public static void registerScene(IView scene, SceneType type) {
		if (_sceneMap == null)
			_sceneMap = new HashMap<SceneType, IView>();

		_sceneMap.put(type, scene);
	}

	public static IView getScene(SceneType type) {
		return _sceneMap.get(type);
	}
	
}
