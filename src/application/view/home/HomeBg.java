package application.view.home;

import static processing.core.PApplet.println;
import application.view.MainView;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import framework.view.View;

public class HomeBg extends View {

	private PGraphics _buffer;

	public HomeBg(){
		
		_width = MainView.SCREEN_WIDTH;
		_height = MainView.SCREEN_HEIGHT;
		
	}

	public void draw(PApplet p) {
		if(_buffer == null){
			createBuffer(p);
		}
		
		PVector absPos = get_absPos();
		p.image(_buffer, absPos.x, absPos.y);
	};
	
	private void createBuffer(PApplet p) {
		_buffer = p.createGraphics((int)_width, (int)_height,PApplet.P2D);
		_buffer.beginDraw();
		_buffer.fill(0);
		_buffer.rect(0, 0, _width, _height);
		_buffer.noStroke();
		
		
		int mRW = (int) (_width + 200);
		int mRH = (int) (_height - 200);
		int x = (int) (_width / 2);
		int y = (int) (_height / 2);
		
		int rW;
		int rH;
		float theta;
		for (int i = 0; i < 100; i++) {
			theta = 1 - (float)i / 100;
			rW = (int) (theta * mRW);
			rH = (int) (theta * mRH);
			int color = (int) (35 * ((float)i/100));
			_buffer.fill(color);
			_buffer.ellipse(x, y, rW, rH);
		}
		
		_buffer.filter(PApplet.BLUR, 8.0f);
		_buffer.endDraw();
	}
}
