package FrameWork.Interaction;

import FrameWork.Interaction.Types.InteractionEventType;
import FrameWork.view.View;

public class InteractionEvent {
	private InteractionEventType _type;
	private View _target;
	private float _x;
	private float _y;

	public InteractionEvent(View target, InteractionEventType type, float x,
			float y) {
		_target = target;
		_type = type;
		_x = x;
		_y = y;
	}
}
