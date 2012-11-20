package br.com.mylocation.protocol;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.define.ProtocolDefines;

public class ProtocolParser {

	public void switchMessage(Message message) {
		switch (message.getType()) {
		case ProtocolDefines.TYPE_COMMAND:
			switchCommand((Command) message);
			break;
		case ProtocolDefines.TYPE_COMMAND_RESPONSE:
			switchCommandResponse((CommandResponse) message);
			break;
		case ProtocolDefines.TYPE_EVENT:
			switchEvent((Event) message);
			break;
		default:
			break;
		}
	}

	private void switchCommand(Command command) {
		switch (command.getOperation()) {
		case ProtocolDefines.OPERATION_LOGIN:
			System.out.println("Command Login");
			break;
		default:
			System.out.println("Command não tratado!");
			break;
		}
	}

	private void switchCommandResponse(CommandResponse commandResponse) {
		switch (commandResponse.getOperation()) {
		default:
			System.out.println("CommandResponse não tratado!");
			break;
		}
	}

	private void switchEvent(Event event) {
		switch (event.getOperation()) {
		case ProtocolDefines.OPERATION_POSITION:
			System.out.println("Event Position");
			break;
		default:
			System.out.println("Event não tratado!");
			break;
		}
	}

}
