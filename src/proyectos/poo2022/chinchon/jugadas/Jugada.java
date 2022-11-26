package proyectos.poo2022.chinchon.jugadas;

import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.ConjuntoCartas;

public class Jugada extends ConjuntoCartas {
	
	
	public Jugada(ConjuntoCartas combinacionCartas) throws Exception {
		super();
		if (!!(esJugadaValida(combinacionCartas))){
			throw new Exception("Jugada invalida.");
		}
	}
	
	
	private boolean esJugadaValida(ConjuntoCartas combinacionCartas) {
		if (cantidadComodines(combinacionCartas)>1) {
			return false;
		}
		
		if (esRepeticion(combinacionCartas) || esEscalera(combinacionCartas)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	private boolean esEscalera(ConjuntoCartas combinacionCartas) {
		boolean respuesta = true;
		Carta cartaAnterior = combinacionCartas.getCarta(1);
		int inicio = 2;
		if (cartaAnterior.getPalo()==Palo.COMODIN) {
			cartaAnterior= combinacionCartas.getCarta(2);
			inicio = 3;
		}
		Palo paloDelJuego = cartaAnterior.getPalo();

			for (int i=inicio; i<=combinacionCartas.getCantidadCartas();i++) {
				if ((combinacionCartas.getCarta(i).getNumero() == cartaAnterior.getNumero()+1) 
						&& (combinacionCartas.getCarta(i).getPalo()==paloDelJuego)) {
					cartaAnterior = combinacionCartas.getCarta(i);
				}
				else if(combinacionCartas.getCarta(i).getPalo()==Palo.COMODIN) {
					cartaAnterior = new Carta(paloDelJuego, cartaAnterior.getNumero()+1);
				}
				else {
					respuesta = false;
					break;
				}
			}
		
		return respuesta;
	}
	
	private boolean esRepeticion(ConjuntoCartas combinacionCartas) {
		boolean resultado = true;
		Carta primeraCarta = combinacionCartas.getCarta(1);
		int inicio = 1;
		if (primeraCarta.getPalo()==Palo.COMODIN) {
			inicio = 2;
			primeraCarta = combinacionCartas.getCarta(2);
		}
		for (int i=inicio; i<=combinacionCartas.getCantidadCartas();i++) {
			if (combinacionCartas.getCarta(i).getNumero()!=primeraCarta.getNumero()) {
				resultado = false;
				break;
			}
		}
		return resultado;
	}
	
	private int cantidadComodines(ConjuntoCartas combinacionCartas) {
		int resultado = 0;
		for (int i=1; i<=combinacionCartas.getCantidadCartas();i++) {
			if (combinacionCartas.getCarta(i).getPalo()==Palo.COMODIN) {
				resultado++;
			}
		}
		return resultado;
	}
	
	
}
