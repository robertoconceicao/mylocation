package br.com.mylocation.model;

public class LocationInfo {

	private String latitude;
	private String longitude;
	private String speed;
	
	public LocationInfo(String latitude, String longitude, String speed) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getSpeed() {
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
}
