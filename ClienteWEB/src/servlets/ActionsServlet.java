package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import delegado.BusinessDelegate;
import dto.CartaDTO;
import dto.ParejaDTO;
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
        }else if ("LibreIndiv".equals(action)){
        	buscarPartidaLibreIndividual(request,response);
        }else if ("LibreIndivLob".equals(action)){
        	buscarPartidaLibreIndividualLobby(request,response);
        }
       
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
    
    private void buscarPartidaLibreIndividual(HttpServletRequest request, HttpServletResponse response) {
        try {
        	String usuario = request.getParameter("Usuario");
			PartidoDTO partido = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(request.getParameter("Categoria"), usuario);
			String jspPage = "/partido.jsp";
			if(partido != null){
				armarDetallePartido(request, partido, usuario);
				dispatch(jspPage, request, response);
			}else {
				jspPage = "/lobby.jsp";
				dispatch(jspPage, request, response);
			}
		} catch (ComunicationException |ServletException |IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
    
    private void buscarPartidaLibreIndividualLobby(HttpServletRequest request, HttpServletResponse response) {
        try {
        	String usuario = request.getParameter("Usuario");
        	//TODO Debe buscar un partido creado
			PartidoDTO partido = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(request.getParameter("Categoria"), usuario);
			String jspPage = "/partido.jsp";
			if(partido != null){
				armarDetallePartido(request, partido, usuario);
				dispatch(jspPage, request, response);
			}else {
				
			}
        } catch (ComunicationException |ServletException |IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
    }
    
    private void armarDetallePartido(HttpServletRequest request, PartidoDTO partido, String usuario) {
		Gson g = new Gson();
		request.setAttribute("idPartido", partido.getIdPartido());
		List<ParejaDTO> parejas = partido.getParejaDTOs();
		Map<String,String> datos = new HashMap<String,String>();
		List<List<CartaDTO>> cartasOrden = new ArrayList<List<CartaDTO>>();
		ParejaDTO par1 = parejas.get(0);
		ParejaDTO par2 = parejas.get(1);
			datos.put("apodoJugador1", par1.getJugadorDTO1().getApodo());
			datos.put("idJugador1", par1.getJugadorDTO1().getId().toString());
			datos.put("catJugador1", par1.getJugadorDTO1().getCategoria().toString());
			
			datos.put("apodoJugador3", par1.getJugadorDTO2().getApodo());
			datos.put("idJugador3", par1.getJugadorDTO2().getId().toString());
			datos.put("catJugador3", par1.getJugadorDTO2().getCategoria().toString());
			
			datos.put("apodoJugador2", par2.getJugadorDTO1().getApodo());
			datos.put("idJugador2", par2.getJugadorDTO1().getId().toString());
			datos.put("catJugador2", par2.getJugadorDTO1().getCategoria().toString());
			
			datos.put("apodoJugador4", par2.getJugadorDTO2().getApodo());
			datos.put("idJugador4", par2.getJugadorDTO2().getId().toString());
			datos.put("catJugador4", par2.getJugadorDTO2().getCategoria().toString());
			
			cartasOrden.add(par1.getCartasJug1());
			cartasOrden.add(par2.getCartasJug1());
			cartasOrden.add(par1.getCartasJug2());
			cartasOrden.add(par2.getCartasJug2());
			//Los otros jugadores estaran en modo busqueda pegandole constantemente a este metodo hasta que encuentre partido,
			//entonces le enviamos las cartas que correspondan al jugador que hace la peticion
			for (int i = 1; i <= 4; i++) {
				if(datos.get("apodoJugador"+i).equals(usuario)) {
					List<CartaDTO> cartasUsr = cartasOrden.get(i);
					int n = 1;
					//TODO ver si es necesario mandar el id de la carta para cuando se hagan movimientos (agregar al DTO el id)
					for (CartaDTO c : cartasUsr) {
						datos.put("carta"+n++, c.getNumero()+""+c.getPalo());
					}
					break;
				}
			}						
			String  detallePartido = g.toJson(datos);
			request.setAttribute("detalle", detallePartido);
    }
}
