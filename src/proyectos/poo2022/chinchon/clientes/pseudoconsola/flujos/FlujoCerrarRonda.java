package proyectos.poo2022.chinchon.clientes.pseudoconsola.flujos;

import java.util.HashSet;
import java.util.Set;

import proyectos.poo2022.chinchon.clientes.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.jugadas.Jugada;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.ConjuntoCartas;
import proyectos.poo2022.chinchon.principal.Mano;
import proyectos.poo2022.chinchon.utilidades.MetodosUtiles;

public class FlujoCerrarRonda extends Flujo{
    private ConjuntoCartas 	jugadaEnProceso1;
    private ConjuntoCartas	jugadaEnProceso2;
    private Jugada 		jugada1;
    private Jugada 		jugada2;
    private Mano		cartasDisponibles; //Esto es para no modificar la mano real;
    private Carta		cartaQueCorta;
    
    public FlujoCerrarRonda(PseudoConsola vista) {
	super(vista);
	this.reiniciarCopiaMano();
	this.clear();
	this.mensajeRecibirCartaQueCorta();
	this.setEstadoActual(EstadoPrograma.RECIBIENDO_CARTA_QUE_CORTA);
    }

    
    private void mensajeRecibirCartaQueCorta() {
	println("Indique la carta con la cual quiere cortar.");
	println(this.cartasDisponibles.toString());
    }


    private void mensajeRecibirCantidadJugadas() {
	this.println("Elija su cantidad de jugadas.");
	this.println("1. Para hacer una única jugada.");
	this.println("2. Para hacer 2 jugadas.");
	this.println("0. Para volver hacia atrás.");
    }
    
    private void volverAtras() {
	//blabla
	
    }


    public void recibirInput(String texto) {
	switch ((EstadoPrograma) this.getEstadoActual()) {
		default:
		    break;
		 
		case RECIBIENDO_CANTIDAD_JUGADAS:
		    this.cantidadJugadas(texto);
		    break;
		    
		case ARMANDO_1JUGADA:
		    this.armar1Jugada(texto);
		    break;
		    
		case ARMANDO_2JUGADAS:
		    this.armar2Jugadas(texto);
		    break;
		
		case RECIBIENDO_CARTA_QUE_CORTA:
		    this.setCartaQueCorta(texto);
		    break;
		
		case ESPERANDO_CONFIRMACION:
		    this.confirmar(texto);
		    break;
		   
		
	}
	
    }

    private void confirmar(String texto) {
	if (texto.equals("0")) {
	    this.volverAtras();
	    return;
	}
	if (texto.equals("1")) {
	    if (this.jugada2==null) {
		if (this.cartasDisponibles.esCerrable(this.jugada1,this.cartaQueCorta)) {
		    this.terminarFlujo();
		}
		
	    }
	    else {
		if (this.cartasDisponibles.esCerrable(jugada1,jugada2, cartaQueCorta)) {
		   this.terminarFlujo(); 
		}
	    }
	    return;
	}
	this.clear();
	this.println("No se puede cerrar con esa mano.");
	this.mensajeConfirmar();
	
    }


    private void mensajeConfirmar() {
	println("1. Para confirmar.");
	println("0. Para volver atrás.");
    }


    private void setCartaQueCorta(String texto) {
	if (!( 	   MetodosUtiles.esInt(texto) //valido estas 3 cosas
		&& ( this.cartasDisponibles.getCantidadCartas()>=Integer.valueOf(texto) )
		&& (Integer.valueOf(texto)>=1) )) {
	    this.clear();
	    this.mensajeCartaInvalida();
	    this.mensajeRecibirCartaQueCorta();
	    return;
	}
	this.cartaQueCorta = this.cartasDisponibles.getCarta(Integer.valueOf(texto));
	this.cartasDisponibles.tomarCarta(Integer.valueOf(texto));
	this.mensajeRecibirCantidadJugadas();
	this.setEstadoActual(EstadoPrograma.RECIBIENDO_CANTIDAD_JUGADAS);
    }


    private void mensajeCartaInvalida() {
	this.println("Carta inválida. Intente nuevamente.");
    }


