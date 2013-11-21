package FrameWork.view;

import FrameWork.Rectangle;
import FrameWork.events.TouchEvent;
import processing.core.PApplet;
import processing.core.PVector;

public interface IView {
	void draw(PApplet p);
	void addChild(IView child);
	void removeChild(IView child);
	void set_parent(IView view);
	IView get_parent();
	Boolean isTouchEnabled();
	Boolean isPressTarget();
	Boolean isHoverTarget();
	Rectangle get_rect();
	int get_numChildren();
	IView get_childAt(int index);
	float get_x();
	float get_y();
	float get_width();
	float get_height();
	PVector get_absPos();
	void handleInteraction(TouchEvent event);
}
