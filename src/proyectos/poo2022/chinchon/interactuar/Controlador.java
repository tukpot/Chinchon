package proyectos.poo2022.chinchon.interactuar;

import java.util.List;

import proyectos.poo2022.chinchon.enumerados.*;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.Juego;
import proyectos.poo2022.chinchon.principal.Jugador;

public class Controlador implements Observador {

	private IVista 	vista;
	private Juego	modelo;
	private Jugador jugador;
	
	public Controlador(Juego modelo, IVista vista) {
	    this.modelo 				= modelo;
	    this.vista 					= vista;
	    this.vista.setControlador(this);
	    this.modelo.agregarObservador(this);
	}

	public void actualizar(Object evento, Observable observado) {
	    vista.actualizarManoYPila();
	    if(evento instanceof Evento) {
		switch((Evento) evento) {
			default:
				
			    break;
				
				case ACTUALIZAR_CARTAS:
					//vista.actualizarManoYPila();
					break;
			
				/*case DESCARTAR:
				    vista.actualizarManoYPila();
				    if (this.getJugador()==this.getJugadorActual())
					vista.descartar();
				    else {
					vista.bloquear();
				    }
				    break; */
					
				case NUEVO_TURNO:
				    if (this.getJugador()==this.getJugadorActual()) {
					this.nuevoTurno();
					vista.jugarTurno();
				    }
				    else {
					vista.bloquear();
				    }
				    break;
				    
				case MOSTRAR_PUNTOS:
				    this.vista.mostrarPuntos();
				    break;
					
				/*case FIN_TURNO:
					vista.terminarTurno();
					break;*/
					
				//case INICIAR_PARTIDA:
				    
				}
	
		
			}
		}
	
	
	
	
	
		public void juegoNuevo(int cantidadJugadores) {
			this.modelo.nuevaRonda(cantidadJugadores);
		}
		
		public Jugador getJugadorActual() {
			return modelo.getJugadorActual();
		}
		

		public Carta getTopePila() {
			return this.modelo.getTopePila();
		}

		public void tomarTopePilaDescarte() { //cambiar
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
			return this.modelo.validarNombre(nombre);
		}

		public void setListoParaJugar(boolean listo) {
			this.jugador.setListoParaJugar(listo);
		}

		public void empezarAJugar() {
			this.modelo.empezarAJugar();
		}
		
		private void nuevoTurno() {
		    if (this.jugador == this.modelo.getJugadorActual()) {
			this.vista.jugarTurno();
		    }
		    else {
			this.vista.bloquear();
		    }
		}

		public void terminarTurno() {
		    this.modelo.siguienteTurno();
		}

		public void terminarRonda() {
		    this.modelo.terminarRonda();
		    
		}

		public int getCantidadJugadores() {
		    return this.modelo.getCantidadJugadores();
		}

		public Jugador getJugador(int i) {
		    return this.modelo.getJugador(i);
		}
}
