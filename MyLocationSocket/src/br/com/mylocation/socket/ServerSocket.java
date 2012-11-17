package br.com.mylocation.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
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

	private ControllerClient controllerClient;

	public ServerSocket() {
		controllerClient = new ControllerClient(this);
		startSocket();
	}

	private void startSocket() {
		ServerSocketChannel serverSocket = null;
		java.net.ServerSocket socket = null;
		InetSocketAddress address = null;
		Selector selector = null;
		SelectionKey key = null;

		try {
			serverSocket = ServerSocketChannel.open();
			serverSocket.configureBlocking(false);
			socket = serverSocket.socket();
			address = new InetSocketAddress(8000);
			socket.bind(address);
			selector = Selector.open();
			key = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Open socket port 8000...");
		} catch (IOException e) {
			e.printStackTrace();
		}

		loopSocket(serverSocket, selector);
	}

	private void loopSocket(ServerSocketChannel server, Selector selector) {
		while (true) {
			try {
				selector.select();
				// System.out.println("New event...");
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = keys.iterator();

			while (keyIterator.hasNext()) {
				// System.out.println("New iteration...");
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
		// System.out.println("Accept...");
		SocketChannel socketClient = null;
		try {
			socketClient = server.accept();
			socketClient.configureBlocking(false);
			socketClient.register(selector, SelectionKey.OP_READ);
			controllerClient.newClient(socketClient);
		} catch (IOException e) {
			// System.out.println("Close socket...");
			e.printStackTrace();
			close(socketClient);
		}
		// System.out.println("Conexão aceita.");
	}

	private void read(SelectionKey key) {
		// System.out.println("Read...");
		SocketChannel socketClient = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			if ((socketClient.read(buffer) <= 0)) {
				// System.out.println("client.read(buffer) <= 0");
				close(socketClient);
			}
		} catch (Exception e) {
			e.printStackTrace();
			close(socketClient);
			return;
		}

		buffer.flip();
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder decoder = charset.newDecoder();
		CharBuffer charBuffer;
		try {
			charBuffer = decoder.decode(buffer);
			// System.out.println(charBuffer.toString());
		} catch (CharacterCodingException e) {
			e.printStackTrace();
			close(socketClient);
		}
	}

	private void write(SelectionKey key) {
		// System.out.println("Write...");
		SocketChannel socketClient = (SocketChannel) key.channel();
		ByteBuffer output = (ByteBuffer) key.attachment();
		if (!output.hasRemaining()) {
			output.rewind();
		}
		try {
			socketClient.write(output);
		} catch (IOException e) {
			e.printStackTrace();
			close(socketClient);
		}
	}

	private void close(SocketChannel socketClient) {
		try {
			controllerClient.killClient(socketClient);
			socketClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
