package proyectos.poo2022.chinchon.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ConjuntoCartas {
	private ArrayList<Carta> cartas = new ArrayList<Carta>();

	public ConjuntoCartas() {
	}

	public void añadirCarta(Carta cartaIn) {
		this.cartas.add(cartaIn);
	}

	protected ArrayList<Carta> getCartas() {
		return this.cartas;
	}

	public int getCantidadCartas() {
		return this.cartas.size();
	}

	public Carta getCarta(int posicionCarta) {
		if (posicionCarta < 1) {
			return null;
		}
		if (posicionCarta > this.getCantidadCartas()) {
			return null;
		}
		return this.cartas.get(posicionCarta - 1);
	}

	public void transferirCarta(int posicionCarta, ConjuntoCartas receptor) {
		receptor.añadirCarta(this.cartas.get(posicionCarta - 1));
		this.cartas.remove(posicionCarta - 1);
	}

	public Carta tomarCarta(int posicionCarta) {
		Carta devolver = this.cartas.get(posicionCarta - 1);
		this.cartas.remove(posicionCarta - 1);
		return devolver;
	}

	public void ordenarCartas() {
		Collections.sort(this.getCartas(), new Comparator<Carta>() {
			public int compare(Carta carta1, Carta carta2) {
				if (carta1.getNumero() == carta2.getNumero())
					return 0;
				return carta1.getNumero() < carta2.getNumero() ? -1 : 1;
			}
		});
	}

	public void añadirConjunto(ConjuntoCartas conjuntoCartasAñadir) {
		for (int i = 1; i <= conjuntoCartasAñadir.getCantidadCartas(); i++) {
			this.añadirCarta(conjuntoCartasAñadir.getCarta(i));
		}
	}

	public boolean quitarConjuntoCartas(ConjuntoCartas cartasAQuitar) {
		HashSet<Carta> cartasQueAparecieron = new HashSet<Carta>();
		ConjuntoCartas conjuntoSalida = new ConjuntoCartas();

		for (int i = 1; i <= cartasAQuitar.getCantidadCartas(); i++) {
			cartasQueAparecieron.add(cartasAQuitar.getCarta(i));
		}
		;
		for (int i = 1; i <= this.getCantidadCartas(); i++) {
			if (cartasQueAparecieron.add(this.getCarta(i))) {
				conjuntoSalida.añadirCarta(this.getCarta(i));
			}
		}
		this.cartas = conjuntoSalida.cartas;
		return true;
	}

	public ConjuntoCartas devolverCopiaConjunto() {
		ConjuntoCartas conjuntoCopia = new ConjuntoCartas();

		for (int i = 1; i <= this.getCantidadCartas(); i++) {
			conjuntoCopia.añadirCarta(this.getCarta(i));
		}
		return conjuntoCopia;
	}

	public ConjuntoCartas getCartas(int[] posiciones) {
		if ((posiciones.length < 1) || (posiciones.length > this.getCantidadCartas())) {
			return null;
		}

		HashSet<Integer> set = new HashSet<Integer>();
		ConjuntoCartas conjunto = new ConjuntoCartas();

		for (int i = 0; i < posiciones.length; i++) {
			if (set.add(posiciones[i]) == false) {
				return null;
			}
			conjunto.añadirCarta(this.getCarta((posiciones[i])));
		}

		return conjunto;

	}

	public String toString() {
		if (this.getCantidadCartas() == 0) {
			return "[NO HAY CARTAS]";
		}
		String result = "";
		for (int i = 1; i <= this.getCantidadCartas(); i++) {
			result = i + ". " + this.getCarta(i).toString() + "\n";
		}
		return result;
	}

	public void quitarCarta(Carta cartaAQuitar) {
		this.getCartas().remove(cartaAQuitar);
	}

	public ArrayList<Carta> getCopiaArrayList() {
		return new ArrayList<Carta>(cartas);
	}

	public static ConjuntoCartas arrayToConjuntoCartas(List<Carta> cartas) {
		ConjuntoCartas resultado = new ConjuntoCartas();
		for (int i = 0; i < cartas.size(); i++) {
			resultado.añadirCarta(cartas.get(i));
		}
		return resultado;
	}
}
