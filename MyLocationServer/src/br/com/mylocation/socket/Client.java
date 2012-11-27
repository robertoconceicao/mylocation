package br.com.mylocation.socket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import br.com.mylocation.bean.message.Message;
import br.com.mylocation.model.ClientInfo;
import br.com.mylocation.protocol.ProtocolParser;

public class Client {

	private ProtocolParser protocolParser;
	private ControllerClient controllerClient;
	private SocketChannel socket;
	private ClientInfo clientInfo;

	public Client(ControllerClient controllerClient, SocketChannel socket) {
		System.out.println("Novo cliente...");
		this.controllerClient = controllerClient;
		this.socket = socket;
		protocolParser = new ProtocolParser(this);
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
		protocolParser.switchMessage(message);
	}

	public void sendMessage(Message message) {
		System.out.println("Cliente escreveu mensagem...");
		controllerClient.sendMessage(this, message);
	}

	public void kill() {
		System.out.println("Matando cliente...");
		clientInfo.kill();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		protocolParser = null;
		controllerClient = null;
		socket = null;
		clientInfo = null;
	}
}
