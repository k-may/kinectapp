package kinectapp.Interaction;

import java.util.ArrayList;

import processing.core.PVector;
import static processing.core.PApplet.println;

public class HandData {

	private ArrayList<PVector> _positions;
	
	private int _id;
	
	public int get_id() {
		return _id;
	}

	private PVector position;
	private float minZ = Float.MAX_VALUE;
	private float maxZ = Float.MIN_VALUE;
	private float minX = Float.MAX_VALUE;
	private float maxX = Float.MIN_VALUE;
	private float minY = Float.MAX_VALUE;
	private float maxY = Float.MIN_VALUE;

	private final float DAMPENING = 0.5f;
	private final int ZRANGE = 300;
	private final int RANGE = 200;
	private final int SAMPLES = 7;
	

	public HandData(int id) {
		_id = id;
		_positions = new ArrayList<PVector>();
	}

	public void addPosition(PVector pos) {
		if (position != null)
			position = PVector.lerp(position, pos, DAMPENING);
		else
			position = pos;
		
		_positions.add(position);

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
			maxZ = minZ + ZRANGE;
		}

		if (maxZ < pos.z) {
			maxZ = pos.z;
			minZ = maxZ - ZRANGE;
		}

		
	}

	private float getMapped(float val, float min, float range) {
		// println(position.z + " : " + minZ + " : " + maxZ);
		return (val - min) / range;
	}

	public PVector getPosition() {
		float x = getMapped(position.x, minX, RANGE);
		float y = getMapped(position.y, minY, RANGE);
		float z = 1 - getMapped(position.z, minZ, ZRANGE);
		return new PVector(x, y, z);
	}

	private PVector getLastPosition(){
		return _positions.get(_positions.size() - 3);
	}
	
	public PVector getTendency(){
		int i = _positions.size() - 1;
		int count = 0;
		float xDiff = 0, yDiff = 0, zDiff = 0;
		
		while( i > 0 && i > _positions.size() - SAMPLES + 1){
			PVector start = _positions.get(i - 1);
			PVector finish = _positions.get(i);
			xDiff += start.x - finish.x;
			yDiff += start.y - finish.y;
			zDiff += start.z - finish.z;
			i --;
			count ++;
		}
		
		xDiff /= count;
		yDiff /= count;
		zDiff /= count;
		
		return new PVector(xDiff, yDiff, zDiff);
	}
}
