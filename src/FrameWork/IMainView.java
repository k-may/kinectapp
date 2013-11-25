package FrameWork;

import java.util.ArrayList;

import FrameWork.Interaction.IInteractionRegion;
import FrameWork.Interaction.IInteractionView;
import FrameWork.audio.IAudioView;
import FrameWork.scenes.SceneType;
import FrameWork.view.CanvasState;
import FrameWork.view.IView;
import FrameWork.view.View;

public interface IMainView extends IView {
	ArrayList<IView> getTargetsAtLocation(float x, float y);

	void start();

	IInteractionRegion get_region();

	void addInteractionView(IInteractionView view);

	void set_region(IInteractionRegion region);

	void showMenu();

	void hideMenu();
	
	void addPressDownEvent(IView target, float x, float y, float pressure,
			int id);

	void addPressReleaseEvent(IView target, float x, float y, float pressure,
			int id);

	void addRollOverEvent(IView target, float x, float y, float pressure, int id);

	void addCancelEvent(IView target, float x, float y, float pressure, int id);

	void addMoveEvent(IView target, float x, float y, float pressure, int id);

	void startHover(int userID, int interval, IView target);
	
	void endHover(int userID);

	void addHoverStartEvent(IView target, float x, float y, float pressure,
			int id);

	void addHoverEndEvent(IView target, float x, float y, float pressure, int id);
	
	CanvasState get_currentState();
	
	void set_currentState(CanvasState state);
}
