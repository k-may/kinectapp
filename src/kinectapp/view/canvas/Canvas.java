package kinectapp.view.canvas;

import java.util.ArrayList;

import kinectapp.KinectApp;
import kinectapp.view.Image;
import kinectapp.view.avatar.AvatarCursor;
import static processing.core.PApplet.println;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import FrameWork.data.UserData;
import FrameWork.events.TouchEvent;
import FrameWork.stroke.ICanvas;
import FrameWork.stroke.StrokeFragment;
import FrameWork.stroke.StrokeHandler;
import FrameWork.view.View;

public class Canvas extends View implements ICanvas {

	private StrokeHandler _handler;
	private PGraphics _buffer;

	private Image _savedDialogue;
	private Boolean _dialogueVisible = false;
	private int _startTime;

	public static int BG_COLOR = 0xff111011;

	public Canvas() {
		_handler = new StrokeHandler();
		_savedDialogue = new Image("savedDialogue");
	}

	@Override
	public void draw(PApplet p) {
		drawBuffer(p);
		p.image(_buffer, _x, _y);

		super.draw(p);

		if (_dialogueVisible) {
			int elapsed = KinectApp.instance.millis() - _startTime;
			if (elapsed > 3000)
				_dialogueVisible = false;

			_savedDialogue.set_x((_width - _savedDialogue.get_width()) / 2);
			_savedDialogue.set_y((_height - _savedDialogue.get_height()) / 2);
			_savedDialogue.draw(p);
		}

	}

	private void drawBuffer(PApplet p) {
		if (_buffer == null) {
			_buffer = p.createGraphics((int) _width, (int) _height);
			_buffer.beginDraw();
			_buffer.background(BG_COLOR);
		} else
			_buffer.beginDraw();

		_buffer.noFill();

		ArrayList<StrokeFragment> strokes = _handler.getStrokes();
		for (StrokeFragment stroke : strokes) {
			_buffer.stroke(stroke.get_color());
			drawStrokeFragment(stroke.get_startPt(), stroke.get_ctrlPt(), stroke.get_endPt(), stroke.get_pressure());
		}

		_buffer.endDraw();
	}

	private void drawStrokeFragment(PVector pt1, PVector ctrl, PVector pt2,
			float pressure) {

		_buffer.beginShape();
		_buffer.strokeWeight(AvatarCursor.GetRadiusForPressure(pressure));
		_buffer.vertex(pt1.x, pt1.y);
		_buffer.quadraticVertex(ctrl.x, ctrl.y, pt2.x, pt2.y);
		_buffer.endShape();
		// println(pt1 + " : " + ctrl + " : " + pt2);
	}

	@Override
	public void handleInteraction(TouchEvent event) {
		UserData data = event.getUser();
		float strokePressure = data.get_strokePressure();

		switch (event.get_interactionType()) {
			case PressDown:
				println("press down");
				_handler.start(event.get_localX(), event.get_localY(), strokePressure, event.getUser());
				break;
			case PressUp:
				// case RollOut:
				// println("press up");
				_handler.end(event.get_localX(), event.get_localY(), strokePressure, event.getUser());
				break;
			// case RollOver:
			case Move:
				_handler.move(event.get_localX(), event.get_localY(), strokePressure, event.getUser());
				break;
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		_buffer.background(BG_COLOR);
	}

	@Override
	public void save(String filePath) {
		_buffer.save(filePath);
		_dialogueVisible = true;
		_startTime = KinectApp.instance.millis();
	}

}
