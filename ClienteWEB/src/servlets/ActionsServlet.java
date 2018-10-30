package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import delegado.BusinessDelegate;
import dto.PartidoDTO;
import excepciones.ComunicationException;

@WebServlet("/ActionsServlet")
public class ActionsServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
       
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");
        String jspPage = "/index.jsp";
        
        if ((action == null) || (action.length() < 1))
        {
            action = "default";
        }else if ("LibreIndiv".equals(action))
        {
            try {
				PartidoDTO partido = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(request.getAttribute("usuario"));
				request.setAttribute("partido", partido);
			} catch (ComunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            jspPage = "/partido.jsp";
        }
        
        
    
        dispatch(jspPage, request, response);
    }
	
	
    protected void dispatch(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (jsp != null)
        {
        	/*Envía el control al JSP que pasamos como parámetro, y con los 
        	 * request / response cargados con los parámetros */
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            rd.forward(request, response);
        }
    }
}
