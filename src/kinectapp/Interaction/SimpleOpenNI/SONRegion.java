package kinectapp.Interaction.SimpleOpenNI;

import java.util.ArrayList;

import kinectapp.Interaction.Region;
import FrameWork.Interaction.InteractionStreamData;
import SimpleOpenNI.SimpleOpenNI;

public class SONRegion extends Region<SimpleOpenNI>{

	public SONRegion(SimpleOpenNI source) {
		super(source);
		// TODO Auto-generated constructor stub
		_adapter = new SONAdapter();
	}

	@Override
	public void runInteractions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<InteractionStreamData> getStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
