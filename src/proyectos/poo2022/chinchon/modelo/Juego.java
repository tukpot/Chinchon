package proyectos.poo2022.chinchon.modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import proyectos.poo2022.chinchon.enumerados.Evento;
import proyectos.poo2022.chinchon.interactuar.*;

//
public class Juego extends ObservableRemoto implements IJuego {
	// private ArrayList<IObservadorRemoto> observadores = new
	// ArrayList<IObservadorRemoto>();
	// private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	private HashMap<Integer, Jugador> jugadores = new HashMap<>();
	private Ronda ronda;
	private int jugadorMano = -1;

	public Jugador conectarJugador(String nombre) {
		Jugador jugador = new Jugador(nombre);
		agregarJugador(jugador);
		System.out.println("Se conectó el jugador: " + jugador.getNombre());
		return jugador;
	}

	@Override
	public void desconectarJugador(int usuarioId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'desconectarJugador'");
	}

	@Override
	public Jugador[] getJugadores() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getJugadores'");
	}

	@Override
	public void setListoParaJugar(int jugador, boolean estaListo) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setListoParaJugar'");
	}

	@Override
	public void tomarTopeMazo(int jugadorQueToma) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'tomarTopeMazo'");
	}

	@Override
	public void tomarTopePilaDescarte(int jugadorQueToma) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'tomarTopePilaDescarte'");
	}

	@Override
	public void descartar(int cartaElegida, int jugadorQueDescarta) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'descartar'");
	}

	@Override
	public Mano getMano(int numJugador) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMano'");
	}

	public void nuevaRonda() throws RemoteException {
		this.cambiarJugadorMano();
		ArrayList<Jugador> listaJugadores = new ArrayList<>(jugadores.values());
		this.ronda = new Ronda(this.jugadorMano, listaJugadores);
		this.notificarObservadores(Evento.NUEVO_TURNO);
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

	public void tomarTopePilaDescarte(Jugador jugadorQueToma) throws RemoteException {
		if (jugadorQueToma != this.getJugadorActual())
			return;

		this.ronda.tomarTopePilaDescarte(jugadorQueToma);
		this.notificarObservadores(Evento.DESCARTAR_O_CERRAR);
	}

	public void tomarTopeMazo(Jugador jugadorQueToma) throws RemoteException {
		if (jugadorQueToma != this.getJugadorActual())
			return;

		this.ronda.tomarTopeMazo(jugadorQueToma);
		this.notificarObservadores(Evento.DESCARTAR_O_CERRAR);
	}

	public void descartar(int cartaElegida, Jugador jugadorQueDescarta) throws RemoteException {
		if (jugadorQueDescarta != this.getJugadorActual())
			return;

		this.ronda.descartar(cartaElegida, jugadorQueDescarta);
		this.siguienteTurno();
	}

	public void agregarJugador(Jugador jugador) {
		this.jugadores.put(jugador.getId(), jugador);
	}

	public void empezarAJugar() throws RemoteException {
		if (this.jugadores.size() < 2) {
			return;
		}
		for (int i = 1; i <= jugadores.size(); i++) {
			if (!(this.getJugador(i).getListoParaJugar())) {
				return;
			}
		}
		this.nuevaRonda();
	}

	public void eliminarPerdedor(Jugador perdedor) {
		perdedor.notificar(Evento.PERDISTE);
		this.jugadores.remove(perdedor);
	}

	public void declararGanador(Jugador ganador) {
		ganador.notificar(Evento.GANASTE);
	}

	public void terminarRonda(Jugador jugadorQueCierra) throws RemoteException {
		// if not jugador.getMano().esCerrable() notificar error
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
		}
		if (this.getCantidadJugadores() < 2) {
			declararGanador(this.getJugador(1));
		}

		this.nuevaRonda();
		// echar a jugadores con más de 100 puntos
		// si queda un único jugador, declararlo ganador absoluto god dios el mejor de
		// todos.

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
