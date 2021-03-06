package application.view.avatar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.data.UserData;
import framework.interaction.IInteractionView;
import framework.scenes.SceneManager;
import framework.scenes.SceneType;
import framework.view.View;


import processing.core.PApplet;
import static processing.core.PApplet.println;


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
			_avatarViews.remove(user.get_id());
			removeChild(view);
		}
	}

	@Override
	public UserData addUser(int id) {

		UserData user = new UserData(id);
		_users.add(user);

		AvatarView view = new AvatarView(user);
		_avatarViews.put(user.get_id(), view);
		addChild(view);
		//updateCursorMode();
		
		return user;
	}

	@Override
	public UserData getUser(int id) {
		for (UserData u : _users) {
			if (u.get_id() == id)
				return u;
		}
		
		return null;
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
		//CursorMode mode = getMode();
	}

	private CursorMode getMode() {
		CursorMode mode = SceneManager.GetSceneType() == SceneType.Canvas ? CursorMode.Drawing
				: CursorMode.Navigating;
		return mode;
	}

	public AvatarView getAvatarById(int id) {
		if (_avatarViews.containsKey(id))
			return _avatarViews.get(id);

		return null;
	}

}
