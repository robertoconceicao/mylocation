package br.com.mylocationclient.app;

import java.io.IOException;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.command.Login;
import br.com.mylocation.bean.message.commandresponse.LoginResponse;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocationclient.core.Host;
import br.com.mylocationclient.views.MainActivity;

public class Client extends Host implements ProtocolDefines {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2440942261393083891L;
	private String key;
	private MainActivity mainActivity;
	
	public Client(MainActivity mainActivity) {		
		super("Client");
		this.mainActivity = mainActivity;
	}

	public void connect() {
		try {
			socket.connect(HOST_NAME, PORT);
			sendLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private void sendLogin() {
		Command command = new Command(OPERATION_LOGIN);
		Login login = new Login("Teste");
		command.setData(login);
		sendCommand(command);
	}

	private void onLogin(CommandResponse response){
		if(response.getData() != null){
			final LoginResponse loginResponse = (LoginResponse)response.getData();
			key = loginResponse.getKey();
			
			mainActivity.runOnUiThread ( new Runnable () {
				@Override
				public void run () {
					mainActivity.dialog("Conectado key: "+loginResponse.getKey());
				}
			});
		}
	}
	
	public void sendPosition(Position position){
		Event event = new Event(OPERATION_POSITION);
		event.setData(position);
		sendEvent(event);
	}
	
	@Override
	public void onResponse(CommandResponse response) {		
		switch(response.getOperation()){
			case OPERATION_LOGIN:
				System.out.println("Resposta de login");
				onLogin(response);
				break;
			default:
				System.out.println("Resposta nao tratada rid: "+response.getRid()+" opr: "+response.getOperation());
				break;
		}
	}

	@Override
	public void onEvent(Message message) {
		System.out.println("Client onEvent: "+message.getOperation());		
	}

	@Override
	public CommandResponse onCommand(Command command) {
		System.out.println("Client onCommand: "+command.getOperation());
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
