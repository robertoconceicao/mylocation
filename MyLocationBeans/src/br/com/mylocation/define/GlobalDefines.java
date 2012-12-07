package br.com.mylocation.define;

public interface GlobalDefines {

	// operations
	static final int OPERATION_LOGIN = 0;
	static final int OPERATION_POSITION = 1;
	static final int OPERATION_LOGOUT = 2;

	// types
	static final int TYPE_EVENT = 0;
	static final int TYPE_COMMAND = 1;
	static final int TYPE_COMMAND_RESPONSE = 2;

	// status
	static final int STATUS_SUCCESS = 0;
	static final int STATUS_FAIL = 1;
	
	// actions
	static final int ACTION_INSERT = 0;
	static final int ACTION_UPDATE = 1;
	static final int ACTION_REMOVE = 2;

	static final String HOST_NAME = "201.67.212.41";
	static final int PORT = 8000;
}
