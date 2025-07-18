package proyectos.poo2022.chinchon.modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import proyectos.poo2022.chinchon.enumerados.Evento;
import proyectos.poo2022.chinchon.interactuar.*;

public class Juego extends ObservableRemoto implements IJuego {
	private HashMap<Integer, Jugador> jugadores = new HashMap<>();
	private Ronda ronda;
	private int jugadorMano = -1;

	public int conectarJugador(String nombre) {
		Jugador jugador = new Jugador(nombre);
		agregarJugador(jugador);
		System.out.println("Se conectó el jugador: " + jugador.getNombre() + " id:" + jugador.getId());
		return jugador.getId();
	}

	@Override
	public void desconectarJugador(int usuarioId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'desconectarJugador'");
	}

	public Jugador[] getJugadores() {
		return jugadores.values().toArray(new Jugador[0]);
	}

	public void setListoParaJugar(int jugador, boolean estaListo) throws RemoteException {
		Jugador jugadorListo = this.getJugador(jugador);

		System.out.println(
				"id solicitada: " + jugador + " jugador obtenido:" + jugadorListo.getNombre() + jugadorListo.getId());
		jugadorListo.setListoParaJugar(true);
		this.empezarAJugar();
	}

	public void tomarTopeMazo(int jugadorQueToma) throws RemoteException {
		Jugador jugador = this.getJugador(jugadorQueToma);
		if (jugador != this.getJugadorActual())
			return;

		this.ronda.tomarTopeMazo(jugador);
		this.notificarObservadores(Evento.DESCARTAR_O_CERRAR);
	}

	public void tomarTopePilaDescarte(int jugadorQueToma) throws RemoteException {
		Jugador jugador = this.getJugador(jugadorQueToma);
		if (jugador != this.getJugadorActual())
			return;

		this.ronda.tomarTopePilaDescarte(jugador);
		this.notificarObservadores(Evento.DESCARTAR_O_CERRAR);
	}

	public void descartar(int cartaElegida, int jugadorQueDescarta) throws RemoteException {
		Jugador jugador = this.getJugador(jugadorQueDescarta);
		if (jugador != this.getJugadorActual())
			return;

		this.ronda.descartar(cartaElegida, jugador);
		this.siguienteTurno();
	}

	public Mano getMano(int numJugador) {
		return this.getJugador(numJugador).getMano();
	}

	public void nuevaRonda() throws RemoteException {
		this.cambiarJugadorMano();
		ArrayList<Jugador> listaJugadores = new ArrayList<>(jugadores.values());
		this.ronda = new Ronda(this.jugadorMano, listaJugadores);
		this.notificarObservadores(Evento.NUEVO_TURNO);
	}

	private void cambiarJugadorMano() {
		//mejorar manejo de jugador mano (usar random)
		if (this.jugadorMano == -1) {
			this.jugadorMano = 0;
		} else if (this.jugadorMano < this.jugadores.size() - 1) {
			this.jugadorMano++;
		} else {
			this.jugadorMano = 0;
		}
	}

	public void siguienteTurno() throws RemoteException {
		this.ronda.siguienteTurno();
		this.notificarObservadores(Evento.NUEVO_TURNO);
	}

	public Jugador getJugadorActual() {
		return this.ronda.getJugadorActual();
	}

	public Carta getTopePila() {
		return this.ronda.getTopePila();
	}

	private void agregarJugador(Jugador jugador) {
		this.jugadores.put(jugador.getId(), jugador);
	}

	public void empezarAJugar() throws RemoteException {
		if (this.jugadores.size() < 2) {
			System.out.println("no hay suficientes jugadores para empezar");
			return;
		}

		for (Jugador jugador : this.jugadores.values()) {
			if (!jugador.getListoParaJugar()) {
				System.out.println("el jugador [" + jugador.getNombre() + "] aún no está listo");
				return;
			}
		}

		this.nuevaRonda();
	}

	private void eliminarPerdedor(Jugador perdedor) throws RemoteException {
		EventoConPayload eventoPerder = new EventoConPayload(Evento.PERDISTE, perdedor.getId());
		this.notificarObservadores(eventoPerder);
		this.jugadores.remove(perdedor.getId());
	}

	private void declararGanador(Jugador ganador) throws RemoteException {
		EventoConPayload eventoGanar = new EventoConPayload(Evento.GANASTE, ganador.getId());
		this.notificarObservadores(eventoGanar);
		// guardar jugador en el top de ganadores
	}

	public void terminarRonda(int idJugadorQueCierra) throws RemoteException {
		Jugador jugadorQueCierra = this.getJugador(idJugadorQueCierra);
		if (!jugadorQueCierra.getMano().esCerrable())
			return;

		if (jugadorQueCierra != this.getJugadorActual())
			return;

		int puntajeDeCierre = jugadorQueCierra.getMano().cerrarMano();
		if (puntajeDeCierre <= -100) {
			// victoria total y absoluta por chinchon
			declararGanador(jugadorQueCierra);
			return;
		}

		this.ronda.sumarPuntos();
		this.notificarObservadores(Evento.RONDA_TERMINADA);
		for (Jugador jugador : this.jugadores.values()) {
			if (jugador.getPuntos() >= 100) {
				eliminarPerdedor(jugador);
			}
			jugador.setListoParaJugar(false);
		}
		if (this.getCantidadJugadores() < 2) {
			declararGanador(this.getJugadores()[0]);
		}
	}

	public int getCantidadJugadores() {
		return this.jugadores.size();
	}

	public Jugador getJugador(int idJugador) {
		return this.jugadores.get(idJugador);
	}

	public void testearConectividad() throws RemoteException {
		System.out.println("mostrando mensaje en el servidor");
	}
}
