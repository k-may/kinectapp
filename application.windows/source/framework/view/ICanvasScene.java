package framework.view;

import framework.audio.IAudioView;
import framework.data.UserData;
import framework.stroke.ICanvas;
import processing.core.PImage;

public interface ICanvasScene {
	ICanvas get_canvas();
	IAudioView get_audioView();
	void showTracks();
	void hideTracks();
	void showGallery();
	void hideGallery();
}
