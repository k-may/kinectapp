package kinectapp.Interaction.view;

import processing.core.PApplet;
import FrameWork.view.View;

public class AvatarCursor extends View {

	private int _color = 0xffffffff;
	private float _pressure = 0.0f;
	private static final int MAX_RADIUS = 20;
	private static final int MIN_RADIUS = 4;

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
		
		float cRadius = GetRadiusForPressure(_pressure);
		
		p.ellipse(cX, cY, cRadius, cRadius);
	}

	public void setPressure(float pressure) {
		_pressure = pressure;
	}

	public void setColor(int color) {
		_color = color;
	}
	
	public static float GetRadiusForPressure(float pressure){
		return (MAX_RADIUS - MIN_RADIUS) * pressure + MIN_RADIUS;
	}

	public void setPressing(Boolean pressing) {
		// TODO Auto-generated method stub
		
	}

	public void isOverPressTarget(Boolean isOverPressTarget) {
		// TODO Auto-generated method stub
		
	}
}
