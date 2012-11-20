package br.com.mylocation.bean.message.event;

import java.io.Serializable;

public class Position implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -9151265036548487054L;
	private double latitude;
	private double longitude;
	private float speed;
	private float accuracy;
	private double altitude;
	private long timestamp;

	public Position() {

	}

	public Position(double latitude, double longitude, float speed, float accuracy, double altitude, long timestamp) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.accuracy = accuracy;
		this.altitude = altitude;
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
