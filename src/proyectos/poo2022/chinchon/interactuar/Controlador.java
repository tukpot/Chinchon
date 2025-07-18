package proyectos.poo2022.chinchon.interactuar;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import proyectos.poo2022.chinchon.enumerados.*;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.EventoConPayload;
import proyectos.poo2022.chinchon.modelo.Jugador;
import proyectos.poo2022.chinchon.vista.IVista;

public class Controlador implements IControladorRemoto {

	private IVista vista;
	private IJuego modelo;
	private int idJugador;

	public <T extends IObservableRemoto> Controlador(T modelo) {
		try {
			this.setModeloRemoto(modelo);
			System.out.println(modelo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Controlador() {
	}

	@Override
	public void actualizar(IObservableRemoto arg0, Object arg1) throws RemoteException {

		if (arg1 instanceof EventoConPayload) {
			EventoConPayload eventoConPayload = (EventoConPayload) arg1;
			this.manejarEventoConPayload(eventoConPayload);
			return;
		}

		if (arg1 instanceof Evento) {
			Evento evento = (Evento) arg1;
			this.manejarEventoSinPayload(evento);

		}
		vista.actualizarManoYPila();
	}

	private void manejarEventoSinPayload(Evento evento) throws RemoteException {
		System.out.println("evento: " + evento + ", controlador de: " + this.getJugador().getNombre());
		switch ((Evento) evento) {
			default:

				break;

			case ACTUALIZAR_CARTAS:
				// Se actualiza siempre. Esto es solamente para que al leer el código no
				// quede como que quedó un evento sin handler
				break;

			case DESCARTAR_O_CERRAR:
				if (this.idJugador == this.getJugadorActual().getId())
					vista.descartarOCerrar();
				else {
					vista.bloquear();
				}
				break;

			case NUEVO_TURNO:
				if (this.idJugador == this.getJugadorActual().getId()) {
					this.nuevoTurno();
				} else {
					vista.bloquear();
				}
				break;

			case RONDA_TERMINADA:
				// this.vista.actualizarPuntaje();
				this.vista.mostrarPuntos();
				this.vista.esperarNuevaRonda();
				// retorna para evitar actualización de mano y pila que actualiza la consola
				return;
		}
	}

	private void manejarEventoConPayload(EventoConPayload eventoConPayload) throws RemoteException {
		Evento evento = eventoConPayload.getEvento();
		System.out.println("evento: " + evento + ", controlador de: " + this.getJugador().getNombre());
		switch ((Evento) evento) {
			default:

				break;

			case GANASTE:
				int idGanador = eventoConPayload.getDatoNumerico();
				if (idGanador == this.idJugador) {
					vista.ganar();
				} else {
					// avisar quién ganó
				}
				return;

			case PERDISTE:
				int idPerdedor = eventoConPayload.getDatoNumerico();
				if (idPerdedor == this.idJugador) {
					vista.perder();
					vista.bloquear();
				} else {
					// avisar quién perdió
				}
				return;
		}

	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T arg0) throws RemoteException {
		this.modelo = (IJuego) arg0;
	}

	public void setVista(IVista vista) {
		this.vista = vista;
	}

	public void conectarJugador(String nombre) {
		try {
			this.idJugador = this.modelo.conectarJugador(nombre); // cambiar por IJugador
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public Jugador getJugadorActual() throws RemoteException {
		return modelo.getJugadorActual();
	}

	public Carta getTopePila() throws RemoteException {
		return this.modelo.getTopePila();
	}

	public void tomarTopePilaDescarte() throws RemoteException {
		this.modelo.tomarTopePilaDescarte(this.getJugador().getId());
	}

	public void tomarTopeMazo() throws RemoteException {
		this.modelo.tomarTopeMazo(this.getJugador().getId());
	}

	public void descartar(int cartaElegida) throws RemoteException {
		this.modelo.descartar(cartaElegida, this.getJugador().getId());
	}

	public Jugador[] getJugadores() throws RemoteException {
		return this.modelo.getJugadores();

	}

	public Jugador getJugador() throws RemoteException {
		return this.modelo.getJugador(idJugador);
	}

	public Jugador getJugador(int id) throws RemoteException {
		return this.modelo.getJugador(id);
	}

	public void setListoParaJugar(boolean listo) throws RemoteException {
		this.modelo.setListoParaJugar(this.getJugador().getId(), listo);
	}

	private void nuevoTurno() throws RemoteException {
		if (this.idJugador == this.modelo.getJugadorActual().getId()) {
			this.vista.tomarDeMazoOPila();
		} else {
			this.vista.bloquear();
		}
	}

	public void terminarRonda() throws RemoteException {
		this.modelo.terminarRonda(this.idJugador);

	}

	public int getCantidadJugadores() throws RemoteException {
		return this.modelo.getCantidadJugadores();
	}

	public void testConectado() throws RemoteException {
		this.modelo.testearConectividad();
	}

}
