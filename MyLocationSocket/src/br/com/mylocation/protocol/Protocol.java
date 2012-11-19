package br.com.mylocation.protocol;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.define.ProtocolDefines;

public class Protocol {

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
		case ProtocolDefines.OPERATION_POSITION:
		    System.out.println("Command Position");
			break;

		default:
			break;
		}
	}

	private void switchCommandResponse(CommandResponse commandResponse) {
		switch (commandResponse.getOperation()) {
		case ProtocolDefines.OPERATION_LOGIN:
			break;
		default:
			break;
		}
	}

	private void switchEvent(Event event) {
		switch (event.getOperation()) {
		default:
			break;
		}
	}

}
