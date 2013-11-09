package FrameWork.Interaction;

import java.util.ArrayList;

import FrameWork.IMainView;

public interface IAdapter {
	void set_canvas(IMainView canvas);
	//convert finite location to screen coords, get press target location, etc.
	InteractionTargetInfo getInteractionInfoAtLocation(float x, float y, float z, int userId,
			InteractionType type);
	void handleStreamData(ArrayList<InteractionStreamData> data);
	void beginInteractionFrame();
	void endInteractionFrame();
}
