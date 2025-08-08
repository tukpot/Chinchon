package proyectos.poo2022.chinchon.modelo;

import java.io.Serializable;
import java.util.Objects;

import proyectos.poo2022.chinchon.enumerados.Palo;

public class Carta implements Serializable {
	private final Palo palo;
	private final int numero;

	public Carta(Palo paloIn, int numeroIn) {
		this.palo = paloIn;
		this.numero = numeroIn;
	}

	public Palo getPalo() {
		return this.palo;
	}

	public int getNumero() {
		return this.numero;
	}

	public String getImageName() {
		if (this.getPalo() == Palo.COMODIN)
			return "comodin.png";
		return this.getPalo().toString().toLowerCase() + "-" + this.getNumero() + ".png";
	}

	public String toString() {
		if (this.getPalo() == Palo.COMODIN) {
			return "[COMODÍN]";
		}
		String textoSalida = "[" + String.valueOf(this.getNumero()) + " de " + this.getPalo() + "]";
		return textoSalida;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Carta))
			return false;
		Carta otra = (Carta) obj;
		return numero == otra.getNumero() && palo.equals(otra.palo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero, palo);
	}

}
