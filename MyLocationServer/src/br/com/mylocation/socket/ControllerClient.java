package br.com.mylocation.socket;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.model.ClientInfo;

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

	public List<ClientInfo> getClientInfoList() {
		List<ClientInfo> clientsList = new ArrayList<>();

		for (Map.Entry<SocketChannel, Client> entry : clients.entrySet()) {
			Client client = entry.getValue();
			clientsList.add(client.getClientInfo());
		}

		return clientsList;
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
