package br.com.mylocation.bean.message.commandresponse;

import java.io.Serializable;

public class LoginResponse implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7770189981244036600L;
	private String key;

	public LoginResponse() {

	}

	public LoginResponse(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
