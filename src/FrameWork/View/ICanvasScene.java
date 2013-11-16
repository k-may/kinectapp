package FrameWork.view;

import FrameWork.audio.IAudioView;
import FrameWork.data.UserData;
import processing.core.PImage;
import stroke.ICanvas;

public interface ICanvasScene {
	ICanvas get_canvas();
	void setState(CanvasState state);
	IAudioView get_audioView();
}
