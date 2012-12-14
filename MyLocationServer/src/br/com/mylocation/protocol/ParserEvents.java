package br.com.mylocation.protocol;

import org.apache.log4j.Logger;

import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.event.Logout;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.GlobalDefines;

public class ParserEvents {

	private static Logger log = Logger.getLogger(ParserEvents.class);
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
			log.warn("Evento inválido.");
			break;
		}
	}

	private void parserEventPosition(Event event) {
		log.debug("Processando evento Position.");

		if (event.getData() != null && event.getData() instanceof Position) {
			Position position = (Position) event.getData();
			switchMessages.getClient().getClientInfo().setPosition(position);
		} else {
			log.error("Falha ao processar evento Position.");
		}
	}

	private void parserEventLogout(Event event) {
		log.debug("Processando evento Logout.");

		if (event.getData() != null && event.getData() instanceof Logout) {
			switchMessages.getClient().removeAndKill();
		} else {
			log.error("Falha ao processar evento Logout.");
		}
	}
}
