package kinectapp.view.Gallery;

import java.util.ArrayList;

import kinectapp.view.Canvas;
import FrameWork.Data.ImageEntry;
import FrameWork.Scenes.Scene;
import FrameWork.Scenes.SceneType;
import FrameWork.View.IGallery;
import FrameWork.View.View;

public class GalleryScene extends Scene implements IGallery {

	private ArrayList<ImageEntry> _data;
	private ArrayList<ImageEntryView> _entries;
	private View _scrollable;

	private int _entryPadding = 20;
	private float _paddingTop = 100;
	private float _paddingBottom = 100;

	public GalleryScene() {
		super(SceneType.Gallery);

		_width = Canvas.SCREEN_WIDTH;
		_height = Canvas.SCREEN_HEIGHT;

		createChilds();
	}

	private void createChilds() {
		// TODO Auto-generated method stub
		_scrollable = new View("galleryScrollable");
		addChild(_scrollable);
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
