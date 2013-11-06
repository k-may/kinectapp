package kinectapp.Content;

import processing.core.PApplet;

public class ContentManager {

	private PApplet _parent;
	
	public ContentManager(PApplet parent) {
		_parent = parent;
		
		initLoading();
	}

	private void initLoading() {
		// TODO Auto-generated method stub
		Assets.LeftHand = _parent.loadImage("hand.png");
		Assets.RightHand = Utils.ReflectImageVertical(Assets.LeftHand);
		
		Assets.LeftPressingHand = _parent.loadImage("handOver.png");
		Assets.RightPressingHand = Utils.ReflectImageVertical(Assets.LeftPressingHand);
		
	//	Assets.ColorGuide = _parent.loadImage("");
		Assets.Font_48 = new FontInfo(_parent.loadFont("GillSansMT-48.vlw"), 48,"GillSansMT-48"); 
	}

}
