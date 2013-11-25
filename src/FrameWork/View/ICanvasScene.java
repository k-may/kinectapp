package FrameWork.view;

import FrameWork.audio.IAudioView;
import FrameWork.data.UserData;
import FrameWork.stroke.ICanvas;
import processing.core.PImage;

public interface ICanvasScene {
	ICanvas get_canvas();
	IAudioView get_audioView();
	void showTracks();
	void hideTracks();
	void showGallery();
	void hideGallery();
}
