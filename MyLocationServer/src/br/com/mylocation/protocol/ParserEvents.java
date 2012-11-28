package br.com.mylocation.protocol;

import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.event.Logout;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.GlobalDefines;

public class ParserEvents {

	private SwitchMessages switchMessages;

	public ParserEvents(SwitchMessages switchMessages) {
		this.switchMessages = switchMessages;
	}

	public void switchEvent(Event event) {
		switch (event.getOperation()) {
		case GlobalDefines.OPERATION_POSITION:
			parserEventPosition(event);
			break;
		case GlobalDefines.OPERATION_LOGOUT:
			parserEventLogout(event);
			break;
		default:
			System.out.println("Erro: Event não tratado!");
			break;
		}
	}

	private void parserEventPosition(Event event) {
		System.out.println("Parsing Event Position");

		if (event.getData() != null && event.getData() instanceof Position) {
			Position position = (Position) event.getData();
			switchMessages.getClient().getClientInfo().setPosition(position);
		} else {
			System.out.println("Erro: Parsing Event Position");
		}
	}

	private void parserEventLogout(Event event) {
		System.out.println("Parsing Event Logout");

		if (event.getData() != null && event.getData() instanceof Logout) {
			switchMessages.getClient().removeAndKill();
		} else {
			System.out.println("Erro: Parsing Event Logout");
		}
	}
}
