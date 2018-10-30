package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
        request.getParameterNames();
        request.getAttributeNames();
        
        if ((action == null) || (action.length() < 1))
        {
            action = "default";
        }else if ("LibreIndiv".equals(action))
        {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } finally {
                    reader.close();
                }
                Gson g = new Gson();
                Map<String,String> mapa = g.fromJson(sb.toString(), Map.class);
				PartidoDTO partido = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(mapa.get("Categoria"), mapa.get("Usuario"));
				jspPage = "/partido.jsp";
				request.setAttribute("partido", partido);
			} catch (ComunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   
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
