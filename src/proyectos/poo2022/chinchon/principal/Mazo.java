package proyectos.poo2022.chinchon.principal;

import java.util.ArrayList;
import java.util.Collections;

import proyectos.poo2022.chinchon.enumerados.Palo;

public class Mazo extends PilaCartas {

	public Mazo() {
		super();
		for (int i = 1; i <= 12; i++) {
			Carta cartaAñadir = new Carta(Palo.BASTO, i);
			this.añadirCarta(cartaAñadir);
		}

		for (int i = 1; i <= 12; i++) {
			Carta cartaAñadir = new Carta(Palo.COPA, i);
			this.añadirCarta(cartaAñadir);
		}

		for (int i = 1; i <= 12; i++) {
			Carta cartaAñadir = new Carta(Palo.ESPADA, i);
			this.añadirCarta(cartaAñadir);
		}

		for (int i = 1; i <= 12; i++) {
			Carta cartaAñadir = new Carta(Palo.ORO, i);
			this.añadirCarta(cartaAñadir);
		}

		// for (int i = 1; i <= 2; i++) {
		// Carta cartaAñadir = new Carta(Palo.COMODIN, 50);
		// this.añadirCarta(cartaAñadir);
		// }
	}

	public void barajar() {
		Collections.shuffle(this.getCartas());
	}

	public void repartir(ArrayList<Jugador> jugadoresIn, int cantidadCartas, boolean darCartaExtra) {
		for (int i = 0; i < jugadoresIn.size(); i++) {
			for (int c = 0; c < 7; c++) {
				jugadoresIn.get(i).tomarCartaMazo(this);
			}
		}
		if (darCartaExtra) {
			jugadoresIn.get(0).tomarCartaMazo(this);
		}
	}

	public void reutilizarPilaDescarte() {

	}

}
