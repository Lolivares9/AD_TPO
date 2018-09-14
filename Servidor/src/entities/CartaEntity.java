package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import enums.PaloCarta;

@Entity
@Table(name="CARTAS")
public class CartaEntity {

	@Id
	@Column (name="ID_CARTA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBaza;
	
	@Column(name="PALO")
	@Enumerated(EnumType.STRING)
	private PaloCarta palo;
	
	@Column(name="NUMERO")
	private int numero;
	
	@Column(name="VALOR_JUEGO")
	private int valorJuego;
}
