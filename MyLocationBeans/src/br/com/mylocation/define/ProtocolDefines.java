package br.com.mylocation.define;

public interface ProtocolDefines {

    // operations
    static final int OPERATION_LOGIN = 0;
    static final int OPERATION_POSITION = 1;

    // types
    static final int TYPE_EVENT = 0;
    static final int TYPE_COMMAND = 1;
    static final int TYPE_COMMAND_RESPONSE = 2;

    // status
    static final int STATUS_SUCCESS = 0;
    static final int STATUS_FAIL = 1;
    
    static final String HOST_NAME = "127.0.0.1";
    static final int PORT = 8889;
}