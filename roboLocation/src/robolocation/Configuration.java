/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robolocation;

/**
 *
 * @author roberto
 */
public class Configuration {
    
    private int qtdeClients;
    private int timeLogin;
    private int timePosition;
    private String hostName;
    private int port;
    
    public Configuration(int qtdeClients, int timeLogin, int timePosition,
                         String hostName, int port) {
        this.qtdeClients = qtdeClients;
        this.timeLogin = timeLogin;
        this.timePosition = timePosition;
        this.hostName = hostName;
        this.port = port;
    }

    public int getQtdeClients() {
        return qtdeClients;
    }

    public void setQtdeClients(int qtdeClients) {
        this.qtdeClients = qtdeClients;
    }

    public int getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(int timeLogin) {
        this.timeLogin = timeLogin;
    }

    public int getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(int timePosition) {
        this.timePosition = timePosition;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}