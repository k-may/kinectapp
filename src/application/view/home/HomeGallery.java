package application.view.home;

import java.awt.List;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import framework.data.GalleryEntry;
import framework.view.IGallery;
import framework.view.View;

public class HomeGallery extends View implements IGallery<PImage>{

	private int _lastUpdate = 0;
	private final int TTime = 1000;
	private int _index = 0;
	private ArrayList<PImage> _images;
	private int _imageCount = 0;
	
	public HomeGallery(){
		_images = new ArrayList<PImage>();
		
		
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);
		
		if(_imageCount == 0)
			return;
		
		if(p.millis() - _lastUpdate > TTime ){
			_lastUpdate = p.millis();
			navigate("right");
		}
		
		//draw current image
		PVector absPos = get_absPos();
		p.image(_images.get(_index), absPos.x, absPos.y, _width, _height);
	}
	
	
	@Override
	public void setImages(ArrayList<GalleryEntry<PImage>> entries) {
		
		if(entries == null)
			return;
		
		for (GalleryEntry<PImage> entry : entries) {
			addImage(entry);
		}
	}

	@Override
	public void addImage(GalleryEntry<PImage> entry) {
		_images.add(entry.get_image());
		_imageCount = _images.size();
	}

	@Override
	public void navigate(String direction) {
		
		_index = _index % _images.size();
		if (_index < 0)
			_index += _images.size();
	}
}
