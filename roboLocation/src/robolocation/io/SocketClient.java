package robolocation.io;

import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.GlobalDefines;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import robolocation.RoboLocation;

public class SocketClient implements Runnable {

    private SocketChannel socketChannel;
    private int timeEventPosition;
    private int id;
    private long timeLogin;
    private RoboLocation robo;
    
    public SocketClient(int id, RoboLocation robo) {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            this.robo = robo;
            timeEventPosition = robo.getConfig().getTimePosition();
            if(robo.getConfig().getTimeLogin() > 0){
                this.timeLogin = System.currentTimeMillis() + timeLogin;
            }else{
                this.timeLogin = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socketChannel.close();
            robo.finishClient(this);
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

    public void loop() {
        while (isConnected()) {
            try {
                long time = System.currentTimeMillis();
                
                if(timeLogin != 0 && time > timeLogin){
                    break; // acabou o tempo de login
                }
                
                Thread.sleep(timeEventPosition);
                
                double latitude = -27.0+id+time;
                double longitude = -48.0+id+time;
                
                Event event = new Event(GlobalDefines.OPERATION_POSITION);		
                Position position = new Position(latitude, 
                                                longitude, 
                                                0, 0, 0, time);
                event.setData(position);
                
                write(event);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close();
    }

    public boolean isConnected() {
        return socketChannel.isConnected();
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
