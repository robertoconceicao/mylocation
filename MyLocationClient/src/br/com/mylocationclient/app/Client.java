package br.com.mylocationclient.app;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocationclient.core.Host;
import br.com.mylocationclient.views.MainActivity;

public class Client extends Host  {
	
	private String key;
	private MainActivity mainActivity;
	private static Client client = new Client();
	
	private Client() {		
		super("Client");
	}

	public static Client getInstance(){
		if(client == null){
			client = new Client();
		}
		return client;
	}
	
	public void connect() throws Exception {
		socket.connect(ProtocolDefines.HOST_NAME, ProtocolDefines.PORT);			
    }
			
	public void sendPosition(Position position){
		Event event = new Event(ProtocolDefines.OPERATION_POSITION);
		event.setData(position);
		sendEvent(event);
	}
	
	@Override
	public void onResponse(CommandResponse response) {
		if(response != null){
			System.out.println("Resposta nao tratada rid: "+response.getRid()+" opr: "+response.getOperation());
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

	public boolean isConnected(){
		return socket.isConnected();
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
}
