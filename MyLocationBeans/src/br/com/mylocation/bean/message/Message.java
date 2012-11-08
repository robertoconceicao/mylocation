package br.com.mylocation.bean.message;

import java.io.Serializable;

public class Message
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3090792066911789558L;
    private int operation;
    private int type;
    private Object data;

    protected Message(int operation, int type, Object data) {
        this.operation = operation;
        this.type = type;
        this.data = data;
    }
    
    protected Message(int operation, int type) {
        this.operation = operation;
        this.type = type;
    }
    
    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
