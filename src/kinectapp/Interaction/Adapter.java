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

	public Adapter() {
	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			int userId, InteractionType type) {
		// TODO Auto-generated method stub
		InteractionTargetInfo info = new InteractionTargetInfo();

		UserData user = _canvas.getUser(userId);
		user.set_updated(true);

		View target = (View) _canvas.getTargetAtLocation(x
				* _canvas.get_width(), y * _canvas.get_height());
		info.set_isPressTarget(target != null);

		return info;
	}


	@Override
	public void set_canvas(IMainView canvas) {
		// TODO Auto-generated method stub
		_canvas = (MainView) canvas;
	}

	@Override
	public void beginInteractionFrame() {
		for (UserData data : _canvas.get_users()) {
			data.set_updated(false);
		}
	}

	@Override
	public void endInteractionFrame() {
		// TODO Auto-generated method stub
		ArrayList<UserData> staleUsers = new ArrayList<UserData>();
		for (UserData user : _canvas.get_users()) {
			if (!user.isUpdated())
				staleUsers.add(user);
		}

		for (UserData user : staleUsers) {
			_canvas.get_users().remove(user);
		}
	}

}
