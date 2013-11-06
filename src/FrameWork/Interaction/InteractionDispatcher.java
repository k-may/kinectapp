package FrameWork.Interaction;

import java.util.ArrayList;

import kinectapp.view.Canvas;
import static processing.core.PApplet.println;
import processing.core.PVector;

import FrameWork.ICanvas;
import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.View.IView;
import FrameWork.View.View;

public class InteractionDispatcher {

	public Canvas _canvas;
	public ArrayList<InteractionHandle> _handles;

	public InteractionDispatcher(Canvas canvas) {
		_canvas = canvas;
		_handles = new ArrayList<InteractionHandle>();
	}

	public void setStream(ArrayList<InteractionStreamData> data) {
		for (InteractionStreamData d : data)
			seeData(d);

		preUpdateHandles();
	}

	private void seeData(InteractionStreamData data) {
		float x = data.get_x() * _canvas.get_width();
		float y = data.get_y() * _canvas.get_height();
		View target = (View) _canvas.getTargetAtLocation(x, y);

		getInteractionHandle(target, data);
	}

	private void getInteractionHandle(View target, InteractionStreamData data) {
		for (InteractionHandle handle : _handles) {
			if (handle.get_target() == target
					&& handle.get_id() == data.get_userId()) {
				handle.add(data);
				return;// handle;
			} else {
				if (handle.get_id() == data.get_userId())
					handle.cancel();
			}
		}

		if (target != null) {
			InteractionHandle handle = new InteractionHandle(data.get_userId(),
					target);
			handle.add(data);
			_handles.add(handle);
		}
	}
	
	private void preUpdateHandles() {

		ArrayList<InteractionHandle> completeHandles = new ArrayList<InteractionHandle>();
		ArrayList<InteractionEvent> events = new ArrayList<InteractionEvent>();
		// println("handles : " + _handles.size());
		for (InteractionHandle handle : _handles) {

			View target = handle.get_target();
			float x = handle.get_currentX();
			float y = handle.get_currentY();
			InteractionStreamData currentInteraction = handle
					.get_currentInteraction();
			InteractionStreamData lastInteraction = handle
					.get_lastInteraction();

			if (handle.isCancelled()) {
				dispatchEvent(target, InteractionEventType.RollOut, x, y);
				completeHandles.add(handle);
			} else {
				if (lastInteraction != null) {

					if (currentInteraction.isPressing() && !handle.isPressing())
						dispatchEvent(target, InteractionEventType.PressDown,
								x, y);
					else if (!currentInteraction.isPressing()
							&& handle.isPressing())
						dispatchEvent(target, InteractionEventType.PressUp, x,
								y);
				} else {
					dispatchEvent(target, InteractionEventType.RollOver, x, y);

					if (currentInteraction.isPressing())
						dispatchEvent(target, InteractionEventType.PressDown,
								x, y);
				}
			}
		}

		// clear completed
		for (InteractionHandle handle : completeHandles) {
			_handles.remove(handle);
		}

		postUpdateHandles();
	}

	private void postUpdateHandles() {
		// update handles post dispatch (update press state, etc)
		for (InteractionHandle handle : _handles)
			handle.update();
	}

	private void dispatchEvent(View target, InteractionEventType type, float x,
			float y) {
		println("dispatch : " + type);
		switch (type) {
			case None:
				break;
			case PressDown:
				_canvas.addPressDownEvent(target, x, y);
				break;
			case PressUp:
				_canvas.addPressReleaseEvent(target, x, y);
				break;
			case RollOut:
				_canvas.addRollOutEvent(target, x, y);
				break;
			case RollOver:
				_canvas.addRollOverEvent(target, x, y);
				break;
		}
	}
}
