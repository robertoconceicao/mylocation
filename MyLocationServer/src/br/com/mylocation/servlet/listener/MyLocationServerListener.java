package br.com.mylocation.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import br.com.mylocation.socket.ControllerClient;
import br.com.mylocation.socket.ServerSocket;

public class MyLocationServerListener implements ServletContextListener {
	
	private ServerSocket serverSocket;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		serverSocket.destroy();
		System.out.println("Finalizando sistema.");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		System.out.println("Iniciando sistema...");
		ControllerClient controllerClient = new ControllerClient();
		controllerClient.setServerSocket(new ServerSocket(controllerClient));
		
		context.setAttribute("controller_client", controllerClient);
	}

}
