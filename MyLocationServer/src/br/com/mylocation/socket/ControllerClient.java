package br.com.mylocation.socket;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import br.com.mylocation.bean.message.Message;

public class ControllerClient {

	private ServerSocket serverSocket;
	private Map<SocketChannel, Client> clients;

	public ControllerClient(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		clients = new HashMap<SocketChannel, Client>();
	}
	
	public ControllerClient() {
		clients = new HashMap<SocketChannel, Client>();
	}

	public void newClient(SocketChannel socket) {
		Client client = new Client(this, socket);
		clients.put(socket, client);
	}

	public void receiveMessage(SocketChannel socket, Message message) {
		Client client = clients.get(socket);
		if (client != null) {
			client.receiveMessage(message);
		}
	}

	public void sendMessage(Client client, Message message) {
		serverSocket.write(client.getSocket(), message);
	}

	public void killClient(SocketChannel socket) {
		Client client = clients.remove(socket);
		if (client != null) {
			client.kill();
		}
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
}
