package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.interactuar.Controlador;

public class Jugador  {
	private			Mano 				mano 				= new Mano();
	private 		int 				puntos 				= 0;
	private 		String				nombre;	
	private 		Controlador 			controlador;
	private			boolean				listoParaJugar 		= false;
	

	public Jugador(String nombre) {
		this.nombre = nombre;
	}
	
	
	public void tomarCartaMazo(Mazo mazoIn) {
		this.mano.añadirCarta(mazoIn.tomarCartaTope());
		}
		
	public void descartarCarta(int cartaATirar, PilaDescarte pilaDescarte) { //añadir forma de recibir carta
		this.mano.transferirCarta(cartaATirar, pilaDescarte);
		}
	
	public void tomarCartaPilaDescarte(PilaDescarte pilaDescarteIn) {
		Carta cartaRecienTomada = pilaDescarteIn.tomarCartaTope();
		this.mano.añadirCarta(cartaRecienTomada);
		}
	
	
	public void jugar() {
		
		}

	public Mano getMano() {
		return this.mano;
	}


	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}


	public String getNombre() {
		return this.nombre;
	}


	public void setListoParaJugar(boolean listo) {
		this.listoParaJugar = listo;
	}


	public boolean getListoParaJugar() {
		return this.listoParaJugar;
	}

	

}
