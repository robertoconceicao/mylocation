package br.com.mylocation.bean.message;

import br.com.mylocation.define.ProtocolDefines;

public class Command extends Message {

	/**
     * 
     */
	private static final long serialVersionUID = 434133680092147294L;
	private int rid;
	private static int sequence = 1; 
	
	public Command(int operation, Object data) {
		super(operation, ProtocolDefines.TYPE_COMMAND, data);
		this.rid = newRid();
	}

	public Command(int operation) {
		super(operation, ProtocolDefines.TYPE_COMMAND);
		this.rid = newRid();
	}

	protected Command(int operation, int type, Object data) {
		super(operation, type, data);
		this.rid = newRid();
	}

	protected Command(int operation, int type) {
		super(operation, type);
		this.rid = newRid();
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	private int newRid(){
		if(sequence++ > Integer.MAX_VALUE){			
			sequence = 1;
		}
		return sequence;
	}
	
}
