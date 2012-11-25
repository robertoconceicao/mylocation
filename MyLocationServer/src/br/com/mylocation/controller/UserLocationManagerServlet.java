package br.com.mylocation.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Gerenciador de Localização do Usuário", urlPatterns = { "/UserLocationManagerServlet" })
public class UserLocationManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCode = request.getParameter("user_code");

		/*
		 * request.setAttribute("user_info", getUserInfo(userCode));
		 * 
		 * RequestDispatcher dispatcher = request.getRequestDispatcher("UserLocation.jsp"); dispatcher.forward(request, response);
		 */

		PrintWriter out = response.getWriter();
		out.print("klasjfadflakjflakdjfalskdfadfhadfjdfa");
		out.close();
	}

	/*
	 * private User getUserInfo(String userCode) { //TODO: Temos que ver como pegaremos as informações. User user = new User();
	 * user.setName("Johannes Ferreira"); user.setCode("a34hh2gd"); user.setInfo("Alguma informação relevante."); user.setPoints(new
	 * ArrayList<LocationInfo>()); user.getPoints().add(new LocationInfo("-27.606574", "-48.578796", null)); user.getPoints().add(new
	 * LocationInfo("-27.603399", "-48.576307", null)); user.getPoints().add(new LocationInfo("-27.60959", "-48.578861", null));
	 * 
	 * return user; }
	 */
}
