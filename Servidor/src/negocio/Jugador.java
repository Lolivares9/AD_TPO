package negocio;

import java.util.ArrayList;
import java.util.List;

import dao.JugadorDAO;
import dto.JugadorDTO;
import enums.Categoria;
import excepciones.JugadorException;

public class Jugador {
	//El id de jugador lo puse en esta clase porque no se puede guardar si no est� ac�,
	//preguntar al profesor si est�n bien los id que pusimos en la tabla
	//cuando el id deberia ser el apodo
	private Integer id;
		
	private String nombre;
	private String apodo;
	private String mail;
	private Categoria categoria;
	private int puntaje;
	private int partidosJugados;
	private int partidosGanados;
	private boolean conectado;
	private boolean jugando;
	private boolean buscandoLibreIndividual;
	private String password;
	private Integer numeroTurnoPartido;

	public Jugador(String nombre, String apodo, String mail, Categoria categoria, int puntaje, int partidosJugados,
			int partidosGanados, boolean conectado, boolean jugando,boolean buscandoLibreIndividual, String password) {
		super();
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.categoria = categoria;
		this.puntaje = puntaje;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.conectado = conectado;
		this.jugando = jugando;
		this.buscandoLibreIndividual = buscandoLibreIndividual;
		this.password = password;
	}
	
	public Jugador(String nombre, String apodo, String mail, Categoria categoria, int puntaje, int partidosJugados,
			int partidosGanados, boolean conectado, boolean jugando,boolean buscandoLibreIndividual, String password,int numeroJugadorPartido) {
		super();
		this.nombre = nombre;
		this.apodo = apodo;
		this.mail = mail;
		this.categoria = categoria;
		this.puntaje = puntaje;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.conectado = conectado;
		this.jugando = jugando;
		this.buscandoLibreIndividual = buscandoLibreIndividual;
		this.password = password;
		this.numeroTurnoPartido = numeroJugadorPartido;

	}

	public Jugador(Integer idJugador, String apodo, String mail, String password, Categoria categoria, int puntaje,
			int partidosJugados, int partidosGanados, boolean conectado, boolean jugando,
			int numeroTurnoPartido, List<Grupo> grupos) {
		super();
		this.id = idJugador;
		this.apodo = apodo;
		this.mail = mail; 
		this.password = password; 
		this.categoria =  categoria; 
		this.puntaje =  puntaje;
		this.partidosJugados = partidosJugados; 
		this.partidosGanados = partidosGanados; 
		this.conectado = conectado;
		this.jugando = jugando;
		this.numeroTurnoPartido = numeroTurnoPartido; 
	}

	public boolean isBuscandoLibreIndividual() {
		return buscandoLibreIndividual;
	}


