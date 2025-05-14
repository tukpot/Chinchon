package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.enumerados.Palo;

public class Jugada extends ConjuntoCartas {

	public Jugada(ConjuntoCartas cartasJugada) throws Exception {
		super();
		if (Jugada.esJugadaValida(cartasJugada)) {
			this.añadirConjunto(cartasJugada);
		} else {
			throw new Exception("Jugada inválida");
		}
	}

	public static boolean esJugadaValida(ConjuntoCartas jugada) {
		if (jugada.getCantidadCartas() < 3) {
			return false;
		}
		if (Jugada.cantidadComodines(jugada) > 1) {
			return false;
		}

		if (Jugada.esRepeticion(jugada) || Jugada.esEscalera(jugada)) {
			return true;
		}

		return false;
	}

	private static boolean esEscalera(ConjuntoCartas jugada) {
		jugada.ordenarCartas(); // Por regla, sabemos que los comodines se ordenan hacia el final
		int fallos = 0; // Cada vez que una carta no sea exactamente 1 mayor, se suma 1.
		int cantidadComodines = cantidadComodines(jugada);
		Carta cartaAnterior = jugada.getCarta(1);
		Palo paloDelJuego = cartaAnterior.getPalo();

		for (int i = 1; i <= jugada.getCantidadCartas() - cantidadComodines; i++) {
			if (!(jugada.getCarta(i).getPalo() == paloDelJuego)) {
				return false;
			}
			if (!(jugada.getCarta(i).getNumero() == cartaAnterior.getNumero() + 1)) {
				fallos++;
			}
			cartaAnterior = jugada.getCarta(i);
		}
		if ((fallos - cantidadComodines) > 0) {
			return false;
		}
		return true;
	}

	private static boolean esRepeticion(ConjuntoCartas jugada) {
		boolean resultado = true;
		Carta primeraCarta = jugada.getCarta(1);
		int inicio = 1;
		if (primeraCarta.getPalo() == Palo.COMODIN) {
			inicio = 2;
			primeraCarta = jugada.getCarta(2);
		}
		for (int i = inicio; i <= jugada.getCantidadCartas(); i++) {
			if (!(jugada.getCarta(i).tieneMismoNumero(primeraCarta))) {
				resultado = false;
				break;
			}
		}
		return resultado;
	}

	private static int cantidadComodines(ConjuntoCartas jugada) {
		int resultado = 0;
		for (int i = 1; i <= jugada.getCantidadCartas(); i++) {
			if (jugada.getCarta(i).getPalo() == Palo.COMODIN) {
				resultado++;
			}
		}
		return resultado;
	}

}
