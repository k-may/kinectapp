package kinectapp.view.gallery;

import java.util.ArrayList;

import processing.core.PApplet;

import kinectapp.content.GalleryEntry;
import kinectapp.view.Image;
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
	private ImageEntryView _currentImage;

	private float _paddingTop = 100;
	private float _paddingBottom = 100;

	private int _index = 0;

	private NavButton _leftButton;
	private NavButton _rightButton;
	private float _buttonPaddingTop = 567;

	private Image _text;
	private Image _icon;
	private float _textPaddingTop;
	private float _iconPaddingLeft = 152;

	public GalleryView() {

		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;
		_textPaddingTop = _height - 93;

		createChilds();
	}

	private void createChilds() {
		_leftButton = new NavButton("left");
		addChild(_leftButton);
		_leftButton.set_y(_buttonPaddingTop);

		_rightButton = new NavButton("right");
		addChild(_rightButton);
		_rightButton.set_x(_width - _rightButton.get_width());
		_rightButton.set_y(_buttonPaddingTop);

		_text = new Image("galleryText");
		_text.set_color(MainView.ICON_COLOR);
		addChild(_text);
		_text.set_y(_textPaddingTop);

		_icon = new Image("galleryIcon");
		_icon.set_color(MainView.ICON_COLOR);
		addChild(_icon);
		_icon.set_y(_textPaddingTop);
		_icon.set_x(_iconPaddingLeft);
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		super.draw(p);
		
		_rightButton.set_x(_width - _rightButton.get_width());

	}
	@Override
	public void setImages(ArrayList<GalleryEntry> entries) {
		_data = entries;

		ImageEntryView view;
		int x = 0;
		_index = 0;
		_entries = new ArrayList<ImageEntryView>();

		for (GalleryEntry entry : _data) {
			view = new ImageEntryView(entry);
			// _scrollable.addChild(view);
			// view.set_x(x);
			view.set_height(get_maxImageHeight());
			// x += view.get_width() + _entryPadding;
			_entries.add(view);
		}
		// _scrollable.set_width(x);

		_currentImage = _entries.get(_index);
		addImage(_currentImage);
	}

	private void addImage(ImageEntryView image) {

		image.set_x((_width - image.get_width()) / 2);
		image.set_y(_paddingTop);
		addChild(image);
	}

	private float get_maxImageHeight() {
		return _height - _paddingTop - _paddingBottom;
	}

	private void clear() {
		// TODO Auto-generated method stub
		// _scrollable.removeAllChildren();
	}

	@Override
	public void navigate(String direction) {
		removeChild(_entries.get(_index));
		int inc = direction == "left" ? -1 : 1;
		_index += inc;
		_index = _index % _data.size();
		if(_index < 0)
			_index += _data.size();
		
		_currentImage = _entries.get(_index);
		addImage(_currentImage);
	}

}
