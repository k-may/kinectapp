package stroke;

import java.util.ArrayList;

import kinectapp.KinectApp;
import static processing.core.PApplet.println;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;
import FrameWork.events.TouchEvent;
import FrameWork.view.View;

public class Canvas extends View implements ICanvas {

	private StrokeHandler _handler;
	private PGraphics _buffer;

	public Canvas() {
		_handler = new StrokeHandler();
	}

	@Override
	public void draw(PApplet p) {
		p.fill(0xffffffff);
		p.rect(_x, _y, _width, _height);

		drawBuffer(p);
		p.image(_buffer, _x, _y);

		super.draw(p);
	}

	private void drawBuffer(PApplet p) {
		if (_buffer == null) {
			_buffer = p.createGraphics((int) _width, (int) _height);
			_buffer.beginDraw();
			_buffer.background(0xffffffff);
		} else
			_buffer.beginDraw();

		_buffer.noFill();

		ArrayList<StrokeFragment> strokes = _handler.getStrokes();
		for (StrokeFragment stroke : strokes) {
			_buffer.stroke(stroke.get_color());
			drawStrokeFragment(stroke.get_startPt(), stroke.get_ctrlPt(), stroke.get_endPt());
		}
		
		_buffer.endDraw();
	}

	private void drawStrokeFragment(PVector pt1, PVector ctrl, PVector pt2) {
		_buffer.beginShape();
		_buffer.vertex(pt1.x, pt1.y);
		_buffer.quadraticVertex(ctrl.x, ctrl.y, pt2.x, pt2.y);
		_buffer.endShape();
		//println(pt1 + " : " + ctrl + " : " + pt2);
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		switch (event.get_interactionType()) {
			case PressDown:
				_handler.start(event.get_localX(), event.get_localY(), event.get_pressure(), event.getUser());
				break;
			case PressUp:
				// case RollOut:
				_handler.end(event.get_localX(), event.get_localY(), event.get_pressure(), event.getUser());
				break;
			// case RollOver:
			case Move:
				_handler.move(event.get_localX(), event.get_localY(), event.get_pressure(), event.getUser());
				break;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		_buffer.background(0xffffffff);
	}

	@Override
	public void save(String filePath) {
		_buffer.save(filePath);
	}

}
