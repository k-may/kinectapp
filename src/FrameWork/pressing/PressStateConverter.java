package FrameWork.pressing;


public class PressStateConverter {

	private float _startS;
	private float _startE;

	private float _colorS;
	private float _colorE;

	private float _preDrawS;
	private float _preDrawE;

	private float _drawS;
	private float _drawE;

	public PressStateConverter(float startTo, float colorTo, float preDrawTo) {
		_startS = 0.0f;
		_startE = startTo;

		_colorS = _startE;
		_colorE = colorTo;

		_preDrawS = colorTo;
		_preDrawE = preDrawTo;

		_drawS = preDrawTo;
		_drawE = 1.0f;
	}

	public PressStateData getStateData(float pressure) {
		PressStateData data = null;

		if (pressure >= _startS && pressure < _startE) {
			data = new PressStateData(PressState.Start, map(pressure, _startS, _startE));
		} else if (pressure >= _colorS && pressure < _colorE) {
			data = new PressStateData(PressState.ColorSelection, map(pressure, _colorS, _colorE));
		} else if (pressure >= _preDrawS && pressure < _preDrawE) {
			data = new PressStateData(PressState.PreDrawing, map(pressure, _preDrawS, _preDrawE));
		} else {
			data = new PressStateData(PressState.Drawing, map(pressure, _drawS, _drawE));
		}

		return data;
	}

	private float map(float value, float start, float end) {
		return (value - start)/(end - start);
	}
}
