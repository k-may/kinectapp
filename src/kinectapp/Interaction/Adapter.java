package kinectapp.Interaction;

import java.util.ArrayList;

import kinectapp.view.MainView;
import FrameWork.IMainView;
import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import FrameWork.data.UserData;
import FrameWork.view.View;

public class Adapter implements IAdapter {

	protected MainView _canvas;
	protected InteractionView _interactionView;

	public Adapter() {
	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			int userId, InteractionType type) {
		// TODO Auto-generated method stub
		InteractionTargetInfo info = new InteractionTargetInfo();
		
		float localX = x * _canvas.get_width();
		float localY = y *  _canvas.get_height();

		UserData user = _interactionView.getUser(userId);
		user.set_updated(true);
		user.set_localX(localX);
		user.set_localY(localY);
		
		View target = (View) _canvas.getTargetAtLocation(localX, localY);
		info.set_isPressTarget(target != null);

		return info;
	}


	@Override
	public void set_canvas(IMainView canvas) {
		// TODO Auto-generated method stub
		_canvas = (MainView) canvas;
		_canvas.addChild(_interactionView);
	}

	@Override
	public void beginInteractionFrame() {
		for (UserData data : _interactionView.get_users()) {
			data.set_updated(false);
		}
	}

	@Override
	public void endInteractionFrame() {
		// TODO Auto-generated method stub
		ArrayList<UserData> staleUsers = new ArrayList<UserData>();
		for (UserData user : _interactionView.get_users()) {
			if (!user.isUpdated())
				staleUsers.add(user);
		}

		for (UserData user : staleUsers) {
			_interactionView.get_users().remove(user);
		}
	}

}
