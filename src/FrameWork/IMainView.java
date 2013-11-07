package FrameWork;

import FrameWork.scenes.SceneType;
import FrameWork.view.IView;
import FrameWork.view.View;

public interface IMainView extends IView {
	IView getTargetAtLocation(float x, float y);

	void setScene(SceneType scene);

	void addPressDownEvent(View target, float x, float y, float pressure, int id);

	void addPressReleaseEvent(View target, float x, float y, float pressure, int id);

	void addRollOverEvent(View target, float x, float y, float pressure, int id);

	void addRollOutEvent(View target, float x, float y, float pressure, int id);

	void addMoveEvent(View target, float x, float y, float pressure, int id);
}
