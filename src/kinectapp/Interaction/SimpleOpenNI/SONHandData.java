package kinectapp.Interaction.SimpleOpenNI;

import processing.core.PVector;
import static processing.core.PApplet.println;

public class SONHandData {

	private int _id;
	private PVector position;
	private float minZ = Float.MAX_VALUE;
	private float maxZ = Float.MIN_VALUE;
	private float minX = Float.MAX_VALUE;
	private float maxX = Float.MIN_VALUE;
	private float minY = Float.MAX_VALUE;
	private float maxY = Float.MIN_VALUE;

	private final float DAMPENING = 0.5f;
	private final int RANGE = 300;

	public SONHandData(int id) {
		_id = id;
	}

	public void addPosition(PVector pos) {
		if (position != null)
			position = PVector.lerp(position, pos, DAMPENING);
		else
			position = pos;

		if (minX > pos.x) {
			minX = pos.x;
			maxX = minX + RANGE;
		}

		if (maxX < pos.x) {
			maxX = pos.x;
			minX = maxX - RANGE;
		}

		if (minY > pos.y) {
			minY = pos.y;
			maxY = minY + RANGE;
		}

		if (maxY < pos.y) {
			maxY = pos.y;
			minY = maxY - RANGE;
		}
		if (minZ > pos.z) {
			minZ = pos.z;
			maxZ = minZ + RANGE;
		}

		if (maxZ < pos.z) {
			maxZ = pos.z;
			minZ = maxZ - RANGE;
		}

	}

	private float getMapped(float val, float min) {
		// println(position.z + " : " + minZ + " : " + maxZ);
		return (val - min) / RANGE;
	}

	public PVector getPosition() {
		float x = getMapped(position.x, minX);
		float y = getMapped(position.y, minY);
		float z = 1 - getMapped(position.z, minZ);
		return new PVector(x, y, z);
	}

}
