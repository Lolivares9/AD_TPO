package servlets;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import delegado.BusinessDelegate;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.ComunicationException;

@WebServlet("/AltaJugadorServlet")
public class AltaJugadorServlet  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JugadorDTO usuarioAlta;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jspPage = "/index.jsp";
		String nombre = request.getParameter("nombre");
		String apodo = request.getParameter("apodo");
		String mail = request.getParameter("mail");
		String contraseña = request.getParameter("pass");

		if(altaUsuario(nombre,apodo,mail,contraseña)){
			request.setAttribute("Message", "Jugador dado de alta correctamente");
			dispatch(jspPage, request, response);
		}
		else{
			jspPage = "/AltaJugador.jsp";
			request.setAttribute("errorMessage", "Apodo existente");
			dispatch(jspPage, request, response);
		}
		
	}
		
	private boolean altaUsuario(String nombre,String apodo,String mail, String contraseña){
		JugadorDTO jug = new JugadorDTO();
		jug.setNombre(nombre);
		jug.setApodo(apodo);
		jug.setMail(mail);
		jug.setPassword(contraseña);
		jug.setCategoria(Categoria.Novato);
		try {
			JugadorDTO juglog = BusinessDelegate.getInstancia().altaJugador(jug);
			if (juglog!=null){
				setUsuarioAlta(juglog);
				return true;
			} else {
				return false;
			}
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
		return false;
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
	
	public static JugadorDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public static void setUsuarioAlta(JugadorDTO usuarioAlta) {
		AltaJugadorServlet.usuarioAlta = usuarioAlta;
	}


	
}
