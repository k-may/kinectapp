package kinectapp.Interaction.Processing;

import static processing.core.PApplet.println;
import java.util.ArrayList;

import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import processing.core.PApplet;
import kinectapp.Interaction.Adapter;
import kinectapp.Interaction.Region;

public class PRegion extends Region<PApplet> {

	public PRegion(PApplet source) {
		super(source);

		_type = InteractionType.Mouse;
		_adapter = new Adapter();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runInteractions() {
		// TODO Auto-generated method stub
		float mX = (float) _source.mouseX / _source.width;
		float mY = (float) _source.mouseY / _source.height;

		// println(mX + " : " + mY);
		// InteractionTargetInfo info =
		// _adapter.getInteractionInfoAtLocation(mX,mY, -1, _type);

		float pressure = _source.mousePressed ? 1 : 0;
		InteractionStreamData data = new InteractionStreamData(pressure, mX,
				mY, -1, _type);

		_interactions = new ArrayList<InteractionStreamData>();
		_interactions.add(data);
	}

	@Override
	public ArrayList<InteractionStreamData> getStream() {
		// TODO Auto-generated method stub
		return _interactions;
	}
}
