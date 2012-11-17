package br.com.mylocation.socket;

import br.com.mylocation.bean.message.Message;
import br.com.mylocation.protocol.Protocol;

public class Client {

	private Protocol protocol;
	private ControllerClient controllerClient;

	public Client(ControllerClient controllerClient) {
		this.controllerClient = controllerClient;
		protocol = new Protocol();
	}

	public void read(Message message) {
		protocol.switchMessage(message);
	}

	public void write(Message message) {

	}

	public void kill() {

	}
}
