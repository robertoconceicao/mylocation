package br.com.mylocation.bean.message;

import br.com.mylocation.define.ProtocolDefines;

public class CommandResponse extends Command {

	/**
     * 
     */
	private static final long serialVersionUID = -1080168134636931584L;
	private int status;

	public CommandResponse(int status, int rid, int operation, Object data) {
		super(operation, ProtocolDefines.TYPE_COMMAND_RESPONSE, data);
		this.status = status;
		setRid(rid);
	}

	public CommandResponse(int status, int rid, int operation) {
		super(operation, ProtocolDefines.TYPE_COMMAND_RESPONSE);
		this.status = status;
		setRid(rid);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
