package robolocation.io;

import br.com.mylocation.bean.message.Message;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClient implements Runnable {

    private SocketChannel socketChannel;

    public SocketClient() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
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

    public void loop() {
        while (isConnected()) {
            read();
        }
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
