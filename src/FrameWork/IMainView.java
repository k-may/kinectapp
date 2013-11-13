package FrameWork;

import java.util.ArrayList;

import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.IInteractionView;
import FrameWork.audio.IAudioView;
import FrameWork.scenes.SceneType;
import FrameWork.view.IView;
import FrameWork.view.View;

public interface IMainView extends IView {
	ArrayList<IView> getTargetsAtLocation(float x, float y);
	
	void start();
	
	IAudioView get_audioView();
	
	void addInteractionView(IInteractionView view);
	
	void set_region(IInteractionRegion region);
	
	void showMenu();
	
	void hideMenu();

	void setScene(SceneType scene);

	void addPressDownEvent(IView target, float x, float y, float pressure, int id);

	void addPressReleaseEvent(IView target, float x, float y, float pressure, int id);

	void addRollOverEvent(IView target, float x, float y, float pressure, int id);

	void addRollOutEvent(IView target, float x, float y, float pressure, int id);

	void addMoveEvent(IView target, float x, float y, float pressure, int id);
}
