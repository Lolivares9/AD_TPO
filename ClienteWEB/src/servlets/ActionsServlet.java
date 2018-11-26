package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import dto.BazaDTO;
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
        	getSiguienteTurno(request,response);
        }else if ("BuscarActualizacion".equals(action)){
        	buscarActualizacion(request,response);
        }else if ("GetRespuestaEnvite".equals(action)){
        	getRespuestaEnvite(request,response);  
	    }else if ("ResultadoBaza".equals(action)){
        	resultadoBaza(request,response);
	    }
    }

	protected void dispatch(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (jsp != null)
        {
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
					armarResponsePartido(request, partido, usuario);
					dispatch(jspPage, request, response);
				}else {
					request.setAttribute("usuario", usuario);
					request.setAttribute("categoria", categoria);
					request.setAttribute("modalidad", TipoModalidad.Libre_individual.name());
					jspPage = "/lobby.jsp";
					dispatch(jspPage, request, response);
				}
			} catch (ComunicationException |ServletException |IOException e) {
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
					armarResponsePartido(request, partido, usuario);
					dispatch(jspPage, request, response);
				}else {
					request.setAttribute("usuario", usuario);
					request.setAttribute("categoria", categoria);
					request.setAttribute("modalidad", modalidad);
					jspPage = "/lobby.jsp";
					dispatch(jspPage, request, response);
				}
	        } catch (ComunicationException |ServletException |IOException e) {
				e.printStackTrace();
			}     
	    }
	
    private void getRespuestaEnvite(HttpServletRequest request, HttpServletResponse response) {
    	try {
        	String idBaza = request.getParameter("baza");
        	String enviteActual = request.getParameter("enviteActual");
        	Envite e = Envite.valueOf(enviteActual);
			TurnoDTO turno = BusinessDelegate.getInstancia().getRespuestaEnvite(Integer.valueOf(idBaza), e);
			if(turno != null){
				Gson g = new Gson();
				Map<String,Object> turnoMapa = new HashMap<String, Object>();
				turnoMapa.put("apodo", turno.getJugadorDTO().getApodo());
				int index = turno.getEnviteActual().name().lastIndexOf("_");
				String envite = turno.getEnviteActual().name().substring(index+1);
				turnoMapa.put("envite", envite);
				String j = g.toJson(turnoMapa);

			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    try {
					response.getWriter().write(j);
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ComunicationException e) {
			e.printStackTrace();
		}		
	}
	
	private void guardarJugada(HttpServletRequest request, HttpServletResponse response) {
		String idPartido = request.getParameter("partido");
    	String apodo = request.getParameter("apodo");
    	String carta = request.getParameter("idCarta");
    	String envite = request.getParameter("envite");
    	Integer numTurno = Integer.parseInt(request.getParameter("numTurno"));
    	JugadorDTO j = new JugadorDTO();
    	j.setApodo(apodo);
    	CartaDTO c = new CartaDTO();
    	c.setIdCarta(carta.equals("") ? null : Integer.valueOf(carta));
    	Integer idBaza = 1;
    	Envite e = envite.equals("") ?  Envite.Nada : Envite.valueOf(envite);
    	TurnoDTO turno = new TurnoDTO(idBaza, numTurno,  j, e, c); //aca va el id de baza
    	try {
			BusinessDelegate.getInstancia().nuevaJugada(Integer.valueOf(idPartido), turno);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ComunicationException exc) {
			exc.printStackTrace();
		}
	}
	
	private void getSiguienteTurno(HttpServletRequest request, HttpServletResponse response) {
    	String idBaza = request.getParameter("idBaza");
    	int numTurno = Integer.parseInt(request.getParameter("numTurno"));
    	boolean trucoCantado = Boolean.parseBoolean(request.getParameter("trucoCantado"));
    	boolean envidoCantado = Boolean.parseBoolean(request.getParameter("envidoCantado"));
    	//String trucoActual = "Truco_Querido";
    	try {
    		TurnoDTO turno = null;
    		//En el servidor ver si no termino la mano ya, y mandar un flag en el turno para que en la web se cargue otra mano
			List<TurnoDTO> turnos = BusinessDelegate.getInstancia().buscarTurnos(Integer.valueOf(idBaza));
			if(turnos.size() > numTurno) {
				Collections.sort(turnos);
				turno = turnos.get(numTurno);
			}
			if(turno != null){
				Gson g = new Gson();
				Map<String,Object> turnoMapa = new HashMap<String, Object>();
				if(turno.getCartaDTO() != null) {// && !trucoCantado && !envidoCantado) { //Si la carta no esta null puede ser un envite de truco o tantos
					turnoMapa.put("carta", turno.getCartaDTO().getNumero()+""+turno.getCartaDTO().getPalo());
					turnoMapa.put("enviteTruco", "");
					turnoMapa.put("enviteTantos", "");
				}
				if(!envidoCantado && turno.getEnviteTantos() != null){
					turnoMapa.put("enviteTantos", turno.getEnviteTantos());
					turnoMapa.put("enviteTruco", "");
				}else if(turno.getEnviteJuego() != null && !trucoCantado && turno.getEnviteJuego() != Envite.Nada){
					//}else if(turno.getEnviteJuego() != null && (!trucoCantado || (turno.getEnviteJuego().name().length() > trucoActual))){
					turnoMapa.put("enviteTruco", turno.getEnviteJuego());
					turnoMapa.put("enviteTantos", "");
				}
				
				if(!turnoMapa.isEmpty()) {
					turnoMapa.put("apodo", turno.getJugadorDTO().getApodo()); 
					String j = g.toJson(turnoMapa);
					
				    response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				    try {
						response.getWriter().write(j);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ComunicationException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void buscarActualizacion(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		Gson g = new Gson();
    		Map<String,Object> datosActualizados = new HashMap<String, Object>();
        	String numBazas = request.getParameter("numBazas");
        	String numManos = request.getParameter("numManos");
        	String numChico = request.getParameter("numChico");
        	String idPartido = request.getParameter("idPartido");
        	String usuario = request.getParameter("usuario");
			Map<String,Object> datos = BusinessDelegate.getInstancia().buscarActualizacion(Integer.parseInt(idPartido), Integer.parseInt(numBazas), Integer.parseInt(numManos), Integer.parseInt(numChico));
			if(datos != null){
				Integer idBaza = (Integer) datos.get("idBaza");
				if(datos.get("flag").equals("Baza")) {
					datosActualizados.put("flag", "Baza");
					datosActualizados.put("idBaza", idBaza);
					datosActualizados.putAll(armarDatosJugadores((List<ParejaDTO>) datos.get("parejas"),usuario));
				}else if(datos.get("flag").equals("Mano")) {
					datosActualizados.put("flag", "Mano");
					datosActualizados.put("idBaza", idBaza);
					datosActualizados.putAll(armarDatosPartido((List<ParejaDTO>) datos.get("parejas"),usuario));
				}else if(datos.get("flag").equals("Chico")) {
					datosActualizados.put("flag", "Chico");
					datosActualizados.put("idBaza", idBaza);
					datosActualizados.putAll(armarDatosPartido((List<ParejaDTO>) datos.get("parejas"),usuario));
				}else if(datos.get("flag").equals("Partido")) {
					datosActualizados.put("flag", "Partido");
					datosActualizados.put("parejaGanadora", datos.get("parejaGanadora"));
				}
			}
			String j = g.toJson(datosActualizados);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    try {
				response.getWriter().write(j);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ComunicationException e) {
			e.printStackTrace();
		}

	}
    
    private void resultadoBaza(HttpServletRequest request, HttpServletResponse response) {
    	try {
	    	Gson g = new Gson();
	    	Map<String,Object> datos = new HashMap<String,Object>();
	    	Integer idBaza = Integer.valueOf(request.getParameter("idBaza"));
	    	BazaDTO baza = BusinessDelegate.getInstancia().buscarBaza(idBaza);
	    	datos.put("puntajeP1", baza.getPuntajePareja1());
	    	datos.put("puntajeP2", baza.getPuntajePareja2());
	    	datos.put("parejaGanadora", baza.getGanadores().getIdPareja());
			String  result = g.toJson(datos);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
    	} catch (ComunicationException | IOException e) {
			e.printStackTrace();
		}
    }
    
    private void armarResponsePartido(HttpServletRequest request, PartidoDTO partido, String usuario){
    	Gson g = new Gson();
		request.setAttribute("idPartido", partido.getIdPartido());
		Map<String,String> datos = armarDatosPartido(partido.getParejaDTOs(), usuario);
		datos.put("idBaza", Integer.toString(partido.getIdBazaInicial()));
		String  detallePartido = g.toJson(datos);
		request.setAttribute("detalle", detallePartido);
    }
    
    private Map<String,String> armarDatosPartido(List<ParejaDTO> parejas, String usuario) {
		Map<String,String> datos = new HashMap<String,String>();
		
		ParejaDTO par1 = parejas.get(0);
		ParejaDTO par2 = parejas.get(1);
		List<JugadorDTO> jug = new ArrayList<JugadorDTO>();
		jug.add(par1.getJugadorDTO1());
		jug.add(par1.getJugadorDTO2());
		
		List<JugadorDTO> jug2 = new ArrayList<JugadorDTO>();
		jug2.add(par2.getJugadorDTO1());
		jug2.add(par2.getJugadorDTO2());
		
		List<CartaDTO> cartasUsr = null;
		if(!buscarParejaUsuario(jug, datos, usuario)) {
			datos.put("parejaJugador1", "2");
			buscarParejaUsuario(jug2, datos, usuario);
			ordenarParejaContraria(jug, datos);
			cartasUsr = obtenerCartasUsuario(par2,usuario);
		}else {
			datos.put("parejaJugador1", "1");
			ordenarParejaContraria(jug2, datos);
			cartasUsr = obtenerCartasUsuario(par1,usuario);
		}
		
		int n = 1;
		for (CartaDTO c : cartasUsr) {
			datos.put("IdCarta"+ n, ""+c.getIdCarta());
			datos.put("carta"+ n++, c.getNumero()+""+c.getPalo());
		}
		return datos;
    }
    
    private Map<String,String> armarDatosJugadores(List<ParejaDTO> parejas, String usuario) {
		Map<String,String> datos = new HashMap<String,String>();
		
		ParejaDTO par1 = parejas.get(0);
		ParejaDTO par2 = parejas.get(1);
		List<JugadorDTO> jug = new ArrayList<JugadorDTO>();
		jug.add(par1.getJugadorDTO1());
		jug.add(par1.getJugadorDTO2());
		
		List<JugadorDTO> jug2 = new ArrayList<JugadorDTO>();
		jug2.add(par2.getJugadorDTO1());
		jug2.add(par2.getJugadorDTO2());
		
		if(!buscarParejaUsuario(jug, datos, usuario)) {
			datos.put("parejaJugador1", "2");
			buscarParejaUsuario(jug2, datos, usuario);
			ordenarParejaContraria(jug, datos);
		}else {
			datos.put("parejaJugador1", "1");
			ordenarParejaContraria(jug2, datos);
		}
		
		return datos;
    }
    
    private List<CartaDTO> obtenerCartasUsuario(ParejaDTO par, String usuario) {
    	if(par.getJugadorDTO1().getApodo().equals(usuario)){
    		return par.getCartasJug1();
    	}else {
    		return par.getCartasJug2();
    	}
	}

	private void ordenarParejaContraria(List<JugadorDTO> jug, Map<String, String> datos) {
    		int primero = 0;
    		int segundo = 0;
    		String pareja = datos.get("parejaJugador1");
			if(jug.get(0).getNumJugador() < jug.get(1).getNumJugador()) {
				segundo++;
			}else{
				primero++;
			}
			if(pareja.equals("1")){
				datos.put("apodoJugador2", jug.get(primero).getApodo());
				datos.put("idJugador2", jug.get(primero).getId().toString());
				datos.put("catJugador2", jug.get(primero).getCategoria().toString());
				datos.put("posJugador2", jug.get(primero).toString());
				datos.put("apodoJugador4", jug.get(segundo).getApodo());
				datos.put("idJugador4", jug.get(segundo).getId().toString());
				datos.put("catJugador4", jug.get(segundo).getCategoria().toString());
				datos.put("posJugador4", jug.get(segundo).toString());
			}else{			
				datos.put("apodoJugador4", jug.get(primero).getApodo());
				datos.put("idJugador4", jug.get(primero).getId().toString());
				datos.put("catJugador4", jug.get(primero).getCategoria().toString());
				datos.put("posJugador4", jug.get(primero).toString());
				datos.put("apodoJugador2", jug.get(segundo).getApodo());
				datos.put("idJugador2", jug.get(segundo).getId().toString());
				datos.put("catJugador2", jug.get(segundo).getCategoria().toString());
				datos.put("posJugador2", jug.get(segundo).toString());
			}
	}

	private boolean buscarParejaUsuario(List<JugadorDTO> jug, Map<String, String> datos, String usuario) {
		boolean parUsuario = false;
		for (JugadorDTO jugadorDTO : jug) {
			if(jugadorDTO.getApodo().equals(usuario)) {
				datos.put("apodoJugador1", jugadorDTO.getApodo());
				datos.put("idJugador1", jugadorDTO.getId().toString());
				datos.put("catJugador1", jugadorDTO.getCategoria().toString());
				datos.put("posJugador1", jugadorDTO.getNumJugador().toString());
				jug.remove(jugadorDTO);
				parUsuario = true;
				break;
			}
		}
		if(parUsuario){
			datos.put("apodoJugador3", jug.get(0).getApodo());
			datos.put("idJugador3", jug.get(0).getId().toString());
			datos.put("catJugador3", jug.get(0).getCategoria().toString());
			datos.put("posJugador3", jug.get(0).getNumJugador().toString());
		}
		return parUsuario;
    }
}