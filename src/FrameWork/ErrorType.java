package FrameWork;

public enum ErrorType {
	KinectError("Kinect Error"), AssetError("Asset Error"), MusicError("Audio Error");
	
	private String _name;
	private ErrorType(String name){
		_name = name;
	}
	
	@Override
	public String toString() {
		return _name;
	}
}
