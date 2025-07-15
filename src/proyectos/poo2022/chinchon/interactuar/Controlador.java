package proyectos.poo2022.chinchon.interactuar;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import proyectos.poo2022.chinchon.enumerados.*;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.Jugador;
import proyectos.poo2022.chinchon.vista.IVista;

public class Controlador implements IControladorRemoto {

	private IVista vista;
	private IJuego modelo;
	private Jugador jugador;
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
		Evento evento = (Evento) arg1;
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
					if (this.getJugador().getId() == this.getJugadorActual().getId()) {
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
					// retorna para evitar actualización de mano y pila que actualiza la consola

					// debug y otras
					return;
			}
		}
		vista.actualizarManoYPila();
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
			this.jugador = (Jugador) this.modelo.conectarJugador(nombre); // cambiar por IJugador
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
		this.modelo.tomarTopePilaDescarte(this.idJugador);
	}

	public void tomarTopeMazo() throws RemoteException {
		this.modelo.tomarTopeMazo(this.idJugador);
	}

	public void descartar(int cartaElegida) throws RemoteException {
		this.modelo.descartar(cartaElegida, this.getJugador().getId());
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public Jugador getJugador(int id) throws RemoteException {
		return this.modelo.getJugador(id);
	}

	// public boolean getListoParaJugar() {
	// return this.jugador.getListoParaJugar();
	// }

	public void setListoParaJugar(boolean listo) throws RemoteException {
		this.modelo.setListoParaJugar(idJugador, listo);
		this.modelo.empezarAJugar();
	}

	private void nuevoTurno() throws RemoteException {
		if (this.jugador.getId() == this.modelo.getJugadorActual().getId()) {
			this.vista.tomarDeMazoOPila();
		} else {
			this.vista.bloquear();
		}
	}

	public void terminarRonda() throws RemoteException {
		this.modelo.terminarRonda(this.getJugador());

	}

	public int getCantidadJugadores() throws RemoteException {
		return this.modelo.getCantidadJugadores();
	}

	public void testConectado() throws RemoteException {
		this.modelo.testearConectividad();
	}

}
