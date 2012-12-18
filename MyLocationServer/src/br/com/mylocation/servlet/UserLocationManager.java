package br.com.mylocation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import br.com.mylocation.model.ClientInfo;
import br.com.mylocation.socket.ControllerClient;

@WebServlet("/UserLocationManager")
public class UserLocationManager extends HttpServlet {

	private static Logger log = Logger.getLogger(UserLocationManager.class);
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");
		JSONObject jsonObject = new JSONObject();

		if (userCode == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			log.info("Código de usuário nulo.");
			return;
		}

		ControllerClient controllerClient = (ControllerClient) getServletContext().getAttribute("controller_client");

		if (controllerClient == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			log.error("ControllerClient está nulo.");
			return;
		}

		if (controllerClient.getClientInfoList() == null) {
			jsonObject.put("status", -1);
			printResponse(response, jsonObject.toString());
			log.error("Lista com informações dos clientes está nula.");
			return;
		}

		if (controllerClient.getClientInfoList().size() == 0) {
			jsonObject.put("status", 0);
			printResponse(response, jsonObject.toString());
			log.debug("Lista com informações dos clientes está vazia.");
			return;
		}
		
		if (userCode.equals("all")) {
			LinkedList<JSONObject> jsonList = new LinkedList<JSONObject>();
			JSONObject json;
			for (ClientInfo client : controllerClient.getClientInfoList()) {
				json = new JSONObject();
				json.put("status", 1);
				json.put("name", client.getName());
				json.put("code", client.getKey());
				json.put("latitude", client.getPosition().getLatitude());
				json.put("longitude", client.getPosition().getLongitude());
				json.put("extra", "Last place!!!");
				jsonList.add(json);			
			}
			printResponse(response, JSONValue.toJSONString(jsonList));
			return;
		}

		log.debug("Tamanho da lista com informações dos clientes: " + controllerClient.getClientInfoList().size());

		for (ClientInfo client : controllerClient.getClientInfoList()) {
			log.debug("client.key: [" + client.getKey() + "] userCode: [" + userCode + "]");
			if (client.getKey().equals(userCode)) {
				jsonObject.put("status", 1);
				jsonObject.put("name", client.getName());
				jsonObject.put("code", client.getKey());
				jsonObject.put("latitude", client.getPosition().getLatitude());
				jsonObject.put("longitude", client.getPosition().getLongitude());
				jsonObject.put("extra", "Last place!!!");
				log.debug("json: " + jsonObject.toJSONString());
				printResponse(response, jsonObject.toString());
				return;
			}
		}
		jsonObject.put("status", 0);
		printResponse(response, jsonObject.toString());
		log.debug("Código de usuário não encontrado.");
	}

	private void printResponse(HttpServletResponse response, String responseContent) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(responseContent);
		out.close();
	}
}
