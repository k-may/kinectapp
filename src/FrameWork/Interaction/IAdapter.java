package FrameWork.Interaction;

import FrameWork.IMainView;

public interface IAdapter {
	void set_canvas(IMainView canvas);
	//convert finite location to screen coords, get press target location, etc.
	InteractionTargetInfo getInteractionInfoAtLocation(float x, float y, int userId,
			InteractionType type);
	void beginInteractionFrame();
	void endInteractionFrame();
}
