package FrameWork.View;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import static processing.core.PApplet.println;
import FrameWork.Rectangle;
import FrameWork.Events.TouchEvent;
import FrameWork.Interaction.InteractionStreamData;

public class View implements IView {
	
	protected Boolean _isTouchEnabled = true;
	protected float _x;
	protected float _y;
	protected float _width;
	protected float _height;
	private String _name;
	private IView _parent;
	private ArrayList<IView> _childs;
	protected Boolean _invalidated = false;

	public View() {
		_childs = new ArrayList<IView>();
	}

	public View(String name) {
		this();
		_name = name;
		println("new view : " + _name);
	}

	@Override
	public void draw(PApplet p) {
		// TODO Auto-generated method stub
		for (IView view : _childs)
			view.draw(p);
	}

	@Override
	public void addChild(IView child) {
		// TODO Auto-generated method stub
		child.set_parent(this);
		_childs.add(child);
	}

	@Override
	public void removeChild(IView child) {
		// TODO Auto-generated method stub
		child.set_parent(null);

		if (_childs.contains(child))
			_childs.remove(child);
	}

	public float get_x() {
		return _x;
	}

	public void set_x(float _x) {
		this._x = _x;
	}

	public float get_y() {
		return _y;
	}

	public void set_y(float _y) {
		this._y = _y;
	}

	public float get_width() {
		return _width;
	}

	public void set_width(float _width) {
		this._width = _width;
	}

	public float get_height() {
		return _height;
	}

	public void set_height(float _height) {
		this._height = _height;
	}

	@Override
	public void set_parent(IView view) {
		// TODO Auto-generated method stub
		_parent = view;
	}

	@Override
	public IView get_parent() {
		// TODO Auto-generated method stub
		return _parent;
	}

	public PVector get_absPos() {
		// TODO Auto-generated method stub

		Rectangle rect = get_rect();
		IView tempParent = _parent;

		while (tempParent != null) {
			rect = tempParent.get_rect().offset(
					new PVector(rect.get_x(), rect.get_y()));
			tempParent = tempParent.get_parent();
		}

		return new PVector(rect.get_x(), rect.get_y());
	}

	@Override
	public Rectangle get_rect() {
		// TODO Auto-generated method stub
		return new Rectangle((int) _x, (int) _y, (int) _width, (int) _height);
	}

	protected void log(String message) {
		println(message);
	}

	public void removeAllChildren() {
		// TODO Auto-generated method stub
		for (IView child : _childs) {
			removeChild(child);
		}

		_childs = new ArrayList<IView>();
	}

	public int get_numChildren() {
		// TODO Auto-generated method stub
		return _childs.size();
	}

	public View get_childAt(int i) {
		// TODO Auto-generated method stub
		return (View) _childs.get(i);
	}

	public void handleInteraction(TouchEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean isTouchEnabled() {
		return _isTouchEnabled;
	}
}
