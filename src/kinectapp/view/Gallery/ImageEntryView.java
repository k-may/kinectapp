package kinectapp.view.gallery;

import processing.core.PApplet;
import kinectapp.content.GalleryEntry;
import FrameWork.view.View;

public class ImageEntryView extends View {

	private GalleryEntry _entry;
	
	public ImageEntryView(GalleryEntry entry) {
		_entry = entry;
		
	}
	
	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);
		
		p.image(_entry.get_image(), _width, _height);
	}

}
