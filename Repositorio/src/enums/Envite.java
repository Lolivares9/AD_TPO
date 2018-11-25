package enums;

public enum Envite {
	Envido(1),Envido_Envido(2),Real_Envido(3),Falta_Envido(4),Truco(1),Re_Truco(2),Vale_Cuatro(3),Quiero(2), No_Quiero(1), Mazo(0), Nada(0),Querido(2),NoQuerido(1),EnvidoNada(0),
	//Posibles estados del envido
	Envido_Querido(1),
	Envido_NoQuerido(1),
	Envido_RealEnvido_FaltaEnvido_Querido(1),
	Envido_RealEnvido_FaltaEnvido_NoQuerido(1),
	Envido_RealEnvido_Querido(1),
	Envido_RealEnvido_NoQuerido(1),
	Envido_FaltaEnvido_Querido(1),
	Envido_FaltaEnvido_NoQuerido(1),
	//Posibles estados del envido
	
	
	//Posibles estados del envido envido
	EnvidoEnvido_Querido(1),
	EnvidoEnvido_RealEnvido_Querido(1),
	EnvidoEnvido_RealEnvido_NoQuerido(1),
	EnvidoEnvido_FaltaEnvido_Querido(1),
	EnvidoEnvido_FaltaEnvido_NoQuerido(1),
	EnvidoEnvido_RealEnvido_FaltaEnvido_Querido(1),
	EnvidoEnvido_RealEnvido_FaltaEnvido_NoQuerido(1),
	EnvidoEnvido_NoQuerido(1),
	//Posibles estados del envido envido
	
	
	//Posibles estados del real envido
	RealEnvido_Querido(1),
	RealEnvido_NoQuerido(1),
	RealEnvido_FaltaEnvido_Querido(1),
	RealEnvido_FaltaEnvido_NoQuerido(1),
	//Posibles estados del real envido
	
	//Posibles estados del falta envido
	FaltaEnvido_Querido(1),
	FaltaEnvido_NoQuerido(1),
	//Posibles estados del falta envido
	
	//Posibles estados del truco
	Truco_Querido(1),
	Truco_NoQuerido(1),
	Truco_QuieroRetruco_Querido(1),
	Truco_QuieroRetruco_NoQuerido(1),
	Truco_QuieroRetruco_QuieroValeCuatro_Querido(1),
	Truco_QuieroRetruco_QuieroValeCuatro_NoQuerido(1),
	//Posibles estados del truco
	
	//Agrego este que tambien es posible, cuando te dicen *el envido va primero*
	Truco_Envido(1)
	;
	
	private int numVal;

	Envite(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
