package br.com.mylocation.protocol;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.define.GlobalDefines;
import br.com.mylocation.socket.Client;

public class SwitchMessages {

	private Client client;
	private ParserCommands parserCommands;
	private ParserEvents parserEvents;

	public SwitchMessages(Client client) {
		this.client = client;
		parserCommands = new ParserCommands(this);
		parserEvents = new ParserEvents(this);
	}

	public Client getClient() {
		return client;
	}

	public void switchMessage(Message message) {
		switch (message.getType()) {
		case GlobalDefines.TYPE_COMMAND:
			if (message instanceof Command) {
				parserCommands.switchCommand((Command) message);
			} else {
				System.out.println("Erro: Message recebido não é um Command!");
			}
			break;
		case GlobalDefines.TYPE_EVENT:
			if (message instanceof Event) {
				parserEvents.switchEvent((Event) message);
			} else {
				System.out.println("Erro: Message recebido não é um Event!");
			}
			break;
		default:
			System.out.println("Erro: Type incorreto!");
			break;
		}
	}
}
