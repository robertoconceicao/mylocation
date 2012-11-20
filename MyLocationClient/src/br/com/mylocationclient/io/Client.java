package br.com.mylocationclient.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import br.com.mylocation.bean.message.Message;


public class Client {

	private SocketChannel socketChannel;
	
	public Client(){
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {			
			socketChannel.close();
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
    }
    
    public void write(Message message) {
        try {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(byteOutput);
            output.writeObject(message);
            output.flush();
            ByteBuffer buffer = ByteBuffer.wrap(byteOutput.toByteArray());
            socketChannel.write(buffer);
        } catch (IOException e1) {
            e1.printStackTrace();
            close();
        }
    }
    
    public void sendMessage(Message message) throws IOException{
    	write(message);
    }
    
    public void sendString(String string) throws IOException{
    	if(socketChannel.isConnected()){
	    	ByteBuffer bytes = ByteBuffer.allocate(1024);
	    	bytes.put(string.getBytes());	    	
	    	bytes.flip();
	    	socketChannel.write(bytes);
    	}    	
    }
    
    public Message onMessage() throws IOException, ClassNotFoundException{
    	Message message = null;
    	return message;
    }
}
