package FrameWork.view;

import FrameWork.Rectangle;
import processing.core.PApplet;

public interface IView {
	void draw(PApplet p);
	void addChild(IView child);
	void removeChild(IView child);
	void set_parent(IView view);
	IView get_parent();
	Boolean isTouchEnabled();
	Rectangle get_rect();
}
