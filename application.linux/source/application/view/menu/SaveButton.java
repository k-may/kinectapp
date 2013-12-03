package application.view.menu;

import framework.events.SaveCanvasEvent;
import framework.events.TouchEvent;
import application.Image;
import application.MainView;
import application.ShadowButton;


public class SaveButton extends ShadowButton {

	private Image _text;
	private Image _icon;
	private int _iconPaddingLeft = 109;
	
	public SaveButton(){
		super();
		_width = 169;
		_height = Menu.ClosedHeight;
		
		createChilds();
				
	}
	private void createChilds() {
		_text = new Image("saveText");
		addChild(_text);
		_text.set_color(0xff000000);
		
		_icon = new Image("saveIcon");
		addChild(_icon);
		_icon.set_color(MainView.ICON_COLOR);
		_icon.set_x(_iconPaddingLeft);
		
	}
	
	@Override
	protected void onPress(TouchEvent event) {
		new SaveCanvasEvent().dispatch();
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
