package br.com.mylocation.socket;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ControllerClient {

	private ServerSocket serverSocket;
	private Map<SocketChannel, Client> clients;

	public ControllerClient(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		clients = new HashMap<>();
	}

	public void newClient(SocketChannel socket) {
		Client client = new Client(this);
		clients.put(socket, client);
	}

	public void read() {

	}

	public void write() {

	}

	public void killClient(SocketChannel socket) {
		Client client = clients.remove(socket);
		if (client != null) {
			client.kill();
		}
	}

}
