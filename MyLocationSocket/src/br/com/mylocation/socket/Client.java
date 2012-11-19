package br.com.mylocation.socket;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import br.com.mylocation.bean.message.Message;
import br.com.mylocation.protocol.Protocol;

public class Client {

	private Protocol protocol;
	private ControllerClient controllerClient;
	private SocketChannel socket;

	public Client(ControllerClient controllerClient, SocketChannel socket) {
		System.out.println("Novo cliente...");
		this.controllerClient = controllerClient;
		this.socket = socket;
		protocol = new Protocol();
	}	

	public SocketChannel getSocket() {
		return socket;
	}
	
	public void read(Message message) {
		System.out.println("Cliente recebeu mensagem...");
		protocol.switchMessage(message);
	}

	public void write(Message message) {
		System.out.println("Cliente escreveu mensagem...");
		controllerClient.write(this, message);
	}

	public void kill() {
		System.out.println("Matando cliente...");
		try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
