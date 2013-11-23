package FrameWork.view;

import java.util.ArrayList;

import kinectapp.content.GalleryEntry;

public interface IGallery {
	void setImages(ArrayList<GalleryEntry> entries);
	void navigate(String direction);
}
