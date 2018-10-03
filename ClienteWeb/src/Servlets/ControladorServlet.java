package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import excepciones.ComunicationException;


@WebServlet("/ControladorServlet")
public class ControladorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jspPage = "/asignaciones.jsp";
		String apodo = request.getParameter("usuario");
		String password = request.getParameter("password");
		JugadorDTO jug = new JugadorDTO();
		jug.setApodo(apodo);
		jug.setPassword(password);
		try {
			if (BusinessDelegate.getInstancia().iniciarSesion(jug)) {
				dispatch(jspPage, request, response);
			} else {
				jspPage = "/login.jsp?error=LoginInvalido";
				dispatch(jspPage, request, response);
			}
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	protected void dispatch(String jsp, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (jsp != null) {
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			rd.forward(request, response);
		}
	
	}
}