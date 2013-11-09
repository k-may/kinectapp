package kinectapp.Interaction.view;

import processing.core.PApplet;
import FrameWork.view.View;

public class AvatarCursor extends View {

	private int _color = 0xffffffff;
	private float _pressure = 0.0f;
	private final int MAX_RADIUS = 20;
	private final int MIN_RADIUS = 4;

	public AvatarCursor() {

	}
	
	@Override
	public void draw(PApplet p) {

		float cX = _x;
		float cY = _y;
				
		p.stroke(_color);
		p.noFill();
		p.strokeWeight(1);
		p.ellipse(cX, cY, MAX_RADIUS, MAX_RADIUS);
		
		float cRadius = (MAX_RADIUS - MIN_RADIUS) * _pressure + MIN_RADIUS;
		
		p.ellipse(cX, cY, cRadius, cRadius);
	}

	public void setPressure(float pressure) {
		_pressure = pressure;
	}

	public void setColor(int color) {
		_color = color;
	}
}
