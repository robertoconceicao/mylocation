package br.com.mylocation.comunication;

import java.io.Serializable;

public class People implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7808438250895803394L;
	private String name;
	private int age;
	private Object object;
	
	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
		this.object = null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String toString(){
		return "Name: "+name+" Age: "+age;
	}
}
