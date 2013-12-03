package framework.data;

public interface IXMLClient {
	String getImagesPath();
	String getAssetsPath();
	String getTracksPath();
	void writeXML(ImageEntry entry);
	void writeXML(MusicEntry entry);
}
