package framework.stroke;

import processing.core.PImage;

public interface ICanvas {
	void save(String filePath);

	void clear();

	PImage getImage();
}
