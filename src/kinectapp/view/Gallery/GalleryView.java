package kinectapp.view.gallery;

import java.util.ArrayList;

import processing.core.PApplet;

import kinectapp.content.GalleryEntry;
import kinectapp.view.MainView;
import kinectapp.view.labels.LabelButton;
import kinectapp.view.scene.Scene;
import FrameWork.data.ImageEntry;
import FrameWork.scenes.SceneType;
import FrameWork.view.IGallery;
import FrameWork.view.View;

public class GalleryView extends View implements IGallery {

	private ArrayList<GalleryEntry> _data;
	private ArrayList<ImageEntryView> _entries;
	private View _scrollable;

	private int _entryPadding = 20;
	private float _paddingTop = 100;
	private float _paddingBottom = 100;

	private LabelButton _homeButton;
	private LabelButton _canvasButton;

	public GalleryView() {
		
		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;

		createChilds();
	}

	@Override
	public void draw(PApplet p) {
		p.background(0xffffff);

		super.draw(p);

	}

	private void createChilds() {
		// TODO Auto-generated method stub
		_scrollable = new View("galleryScrollable");
		addChild(_scrollable);
	}

	@Override
	public void setImages(ArrayList<GalleryEntry> entries) {
		_data = entries;

		clear();
		createEntries();
	}

	private void createEntries() {
		ImageEntryView view;
		int x = 0;

		for (GalleryEntry entry : _data) {
			view = new ImageEntryView(entry);
			_scrollable.addChild(view);
			view.set_x(x);
			view.set_height(get_maxImageHeight());
			x += view.get_width() + _entryPadding;
		}
		_scrollable.set_width(x);
	}

	private float get_maxImageHeight() {
		return _height - _paddingTop - _paddingBottom;
	}

	private void clear() {
		// TODO Auto-generated method stub
		_scrollable.removeAllChildren();
	}


}
