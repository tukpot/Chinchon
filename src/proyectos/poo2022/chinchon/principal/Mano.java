package proyectos.poo2022.chinchon.principal;

public class Mano extends ConjuntoCartas{

    
    public boolean esCerrable(Jugada jugada1, Jugada jugada2, Carta cartaQueCorta) {
	if (1<(this.getCantidadCartas() -1 - jugada1.getCantidadCartas() - jugada2.getCantidadCartas())) {
	    return false; //Si la cantidad de cartas de la mano, menos la cantidad de cartas de las jugadas, menos la carta que corta es mayor a 1
	}
	int valorTotalMano 	= Mano.sumarTotal(this) - cartaQueCorta.getNumero();
	int valorTotalJugadas 	= Mano.sumarTotal(jugada1) + Mano.sumarTotal(jugada2);
	
	if ((valorTotalMano - valorTotalJugadas) <= 5) {
	    return true;
	}
	return false;
    }
    
    public boolean esCerrable(Jugada jugada, Carta cartaQueCorta) {
	if (1<(this.getCantidadCartas() -1 - jugada.getCantidadCartas())) {
	    return false; //Si la cantidad de cartas de la mano, menos la cantidad de cartas de las jugadas, menos la carta que corta es mayor a 1
	}
	
	int valorTotalMano 	= Mano.sumarTotal(this) - cartaQueCorta.getNumero();
	int valorTotalJugadas 	= Mano.sumarTotal(jugada);
	
	if ((valorTotalMano - valorTotalJugadas) <= 5) {
	    return true;
	}
	return false;
    }
    
    public int calcularPuntajeRestante() {
	return Mano.sumarTotal(this);
    }
    
    public int calcularPuntajeRestante(ConjuntoCartas jugada) {
	return Mano.sumarTotal(this) - Mano.sumarTotal(jugada);
    }
    
    public int calcularPuntajeRestante(ConjuntoCartas jugada1, ConjuntoCartas jugada2) {
	return Mano.sumarTotal(this) - Mano.sumarTotal(jugada1) - Mano.sumarTotal(jugada2);
    }
    
    
    private static int sumarTotal(ConjuntoCartas conjuntoASumar) {
	int resultado= 0;
	for (int i=1; i<=conjuntoASumar.getCantidadCartas();i++) {
	    resultado = resultado + conjuntoASumar.getCarta(i).getNumero();
	}
	return resultado;
    }
    
    public String toString() {
        String textoSalida ="";
        for (int i=1; i<=this.getCantidadCartas();i++) {
            textoSalida = textoSalida + String.valueOf(i) +". ";
            textoSalida = textoSalida + this.getCarta(i).toString();
            textoSalida = textoSalida + "\n";
        }
        return textoSalida;
    }

    public int getValor(Jugada jugada1, Jugada jugada2) {
	System.out.println("Hacer get valor mano");
	// TODO Auto-generated method stub
	return 0;
    }


    
    
    
}
