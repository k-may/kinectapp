package kinectapp.view.tracks;

import static processing.core.PApplet.println;

import java.util.ArrayList;
import java.util.Observable;

import kinectapp.KinectApp;
import processing.core.PApplet;
import FrameWork.audio.IAudioPlayer;
import FrameWork.audio.IAudioView;
import FrameWork.data.MusicEntry;
import FrameWork.events.PlayTrackEvent;
import ddf.minim.AudioOutput;
import ddf.minim.AudioPlayer;
import ddf.minim.Controller;
import ddf.minim.Minim;

public class TrackPlayer extends Observable implements IAudioPlayer {

	private PApplet parent;
	private ArrayList<MusicEntry> _entries;
	// audio
	private Minim _minim;
	private AudioPlayer _audioPlayer;
	private AudioOutput _output;

	private float _volume;
	private MusicEntry _currentEntry;

	public TrackPlayer() {
		parent = KinectApp.instance;
		_minim = new Minim(parent);
		_output = _minim.getLineOut();
		// testControls(_output);

		set_volume(0.1f);
	}

	@Override
	public void setEntries(ArrayList<MusicEntry> entries) {
		_entries = entries;
		changed();
	}

	public ArrayList<MusicEntry> get_entries() {
		return _entries;
	}

	public Boolean isPlaying() {
		return _audioPlayer != null ? _audioPlayer.isPlaying() : false;
	}

	@Override
	public void play(MusicEntry entry) {
		// TODO Auto-generated method stub
		if (_currentEntry != entry) {
			_currentEntry = entry;

			if (_audioPlayer != null) {
				_audioPlayer.close();
				_audioPlayer = null;
			}

			// parent.println("play track : " + _currentEntry.filePath);
			_audioPlayer = _minim.loadFile(_currentEntry.filePath);
			set_volume(_volume);
			new PlayTrackEvent(_currentEntry).dispatch();
		}

		_audioPlayer.play();
		changed();

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		_audioPlayer.play();
		changed();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if (_audioPlayer != null)
			_audioPlayer.close();

		_minim.stop();

		_currentEntry = null;
		changed();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		_audioPlayer.pause();
		changed();
	}

	@Override
	public void set_volume(float value) {
		// TODO Auto-generated method stub
		_volume = parent.map(value, 0, 1, 6, -48);

		if (_audioPlayer != null && _audioPlayer.hasControl(Controller.GAIN)) {
			println("set volume: " + _volume);
			_audioPlayer.setGain(_volume);
		}
		changed();
	}

	private void testControls(Controller controller) {
		if (controller.hasControl(Controller.PAN)) {
			println("The output has a pan control.");// , 15);
		} else {
			println("The output doesn'tcontrollerve a pan control.");// , 15);
		}

		if (controller.hasControl(Controller.VOLUME)) {
			println("The output has a volume control.");// , 30);
		} else {
			println("The output doesn't have a volume control.");// , 30);
		}

		if (controller.hasControl(Controller.SAMPLE_RATE)) {
			println("The output has a sample rate control.");// , 45);
		} else {
			println("The output doesn't have a sample rate control.");// , 45);
		}

		if (controller.hasControl(Controller.BALANCE)) {
			println("The output has a balance control.");// , 60);
		} else {
			println("The output doesn't have a balance control.");// , 60);
		}

		if (controller.hasControl(Controller.MUTE)) {
			println("The output has a mute control.");// , 75);
		} else {
			println("The output doesn't have a mute control.");// , 75);
		}

		if (controller.hasControl(Controller.GAIN)) {
			println("The output has a gain control.");// , 90);
		} else {
			println("The output doesn't have a gain control.");// , 105);
		}
	}

	@Override
	public void set_view(IAudioView view) {
		this.addObserver(view);
	}

	private void changed() {
		setChanged();
		notifyObservers();
	}

}
