package kinectapp.view.avatar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import processing.core.PApplet;
import static processing.core.PApplet.println;

import FrameWork.Interaction.IInteractionView;
import FrameWork.data.UserData;
import FrameWork.scenes.SceneManager;
import FrameWork.scenes.SceneType;
import FrameWork.view.View;

public class AvatarsView extends View implements IInteractionView {

	private ArrayList<UserData> _users;
	private Map<Integer, AvatarView> _avatarViews;

	public AvatarsView() {
		_users = new ArrayList<UserData>();
		_avatarViews = new HashMap<Integer, AvatarView>();
	}

	@Override
	public void draw(PApplet p) {
		// arrange views by user pressure
		if (_avatarViews.values() != null) {
			List<AvatarView> views = new ArrayList<AvatarView>(_avatarViews.values());
			Collections.sort(views);

			for (AvatarView view : views) {
				view.draw(p);
			}
		}
	}

	@Override
	public void removeUser(UserData user) {
		if (_users.contains(user))
			_users.remove(user);

		if (_avatarViews.containsKey(user.get_id())) {
			AvatarView view = _avatarViews.get(user.get_id());
			_avatarViews.remove(view);
			removeChild(view);
		}
	}

	@Override
	public void addUser(UserData user) {

		_users.add(user);

		AvatarView view = new AvatarView(user);
		_avatarViews.put(user.get_id(), view);
		addChild(view);
		updateCursorMode();
	}

	@Override
	public UserData getUser(int id) {
		for (UserData u : _users) {
			if (u.get_id() == id)
				return u;
		}
		UserData user = new UserData(id);
		addUser(user);
		return user;
	}

	public ArrayList<UserData> get_users() {
		return _users;
	}

	@Override
	public Boolean isTouchEnabled() {
		return false;
	}

	public void setScene(SceneType scene) {
		updateCursorMode();
	}

	private void updateCursorMode() {
		CursorMode mode = getMode();
	}

	private CursorMode getMode() {
		CursorMode mode = SceneManager.GetSceneType() == SceneType.Canvas ? CursorMode.Drawing
				: CursorMode.Navigating;
		return mode;
	}

	public AvatarView getAvatarById(int id) {
		if(_avatarViews.containsKey(id))
			return _avatarViews.get(id);
		
		return null;
	}

}
