package FrameWork.Data;

public class ImageEntry {
	public String filePath;
	public String title;
	public String[] artists;

	public ImageEntry(String filePath, String title, String[] artists) {
		this.filePath = filePath;
		this.title = title;
		this.artists = artists;
	}
}
