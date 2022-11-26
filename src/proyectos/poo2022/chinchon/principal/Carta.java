package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.enumerados.Palo;

public class Carta {
	private Palo 	palo;
	private int 	numero;

	public Carta(Palo paloIn, int numeroIn) {
	    this.palo 	= paloIn;
	    this.numero = numeroIn;
	}
	
	public Palo getPalo() {
	    return this.palo;
	}
	
	public int getNumero() {
	    return this.numero;
	}

	
	
	
}
