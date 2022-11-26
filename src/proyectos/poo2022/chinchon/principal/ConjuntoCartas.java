package proyectos.poo2022.chinchon.principal;

import java.util.ArrayList;

public class ConjuntoCartas {
	private ArrayList<Carta> cartas = new ArrayList<Carta>();
	 
	public ConjuntoCartas() {	
		}
	
	
	
	public void añadirCarta(Carta cartaIn) {
		this.cartas.add(cartaIn);
	 	}
	 
	public ArrayList<Carta> getCartas(){
		 return this.cartas;
	 	}
	
	 public int getCantidadCartas() {
		return this.cartas.size(); 
	 }

	 public Carta getCarta(int posicionCarta) {
		 return this.cartas.get(posicionCarta -1);
	 	}
	 
	 public void transferirCarta(int posicionCarta, ConjuntoCartas receptor) {
		 receptor.añadirCarta(this.cartas.get(posicionCarta -1));
		 this.cartas.remove(posicionCarta -1);
	 	}
	 
	 public Carta tomarCarta(int posicionCarta) {
		 Carta devolver = this.cartas.get(posicionCarta -1);
		 this.cartas.remove(posicionCarta -1);
		 return devolver;
	 }

	 
	 
	 
}
