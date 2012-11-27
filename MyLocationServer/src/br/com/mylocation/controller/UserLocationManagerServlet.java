package br.com.mylocation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import br.com.mylocation.model.ClientInfo;
import br.com.mylocation.socket.ControllerClient;

/**
 * Servlet implementation class UserLocationManager
 */
@WebServlet(description = "Gerenciador de Localização do Usuário", urlPatterns = { "/UserLocationManagerServlet" })
public class UserLocationManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ControllerClient controllerClient;
	
	public UserLocationManagerServlet(ControllerClient controllerClient) {
		super();
		this.controllerClient = controllerClient;
	}
 
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");
		
		// Pegamos as informações do usuário e devolvemos um JSON
		
		// Implementação temporária
		List<ClientInfo> clientInfoList = controllerClient.getClientInfoList();
		
		JSONObject jsonObject = new JSONObject();
		for(ClientInfo clientInfo : clientInfoList){
			if(clientInfo.getKey().equals(userCode)){
				jsonObject.put("name", clientInfo.getName());
				jsonObject.put("code", clientInfo.getKey());
				jsonObject.put("latitude", clientInfo.getPosition().getLatitude());
				jsonObject.put("longitude", clientInfo.getPosition().getLongitude());
				jsonObject.put("extra", "Last place!!!");
				break;
			}
		}
		
		System.out.println("json: " + jsonObject.toJSONString());
		
		PrintWriter out = response.getWriter();
		out.print(jsonObject.toString());
		out.close();
	}
}