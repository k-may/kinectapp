package FrameWork.view;

import FrameWork.data.UserData;
import processing.core.PImage;
import stroke.ICanvas;

public interface ICanvasScene {
	ICanvas get_canvas();
	void setState(CanvasState state);
}
