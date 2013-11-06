package FrameWork;

import FrameWork.Scenes.SceneType;
import FrameWork.View.IView;
import FrameWork.View.View;

public interface ICanvas extends IView {
	IView getTargetAtLocation(float x, float y);
	void setScene(SceneType scene);
	void addPressDownEvent(View target, float x, float y);
	void addPressReleaseEvent(View target, float x, float y);
	void addRollOverEvent(View target, float x, float y);
	void addRollOutEvent(View target, float x, float y);
}
