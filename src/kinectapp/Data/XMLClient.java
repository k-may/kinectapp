package kinectapp.Data;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import FrameWork.Data.ImageEntry;
import FrameWork.Data.MusicEntry;

import com.sun.xml.internal.ws.util.StringUtils;

import kinectapp.KinectApp;
import processing.data.XML;

public class XMLClient {
	private String _filePath = "data.xml";

	private static XMLClient instance;
	private static XML xml;
	

	public static XMLClient getXMLCLientInstance() {
		if (instance == null)
			instance = new XMLClient();

		return instance;
	}

	private XMLClient() {
		xml = KinectApp.instance.loadXML(_filePath);
	}

	public ArrayList<MusicEntry> readMusicEntries() {
		ArrayList<MusicEntry> entries = new ArrayList<MusicEntry>();

		for (XML child : xml.getChildren("music")) {
			String content = child.getContent().trim();
			String artist = child.getString("artist").trim();
			log("content  : " + content + "/ artist : " + artist);
			MusicEntry entry = new MusicEntry(content, artist);
			entries.add(entry);
		}

		return entries;
	}

	public void writeXML(Object obj) {

	}

	public void writeXML(ImageEntry entry) {
		String data = entry.toString();
		XML newChild = xml.addChild("image");
		newChild.setContent(data);
		newChild.setString("title", entry.title);
		newChild.setString("artists", join(entry.artists, ","));
		save();
	}

	public void writeXML(MusicEntry entry) {
		// TODO Auto-generated method stub
		String data = entry.toString();
		XML newChild = xml.addChild("music");
		newChild.setContent(data);
		save();
	}

	private void save() {
		KinectApp.instance.saveXML(xml, "bin/data/" + _filePath);
	}

	public static String join(String r[],String d)
	{
	        if (r.length == 0) return "";
	        StringBuilder sb = new StringBuilder();
	        int i;
	        for(i=0;i<r.length-1;i++)
	            sb.append(r[i]+d);
	        return sb.toString()+r[i];
	}
	/*
	 * private void log(Object obj) { this.log(obj.toString()); }
	 */

	private void log(String msg) {
		println("XMLCLient : " + msg);
	}

	public ArrayList<ImageEntry> readImageEntries() {
		ArrayList<ImageEntry> entries = new ArrayList<ImageEntry>();

		for (XML child : xml.getChildren("image")) {
			String filePath = child.getContent().trim();
			String[] artists = child.getString("artists").split("\\,");
			String title = child.getString("title");
			ImageEntry entry = new ImageEntry(filePath,title, artists);
			entries.add(entry);
		}

		return entries;
	}
}
