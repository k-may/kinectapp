package application.clients;

import static processing.core.PApplet.println;

import java.net.MalformedURLException;
import java.util.ArrayList;

import framework.ErrorType;
import framework.data.AssetEntry;
import framework.data.FontEntry;
import framework.data.IXMLClient;
import framework.data.ImageEntry;
import framework.data.MusicEntry;
import framework.events.ErrorEvent;

import kinectapp.KinectApp;
import processing.data.XML;

public class XMLClient implements IXMLClient {

	private static XMLClient instance;
	private static XML xml;
	private String _filePath;

	public static XMLClient getXMLCLientInstance(String filePath) {
		if (instance == null)
			instance = new XMLClient(filePath);

		return instance;
	}

	private XMLClient(String filePath) {
		_filePath = filePath;
		// KinectApp.instance.
		xml = KinectApp.instance.loadXML(filePath + "data.xml");
		if (xml == null) {
			new ErrorEvent(ErrorType.XMLPath, "path '" + filePath
					+ "' could not be found").dispatch();
			println("cant load");
		}
	}

	public ArrayList<MusicEntry> readMusicEntries() {
		ArrayList<MusicEntry> entries = new ArrayList<MusicEntry>();

		for (XML child : xml.getChildren("music")) {
			String content = child.getContent().trim();
			String artist = child.getString("artist").trim();
			String track = child.getString("name").trim();
			log("content  : " + content + "/ artist : " + artist);
			MusicEntry entry = new MusicEntry(content, artist, track);
			entries.add(entry);
		}

		return entries;
	}

	public void writeXML(Object obj) {

	}

	public void writeXML(ImageEntry entry) {
		String data = entry.toString();
		XML newChild = xml.addChild("image");
		newChild.setContent(entry.filePath);
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
		KinectApp.instance.saveXML(xml, _filePath);
	}

	public static String join(String r[], String d) {
		if (r.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < r.length - 1; i++)
			sb.append(r[i] + d);
		return sb.toString() + r[i];
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
			String date = child.getString("date");
			ImageEntry entry = new ImageEntry(filePath, title, artists, date);
			entries.add(entry);
		}

		return entries;
	}

	public ArrayList<AssetEntry> readAssetEntries() {
		ArrayList<AssetEntry> entries = new ArrayList<AssetEntry>();
		for (XML child : xml.getChildren("asset")) {
			String filePath = child.getContent().trim();
			String name = child.getString("name");
			String type = child.getString("type");
			AssetEntry entry = new AssetEntry(name, type, filePath);
			entries.add(entry);
		}
		return entries;
	}

	public ArrayList<FontEntry> readFontEntries() {
		ArrayList<FontEntry> entries = new ArrayList<FontEntry>();
		for (XML child : xml.getChildren("font")) {
			String filePath = child.getContent().trim();
			String name = child.getString("name");
			int size = child.getInt("size");
			FontEntry entry = new FontEntry(name, filePath, size);
			entries.add(entry);
		}
		return entries;
	}

	@Override
	public String getImagesPath() {
		return xml.getChild("imagepath").getContent().trim();
	}

	@Override
	public String getAssetsPath() {
		return xml.getChild("assetpath").getContent().trim();
	}

	@Override
	public String getTracksPath() {
		return xml.getChild("trackpath").getContent().trim();
	}
}