    private void armar2Jugadas(String texto) {
	
	Jugada jugadaNueva;
	
	try { //valida que la jugada sea válida;
	    int[] posiciones = MetodosUtiles.arrayStringAInt(texto.split(","));
	    jugadaNueva = new Jugada(this.cartasDisponibles.getCartas(posiciones));
	}
	catch (Exception e) {
	    this.clear();
	    this.mensajeJugadaInvalida();
	    this.mensajeArmar2Jugadas();
	    return;
	} //Fin validación
	
	if (this.jugada1==null) { //guard clause
	    this.jugada1= jugadaNueva;
	    this.cartasDisponibles.quitarConjuntoCartas(jugadaNueva);
	    this.mensajeArmar2Jugadas();
	    return;
	}

	
	if (this.jugada2==null) { //guard clause
	    this.jugada2 = jugadaNueva;
	    this.mensajeConfirmar();
	    this.setEstadoActual(EstadoPrograma.ESPERANDO_CONFIRMACION);
	    return;
	}
	
	

    }
    
    private void mensajeArmar2Jugadas() {
	if (this.jugada1==null) {
	    println("Ingrese las posiciones de las cartas de su primer jugada."); 
	    println("Use ',' como separador.");
	    println("0. Para volver hacia atrás.");
	    this.mostrarCartasDisponibles();
	    return;
	}
	if (this.jugada2==null) {
	    println("Ingrese las posiciones de las cartas de su segunda jugada."); 
	    println("Use ',' como separador.");
	    println("0. Para volver hacia atrás.");
	    this.mostrarCartasDisponibles();
	}
    }
    


    private void mensajeJugadaInvalida() {
	this.println("Jugada inválida.");
    }


    private void armar1Jugada(String texto) {
	Jugada jugadaNueva;
	try { //validación
	    int[] posiciones = MetodosUtiles.arrayStringAInt(texto.split(","));
	    jugadaNueva = new Jugada(this.cartasDisponibles.getCartas(posiciones));
	}
	catch (Exception e) {
	    this.clear();
	    this.mensajeJugadaInvalida();
	    this.mensajeArmar1Jugada();
	    return;
	} 	
	
	this.jugada1 = jugadaNueva;
	this.mensajeConfirmar();
	this.setEstadoActual(EstadoPrograma.ESPERANDO_CONFIRMACION);

    }
    


    private void mensajeArmar1Jugada() {
	this.println("Ingrese las posiciones de las cartas de su jugada.");
	this.println("Use ',' como separador.");
	this.println("0. (Cero) para volver hacia atrás.");
	this.mostrarCartasDisponibles();
    }


    private void cantidadJugadas(String texto) {
	if (texto.equals("0")) {
	    this.cancelarFlujo();
	    return;
	}
	if (texto.equals("1")) {
	    this.setEstadoActual(EstadoPrograma.ARMANDO_1JUGADA);
	    this.mensajeArmar1Jugada();
	    return;
	}
	if (texto.equals("2")) {
	    this.setEstadoActual(EstadoPrograma.ARMANDO_2JUGADAS);
	    this.mensajeArmar2Jugadas();
	    return;
	}
	
	this.clear();
	this.println("Cantidad inválida.");
	this.mensajeRecibirCantidadJugadas();
    }

    private Controlador getControlador() {
	return this.getVista().getControlador();
    }
    
    private void reiniciarCopiaMano() {
	this.cartasDisponibles = this.getControlador().getJugador().getMano();
    }

    private void mostrarCartasDisponibles() {
	this.println("Estas son sus cartas disponibles.");
	this.println(this.cartasDisponibles.toString());
    }

    protected void terminarFlujo() {
	this.getControlador().getJugador().getMano().quitarCarta(this.cartaQueCorta); //le quito la carta que usó para cortar de la mano real
	this.getControlador().getJugador().setJugadas(this.jugada1,this.jugada2);
	this.getControlador().getJugador().setEsElJugadorQueCerro(true);
	this.getVista().terminarRonda();
	
    }
}
