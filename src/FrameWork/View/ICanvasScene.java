package framework.view;

import java.util.ArrayList;

import framework.audio.IAudioView;
import framework.data.GalleryEntry;

public interface ICanvasScene<T> {
	//ICanvas<T> get_canvas();
	IAudioView get_audioView();
	void showTracks();
	void hideTracks();
	void showGallery();
	void hideGallery();
	void navigate(String direction);
	//IGallery<T> getGallery();
	GalleryEntry<T> save(String filePath, String date);
	void setImages(ArrayList<GalleryEntry<T>> images);
}
