package kinectapp.view.menu;
import static processing.core.PApplet.println;
import FrameWork.data.MusicEntry;
import FrameWork.events.TouchEvent;
import FrameWork.events.OpenTracksEvent;
import kinectapp.content.ContentManager;
import kinectapp.view.MainView;
import kinectapp.view.ShadowButton;
import kinectapp.view.labels.LabelView;
import kinectapp.view.Image;

public class NowPlayingButton extends ShadowButton {

	private Image _text;
	private Image _icon;
	
	private int _textPaddingLeft = 8;
	private int _iconPaddingLeft = 280;
	
	private LabelView _trackLabel;
	
	public NowPlayingButton(){
		super();
		_width = 362;
		_height = Menu.ClosedHeight;
		
		createChilds();
	}
	
	
	private void createChilds() {
		_text = new Image("nowPlayingText");
		addChild(_text);
		_text.set_color(0xff000000);
		
		_icon = new Image("musicIcon");
		_icon.set_x(_iconPaddingLeft);
		addChild(_icon);
		_icon.set_color(MainView.ICON_COLOR);
	}

	@Override
	public void dispatchPress(TouchEvent event) {
		new OpenTracksEvent().dispatch();
	}

	@Override
	public Boolean isHoverTarget() {
		return true;
	}
	
	@Override
	public Boolean isPressTarget() {
		return false;
	}

	@Override
	protected void dispatchHover(TouchEvent event) {
		_icon.set_color(_color);
	}

	@Override
	protected void dispatchHoverOut(TouchEvent event) {
		_icon.set_color(_color);
	}
	
	public void setTrackEntry(MusicEntry entry) {
		String text = entry.artist + ",\"" + entry.trackName + "\"";

		if (_trackLabel == null)
			_trackLabel = new LabelView(text, MainView.TEXT_COLOR, ContentManager.GetFont("small"));
		else
			_trackLabel.set_text(text);

		_trackLabel.set_x(_textPaddingLeft);
		_trackLabel.set_y(15);
		addChild(_trackLabel);
	}

}
