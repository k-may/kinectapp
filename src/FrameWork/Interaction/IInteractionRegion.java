package FrameWork.Interaction;

import java.util.ArrayList;

/*
 * Interface for containing and coordinating interactions
 * 
 * Provides an interaction stream
 */
public interface IInteractionRegion {
	void runInteractions();
	ArrayList<InteractionStreamData> getStream();
	IAdapter get_adapter();
	String get_name();
}
