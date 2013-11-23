package kinectapp.view.gallery;

import kinectapp.content.GalleryEntry;
import kinectapp.view.Image;
import processing.core.PImage;

public class ImageEntryView extends Image{

	private GalleryEntry _entry;
	
	public ImageEntryView(GalleryEntry entry) {
		super("");
		_entry = entry;
	}
	
	@Override
	public void set_width(float width) {
		// TODO Auto-generated method stub
		super.set_width(width);
		_scaleY = _scaleX;
	}
	
	@Override
	public void set_height(float height) {
		// TODO Auto-generated method stub
		super.set_height(height);
		_scaleX = _scaleY;
	}
	
	@Override
	protected PImage getImage() {
		return _entry.get_image();
	}

}
