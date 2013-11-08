package kinectapp.content;

import FrameWork.data.ImageEntry;
import processing.core.PImage;

public class GalleryEntry {

	private ImageEntry _entry;
	private PImage _image;
	
	public GalleryEntry(ImageEntry entry, PImage image){
		_entry = entry;
		_image = image;
	}

	public String get_title() {
		return _entry.title;
	}

	public String[] get_artists() {
		return _entry.artists;
	}

	public PImage get_image() {
		return _image;
	}
}
