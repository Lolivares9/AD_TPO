package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import dto.JugadorDTO;
import dto.ParejaDTO;
import dto.PartidoDTO;
import dto.TurnoDTO;
import enums.Envite;
import enums.TipoModalidad;
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
        request.getParameterNames();
        request.getAttributeNames();
        
        if ((action == null) || (action.length() < 1))
        {
    		String jspPage = "/layout.jsp";
    		String usuario = request.getParameter("usuario");
    		String categoria = request.getParameter("categoria");
    		request.setAttribute("usuario", usuario);
			request.setAttribute("categoria", categoria);
    		dispatch(jspPage, request, response);
        }else if ("LibreIndiv".equals(action)){
        	buscarPartidaLibreIndividual(request,response);
        }else if ("LibreIndivLob".equals(action)){
        	buscarPartidaLibreIndividualLobby(request,response);
        }else if ("GuardarJugada".equals(action)){
        	guardarJugada(request,response);
        }else if ("GetNovedad".equals(action)){
        	getNovedad(request,response);
        }
       
    }
	
    private void getNovedad(HttpServletRequest request, HttpServletResponse response) {
    	String idPartido = request.getParameter("partido");
    	try {
			TurnoDTO turno = BusinessDelegate.getInstancia().buscarNovedades(Integer.getInteger(idPartido));
			if(turno != null){
				Gson g = new Gson();
				Map<String,Object> turnoMapa = new HashMap<String, Object>();
				turnoMapa.put("carta", turno.getCartaDTO().getNumero()+""+turno.getCartaDTO().getPalo());
				turnoMapa.put("apodo", turno.getJugadorDTO().getApodo());
				turnoMapa.put("esMiTurno", true);
				String j = g.toJson(turnoMapa);

			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    try {
					response.getWriter().write(j);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

	private void guardarJugada(HttpServletRequest request, HttpServletResponse response) {
		String idPartido = request.getParameter("partido");
    	String apodo = request.getParameter("apodo");
    	String carta = request.getParameter("idCarta");
    	JugadorDTO j = new JugadorDTO();
    	j.setApodo(apodo);
    	CartaDTO c = new CartaDTO();
    	c.setIdCarta(Integer.valueOf(carta));
    	Integer idBaza = 1;
    	Integer numTurno = 1;
    	TurnoDTO turno = new TurnoDTO(idBaza, numTurno, j, Envite.Nada, c); //aca va el id de baza
    	try {
			BusinessDelegate.getInstancia().nuevaJugada(Integer.valueOf(idPartido), Arrays.asList(turno));
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ComunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        	String usuario = request.getParameter("usuario");
        	String categoria = request.getParameter("categoria");
			PartidoDTO partido = BusinessDelegate.getInstancia().iniciarPartidaLibreIndividual(categoria, usuario);
			String jspPage = "/partido.jsp";
			if(partido != null){
				armarDetallePartido(request, partido, usuario);
				dispatch(jspPage, request, response);
			}else {
				request.setAttribute("usuario", usuario);
				request.setAttribute("categoria", categoria);
				request.setAttribute("modalidad", TipoModalidad.Libre_individual.name());
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
        	String usuario = request.getParameter("usuario");
        	String categoria = request.getParameter("categoria");
        	String modalidad = request.getParameter("modalidad");
			PartidoDTO partido = BusinessDelegate.getInstancia().buscarPartidaLobby(usuario, modalidad);
			String jspPage = "/partido.jsp";
			if(partido != null){
				armarDetallePartido(request, partido, usuario);
				dispatch(jspPage, request, response);
			}else {
				request.setAttribute("usuario", usuario);
				request.setAttribute("categoria", categoria);
				request.setAttribute("modalidad", modalidad);
				jspPage = "/lobby.jsp";
				dispatch(jspPage, request, response);
			}
        } catch (ComunicationException |ServletException |IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
    }
    
    private void armarDetallePartido(HttpServletRequest request, PartidoDTO partido, String usuario) {
		Gson g = new Gson();
		request.setAttribute("idPartido", partido.getIdPartido());
		//partido.getChicoDTO().get(0).getManos().get(0).getBazas().get(0).getNumero();//MANDAR ID de baza a la pagina
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
					request.setAttribute("usuario", usuario);
					//TODO Hardcodeado porque falta guardar las cartas cuando se reparten
					if(cartasUsr == null) {
						datos.put("IdCarta"+ n, ""+5);
						datos.put("carta"+n++, 1+"Basto");
						datos.put("IdCarta"+ n, ""+9);
						datos.put("carta"+n++, 1+"Espada");
						datos.put("IdCarta"+ n, ""+23);
						datos.put("carta"+n++, 1+"Oro");
					}else{
						for (CartaDTO c : cartasUsr) {
							//TODO ver si es necesario mandar el id de la carta para cuando se hagan movimientos (agregar al DTO el id)
							datos.put("IdCarta"+ n, ""+c.getIdCarta());
							datos.put("carta"+ n++, c.getNumero()+""+c.getPalo());
						}
					}
					break;
				}
			}						
			String  detallePartido = g.toJson(datos);
			request.setAttribute("detalle", detallePartido);
    }
}
