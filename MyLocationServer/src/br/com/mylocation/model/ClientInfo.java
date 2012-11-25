package br.com.mylocation.model;

import java.util.Observable;
import java.util.UUID;

import br.com.mylocation.bean.message.event.Position;

public class ClientInfo extends Observable {

	private String key;
	private String name;
	private Position position;

	public ClientInfo() {
		String randomKey = createKey();
		setKey(randomKey);
	}

	public String getKey() {
		return key;
	}

	private void setKey(String key) {
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

	private String createKey() {
		UUID uuid = UUID.randomUUID();
		String randomKey = uuid.toString();
		return randomKey.substring(0, 8);
	}

	private void notifyChange() {
		setChanged();
		notifyObservers(this);
	}
}
