package kinectapp.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FrameWork.data.AssetEntry;
import FrameWork.data.FontEntry;
import FrameWork.data.ImageEntry;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import static processing.core.PApplet.println;

public class ContentManager {

	public static Map<String, PIcon> Icons;
	public static Map<String, FontInfo> Fonts;
	public static ArrayList<GalleryEntry> GalleryEntries;

	//icons
	public static void loadIcons(PApplet parent, ArrayList<AssetEntry> entries) {
		for (AssetEntry entry : entries) {
			loadIcon(parent, entry);
		}
	}

	private static void printload(String name){
		println("--> loaded : " + name);
	}
	
	private static void loadIcon(PApplet parent, AssetEntry entry) {
		printload(entry.get_name());
		PIcon asset = new PIcon(entry, parent.loadImage(entry.get_filePath()));
		if (Icons == null)
			Icons = new HashMap<String, PIcon>();

		Icons.put(entry.get_name(), asset);
	}

	public static PImage GetIcon(String name) {
		if (Icons.containsKey(name))
			return Icons.get(name).get_image();
		
		println("error : no image for " + name);

		return null;
	}

	//galleries
	public static void loadGalleryEntries(PApplet instance,
			ArrayList<ImageEntry> readImageEntries) {

		for (ImageEntry entry : readImageEntries) {
			addGalleryImage(entry, instance.loadImage(entry.filePath));
		}
	}

	private static void addGalleryImage(ImageEntry entry, PImage image) {
		printload(entry.title);
		
		if(image == null)
			return;
		
		if (GalleryEntries == null)
			GalleryEntries = new ArrayList<GalleryEntry>();

		GalleryEntries.add(new GalleryEntry(entry, image));
	}

	public static ArrayList<GalleryEntry> GetGalleyImages() {
		return GalleryEntries;
	}

	//fonts
	public static void loadFonts(PApplet instance,
			ArrayList<FontEntry> readFontEntries) {

		for (FontEntry entry : readFontEntries) {
			loadFont(instance, entry);
		}
	}

	private static void loadFont(PApplet parent, FontEntry entry) {
		printload(entry.get_name());
		FontInfo font = new FontInfo(parent.loadFont(entry.get_filePath()), entry);
		if (Fonts == null)
			Fonts = new HashMap<String, FontInfo>();

		Fonts.put(entry.get_name(), font);
	}

	public static FontInfo GetFont(String name) {
		if (Fonts.containsKey(name))
			return Fonts.get(name);

		return null;
	}

}
