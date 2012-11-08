package br.com.mylocation.comunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.mylocation.bean.Command;
import br.com.mylocation.bean.command.Login;

public class Server {

	public static void main(String[] args) {
        //Chama o método construtor na classe
     
		try {
			ServerSocket server = new ServerSocket(12345);            
	        System.out.println("Servidor aguardando conexao.");
	        
	        Socket socket = server.accept();
	        
	        ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
	        
	        try {
				Command command = (Command) read.readObject();
				Login login = (Login) command.getData();
				
				System.out.println("Dados recebidos: "+command.toString());
				System.out.println("Operation: "+command.getOperation());
				System.out.println("Type: "+command.getType());
				System.out.println("Rid: "+command.getRid());
				System.out.println("Login: "+login.getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				read.close();
		        socket.close();
		        server.close();	
			}
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
}
