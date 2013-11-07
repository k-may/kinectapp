package FrameWork.Interaction;

import java.util.ArrayList;

import kinectapp.view.MainView;
import static processing.core.PApplet.println;
import processing.core.PVector;

import FrameWork.IMainView;
import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.view.IView;
import FrameWork.view.View;

public class InteractionDispatcher {

	public MainView _canvas;
	public ArrayList<InteractionHandle> _handles;

	public InteractionDispatcher(MainView canvas) {
		_canvas = canvas;
		_handles = new ArrayList<InteractionHandle>();
	}

	public void setStream(ArrayList<InteractionStreamData> data) {
		for (InteractionStreamData d : data)
			seeData(d);

		preUpdateHandles();
	}

	private void seeData(InteractionStreamData data) {
		float x = data.get_x();
		float y = data.get_y();
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
			InteractionHandle handle = new InteractionHandle(data.get_userId(), target);
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
			InteractionStreamData currentInteraction = handle.get_currentInteraction();
			InteractionStreamData lastInteraction = handle.get_lastInteraction();
			float pressure = handle.getCurrentPressure();

			if (handle.isCancelled()) {
				dispatchEvent(target, InteractionEventType.RollOut, x, y, pressure, handle.get_id());
				completeHandles.add(handle);
			} else {
				if (lastInteraction != null) {

					if (currentInteraction.isPressing() && !handle.isPressing())
						dispatchEvent(target, InteractionEventType.PressDown, x, y, pressure, handle.get_id());
					else if (!currentInteraction.isPressing()
							&& handle.isPressing())
						dispatchEvent(target, InteractionEventType.PressUp, x, y, pressure, handle.get_id());

					if (x != lastInteraction.get_x()
							|| y != lastInteraction.get_y())
						dispatchEvent(target, InteractionEventType.Move, x, y, pressure, handle.get_id());

				} else {
					dispatchEvent(target, InteractionEventType.RollOver, x, y, pressure, handle.get_id());

					// if (currentInteraction.isPressing())
					// dispatchEvent(target, InteractionEventType.PressDown, x,
					// y);
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
			float y, float pressure, int id) {
		// println("dispatch : " + type);
		switch (type) {
			case None:
				break;
			case PressDown:
				_canvas.addPressDownEvent(target, x, y, pressure, id);
				break;
			case PressUp:
				_canvas.addPressReleaseEvent(target, x, y, pressure, id);
				break;
			case RollOut:
				_canvas.addRollOutEvent(target, x, y, pressure, id);
				break;
			case RollOver:
				_canvas.addRollOverEvent(target, x, y, pressure, id);
				break;
			case Move:
				_canvas.addMoveEvent(target, x, y, pressure, id);
				break;
		}
	}
}
