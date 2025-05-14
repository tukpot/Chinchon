package proyectos.poo2022.chinchon.principal;

import java.util.ArrayList;

import proyectos.poo2022.chinchon.enumerados.Evento;
import proyectos.poo2022.chinchon.interactuar.*;

public class Juego implements Observable {
	private ArrayList<Observador> observadores = new ArrayList<Observador>();
	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	private Ronda ronda;
	private int jugadorMano = -1;

	public void nuevaRonda(int jugadorMano) {
		this.cambiarJugadorMano();
		this.ronda = new Ronda(this.jugadorMano, this.jugadores);
		this.notificar(Evento.NUEVO_TURNO);
	}

	private void cambiarJugadorMano() {
		if (this.jugadorMano == -1) {
			this.jugadorMano = 0;
		} else if (this.jugadorMano < this.jugadores.size() - 1) {
			this.jugadorMano++;
		} else {
			this.jugadorMano = 0;
		}
	}

	public void notificar(Object evento) {
		for (int i = 0; i < this.observadores.size(); i++) {
			this.observadores.get(i).actualizar(evento, this);
		}
	}

	public void agregarObservador(Observador observador) {
		this.observadores.add(observador);
	}

	public void siguienteTurno() {
		this.ronda.siguienteTurno();
		this.notificar(Evento.NUEVO_TURNO);
	}

	public Jugador getJugadorActual() {
		return this.ronda.getJugadorActual();
	}

	public Carta getTopePila() {
		return this.ronda.getTopePila();
	}

	public void tomarTopePilaDescarte(Jugador jugadorQueToma) {
		if (jugadorQueToma != this.getJugadorActual())
			return;

		this.ronda.tomarTopePilaDescarte(jugadorQueToma);
		this.notificar(Evento.DESCARTAR_O_CERRAR);
	}

	public void tomarTopeMazo(Jugador jugadorQueToma) {
		if (jugadorQueToma != this.getJugadorActual())
			return;

		this.ronda.tomarTopeMazo(jugadorQueToma);
		this.notificar(Evento.DESCARTAR_O_CERRAR);
	}

	public void descartar(int cartaElegida, Jugador jugadorQueDescarta) {
		if (jugadorQueDescarta != this.getJugadorActual())
			return;

		this.ronda.descartar(cartaElegida, jugadorQueDescarta);
		this.siguienteTurno();
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
		if (this.jugadores.size() < 2) {
			return;
		}
		for (int i = 1; i <= jugadores.size(); i++) {
			if (!(this.getJugador(i).getListoParaJugar())) {
				return;
			}
		}
		this.nuevaRonda(this.jugadorMano);
	}

	public void eliminarPerdedor(Jugador perdedor) {
		perdedor.notificar(Evento.PERDISTE);
		this.jugadores.remove(perdedor);
		// this.observadores.remove(perdedor);
	}

	public void terminarRonda(Jugador jugadorQueCierra) {
		// if not jugador.getMano().esCerrable() notificar error
		if (jugadorQueCierra != this.getJugadorActual())
			return;

		int puntajeDeCierre = jugadorQueCierra.getMano().cerrarMano();
		// if puntajeDeCierre menor a -100, declarar como ganador al jugador que cierra
		// por chinchon
		this.ronda.sumarPuntos();
		this.notificar(Evento.RONDA_TERMINADA);
		for (Jugador jugador : this.jugadores) {
			if (jugador.getPuntos() >= 100) {
				eliminarPerdedor(jugador);
			}
		}
		// echar a jugadores con más de 100 puntos
		// si queda un único jugador, declararlo ganador absoluto god dios el mejor de
		// todos.

	}

	public int getCantidadJugadores() {
		return this.jugadores.size();
	}

	public Jugador getJugador(int nJugador) {
		return this.jugadores.get(nJugador - 1);
	}
}
