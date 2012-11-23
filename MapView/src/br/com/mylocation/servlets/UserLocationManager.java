package br.com.mylocation.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mylocation.model.LocationInfo;
import br.com.mylocation.model.User;

/**
 * Servlet implementation class UserLocationManager
 */
public class UserLocationManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");
		
		/*request.setAttribute("user_info", getUserInfo(userCode));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserLocation.jsp");
		dispatcher.forward(request,  response);*/
		
		PrintWriter out = response.getWriter();
		out.print("klasjfadflakjflakdjfalskdfadfhadfjdfa");
		out.close();
	}
	
	/*private User getUserInfo(String userCode) {
		//TODO: Temos que ver como pegaremos as informações.
		User user = new User();
		user.setName("Johannes Ferreira");
		user.setCode("a34hh2gd");
		user.setInfo("Alguma informação relevante.");
		user.setPoints(new ArrayList<LocationInfo>());
		user.getPoints().add(new LocationInfo("-27.606574", "-48.578796", null));
		user.getPoints().add(new LocationInfo("-27.603399", "-48.576307", null));
		user.getPoints().add(new LocationInfo("-27.60959", "-48.578861", null));
		
		return user;
	}*/
}
