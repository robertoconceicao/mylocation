package br.com.mylocation.socket;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.mylocation.bean.message.Message;
import br.com.mylocation.model.ClientInfo;

public class ControllerClient {

	private ServerSocket serverSocket;
	private Map<SocketChannel, Client> clientList;
	private List<ClientInfo> clientInfoList;
	private static Logger log = Logger.getLogger(ControllerClient.class);

	public ControllerClient() {
		clientList = new HashMap<SocketChannel, Client>();
		clientInfoList = new ArrayList<ClientInfo>();
	}

	public List<ClientInfo> getClientInfoList() {
		return clientInfoList;
	}

	public void newClient(SocketChannel socket) {
		Client client = new Client(this, socket);
		clientList.put(socket, client);
		clientInfoList.add(client.getClientInfo());
		log.debug(clientList.size() + " clientes conectados.");
	}

	public void receiveMessage(SocketChannel socket, Message message) {
		Client client = clientList.get(socket);
		if (client != null) {
			client.receiveMessage(message);
		}
	}

	public void sendMessage(Client client, Message message) {
		serverSocket.write(client.getSocket(), message);
	}

	public void killClient(SocketChannel socket) {
		Client client = clientList.remove(socket);
		clientInfoList.remove(client.getClientInfo());
		if (client != null) {
			client.kill();
		}
		log.debug(clientList.size() + " clientes conectados.");
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}	
}
