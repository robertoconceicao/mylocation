package br.com.mylocation.protocol;

import org.apache.log4j.Logger;

import br.com.mylocation.bean.message.Command;
import br.com.mylocation.bean.message.CommandResponse;
import br.com.mylocation.bean.message.command.Login;
import br.com.mylocation.bean.message.commandresponse.LoginResponse;
import br.com.mylocation.define.GlobalDefines;

public class ParserCommands {

	private static Logger log = Logger.getLogger(ParserCommands.class);
	private SwitchMessages switchMessages;

	public ParserCommands(SwitchMessages switchMessages) {
		this.switchMessages = switchMessages;
	}

	public void switchCommand(Command command) {
		switch (command.getOperation()) {
		case GlobalDefines.OPERATION_LOGIN:
			parserCommandLogin(command);
			break;
		default:
			log.warn("Comando inválido.");
			break;
		}
	}

	private void parserCommandLogin(Command command) {
		log.debug("Processando comando Login RID: " + command.getRid() + ".");

		if (command.getData() != null && command.getData() instanceof Login) {
			Login login = (Login) command.getData();

			switchMessages.getClient().getClientInfo().setName(login.getName());

			LoginResponse loginResponse = new LoginResponse(switchMessages.getClient().getClientInfo().getKey());

			CommandResponse commandResponse = new CommandResponse(GlobalDefines.STATUS_SUCCESS, command.getRid(),
					GlobalDefines.OPERATION_LOGIN);
			commandResponse.setData(loginResponse);

			switchMessages.getClient().sendMessage(commandResponse);
		} else {
			log.error("Falha ao processar comando Login RID: " + command.getRid() + ".");
		}
	}
}
