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

import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.commandresponse.LoginResponse;
import br.com.mylocation.define.ProtocolDefines;


public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4565273806467925920L;
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
    
    public void sendMessage(Message message) throws IOException{
    	System.out.println("sendMessage opr: "+message.getOperation()+" type: "+message.getType());
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
    
    public void onMessage() throws IOException {
    	Message message = read();
    	
    	if(message != null){
    		System.out.println("onMessage opr: "+message.getOperation()+" type: "+message.getType());
    		switch(message.getType()){    		
    		case ProtocolDefines.TYPE_COMMAND_RESPONSE:
    			parserResponse(message);
    			break;    		
    		default:
				System.out.println("Message type not treated");
				break;
    		}
    	}    	
    }
    
    private void parserResponse(Message message) {
		CommandResponse response = (CommandResponse) message;
		switch(response.getOperation()){
		case ProtocolDefines.OPERATION_LOGIN:
			LoginResponse loginResponse = (LoginResponse) response.getData();
			System.out.println("LoginResponse key: "+loginResponse.getKey()+" rid: "+response.getRid());			
			break;
			default:
				System.out.println("response not treated");
				break;
		}
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

    private Message read() {        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Message message = null;
        
        try {
            if ((socketChannel.read(buffer) <= 0)) {
                close();
            } else {
                ByteArrayInputStream byteInput = new ByteArrayInputStream(buffer.array());
                ObjectInputStream input = new ObjectInputStream(byteInput);
                Object object = input.readObject();
                if (object instanceof Message) {
                    message = (Message) object;                    
                    input.close();
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
        return message;
    }
}
