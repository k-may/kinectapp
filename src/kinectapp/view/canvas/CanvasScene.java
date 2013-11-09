package kinectapp.view.canvas;

import kinectapp.view.MainView;
import kinectapp.view.gallery.GalleryView;
import kinectapp.view.labels.LabelButton;
import processing.core.PApplet;
import stroke.Canvas;
import stroke.ICanvas;
import FrameWork.scenes.Scene;
import FrameWork.scenes.SceneType;
import FrameWork.view.CanvasState;
import FrameWork.view.ICanvasScene;
import FrameWork.view.IGallery;

public class CanvasScene extends Scene implements ICanvasScene {

	private GalleryView _gallery;

	private Canvas _canvas;
	private LabelButton _saveButton;
	private LabelButton _clearButton;
	private CanvasState _state;

	public CanvasScene() {
		super(SceneType.Canvas);

		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;

		createChilds();
	}
	
	private void createChilds(){
		_canvas = new Canvas();
		_canvas.set_x(10);
		_canvas.set_y(10);
		_canvas.set_width(_width - 20);
		_canvas.set_height(_height - 200);

		_gallery = new GalleryView();

		_saveButton = new LabelButton();
		_saveButton.setText("Save");
		addChild(_saveButton);
		_saveButton.set_x(_width - _saveButton.get_width());
		_saveButton.set_y(_height - _saveButton.get_height());

		_clearButton = new LabelButton();
		_clearButton.setText("Clear");
		addChild(_clearButton);
		_clearButton.set_x(_width - _saveButton.get_x()
				- _clearButton.get_width() - 10);
		_clearButton.set_y(_height - _saveButton.get_height());

	}

	@Override
	public void draw(PApplet p) {
		p.background(0x000);
		super.draw(p);
	}

	public ICanvas getCanvas() {
		return _canvas;
	}

	public IGallery getGallery() {
		return _gallery;
	}

	@Override
	public ICanvas get_canvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(CanvasState state) {
		_state = state;

		switch (_state) {
			case Gallery:
				addChild(_gallery);
				removeChild(_canvas);
				break;
			case Canvas:
				addChild(_canvas);
				removeChild(_gallery);
				break;
		}
	}
}
