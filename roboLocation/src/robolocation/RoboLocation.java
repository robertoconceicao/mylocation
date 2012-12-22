/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robolocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import robolocation.gui.GuiPrincipal;
import robolocation.io.SocketClient;

/**
 *
 * @author roberto
 */
public class RoboLocation {

    private GuiPrincipal guiPrincipal;
    private ExecutorService executor;
    private boolean runner;
    private List<SocketClient> listClients;
    private static int sequence = 0;
    
    private int TIME_REFRESH = 5000;
    
    private long refresh;
    
    public RoboLocation(GuiPrincipal guiPrincipal) {
        this.guiPrincipal = guiPrincipal;
        executor = Executors.newCachedThreadPool();
        runner = false;
        listClients = new ArrayList<SocketClient>();
        refresh = System.currentTimeMillis() + TIME_REFRESH;
    }
    
    public void start() {
        runner = true;
        
        if(guiPrincipal.getConfig().getQtdeClients() > 0) {
            for(int i=0;i<guiPrincipal.getConfig().getQtdeClients();i++){                
                newClient();
                updateGui();
            }
        }else {
            while(runner) {
                newClient();
                updateGui();
            }
        }
        
    }
    
    public void stop() {
        runner = false;
        executor.shutdownNow();
        while(!listClients.isEmpty()){
            listClients.get(0).close();
            listClients.remove(0);            
        }
    }
    
    public void newClient(){
        try {
            SocketClient client = new SocketClient(sequence++, this);
            client.connect(guiPrincipal.getConfig().getHostName(), guiPrincipal.getConfig().getPort());
            listClients.add(client);
            
            executor.execute(client);
            
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RoboLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoboLocation.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public Configuration getConfig() {
        return guiPrincipal.getConfig();
    }

    public void finishClient(SocketClient client){
        
    }
    
    public void updateGui(){
        if(System.currentTimeMillis() > refresh){
            guiPrincipal.refresh();
            refresh += TIME_REFRESH;                    
        }
    }
}
