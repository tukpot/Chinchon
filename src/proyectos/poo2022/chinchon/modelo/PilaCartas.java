package proyectos.poo2022.chinchon.modelo;

public class PilaCartas  extends ConjuntoCartas{
	
	
	
	public Carta tomarCartaTope() {
		Carta devolver = this.tomarCarta(this.getCantidadCartas());
		return devolver;
	}
	
	public Carta getTope() {
		if (this.getCantidadCartas()>0) {
			return this.getCarta(this.getCantidadCartas());
		}
		else return null;
	}

}
