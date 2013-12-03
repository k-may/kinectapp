package framework.clients;

import java.util.ArrayList;

import framework.data.ImageEntry;
import framework.data.MusicEntry;

public interface IDataClient {
	
	String getInputType();
	
	ArrayList<ImageEntry> readImageEntries();
	void writeImageEntry(ImageEntry entry);
	
	ArrayList<MusicEntry> readMusicEntries();
	void writeMusicEntry(MusicEntry entry);
}
