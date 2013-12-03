package application.interaction;

import java.util.ArrayList;

import framework.IMainView;
import framework.data.UserData;
import framework.interaction.IAdapter;
import framework.interaction.InteractionStreamData;
import framework.interaction.InteractionTargetInfo;
import framework.interaction.InteractionType;
import framework.pressing.PressStateFactory;
import framework.view.IView;



import application.view.avatar.AvatarsView;

import processing.core.PVector;

import static processing.core.PApplet.println;

public class Adapter implements IAdapter {

	protected IMainView _canvas;
	protected AvatarsView _avatarsView;
	private PressStateFactory _pressStateFactory;

	public Adapter() {
		_avatarsView = new AvatarsView();
		_pressStateFactory = new PressStateFactory(0.1f, 0.3f, 0.5f);

	}

	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			float z, int userId, InteractionType type) {
		// TODO Auto-generated method stub
		InteractionTargetInfo info = new InteractionTargetInfo();
		ArrayList<IView> targets = _canvas.getTargetsAtLocation(x, y);

		IView target = targets.size() > 0 ? targets.get(targets.size() - 1)
				: null;
		Boolean isPressTarget = target != null ? target.isPressTarget() : false;
		
		
		info.set_isPressTarget(isPressTarget);

		Boolean isHoverTarget = target != null ? target.isHoverTarget() : false;
		info.set_isHoverTarget(isHoverTarget);

		if (isPressTarget) {
			PVector targetAbsPos = target.get_absPos();
			float targetWidth = target.get_width();
			float targetHeight = target.get_height();
			float attrX = (targetAbsPos.x + targetWidth / 2)
					/ _canvas.get_width();
			float attrY = (targetAbsPos.y + targetHeight / 2)
					/ _canvas.get_height();

			info.set_pressAttractionX(attrX);
			info.set_pressAttractionY(attrY);
		}
		return info;
	}

	@Override
	public void set_canvas(IMainView canvas) {
		// TODO Auto-generated method stub
		_canvas = canvas;
		_canvas.addInteractionView(_avatarsView);
	}

	@Override
	public void beginInteractionFrame() {
		for (UserData data : _avatarsView.get_users()) {
			data.set_updated(false);
		}
	}

	@Override
	public void endInteractionFrame() {
		// TODO Auto-generated method stub
		ArrayList<UserData> staleUsers = new ArrayList<UserData>();
		for (UserData user : _avatarsView.get_users()) {
			if (!user.isUpdated())
				staleUsers.add(user);
		}

		for (UserData user : staleUsers) {
			_avatarsView.removeUser(user);
		}
	}

	@Override
	public void handleStreamData(ArrayList<InteractionStreamData> data) {
		// println("---handleStream---");
		for (InteractionStreamData streamData : data) {

			UserData user = _avatarsView.getUser(streamData.get_userId());
			
			if(user == null)
				user = _avatarsView.addUser(streamData.get_userId());

			float localX = streamData.get_x() * _canvas.get_width();
			float localY = streamData.get_y() * _canvas.get_height();

			user.set_pressStateData(_pressStateFactory.getStateData(streamData.get_z(), _canvas.get_currentState(), streamData.isOverTarget()));
			user.set_updated(true);
			user.set_localX(localX);
			user.set_localY(localY);
			user.set_isPressing(streamData.isPressing());
			user.set_pressPressure(streamData.get_pressPressure());
			user.set_isOverPressTarget(streamData.isOverPressTarget());
			user.set_isOverHoverTarget(streamData.isOverHoverTarget());

		}
	}

}
