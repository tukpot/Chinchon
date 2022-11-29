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
	
	public boolean tieneMismoNumero(Carta carta) {
	    if (carta.getNumero()==this.getNumero()) {
		return true;
	    }
	    if (carta.getPalo()==Palo.COMODIN) {
		return true;
	    }
	    return false;
	}
	
	public boolean tieneMismoPalo(Carta carta) {
	    if ( (carta.getPalo()==Palo.COMODIN) || (this.getPalo()==Palo.COMODIN) ){
		return true;
	    }
	    if (carta.getPalo()==this.getPalo()) {
		return true;
	    }
	    return false;
	}
	
	public String toString() {
	        if (this.getPalo()==Palo.COMODIN) {
	            return "[COMODÍN]";
	        }
	        String textoSalida= "["+String.valueOf(this.getNumero()) +" de "+ this.getPalo()+"]";
	        return textoSalida;
	}
	
	
}
