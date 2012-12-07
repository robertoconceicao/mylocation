package br.com.mylocation.socket;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.model.ClientInfo;
import br.com.mylocation.protocol.SwitchMessages;

public class Client {

	private SwitchMessages switchMessages;
	private ControllerClient controllerClient;
	private SocketChannel socket;
	private ClientInfo clientInfo;

	public Client(ControllerClient controllerClient, SocketChannel socket) {
		System.out.println("Novo cliente...");
		this.controllerClient = controllerClient;
		this.socket = socket;
		switchMessages = new SwitchMessages(this);
		clientInfo = new ClientInfo();
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public SocketChannel getSocket() {
		return socket;
	}

	public void receiveMessage(Message message) {
		System.out.println("Cliente recebeu mensagem...");
		switchMessages.switchMessage(message);
	}

	public void sendMessage(Message message) {
		System.out.println("Cliente escreveu mensagem...");
		controllerClient.sendMessage(this, message);
	}
	
	public void removeAndKill(){
		controllerClient.killClient(socket);
		kill();
	}

	public void kill() {
		System.out.println("Matando cliente...");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchMessages = null;
		controllerClient = null;
		socket = null;
		clientInfo = null;
	}
}
