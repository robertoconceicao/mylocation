package br.com.mylocationclient.core;

import java.io.IOException;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocationclient.io.SokectClient;

public abstract class Host {

	protected SokectClient socket;
	private String name;	
	
	public Host(String name){
		this.name = name;
		socket = new SokectClient(this);		
	}
	
	public Host(String name, SokectClient client){
		this.name = name;
		this.socket = client;
	}
    
	/**
	 * Metodo que separa o tipo de mensagem recebida do servidor
	 * Não deve ser chamado diretamente
	 * @param message
	 */
    public void onMessage(Message message) {
    	if(message != null){
    		System.out.println("onMessage opr: "+message.getOperation()+" type: "+message.getType());
    		switch(message.getType()) {
    			case ProtocolDefines.TYPE_COMMAND:
    				CommandResponse response = onCommand((Command) message);
    				sendResponse(response);
    				break;
	    		case ProtocolDefines.TYPE_COMMAND_RESPONSE:
	    			onResponse((CommandResponse) message);
	    			break;    		
	    		case ProtocolDefines.TYPE_EVENT:
	    			onEvent((Event)message);
	    			break;
	    		default:
					System.out.println("Message type not treated");
					break;
    		}
    	}    	
    }

    private void write(Message message){
    	if(socket != null){
			socket.write(message);
		}
    }
    
    /**
     * Envia um comando para o servidor que exige uma resposta que irá cair no metodo
     * (callback) onResponse
     * @param command
     */
    public void sendCommand(Command command){
		System.out.println("sendCommand opr: "+command.getOperation()+" rid: "+command.getRid());
		write(command);
    }
	
    /**
     * Envia um comando para o servidor que exige uma resposta que irá cair no metodo
     * (callback) onResponse
     * @param response
     */
    public void sendResponse(CommandResponse response){
		System.out.println("sendResponse response opr: "+response.getOperation()+" rid: "+response.getRid());
		write(response);		
	}
    
    /**
     * Envia um evento para o servidor nao exige resposta do servidor
     * @param event
     * @throws IOException
     */
	public void sendEvent(Event event) {
    	System.out.println("sendEvent opr: "+event.getOperation()+" type: "+event.getType());
    	write(event);
    }
	
	/**
	 * Metodo que recebe a resposta do comando enviado para o servidor
	 * @param response
	 */
    public abstract void onResponse(CommandResponse response);
    
    /**
     * Metodo que recebe um evento do servidor
     * @param message
     */
    public abstract void onEvent(Message message);
	
    /**
     * Metodo que recebe um comando do servidor
     * @param message
     */
    public abstract CommandResponse onCommand(Command command);
    
    
	public SokectClient getClient() {
		return socket;
	}

	public void setClient(SokectClient client) {
		this.socket = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
