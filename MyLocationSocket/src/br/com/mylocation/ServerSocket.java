package br.com.mylocation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;

public class ServerSocket {

    public ServerSocket() {
        startSocket();
    }

    private void startSocket() {
        ServerSocketChannel server;
        Selector selector;
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new java.net.InetSocketAddress(8000));
            System.out.println("Server ativo na porta 8000");
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        loopSocket(server, selector);
    }

    private void loopSocket(ServerSocketChannel server, Selector selector) {
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = (SelectionKey) keyIterator.next();
                keyIterator.remove();

                if (key.isAcceptable()) {
                    accept(server, selector);
                    continue;
                }
                if (key.isReadable()) {
                    read(key);
                    continue;
                }
                if (key.isWritable()) {
                    write(key);
                }
            }
        }
    }

    private void accept(ServerSocketChannel server, Selector selector) {
        SocketChannel client = null;
        try {
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        System.out.println("Conexão aceita.");
    }

    private void read(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(32);
        try {
            client.read(buffer);
        } catch (Exception e) {
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }

        buffer.flip();
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer;
        try {
            charBuffer = decoder.decode(buffer);
            System.out.print(charBuffer.toString());
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer output = (ByteBuffer) key.attachment();
        if (!output.hasRemaining()) {
            output.rewind();
        }
        try {
            client.write(output);
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

}
