package kinectapp.Interaction;

import kinectapp.view.Canvas;
import FrameWork.ICanvas;
import FrameWork.Interaction.IAdapter;
import FrameWork.Interaction.InteractionTargetInfo;
import FrameWork.Interaction.InteractionType;
import FrameWork.View.View;

public class Adapter implements IAdapter {

	protected Canvas _canvas;
	
	public Adapter(){
	}
	
	@Override
	public InteractionTargetInfo getInteractionInfoAtLocation(float x, float y,
			int userId, InteractionType type) {
		// TODO Auto-generated method stub
		InteractionTargetInfo info = new InteractionTargetInfo();
		
		View target = (View) _canvas.getTargetAtLocation(x*_canvas.get_width(), y*_canvas.get_height());
		info.set_isPressTarget(target != null);
		
		return info;
	}

	@Override
	public void set_canvas(ICanvas canvas) {
		// TODO Auto-generated method stub
		_canvas = (Canvas) canvas;
	}

}
