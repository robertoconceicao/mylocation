package br.com.mylocationclient.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.mylocation.bean.message.Message;


public class Client {

	private SocketChannel socketChannel;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	//private Socket socket;
	
	public Client(){
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
//			socket = new Socket();
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
    	//socket.connect(new InetSocketAddress(hostname, port), 3000);
    	
    	//output = new ObjectOutputStream(socket.getOutputStream());
    	//input = new ObjectInputStream(socket.getInputStream());
    	
//        output = new ObjectOutputStream(socketChannel.socket().getOutputStream());
//        input = new ObjectInputStream(socketChannel.socket().getInputStream());
    }
    
    public void sendMessage(Message message) throws IOException{
//    	write.writeObject(message);
    }
    
    public void sendString(String string) throws IOException{
//    	if(output != null){
//    		System.out.println("SEND: "+string);
//    		output.writeUTF(string);
//    	}
    	if(socketChannel.isConnected()){
	    	ByteBuffer bytes = ByteBuffer.allocate(1024);
//	    	System.out.println("SEND: "+string);
	    	bytes.put(string.getBytes());	    	
	    	bytes.flip();
	    	socketChannel.write(bytes);
    	}    	
    }
    
    public Message onMessage() throws IOException, ClassNotFoundException{
    	Message message = null;
    	
//    	message = (Message) read.readObject();
    	
    	return message;
    }
    
    public static void main (String a[]){
    	class Run implements Runnable{
    		int id = 0;
    		int count = 0;
    		public Run(int id){
    			this.id = id;
    		}
			@Override
			public void run() {
				Client client = new Client();
				
				if(id % 2 == 1){
					Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						@Override
						public void run() {
							System.out.println("ThreadId: "+id+" count: "+count+"##################");
						}
					};
					timer.schedule(timerTask, 0,30000);
				}
				try {
					client.connect("10.4.0.15", 8000);
					
					while(true){
						
						client.sendString("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ LINUX Thread: "+id+" count: "+count++ +"%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW" +
								"%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW" +
								"%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW" +
								"%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW" +
								"%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW%$@%%@@hghjgggggggggggggggggggggggg9858959598589UHFUHFUHFUHFUHWEUFHEWUFHEWFIUHEWFIEWUHFEWIUFHEWIUFHEW");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}    	
    	
    	ExecutorService exec = Executors.newCachedThreadPool();
    	for(int i=0;i<100;i++){
    		exec.execute(new Run(i));
    	}    	
    }
}
