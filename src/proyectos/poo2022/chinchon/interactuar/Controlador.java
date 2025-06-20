package proyectos.poo2022.chinchon.interactuar;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import proyectos.poo2022.chinchon.enumerados.*;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.Juego;
import proyectos.poo2022.chinchon.modelo.Jugador;
import proyectos.poo2022.chinchon.vista.IVista;

public class Controlador implements Observador, IControladorRemoto {

	private IVista vista;
	private Juego modelo;
	private Jugador jugador;
	private int idJugador;

	public Controlador(){}

	public Controlador(Juego modelo, IVista vista) throws RemoteException {
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
		this.modelo.agregarObservador(this);
	}

	public <T extends IObservableRemoto> Controlador(T modelo) {
		try {
			this.setModeloRemoto(modelo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actualizar(IObservableRemoto arg0, Object arg1) throws RemoteException {
		this.actualizar(arg1, null);
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T arg0) throws RemoteException {
		// this.modelo = (IJuego) arg0;
	}

	public void setVista(IVista vista){
		this.vista = vista;
	}

	public void actualizar(Object evento, Observable observado) {
		System.out.println("evento: " + evento);
		if (evento instanceof Evento) {
			switch ((Evento) evento) {
				default:

					break;

				case ACTUALIZAR_CARTAS:
					// Se actualiza siempre. Esto es solamente para que al leer el código no
					// quede como que quedó un evento sin handler
					break;

				case DESCARTAR_O_CERRAR:
					if (this.getJugador() == this.getJugadorActual())
						vista.descartarOCerrar();
					else {
						vista.bloquear();
					}
					break;

				case NUEVO_TURNO:
					if (this.getJugador() == this.getJugadorActual()) {
						this.nuevoTurno();
					} else {
						vista.bloquear();
					}
					break;

				case GANASTE:
					vista.ganar();
					return;

				case PERDISTE:
					vista.perder();
					vista.bloquear();
					return;

				case RONDA_TERMINADA:
					this.vista.mostrarPuntos();
					// retorna para evitar actualización de mano y pila que actualiza la consola de
					// debug y otras
					return;
			}
		}
		vista.actualizarManoYPila();
	}

	public Jugador getJugadorActual() {
		return modelo.getJugadorActual();
	}

	public Carta getTopePila() {
		return this.modelo.getTopePila();
	}

	public void tomarTopePilaDescarte() {
		this.modelo.tomarTopePilaDescarte(this.getJugador());
	}

	public void tomarTopeMazo() {
		this.modelo.tomarTopeMazo(this.getJugador());
	}

	public void descartar(int cartaElegida) {
		this.modelo.descartar(cartaElegida, this.getJugador());
	}

	public void setJugador(String nombre) {
		this.jugador = new Jugador(nombre);
		this.jugador.setControlador(this);
		this.modelo.agregarJugador(this.jugador);
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public boolean validarNombre(String nombre) {
		return Jugador.validarNombre(nombre);
	}

	public boolean getListoParaJugar() {
		return this.jugador.getListoParaJugar();
	}

	public void setListoParaJugar(boolean listo) {
		this.jugador.setListoParaJugar(listo);
		this.modelo.empezarAJugar();
	}

	private void nuevoTurno() {
		if (this.jugador == this.modelo.getJugadorActual()) {
			this.vista.tomarDeMazoOPila();
		} else {
			this.vista.bloquear();
		}
	}

	public void terminarRonda() {
		this.modelo.terminarRonda(this.getJugador());

	}

	public int getCantidadJugadores() {
		return this.modelo.getCantidadJugadores();
	}

	public Jugador getJugador(int i) {
		return this.modelo.getJugador(i);
	}
}
