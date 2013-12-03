package application.view.home;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import application.content.ContentManager;
import application.view.GlowingImage;
import application.view.Image;
import application.view.MainView;
import application.view.scene.Scene;
import framework.data.GalleryEntry;
import framework.scenes.IHomeScene;
import framework.scenes.SceneType;
import framework.view.IGallery;

import static processing.core.PApplet.println;

public class HomeScene extends Scene implements IHomeScene<PImage> {

	private Boolean _ready = false;
	private HomeBg _bg;
	private HomeGallery _gallery;

	private GlowingImage _welcome;
	private GlowingImage _start;

	private Image _poweredByText;

	public static int BG_COLOR = 0xff111011;

	public HomeScene() {
		super(SceneType.Home);

		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;

		createChilds();
	}

	@Override
	public void draw(PApplet p) {

		p.background(0xff000000);

		if (_invalidated) {
			float wX = (_width - _welcome.get_width()) / 2;
			float wY = (_height - _welcome.get_height()) / 2;

			_welcome.set_x(wX);
			_welcome.set_y(wY);

			_start.set_x((_width - _start.get_width()) / 2);
			_start.set_y((_height - _start.get_height()) / 2);

			float pX = (_width - _poweredByText.get_width()) / 2;
			float pY = (_height - _poweredByText.get_height());

			_poweredByText.set_x(pX);
			_poweredByText.set_y(pY);

			_invalidated = false;
		}

		super.draw(p);
	}

	private void createChilds() {

		_bg = new HomeBg();
		addChild(_bg);

		_gallery = new HomeGallery();
		addChild(_gallery);
		_gallery.set_x(50);
		_gallery.set_y(100);
		_gallery.set_width(_width - 100);
		float scale = _height / _width;
		_gallery.set_height(scale * (_width - 100));

		_welcome = new GlowingImage("welcomeText");

		_start = new GlowingImage("startText");
		
		_poweredByText = new Image("poweredByText");
		addChild(_poweredByText);

		_invalidated = true;
	}

	@Override
	public void setReady(Boolean value) {
		if (value) {
			addChild(_start);
			removeChild(_welcome);
		} else {
			removeChild(_start);
			addChild(_welcome);
		}
		_ready = value;
	}

	@Override
	public IGallery getGallery() {
		return _gallery;
	}

	@Override
	public void setImages(ArrayList<GalleryEntry<PImage>> images) {
		_gallery.setImages(images);
	}
}
