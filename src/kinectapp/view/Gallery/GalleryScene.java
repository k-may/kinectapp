package kinectapp.view.gallery;

import java.util.ArrayList;

import processing.core.PApplet;

import kinectapp.view.MainView;
import kinectapp.view.LabelButton;
import FrameWork.data.ImageEntry;
import FrameWork.scenes.Scene;
import FrameWork.scenes.SceneType;
import FrameWork.view.IGallery;
import FrameWork.view.View;

public class GalleryScene extends Scene implements IGallery {

	private ArrayList<ImageEntry> _data;
	private ArrayList<ImageEntryView> _entries;
	private View _scrollable;

	private int _entryPadding = 20;
	private float _paddingTop = 100;
	private float _paddingBottom = 100;

	private LabelButton _homeButton;
	private LabelButton _canvasButton;

	public GalleryScene() {
		super(SceneType.Gallery);

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

		_canvasButton = new LabelButton();
		_canvasButton.setText("Canvas");
		addChild(_canvasButton);
		_canvasButton.set_x(_width - _canvasButton.get_width() - 10);
		_canvasButton.set_y(_height - _canvasButton.get_height() - 10);

		_homeButton = new LabelButton();
		_homeButton.setText("Home");
		addChild(_homeButton);
		_homeButton.set_x(10);
		_homeButton.set_y(_height - _homeButton.get_height() - 10);
	}

	public void setImages(ArrayList<ImageEntry> entries) {
		_data = entries;

		clear();
		createEntries();
	}

	private void createEntries() {
		ImageEntryView view;
		int x = 0;

		for (ImageEntry entry : _data) {
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
