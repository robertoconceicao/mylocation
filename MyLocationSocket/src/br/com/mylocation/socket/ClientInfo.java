package br.com.mylocation.socket;

import java.util.Observable;

import br.com.mylocation.bean.message.event.Position;

public class ClientInfo extends Observable {

	private String key;
	private String name;
	private Position position;

	public ClientInfo() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		notifyChange();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		notifyChange();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
		notifyChange();
	}

	private void notifyChange() {
		setChanged();
		notifyObservers(this);
	}
}
