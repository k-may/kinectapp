package FrameWork.Data;

public class MusicEntry{
	public String filePath;
	public String trackName;
	public String artist;
	
	public MusicEntry(String filePath, String artist) {
		this.filePath = filePath;
		this.artist = artist;
		this.trackName = filePath.split("\\.")[0];
	}
}
