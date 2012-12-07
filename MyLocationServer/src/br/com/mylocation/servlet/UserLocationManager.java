package br.com.mylocation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
@WebServlet("/UserLocationManager")
public class UserLocationManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");
		JSONObject jsonObject = new JSONObject();
		
		if (userCode == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			System.out.println("userCode is null.");
			return;
		}
		
		ControllerClient clients = (ControllerClient)getServletContext().getAttribute("controller_client");
		
		if (clients == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			System.out.println("ControllerClient is null.");
			return;
		}
		
		if (clients.getClientInfoList() == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			System.out.println("ClientInfoList is null.");
			return;
		}
		
		if (clients.getClientInfoList().size() == 0) {
			jsonObject.put("status", 0);
			printResponse(response, jsonObject.toString());
			System.out.println("ClientInfoList is empty.");
			return;
		}
		
		System.out.println("clients size: " + clients.getClientInfoList().size());
		
		for (ClientInfo client : clients.getClientInfoList()) {
			System.out.println("client.key: [" + client.getKey() + "] userCode: [" + userCode + "]");
			if (client.getKey().equals(userCode)) {
				jsonObject.put("status", 1);
				jsonObject.put("name", client.getName());
				jsonObject.put("code", client.getKey());
				jsonObject.put("latitude", client.getPosition().getLatitude());
				jsonObject.put("longitude", client.getPosition().getLongitude());
				jsonObject.put("extra", "Last place!!!");
				System.out.println("json: " + jsonObject.toJSONString());
				printResponse(response, jsonObject.toString());
				return;
			}
		}
		jsonObject.put("status", 0);
		printResponse(response, jsonObject.toString());
		System.out.println("Client code not found.");
	}
	
	private void printResponse(HttpServletResponse response, String responseContent) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(responseContent);
		out.close();
	}
}
