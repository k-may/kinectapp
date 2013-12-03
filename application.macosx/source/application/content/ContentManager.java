package application.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kinectapp.KinectApp;
import application.clients.XMLClient;

import framework.ErrorType;
import framework.data.AssetEntry;
import framework.data.FontEntry;
import framework.data.GalleryEntry;
import framework.data.ImageEntry;
import framework.events.ErrorEvent;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import static processing.core.PApplet.println;

public class ContentManager {

	public static String DataPath;
	public static String TracksPath;
	public static String ImagesPath;
	public static String AssetsPath;

	public static Map<String, PIcon> Icons;
	public static Map<String, FontInfo> Fonts;
	public static ArrayList<GalleryEntry> GalleryEntries;
	private static PApplet _parent;
	private static ContentManager _instance;

	public static ContentManager getInstance(PApplet parent, String imagesPath,
			String assetsPath, String tracksPath) {
		if (_instance == null)
			_instance = new ContentManager(parent, imagesPath, assetsPath, tracksPath);

		return _instance;
	}

	private ContentManager(PApplet parent, String imagesPath,
			String assetsPath, String tracksPath) {
		_parent = parent;
		ImagesPath = imagesPath;
		AssetsPath = assetsPath;
		TracksPath = tracksPath;
		DataPath = parent.dataPath("");
	}


	public void loadAssets(KinectApp _parent, XMLClient xmlClient) {
		try{
			loadIcons(_parent, xmlClient.readAssetEntries());
			loadFonts(_parent, xmlClient.readFontEntries());
		}catch(Exception e){
			dispatchAssetError(e.getMessage());
		}
	}
	
	// fonts
	public void loadFonts(PApplet instance, ArrayList<FontEntry> readFontEntries) {

		PFont font;
		String path;
		for (FontEntry entry : readFontEntries) {
			path = AssetsPath + entry.get_filePath();
			try {
				font = instance.loadFont(path);
			} catch (NullPointerException e) {
				dispatchAssetError(path);
				break;
			} catch (RuntimeException e) {
				// if (font == null) {
				dispatchAssetError(path);
				break;
			}

			FontInfo fontInfo = new FontInfo(font, entry);

			if (Fonts == null)
				Fonts = new HashMap<String, FontInfo>();

			Fonts.put(entry.get_name(), fontInfo);
		}
	}
	// icons
	private void loadIcons(PApplet parent, ArrayList<AssetEntry> entries) throws Exception {
		println("DATA PATH : " + parent.dataPath("data") + " SKETCH PATH :"
				+ parent.sketchPath);

		String path;
		PImage icon;
		for (AssetEntry entry : entries) {
			path = AssetsPath + entry.get_filePath();
			icon = parent.loadImage(path);

			if (icon == null) {
				throw new Exception(path);
			}

			PIcon asset = new PIcon(entry, icon);
			if (Icons == null)
				Icons = new HashMap<String, PIcon>();

			Icons.put(entry.get_name(), asset);
		}

	}

	// galleries
	public void loadGalleryEntries(PApplet instance,
			ArrayList<ImageEntry> readImageEntries) {

		PImage image;
		String path;
		for (ImageEntry entry : readImageEntries) {
			path = ImagesPath + entry.filePath;
			image = instance.loadImage(path);
			if (image == null) {
				dispatchAssetError(path);
				break;
			}
			addGalleryImage(entry, instance.loadImage(ImagesPath
					+ entry.filePath));
		}
	}

	private void addGalleryImage(ImageEntry entry, PImage image) {
		printload(entry.title);

		if (image == null)
			return;

		if (GalleryEntries == null)
			GalleryEntries = new ArrayList<GalleryEntry>();

		GalleryEntries.add(new GalleryEntry(entry, image));
	}

	public static ArrayList<GalleryEntry> GetGalleyImages() {
		return GalleryEntries;
	}

	private void printload(String name) {
		println("--> loaded : " + name);
	}

	public static FontInfo GetFont(String name) {
		if (Fonts.containsKey(name))
			return Fonts.get(name);

		return null;
	}

	public static PImage GetIcon(String name) {
		if (Icons.containsKey(name))
			return Icons.get(name).get_image();

		return null;
	}

	private void dispatchAssetError(String path) {
		new ErrorEvent(ErrorType.AssetError, "path '" + path
				+ "' could not be found").dispatch();
	}

}
