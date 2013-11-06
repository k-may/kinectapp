package kinectapp.Interaction.SimpleOpenNI;

import java.util.HashMap;
import java.util.Map;

import processing.core.PVector;

import kinectapp.Interaction.Adapter;
import kinectapp.view.Canvas;
import FrameWork.ICanvas;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import FrameWork.View.View;

public class SONAdapter extends Adapter {

	private Map<Integer, Avatar> _avatars; // <int, Avatar> _avatars;
	private Canvas _canvas;

	@Override
	public void set_canvas(ICanvas canvas) {
		// TODO Auto-generated method stub
		_canvas = (Canvas) canvas;
		_avatars = new HashMap<Integer, Avatar>();
	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			int userId, InteractionType type) {
		// TODO Auto-generated method stub

		Avatar avatar;
		InteractionTargetInfo info = new InteractionTargetInfo();
		
		float localX = x*_canvas.get_width();
		float localY = y*_canvas.get_height();

		info.set_x(localX);
		info.set_y(localY);
		
		if (_avatars.containsKey(userId))
			avatar = _avatars.get(userId);
		else {
			avatar = new Avatar(userId);
			_canvas.addChild(avatar);
			_avatars.put(userId, avatar);
		}

		View target = (View) _canvas.getTargetAtLocation(localX, localY);
		if(target != null){
			info.set_isPressTarget(true);
			PVector absPos = target.get_absPos();
			info.set_pressAttractionX(absPos.x + target.get_width() / 2);
			info.set_pressAttractionY(absPos.y + target.get_height() / 2);
		}
		
		avatar.set_currentTarget(target);
		
		return info;
	}

}
