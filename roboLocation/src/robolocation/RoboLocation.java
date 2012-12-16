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
import robolocation.io.SocketClient;

/**
 *
 * @author roberto
 */
public class RoboLocation {

    private Configuration config;
    private ExecutorService executor;
    private boolean runner;
    private List<SocketClient> listClients;
    
    public RoboLocation(Configuration config) {
        this.config = config;
        executor = Executors.newCachedThreadPool();
        runner = false;
        listClients = new ArrayList<SocketClient>();
    }
    
    public void start() {
        runner = true;
        if(config.getQtdeClients() > 0){
            for(int i=0;i<config.getQtdeClients();i++){                
                newClient();                
            }
        }else{
            while(runner){
                newClient();
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
            SocketClient client = new SocketClient();
            client.connect(config.getHostName(), config.getPort());
            listClients.add(client);
            
            executor.execute(client);
            
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RoboLocation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RoboLocation.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
