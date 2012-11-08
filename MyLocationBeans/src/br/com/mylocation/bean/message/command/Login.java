package br.com.mylocation.bean.message.command;

import java.io.Serializable;

public class Login implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2790132043789758337L;
    private String name;

    public Login() {

    }

    public Login(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
