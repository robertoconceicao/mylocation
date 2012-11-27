package br.com.mylocation.controller;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class UserLocationManager
 */
@WebServlet(description = "Gerenciador de Localização do Usuário", urlPatterns = { "/UserLocationManagerServlet" })
public class UserLocationManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");
		
		// Pegamos as informações do usuário e devolvemos um JSON
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Johannes Ferreira");
		jsonObject.put("code", userCode);
		jsonObject.put("latitude", -27.603608);
		jsonObject.put("longitude", -48.576372);
		jsonObject.put("extra", "Last place!!!");
		
		System.out.println("json: " + jsonObject.toJSONString());
		
		PrintWriter out = response.getWriter();
		out.print(jsonObject.toString());
		out.close();
	}
}