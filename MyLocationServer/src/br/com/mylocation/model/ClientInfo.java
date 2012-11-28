package br.com.mylocation.model;

import java.util.Observable;
import java.util.UUID;

import br.com.mylocation.bean.message.event.Position;
import br.com.mylocation.controller.UserLocationManagerServlet;
import br.com.mylocation.define.GlobalDefines;

public class ClientInfo
    extends Observable {

    private String key;
    private String name;
    private Position position;
    private int action;

    public ClientInfo(UserLocationManagerServlet userLocationManagerServlet) {
        String randomKey = createKey();
        setKey(randomKey);
        setAction(GlobalDefines.ACTION_INSERT);
        addObserver(userLocationManagerServlet);
        notifyChange();
    }

    public String getKey() {
        return key;
    }

    private void setKey(String key) {
        this.key = key;
        setAction(GlobalDefines.ACTION_UPDATE);
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setAction(GlobalDefines.ACTION_UPDATE);
        notifyChange();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        setAction(GlobalDefines.ACTION_UPDATE);
        notifyChange();
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    private String createKey() {
        UUID uuid = UUID.randomUUID();
        String randomKey = uuid.toString();
        return randomKey.substring(0, 8);
    }

    public void kill() {
        setAction(GlobalDefines.ACTION_REMOVE);
        notifyChange();
    }

    public void notifyChange() {
        setChanged();
        notifyObservers(this);
    }
}
