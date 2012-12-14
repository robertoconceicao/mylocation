package br.com.mylocation.servlet.listener;

import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import br.com.mylocation.socket.ControllerClient;
import br.com.mylocation.socket.ServerSocket;

public class MyLocationServerListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(MyLocationServerListener.class);
	private ServerSocket serverSocket;	

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Finalizando sistema...");
		serverSocket.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initLog();
		
		log.info("Inicializando sistema...");
		ServletContext context = event.getServletContext();		
		ControllerClient controllerClient = new ControllerClient();
		serverSocket = new ServerSocket(controllerClient);
		controllerClient.setServerSocket(serverSocket);

		context.setAttribute("controller_client", controllerClient);		
	}

	private void initLog() {
		File propertiesFile = new File("configLog.properties");
		PropertyConfigurator.configure(propertiesFile.toString());
	}

}
