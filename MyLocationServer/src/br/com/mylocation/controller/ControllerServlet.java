package br.com.mylocation.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.mylocation.socket.ControllerClient;
import br.com.mylocation.socket.ServerSocket;

@WebServlet(description = "Servlet de Controle", urlPatterns = { "/ControllerServlet" }, loadOnStartup = 0)
public class ControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	private ControllerClient controllerClient;
	private UserLocationManagerServlet userLocationManagerServlet;

	public ControllerServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
	    launchSystem();
	}
	
	private void launchSystem(){
		System.out.println("Iniciando sistema...");		
		controllerClient = new ControllerClient();
		userLocationManagerServlet = new UserLocationManagerServlet(controllerClient);
		serverSocket = new ServerSocket(controllerClient);
		controllerClient.setServerSocket(serverSocket);
		controllerClient.setUserLocationManagerServlet(userLocationManagerServlet);
	}

	public void destroy() {
	    System.out.println("Finalizando sistema...");
	    serverSocket.destroy();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
