package FrameWork.Interaction;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import FrameWork.IMainView;
import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.view.IView;
import FrameWork.view.View;

public class InteractionDispatcher {

	public IMainView _canvas;
	public ArrayList<InteractionHandle> _handles;

	public InteractionDispatcher(IMainView canvas) {
		_canvas = canvas;
		_handles = new ArrayList<InteractionHandle>();
	}

	public void setStream(ArrayList<InteractionStreamData> data) {
		// println("data : " + data.size());
		if (data == null)
			return;

		for (InteractionStreamData d : data)
			seeData(d);

		processHandles();
	}

	private void seeData(InteractionStreamData data) {
		float x = data.get_x();
		float y = data.get_y();
		ArrayList<IView> targets = _canvas.getTargetsAtLocation(x, y);
		/*
		 * for (IView view : targets) { getInteractionHandle(view, data); }
		 */

		// updateHandles(targets, data);
		// }

		// private void updateHandles(InteractionStreamData
		// data,ArrayList<IView> targets) {

		// }

		// private void getInteractionHandle(IView target, InteractionStreamData
		// data) {
		for (InteractionHandle handle : _handles) {
			if (handle.get_id() == data.get_userId()) {
				for (int i = 0; i < targets.size(); i++) {// IView target :
															// targets) {
					if (handle.get_target() == targets.get(i)) {
						handle.add(data);
						targets.remove(i);
						break;// handle;
					}
				}/*
				 * else { if (handle.get_id() == data.get_userId())
				 * handle.cancel(); }
				 */
			}
		}

		for (IView target : targets) {
			if (target != null) {
				println("target : " + target);
				InteractionHandle handle = new InteractionHandle(data.get_userId(), target);
				handle.add(data);
				_handles.add(handle);
			}
		}

	}

	private void processHandles() {

		ArrayList<InteractionHandle> completeHandles = new ArrayList<InteractionHandle>();
		ArrayList<InteractionEvent> events = new ArrayList<InteractionEvent>();
		// println("handles : " + _handles.size());
		for (InteractionHandle handle : _handles) {

			IView target = handle.get_target();
			float x = handle.get_currentX();
			float y = handle.get_currentY();
			InteractionStreamData currentInteraction = handle.get_currentInteraction();
			InteractionStreamData lastInteraction = handle.get_lastInteraction();
			float pressure = handle.getCurrentPressure();

			if (!handle.isUpdated()) {
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

		resetHandles();
	}

	private void resetHandles() {
		// update handles post dispatch (update press state, etc)
		for (InteractionHandle handle : _handles)
			handle.reset();
	}

	private void dispatchEvent(IView target, InteractionEventType type,
			float x, float y, float pressure, int id) {
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
