package br.com.mylocation.bean.message;

import br.com.mylocation.define.ProtocolDefines;

public class Event
    extends Message {

    /**
     * 
     */
    private static final long serialVersionUID = -2076123513611286508L;

    public Event(int operation, Object data) {
        super(operation, ProtocolDefines.TYPE_EVENT, data);
    }
    
    public Event(int operation) {
        super(operation, ProtocolDefines.TYPE_EVENT);
    }
}
