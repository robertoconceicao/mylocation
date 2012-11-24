package br.com.mylocationclient.core;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;

public abstract class RequestInstance {

	private Command command;
	
	public RequestInstance(Command command){
		this.command = command;
	}
	
	/**
	 * Recebe a resposta do comando que foi enviado como uma RequestInstance
	 * @param response
	 */
	public abstract void onRequestInstance(CommandResponse response);

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
