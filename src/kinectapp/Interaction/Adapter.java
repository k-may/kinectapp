package kinectapp.Interaction;

import java.util.ArrayList;

import kinectapp.Interaction.view.InteractionView;
import kinectapp.view.MainView;
import FrameWork.BaseMainView;
import FrameWork.IMainView;
import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import FrameWork.data.UserData;
import FrameWork.view.IView;
import FrameWork.view.View;

import static processing.core.PApplet.println;

public class Adapter implements IAdapter {

	protected IMainView _canvas;
	protected InteractionView _interactionView;

	public Adapter() {
		_interactionView = new InteractionView();
	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			float z, int userId, InteractionType type) {
		// TODO Auto-generated method stub
		InteractionTargetInfo info = new InteractionTargetInfo();
		ArrayList<IView> targets = _canvas.getTargetsAtLocation(x, y);

		IView target = targets.size() > 0 ? targets.get(targets.size() - 1) : null;

		info.set_isPressTarget(target != null ? target.isPressTarget() : false);
		
		return info;
	}

	@Override
	public void set_canvas(IMainView canvas) {
		// TODO Auto-generated method stub
		_canvas = canvas;
		_canvas.addInteractionView(_interactionView);
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

	@Override
	public void handleStreamData(ArrayList<InteractionStreamData> data) {
		for (InteractionStreamData streamData : data) {

			UserData user = _interactionView.getUser(streamData.get_userId());

			float localX = streamData.get_x() * _canvas.get_width();
			float localY = streamData.get_y() * _canvas.get_height();

			user.set_updated(true);
			user.set_localX(localX);
			user.set_localY(localY);
			user.set_pressure(streamData.get_z());
			// user.set_isPressing(streamData.get_isOverPressTarget())
			user.set_isOverPressTarget(streamData.get_isOverPressTarget());

		}
	}

}
