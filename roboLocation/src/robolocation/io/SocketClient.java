package robolocation.io;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.command.Login;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.GlobalDefines;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import robolocation.RoboLocation;

public class SocketClient implements Runnable {

    private SocketChannel socketChannel;
    private int timeEventPosition;
    private static int lastId = 0;
    private int id = lastId++;
    private long timeLogin;
    private RoboLocation robo;

    public SocketClient(int id, RoboLocation robo) {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            this.robo = robo;
            timeEventPosition = robo.getConfig().getTimePosition();
            if (robo.getConfig().getTimeLogin() > 0) {
                this.timeLogin = robo.getConfig().getTimeLogin();
            } else {
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
        if (isConnected()) {
            Command cmd = new Command(GlobalDefines.OPERATION_LOGIN, new Login("Nome " + id));
            write(cmd);
            try {
                Thread.sleep(timeLogin);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        while (isConnected()) {
            try {
                long time = System.currentTimeMillis();

                double latitude = Double.parseDouble(((int)(Math.random() * 30) * -1) + "." + ((int)((1 + Math.random()) * 10000000)));
                double longitude = Double.parseDouble(((int)(38 + (Math.random() * 30)) * -1) + "." + ((int)((1 + Math.random()) * 10000000)));

                Event event = new Event(GlobalDefines.OPERATION_POSITION);
                Position position = new Position(latitude, longitude, 0, 0, 0, time);
                event.setData(position);

                write(event);
                Thread.sleep(timeEventPosition);
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
