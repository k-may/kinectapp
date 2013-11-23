package kinectapp.view.menu;

import FrameWork.events.GallerySelectedEvent;
import FrameWork.events.TouchEvent;
import kinectapp.view.Image;
import kinectapp.view.MainView;
import kinectapp.view.ShadowButton;

public class GalleryButton extends ShadowButton {

	private Image _text;
	private Image _icon;
	private int _iconPaddingLeft = 156;
	
	public GalleryButton(){
		super();
		
		_width = 228;
		_height = Menu.ClosedHeight;
		
		createChilds();
	}
	private void createChilds() {
		_text = new Image("galleryText");
		addChild(_text);
		_text.set_color(0xff000000);
		
		_icon = new Image("galleryIcon");
		addChild(_icon);
		_icon.set_color(MainView.ICON_COLOR);
		_icon.set_x(_iconPaddingLeft);
		
	}

	@Override
	protected void onPress(TouchEvent event) {
		new GallerySelectedEvent().dispatch();
	}
	
	@Override
	public Boolean isPressTarget() {
		return true;
	}
	
	@Override
	protected void onHover(TouchEvent event) {
		// TODO Auto-generated method stub
		super.onHover(event);
		_icon.set_color(_color);
	}
	
	@Override
	protected void onHoverOut(TouchEvent event) {
		// TODO Auto-generated method stub
		super.onHoverOut(event);
		_icon.set_color(MainView.ICON_COLOR);
	}
}
