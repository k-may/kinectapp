package kinectapp.Interaction.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;

import FrameWork.Interaction.IInteractionView;
import FrameWork.data.UserData;
import FrameWork.view.View;

public class InteractionView extends View implements IInteractionView {

	private ArrayList<UserData> _users;
	private Map<Integer, AvatarView> _avatarViews;

	public InteractionView() {
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
	}

	@Override
	public UserData getUser(int id) {
		for (UserData u : _users) {
			if (u.get_id() == id)
				return u;
		}

		UserData user = new UserData(id);
		addUser(user);
		return new UserData(id);
	}

	public ArrayList<UserData> get_users() {
		return _users;
	}

	@Override
	public Boolean isTouchEnabled() {
		return false;
	}

}
