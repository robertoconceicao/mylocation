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

@WebServlet("/AllUsersLocationManager")
public class AllUsersLocationManager extends HttpServlet {

	private static final long serialVersionUID = 8358927822874276958L;
	private static Logger log = Logger.getLogger(AllUsersLocationManager.class);

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ControllerClient controllerClient = (ControllerClient) getServletContext().getAttribute("controller_client");

		LinkedList<JSONObject> jsonList = new LinkedList<JSONObject>();
		JSONObject json;

		if (controllerClient == null) {
			printResponse(response, JSONValue.toJSONString(jsonList));
			log.error("ControllerClient está nulo.");
			return;
		}

		if (controllerClient.getClientInfoList() == null) {
			printResponse(response, JSONValue.toJSONString(jsonList));
			log.error("Lista com informações dos clientes está nula.");
			return;
		}

		if (controllerClient.getClientInfoList().size() == 0) {
			printResponse(response, JSONValue.toJSONString(jsonList));
			log.debug("Lista com informações dos clientes está vazia.");
			return;
		}

		for (ClientInfo client : controllerClient.getClientInfoList()) {
			if (client.getPosition() == null) {
				continue;
			}
			json = new JSONObject();
			json.put("name", client.getName());
			json.put("code", client.getKey());
			json.put("latitude", client.getPosition().getLatitude());
			json.put("longitude", client.getPosition().getLongitude());
			jsonList.add(json);
		}
		printResponse(response, JSONValue.toJSONString(jsonList));
	}

	private void printResponse(HttpServletResponse response, String responseContent) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(responseContent);
		out.close();
	}

}
