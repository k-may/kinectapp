package kinectapp.Interaction.gestTrackOSC;

public enum TrackingType {
	Normalized("/normalized"), Absolute("/absolute");

	private String _text;

	TrackingType(String text) {
		_text = text;
	}

	public String toString() {
		return _text;
	}
}
