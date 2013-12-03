package application.clients;

import static processing.core.PApplet.println;

import java.util.ArrayList;

import kinectapp.KinectApp;
import processing.data.XML;
import framework.Controller;
import framework.ErrorType;
import framework.clients.IDataClient;
import framework.data.ImageEntry;
import framework.data.MusicEntry;
import framework.events.ErrorEvent;

public class DataXMLClient implements IDataClient{

	private static DataXMLClient instance;
	private static XML dataXML;
	private String _filePath;
	
	public static DataXMLClient getInstance() {
		if (instance == null)
			instance = new DataXMLClient(Controller.DATA_PATH);

		return instance;
	}
	
	private DataXMLClient(String filePath){
		_filePath = filePath;
		loadDataXML();
	}
	
	private void loadDataXML(){
		dataXML = KinectApp.instance.loadXML(_filePath + "config.xml");

		if (dataXML == null) {
			new ErrorEvent(ErrorType.XMLPath, "path '" + _filePath
					+ "' could not be found").dispatch();
			println("cant load");
		}
	}
	
	public void writeImageEntry(ImageEntry entry) {
		String data = entry.toString();
		XML newChild = dataXML.addChild("image");
		newChild.setContent(entry.filePath);
		newChild.setString("title", entry.title);
		newChild.setString("artists", join(entry.artists, ","));
		save();
	}

	public void writeMusicEntry(MusicEntry entry) {
		// TODO Auto-generated method stub
		String data = entry.toString();
		XML newChild = dataXML.addChild("music");
		newChild.setContent(data);
		save();
	}
	
	private void save() {
		KinectApp.instance.saveXML(dataXML, _filePath);
	}

	public ArrayList<MusicEntry> readMusicEntries() {
		ArrayList<MusicEntry> entries = new ArrayList<MusicEntry>();

		for (XML child : dataXML.getChildren("music")) {
			String filePath = Controller.DATA_PATH + child.getContent().trim();
			String artist = child.getString("artist").trim();
			String track = child.getString("name").trim();
			MusicEntry entry = new MusicEntry(filePath, artist, track);
			entries.add(entry);
		}

		return entries;
	}
	

	public ArrayList<ImageEntry> readImageEntries() {
		ArrayList<ImageEntry> entries = new ArrayList<ImageEntry>();

		for (XML child : dataXML.getChildren("image")) {
			String filePath = Controller.DATA_PATH + child.getContent().trim();
			String[] artists = child.getString("artists").split("\\,");
			String title = child.getString("title");
			String date = child.getString("date");
			ImageEntry entry = new ImageEntry(filePath, title, artists, date);
			entries.add(entry);
		}

		return entries;
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

	@Override
	public String getInputType() {
		return dataXML.getChild("input").getContent();
	}
}
