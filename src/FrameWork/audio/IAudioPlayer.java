package FrameWork.audio;

import java.util.ArrayList;

import FrameWork.data.MusicEntry;

public interface IAudioPlayer {
	void setEntries(ArrayList<MusicEntry> entries);

	void play(MusicEntry entry);

	void resume();

	void stop();

	void pause();

	void set_volume(float value);
	
	void set_view(IAudioView view);
}