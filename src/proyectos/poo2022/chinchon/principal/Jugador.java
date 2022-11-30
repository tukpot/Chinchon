package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.interactuar.Controlador;

public class Jugador  {
	private			Mano 				mano 			= new Mano();
	private 		int 				puntos 			= 0;
	private 		String				nombre;	
	private 		Controlador 			controlador;
	private			boolean				listoParaJugar 		= false;
	private			boolean				esElJugadorQueCerro	= false;
	private 		Jugada 				jugada1;
	private 		Jugada 				jugada2;
	

	public Jugador(String nombre) {
		this.nombre = nombre;
	}
	
	
	public void tomarCartaMazo(Mazo mazoIn) {
		this.mano.añadirCarta(mazoIn.tomarCartaTope());
		}
		
	public void descartarCarta(int cartaATirar, PilaDescarte pilaDescarte) { //añadir forma de recibir carta
		this.mano.transferirCarta(cartaATirar, pilaDescarte);
		}
	
	public void tomarCartaPilaDescarte(PilaDescarte pilaDescarteIn) {
		Carta cartaRecienTomada = pilaDescarteIn.tomarCartaTope();
		this.mano.añadirCarta(cartaRecienTomada);
		}
	
	

	public Mano getMano() {
		return this.mano;
	}


	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}


	public String getNombre() {
		return this.nombre;
	}


	public void setListoParaJugar(boolean listo) {
	    this.listoParaJugar = listo;
	}


	public boolean getListoParaJugar() {
	    return this.listoParaJugar;
	}
	
	protected void resetMano() {
	   this.mano = new Mano();
	}

	public int getPuntos() {
	    return puntos;
	}


	public void añadirPuntosMano() {
	    if ((this.esElJugadorQueCerro) && (this.getMano().calcularPuntajeRestante(this.jugada1,this.jugada2)==0)) {
		this.puntos = this.puntos -10;
	    }
	    else {
		this.puntos = this.puntos + this.getMano().calcularPuntajeRestante(this.jugada1,this.jugada2);
	    }
	    
	}


	public void setJugadas(Jugada jugada1, Jugada jugada2) {
	    this.jugada1 =	jugada1;
	    this.jugada2 =	jugada2;
	}

	public void setEsElJugadorQueCerro(boolean siONo) {
	    this.esElJugadorQueCerro = siONo;
	}
}
