package br.com.mylocation.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyLocationServerListener implements ServletContextListener {
	
	//private ControllerClient controllerClient = null;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
//		ServletContext context = event.getServletContext();
//		System.out.println("Iniciando sistema...");
//		controllerClient = new ControllerClient();
//		controllerClient.setServerSocket(new ServerSocket(controllerClient));
//		System.out.println("Sistema inicializado.");
//		
//		context.setAttribute("ControllerClient", controllerClient);
	}

}
