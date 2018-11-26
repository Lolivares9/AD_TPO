package enums;

public enum Envite {
	Envido(1),Envido_Envido(2),Real_Envido(3),Falta_Envido(4),Truco(1),Re_Truco(2),Vale_Cuatro(3),Quiero(2), No_Quiero(1), Mazo(0), Nada(0),Querido(2),NoQuerido(1),EnvidoNada(0),
	//Posibles estados del envido
	Envido_Querido(11),
	Envido_NoQuerido(10),
	Envido_RealEnvido_FaltaEnvido_Querido(13),
	Envido_RealEnvido_FaltaEnvido_NoQuerido(12),
	Envido_RealEnvido_Querido(15),
	Envido_RealEnvido_NoQuerido(14),
	Envido_FaltaEnvido_Querido(17),
	Envido_FaltaEnvido_NoQuerido(16),
	//Posibles estados del envido
	
	
	//Posibles estados del envido envido
	EnvidoEnvido_Querido(21),
	EnvidoEnvido_NoQuerido(20),
	EnvidoEnvido_RealEnvido_Querido(23),
	EnvidoEnvido_RealEnvido_NoQuerido(22),
	EnvidoEnvido_FaltaEnvido_Querido(25),
	EnvidoEnvido_FaltaEnvido_NoQuerido(24),
	EnvidoEnvido_RealEnvido_FaltaEnvido_Querido(27),
	EnvidoEnvido_RealEnvido_FaltaEnvido_NoQuerido(26),
	
	//Posibles estados del envido envido
	
	
	//Posibles estados del real envido
	RealEnvido_Querido(31),
	RealEnvido_NoQuerido(30),
	RealEnvido_FaltaEnvido_Querido(33),
	RealEnvido_FaltaEnvido_NoQuerido(32),
	//Posibles estados del real envido
	
	//Posibles estados del falta envido
	FaltaEnvido_Querido(41),
	FaltaEnvido_NoQuerido(40),
	//Posibles estados del falta envido
	
	//Posibles estados del truco
	Truco_Querido(51),
	Truco_NoQuerido(50),
	Truco_QuieroRetruco_Querido(53),
	Truco_QuieroRetruco_NoQuerido(52),
	Truco_QuieroRetruco_QuieroValeCuatro_Querido(55),
	Truco_QuieroRetruco_QuieroValeCuatro_NoQuerido(54),
	//Posibles estados del truco
	
	//Agrego este que tambien es posible, cuando te dicen *el envido va primero*
	Truco_Envido(0)
	;
	
	private int numVal;

	Envite(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
