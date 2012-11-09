package br.com.mylocation.comunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import br.com.mylocation.bean.message.Message;

public class Client {

	private SocketChannel socketChannel;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public Client(){
		try {
			socketChannel = SocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * @param args argumentos da linha de comando
	 * @throws IOException 
     */
    public void connect(String hostname, int port) throws IOException {
    	socketChannel.connect(new InetSocketAddress(hostname, port));
        output = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        input = new ObjectInputStream(socketChannel.socket().getInputStream());
    }
    
    public void sendMessage(Message message) throws IOException{
    	if(output != null){
    		output.writeObject(message);
    	}
    }
    
    public Message onMessage() throws IOException, ClassNotFoundException{
    	Message message = null;
    	if(input != null){
    		message = (Message) input.readObject();
    	}
    	return message;
    }
}
