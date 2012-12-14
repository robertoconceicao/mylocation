package br.com.mylocation.socket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import br.com.mylocation.bean.message.Message;
import br.com.mylocation.model.ClientInfo;
import br.com.mylocation.protocol.SwitchMessages;

public class Client {

	private static Logger log = Logger.getLogger(Client.class);
	private SwitchMessages switchMessages;
	private ControllerClient controllerClient;
	private SocketChannel socket;
	private ClientInfo clientInfo;

	public Client(ControllerClient controllerClient, SocketChannel socket) {
		try {
			log.debug("Novo cliente (Endereço Local: " + socket.getLocalAddress() + " Endereço Remoto: " + socket.getRemoteAddress() + ").");
		} catch (IOException e) {
		}
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
		log.debug("Cliente recebeu mensagem.");
		switchMessages.switchMessage(message);
	}

	public void sendMessage(Message message) {
		log.debug("Cliente escreveu mensagem.");
		controllerClient.sendMessage(this, message);
	}

	public void removeAndKill() {
		controllerClient.killClient(socket);
		kill();
	}

	public void kill() {
		try {
			log.debug("Matando cliente (ip local: " + socket.getLocalAddress() + " ip remoto: " + socket.getRemoteAddress() + ").");
		} catch (IOException e1) {
		}
		try {
			socket.close();
		} catch (IOException e) {
		}
		switchMessages = null;
		controllerClient = null;
		socket = null;
		clientInfo = null;
	}
}
