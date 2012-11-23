package br.com.mylocation.protocol;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.Event;
import br.com.mylocation.bean.message.Message;
import br.com.mylocation.bean.message.command.Login;
import br.com.mylocation.bean.message.commandresponse.LoginResponse;
import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.define.ProtocolDefines;
import br.com.mylocation.socket.Client;

public class ProtocolParser {

	private Client client;

	public ProtocolParser(Client client) {
		this.client = client;
	}

	public void switchMessage(Message message) {
		switch (message.getType()) {
		case ProtocolDefines.TYPE_COMMAND:
			if (message instanceof Command) {
				switchCommand((Command) message);
			} else {
				System.out.println("Erro: Message recebido não é um Command!");
			}
			break;
		case ProtocolDefines.TYPE_COMMAND_RESPONSE:
			if (message instanceof CommandResponse) {
				switchCommandResponse((CommandResponse) message);
			} else {
				System.out.println("Erro: Message recebido não é um CommandResponse!");
			}
			break;
		case ProtocolDefines.TYPE_EVENT:
			if (message instanceof Event) {
				switchEvent((Event) message);
			} else {
				System.out.println("Erro: Message recebido não é um Event!");
			}
			break;
		default:
			System.out.println("Erro: Type incorreto!");
			break;
		}
	}

	private void switchCommand(Command command) {
		switch (command.getOperation()) {
		case ProtocolDefines.OPERATION_LOGIN:
			parserCommandLogin(command);
			break;
		default:
			System.out.println("Erro: Command não tratado!");
			break;
		}
	}

	private void switchCommandResponse(CommandResponse commandResponse) {
		switch (commandResponse.getOperation()) {
		default:
			System.out.println("Erro: CommandResponse não tratado!");
			break;
		}
	}

	private void switchEvent(Event event) {
		switch (event.getOperation()) {
		case ProtocolDefines.OPERATION_POSITION:
			parserEventPosition(event);
			break;
		default:
			System.out.println("Erro: Event não tratado!");
			break;
		}
	}

	private void parserCommandLogin(Command command) {
		System.out.println("Parsing Command Login RID: " + command.getRid());

		if (command.getData() != null && command.getData() instanceof Login) {
			Login login = (Login) command.getData();

			client.getClientInfo().setName(login.getName());

			LoginResponse loginResponse = new LoginResponse(client.getClientInfo().getKey());

			CommandResponse commandResponse = new CommandResponse(ProtocolDefines.STATUS_SUCCESS, command.getRid(),
					ProtocolDefines.OPERATION_LOGIN);
			commandResponse.setData(loginResponse);

			client.sendMessage(commandResponse);
		} else {
			System.out.println("Erro: Parsing Command Login RID: " + command.getRid());
		}
	}

	private void parserEventPosition(Event event) {
		System.out.println("Parsing Event Position");

		if (event.getData() != null && event.getData() instanceof Position) {
			Position position = (Position) event.getData();
			client.getClientInfo().setPosition(position);
		} else {
			System.out.println("Erro: Parsing Event Position");
		}
	}

}
