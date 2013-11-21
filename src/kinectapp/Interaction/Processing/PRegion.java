package kinectapp.Interaction.Processing;

import java.util.ArrayList;

import kinectapp.Interaction.Adapter;
import kinectapp.Interaction.Region;
import processing.core.PApplet;
import FrameWork.Interaction.InteractionStreamData;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;


public class PRegion extends Region<PApplet> {

	public PRegion(PApplet source) {
		super(source);

		source.noCursor();
		
		_type = InteractionType.Mouse;
		_adapter = new Adapter();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runInteractions() {
		// TODO Auto-generated method stub
		float mX = (float) _source.mouseX / _source.width;
		float mY = (float) _source.mouseY / _source.height;
		float mZ = _source.mousePressed ? 1 : 0;

		InteractionTargetInfo info = _adapter.getInteractionInfoAtLocation(mX, mY, mZ, 1, _type);

		InteractionStreamData data = new InteractionStreamData(mX, mY, mZ, 1, _type, info.get_isHoverTarget(), info.get_isPressTarget(),mZ == 1.0f, mZ);
		//data.set_isOverPressTarget(info.get_isPressTarget());

		_stream = new ArrayList<InteractionStreamData>();
		_stream.add(data);
		
		_adapter.handleStreamData(_stream);
	}

	@Override
	public ArrayList<InteractionStreamData> getStream() {
		// TODO Auto-generated method stub
		return _stream;
	}
}
