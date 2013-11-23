package kinectapp.view.canvas;

import kinectapp.view.MainView;
import kinectapp.view.gallery.GalleryView;
import kinectapp.view.labels.LabelButton;
import kinectapp.view.menu.Menu;
import kinectapp.view.scene.Scene;
import processing.core.PApplet;
import FrameWork.audio.IAudioView;
import FrameWork.scenes.SceneType;
import FrameWork.stroke.ICanvas;
import FrameWork.view.CanvasState;
import FrameWork.view.ICanvasScene;
import FrameWork.view.IGallery;

public class CanvasScene extends Scene implements ICanvasScene {

	private GalleryView _gallery;

	private Menu _menu;
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

	private void createChilds() {

		_canvas = new Canvas();
		_canvas.set_width(_width);
		_canvas.set_height(_height);
		//addChild(_canvas);

		_gallery = new GalleryView();
		addChild(_gallery);
		// if (_menu == null) {
		_menu = new Menu();
		//addChild(_menu);
		// }

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
	public IAudioView get_audioView() {
		// TODO Auto-generated method stub
		return _menu.get_trackView();
	}

	@Override
	public void showTracks() {
		_menu.get_trackView().show();
		_menu.showTracks();
	}

	@Override
	public void hideTracks() {
		_menu.get_trackView().hide();
		_menu.hideTracks();
	}
	

}
