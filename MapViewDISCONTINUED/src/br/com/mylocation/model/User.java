package br.com.mylocation.model;

import java.util.List;

public class User {

	private String name;
	private String code;
	private String info;
	private List<LocationInfo> Points;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public List<LocationInfo> getPoints() {
		return Points;
	}
	
	public void setPoints(List<LocationInfo> points) {
		Points = points;
	}
}
