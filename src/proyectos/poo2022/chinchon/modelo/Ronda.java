package proyectos.poo2022.chinchon.modelo;

import java.util.ArrayList;

public class Ronda {
	private Mazo mazo = new Mazo();
	private PilaCartas pilaDescarte = new PilaCartas();
	private int jugadorActual;
	private ArrayList<Jugador> jugadores;

	public Ronda(int jugadorMano, ArrayList<Jugador> jugadores) {
		this.jugadorActual = jugadorMano;
		this.jugadores = jugadores;
		this.resetManos();
		this.mazo.barajar(); // Comentar para pruebas
		this.mazo.repartir(this.jugadores, 7, false);
		this.pilaDescarte.añadirCarta(this.mazo.tomarCartaTope());

	}

	private void resetManos() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			this.jugadores.get(i).resetMano();
		}
	}

	public Jugador getJugadorActual() {
		return this.jugadores.get(jugadorActual);
	}

	public void siguienteTurno() {
		//mejorar manejo de turnos
		if (this.jugadorActual < this.jugadores.size() - 1) {
			this.jugadorActual = this.jugadorActual + 1;
		} else {
			this.jugadorActual = 0;
		}
	}

	public Carta getTopePila() {
		return this.pilaDescarte.getTope();
	}

	public void tomarTopePilaDescarte(Jugador jugadorQueToma) {
		jugadorQueToma.tomarCartaPilaDescarte(this.pilaDescarte); // Añadir que no pueda tomar la carta que recién
																	// descartó
	}

	public void tomarTopeMazo(Jugador jugadorQueToma) {
		jugadorQueToma.tomarCartaMazo(this.mazo);
	}

	public void descartar(int cartaElegida, Jugador jugadorQueDescarta) {
		jugadorQueDescarta.descartarCarta(cartaElegida, this.pilaDescarte);
	}

	public void sumarPuntos() {
		for (int i = 0; i < this.jugadores.size(); i++) {
			jugadores.get(i).añadirPuntosMano();
		}

	}

}