	public void setBuscandoLibreIndividual(boolean buscandoLibreIndividual) {
		this.buscandoLibreIndividual = buscandoLibreIndividual;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public int getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

	public boolean isJugando() {
		return jugando;
	}

	public void setJugando(boolean jugando) {
		this.jugando = jugando;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNumJugador() {
		return numeroTurnoPartido;
	}

	public void setNumJugador(Integer numJugador) {
		this.numeroTurnoPartido = numJugador;
	}

	public boolean guardar(){
		return JugadorDAO.getInstancia().guardar(this);
	}
	
	public static Jugador getJugador(String mail) throws JugadorException{
		Jugador j = JugadorDAO.getInstancia().buscarPorMail(mail);
		return j;
	}
	
	public Integer getNumeroTurnoPartido() {
		return numeroTurnoPartido;
	}

	public void setNumeroTurnoPartido(Integer numeroTurnoPartido) {
		this.numeroTurnoPartido = numeroTurnoPartido;
	}
	
	public JugadorDTO toDTO(){
		return new JugadorDTO(id, nombre, apodo, mail, categoria, puntaje, partidosJugados, partidosGanados, conectado, jugando, password, null, numeroTurnoPartido);
	}


	public boolean iniciarSesion(String usuarioIngresado, String passwordIngresada) {
		if(apodo.equals(usuarioIngresado) && password.equals(passwordIngresada)){
			conectado = true;
			this.guardar();
			return true;
		}
		return false;
	}


	public static List<Jugador> completarJugadores(Categoria categ, String apodo) {

		List<Jugador> jugadores = JugadorDAO.getInstancia().getAllJugadores();
		List <Jugador> jugadoresDisponibles = new ArrayList<Jugador>();
		
		//SACO EL JUGADOR DE LA LISTA
		for(int i = 0;i<jugadores.size();i++){
			if(jugadores.get(i).getApodo().equals(apodo)){
				//agrego el que traje de la BBDD porque esta completa
				jugadoresDisponibles.add(jugadores.get(i));
				//Lo saco de la lista
				jugadores.remove(jugadores.get(i));
				break;
			}
		}
		
		if (jugadores.size() >= 3) {
			List <Jugador> novatos = new ArrayList<Jugador>();
			List <Jugador> masters = new ArrayList<Jugador>();
			List <Jugador> expertos = new ArrayList<Jugador>();
			List <Jugador> calificados = new ArrayList<Jugador>();
			
			for(int i = 0;i<jugadores.size();i++){
				if(jugadores.get(i).getCategoria().equals(Categoria.Novato)){
					novatos.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Master)){
					masters.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Experto)){
					expertos.add(jugadores.get(i));
				}
				else if(jugadores.get(i).getCategoria().equals(Categoria.Calificado)){
					calificados.add(jugadores.get(i));
				}
			}
			
			if(novatos.size() >= 3 && categ.equals(Categoria.Novato) && jugadoresDisponibles.size() < 4){
				while(!novatos.isEmpty() && jugadoresDisponibles.size() < 4){
					jugadoresDisponibles.add(novatos.get(0));
					novatos.remove(0);
				}
			}
			if(masters.size() >= 3 && categ.equals(Categoria.Master) && jugadoresDisponibles.size() < 4){
				while(!masters.isEmpty() && jugadoresDisponibles.size() < 4 ){
					jugadoresDisponibles.add(masters.get(0));
					masters.remove(0);
				}
			}
			if(expertos.size() >= 3 && categ.equals(Categoria.Experto) && jugadoresDisponibles.size() < 4){
				while(!expertos.isEmpty() && jugadoresDisponibles.size() < 4 ){
					jugadoresDisponibles.add(expertos.get(0));
					expertos.remove(0);
				}
			}
			if(calificados.size() >= 3 && categ.equals(Categoria.Calificado) && jugadoresDisponibles.size() < 4){
				while(!calificados.isEmpty() && jugadoresDisponibles.size() < 4){
					jugadoresDisponibles.add(calificados.get(0));
					calificados.remove(0);
				}
			}
			
			//SI NO COMPLETAMOS CON UNA MISMA CATEGORIA, TENEMOS QUE COMPLETAR CON LAS MAYORES O MENORES CATEGORIAS
			if(jugadoresDisponibles.size() < 4){
				completarMayorCategoria(categ,jugadoresDisponibles,novatos,masters,expertos,calificados);
			}
			if(jugadoresDisponibles.size() < 4){
				completarMenorCategoria(categ,jugadoresDisponibles,novatos,masters,expertos,calificados);
			}
		}
		if (verificarIgualdadParejas(jugadoresDisponibles) == true) {
			return jugadoresDisponibles;
		}
		else {
			//aca deberiamos poner a los jugadores en lista de espera para jugar (Setearle el atributo de buscando partido en TRUE)
			return null;
		}
	}
	private static void completarMayorCategoria(Categoria categ,List<Jugador> jugDisp,List <Jugador> novatos,List <Jugador> masters
			,List <Jugador> expertos, List <Jugador> calificados){
		int x = 0;
		if(categ.equals(Categoria.Novato)){
			if(novatos.size() >= 1){ 
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
			if(masters.size() >= 2){
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(expertos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Master)){
			if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(expertos.size() >= 2){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Experto)){
			if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
		if(categ.equals(Categoria.Calificado)){
			if(calificados.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(calificados.get(0));
					calificados.remove(0);
					x++;
				}
			}
			else if(calificados.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
		}
	}
	
	private static void completarMenorCategoria(Categoria categ,List<Jugador> jugDisp,List <Jugador> novatos,List <Jugador> masters
			,List <Jugador> expertos, List <Jugador> calificados){
		int x = 0;
		if(categ.equals(Categoria.Calificado)){
			if(calificados.size() >= 1){ 
				jugDisp.add(calificados.get(0));
				calificados.remove(0);
			}
			if(expertos.size() >= 2){
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(expertos.get(0));
					expertos.remove(0);
					x++;
				}
			}
			else if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(masters.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Experto)){
			if(expertos.size() >= 1){
				jugDisp.add(expertos.get(0));
				expertos.remove(0);
			}
			if(masters.size() >= 2){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(masters.get(0));
					masters.remove(0);
					x++;
				}
			}
			else if(masters.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Master)){
			if(masters.size() >= 1){
				jugDisp.add(masters.get(0));
				masters.remove(0);
			}
			if(novatos.size() >= 2 && jugDisp.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
		if(categ.equals(Categoria.Novato)){
			if(novatos.size() >= 2 && novatos.size() < 4){
				x = 0;
				while(x < 2 && jugDisp.size() < 4){
					jugDisp.add(novatos.get(0));
					novatos.remove(0);
					x++;
				}
			}
			else if(novatos.size() >= 1 && jugDisp.size() < 4){
				jugDisp.add(novatos.get(0));
				novatos.remove(0);
			}
		}
	}
	
	public static boolean verificarIgualdadParejas(List<Jugador> jugDisp) {
		int novatos = 0,masters = 0,expertos = 0,calificados = 0;
		for(int i = 0;i<jugDisp.size();i++){
			if(jugDisp.get(i).getCategoria().equals(Categoria.Novato)){
				novatos++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Master)){
				masters++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Experto)){
				expertos++;
			}
			if(jugDisp.get(i).getCategoria().equals(Categoria.Calificado)){
				calificados++;
			}
		}
		
		if(novatos != 4 && masters != 4 && expertos != 4 && calificados != 4){
			if(novatos == 1 || masters == 1 || expertos == 1 || calificados == 1){
				return false;
			}
		}
		
		return true;
	}


	public void actualizarEstadoJugador(boolean jugando, boolean buscandoLibreIndividual ) {
		this.jugando = jugando;
		this.buscandoLibreIndividual = buscandoLibreIndividual;
		this.guardar();
	}
	
	public static int calcularTantoJugadores(List<Carta> cartas){
		Carta carta1 = cartas.get(0);
		Carta carta2 = cartas.get(1);
		Carta carta3 = cartas.get(2);
		int envido = 0;
		int sumaCarta1Carta2 = 0;
		int sumaCarta1Carta3 = 0;
		int sumaCarta2Carta3 = 0;
		
		//3 CARTAS DE IGUAL PALO
		if(carta1.getPalo().equals(carta2.getPalo()) && carta1.getPalo().equals(carta3.getPalo())){
			if (carta1.getValorEnvido() == 0 && carta2.getValorEnvido() == 0 && carta3.getValorEnvido() == 0 ) {
				envido = 20;
			}
			else {
				sumaCarta1Carta2 = cargarValorEnvido (carta1, carta2);				
				sumaCarta1Carta3 = cargarValorEnvido (carta1, carta3);
				sumaCarta2Carta3 = cargarValorEnvido (carta2, carta3);
				if((sumaCarta1Carta2 > sumaCarta1Carta3) && (sumaCarta1Carta2 > sumaCarta2Carta3)){
					envido = sumaCarta1Carta2;
				}
				else if((sumaCarta1Carta3 > sumaCarta1Carta2) && (sumaCarta1Carta3 > sumaCarta2Carta3)){
					envido = sumaCarta1Carta3;
				}
				else{
					envido = sumaCarta2Carta3;
				}
			}
		}
		//NINGUNA CARTA TIENE IGUAL PALO
		else if(!carta1.getPalo().equals(carta2.getPalo()) && !carta1.getPalo().equals(carta3.getPalo()) && !carta2.getPalo().equals(carta3.getPalo()) ){
			if((carta1.getValorEnvido() >= carta2.getValorEnvido()) && (carta1.getValorEnvido() >= carta3.getValorEnvido())){
				envido = carta1.getValorEnvido();
			}
			else if((carta2.getValorEnvido() >= carta1.getValorEnvido()) && (carta2.getValorEnvido() >= carta3.getValorEnvido()) ){
				envido = carta2.getValorEnvido();
			}
			else if((carta3.getValorEnvido() >= carta2.getValorEnvido()) && (carta3.getValorEnvido() >= carta1.getValorEnvido())){
				envido = carta3.getValorEnvido();
			}
		}
		//COMENZAMOS A VALIDAR LOS PARES DE IGUAL PALO
		else if(carta1.getPalo().equals(carta2.getPalo())){
			sumaCarta1Carta2 = cargarValorEnvido (carta1, carta2);
			envido = sumaCarta1Carta2;
		}
		else if(carta1.getPalo().equals(carta3.getPalo())){
			sumaCarta1Carta3 = cargarValorEnvido (carta1, carta3);
			envido = sumaCarta1Carta3;
		}
		else if(carta2.getPalo().equals(carta3.getPalo())){
			sumaCarta2Carta3 = cargarValorEnvido (carta2, carta3);
			envido = sumaCarta2Carta3;
		}
		return envido;
	}

	private static int cargarValorEnvido(Carta carta1, Carta carta2) {
		int tantos = 0;
		tantos = carta1.getValorEnvido() + carta2.getValorEnvido();
		
		if(carta1.getValorEnvido() == 0 && carta2.getValorEnvido() == 0){
			tantos = 20;
		}
		else {
			tantos = tantos + 20;
		}
		return tantos;
	}

	public void sumarPartidoGanado(int i) {
		partidosGanados = partidosGanados + i;
		
	}

	public void sumarPartidoJugado(int i) {
		partidosJugados = partidosJugados + i;
	}
}
