package FrameWork.view;

import java.util.ArrayList;

import kinectapp.content.GalleryEntry;

public interface IGallery {
	void setImages(ArrayList<GalleryEntry> entries);
	void addImage(GalleryEntry entry);
	void navigate(String direction);
}
