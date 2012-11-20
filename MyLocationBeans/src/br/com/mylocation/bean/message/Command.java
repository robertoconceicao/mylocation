package br.com.mylocation.bean.message;

import br.com.mylocation.define.ProtocolDefines;

public class Command
    extends Message {

    /**
     * 
     */
    private static final long serialVersionUID = 434133680092147294L;
    private int rid;

    public Command(int rid, int operation, Object data) {
        super(operation, ProtocolDefines.TYPE_COMMAND, data);
        this.rid = rid;
    }
    
    public Command(int rid, int operation) {
        super(operation, ProtocolDefines.TYPE_COMMAND);
        this.rid = rid;
    }

    protected Command(int rid, int operation, int type, Object data) {
        super(operation, type, data);
        this.rid = rid;
    }
    
    protected Command(int rid, int operation, int type) {
        super(operation, type);
        this.rid = rid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
