package proyectos.poo2022.chinchon.principal;

import java.util.ArrayList;

import proyectos.poo2022.chinchon.enumerados.Eventos;
import proyectos.poo2022.chinchon.interactuar.*;

public class Juego implements Observable{
	private ArrayList<Observador> 		observadores 	= new ArrayList<Observador>();
	private ArrayList<Jugador> 		jugadores 	= new ArrayList<Jugador>();
	private int				jugadorActual	= 0;
	private Mazo 				mazo 		= new Mazo();
	private PilaDescarte 			pilaDescarte	= new PilaDescarte();
	private int				turno		= 1;

	public void iniciarJuego(int cantidadJugadoresIn) {
	    this.mazo.barajar();
	    this.mazo.repartir(this.jugadores, 7, false);
	    this.pilaDescarte.añadirCarta(this.mazo.tomarCartaTope());
	    this.notificar(Eventos.NUEVO_TURNO);
	}

	private Jugador getJugador(int i) {
	    return this.jugadores.get(i-1);
	}

	public void notificar(Object evento) {
	    for (int i=0; i<this.observadores.size();i++) {
		this.observadores.get(i).actualizar(evento,this);
	    }
	}
	

	public void agregarObservador(Observador observador) {
	    this.observadores.add(observador);
	}

	public void agregadorObservador(Observador observador) {
	    this.observadores.add(observador);		
	}

	public void siguienteTurno() {
	    if (this.jugadorActual<this.jugadores.size()-1) {
		this.jugadorActual = this.jugadorActual +1;
	    }
	    else {
		this.jugadorActual = 0;
	    }
	    this.turno++;
	    this.notificar(Eventos.NUEVO_TURNO);
	}

	public Jugador getJugadorActual() {
	    return this.jugadores.get(jugadorActual);
	}

	public Carta getTopePila() {
	    return this.pilaDescarte.getTope();
	}

	public void tomarTopePilaDescarte() {
	    this.jugadores.get(this.jugadorActual).tomarCartaPilaDescarte(this.pilaDescarte);
	}

	public void tomarTopeMazo() {
	    this.jugadores.get(this.jugadorActual).tomarCartaMazo(this.mazo);
	}

	public void descartar(int cartaElegida) {
	    this.getJugadorActual().descartarCarta(cartaElegida,this.pilaDescarte);
	    this.notificar(Eventos.ACTUALIZAR_CARTAS);
	}

	public void agregarJugador(Jugador jugador) {
	    this.jugadores.add(jugador);
	}

	public boolean validarNombre(String nombre) {
	    boolean resultado = true;
	    for (Jugador jugador : jugadores) {
		if (jugador.getNombre().equals(nombre)) {
		    resultado = false;
		    break;
		}
	    }
	    return resultado;
	}

	public void empezarAJugar() {
	    if (this.jugadores.size()<2) {
		return;
	    }
	    for (int i=1; i<=jugadores.size();i++) {
		if ( !(this.getJugador(i).getListoParaJugar()) ) {
		    return;
    		}
	    }
	    this.iniciarJuego(jugadorActual);	
	}

	public Jugador getJugadorEnTurno() {
	    return this.jugadores.get(jugadorActual);
	}

}

	


	
