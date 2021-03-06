package framework.interaction;

import java.util.ArrayList;
import java.util.Observer;

import framework.IMainView;





public interface IAdapter{
	void set_canvas(IMainView canvas);
	//convert finite location to screen coords, get press target location, etc.
	InteractionTargetInfo getInteractionInfoAtLocation(float x, float y, float z, int userId,
			InteractionType type);
	void handleStreamData(ArrayList<InteractionStreamData> data);
	void beginInteractionFrame();
	void endInteractionFrame();
}
