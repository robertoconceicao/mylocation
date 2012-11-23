package br.com.mylocationclient.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import br.com.mylocation.bean.message.Message;
import br.com.mylocationclient.core.Host;


public class SokectClient implements Runnable {

	private SocketChannel socketChannel;
	private Host host;
	
	public SokectClient(Host host){
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			this.host = host;
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
    	new Thread(this).start();
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

	public void loop(){
		while(socketChannel.isConnected()){
			read();
		}
	}
	
    public void read() {        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            if ((socketChannel.read(buffer) <= 0)) {
                close();
            } else {
                ByteArrayInputStream byteInput = new ByteArrayInputStream(buffer.array());
                ObjectInputStream input = new ObjectInputStream(byteInput);
                Object object = input.readObject();
                if (object instanceof Message) {
                	Message message = (Message) object;
                	if(host != null){
                		host.onMessage(message);
                	}
                } else {
                    input.close();
                    close();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            close();
        }
    }

	@Override
	public void run() {
		loop();
	}
}
