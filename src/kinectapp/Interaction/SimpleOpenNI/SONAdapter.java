package kinectapp.Interaction.SimpleOpenNI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.PVector;

import kinectapp.Interaction.Adapter;
import kinectapp.view.MainView;
import FrameWork.IMainView;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import FrameWork.view.IView;
import FrameWork.view.View;

public class SONAdapter extends Adapter {

	private Map<Integer, Avatar> _avatars; // <int, Avatar> _avatars;
	private MainView _canvas;

	@Override
	public void set_canvas(IMainView canvas) {
		// TODO Auto-generated method stub
		_canvas = (MainView) canvas;
		_avatars = new HashMap<Integer, Avatar>();
	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y, float z,
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

		ArrayList<IView> targets = _canvas.getTargetsAtLocation(localX, localY);
		/*
		if(target != null){
			info.set_isPressTarget(true);
			PVector absPos = target.get_absPos();
			info.set_pressAttractionX(absPos.x + target.get_width() / 2);
			info.set_pressAttractionY(absPos.y + target.get_height() / 2);
		}
		
		avatar.set_currentTarget(target);
		*/
		return info;
	}

}
