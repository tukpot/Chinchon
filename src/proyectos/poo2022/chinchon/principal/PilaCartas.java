package proyectos.poo2022.chinchon.principal;

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
