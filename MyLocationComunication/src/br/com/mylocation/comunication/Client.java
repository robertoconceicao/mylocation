package br.com.mylocation.comunication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.mylocation.bean.Command;
import br.com.mylocation.bean.command.Login;

public class Client {

	/**
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());            
            
            Command command = new Command();
            command.setOperation(1); //login
            command.setType(0); // command
            command.setRid(123);
            Login login = new Login("Roberto");            
            command.setData(login);
            
            output.writeObject(command);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
