package FrameWork.Interaction;

import FrameWork.ICanvas;
import FrameWork.View.IView;

public interface IAdapter {
	void set_canvas(ICanvas canvas);
	//convert finite location to screen coords, get press target location, etc.
	InteractionTargetInfo getInteractionInfoAtLocation(float x, float y, int userId,
			InteractionType type);
}
