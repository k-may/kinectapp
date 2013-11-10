package FrameWork;

import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.IInteractionView;
import FrameWork.audio.IAudioView;
import FrameWork.scenes.SceneType;
import FrameWork.view.IView;
import FrameWork.view.View;

public interface IMainView extends IView {
	IView getTargetAtLocation(float x, float y);
	
	void start();
	
	IAudioView get_audioView();
	
	void addInteractionView(IInteractionView view);
	
	void set_region(IInteractionRegion region);
	
	void showMenu();
	
	void hideMenu();

	void setScene(SceneType scene);

	void addPressDownEvent(View target, float x, float y, float pressure, int id);

	void addPressReleaseEvent(View target, float x, float y, float pressure, int id);

	void addRollOverEvent(View target, float x, float y, float pressure, int id);

	void addRollOutEvent(View target, float x, float y, float pressure, int id);

	void addMoveEvent(View target, float x, float y, float pressure, int id);
}
