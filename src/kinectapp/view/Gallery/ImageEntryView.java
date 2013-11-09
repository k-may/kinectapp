package kinectapp.view.gallery;

import processing.core.PApplet;
import processing.core.PVector;
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
		
		PVector absPos = get_absPos();
		p.image(_entry.get_image(), absPos.x, absPos.y, _width, _height);
	}
	
	@Override
	public void set_height(float height) {
		_height = height;
		
		float scale = _entry.get_image().height / _height;
		_width = _entry.get_image().width * scale;
	}

}
