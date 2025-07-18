package proyectos.poo2022.chinchon.modelo;

import java.io.Serializable;
import java.util.Random;

public class Jugador implements Serializable {
	transient private static int lastGeneratedId = 0;

	private int id;


	private Mano mano = new Mano();
	private int puntos = 0;
	private String nombre;
	private boolean listoParaJugar = false;

	public Jugador(String nombre) {
		this.nombre = nombre;
		this.id = Jugador.lastGeneratedId++;
	}


	public void tomarCartaMazo(Mazo mazoIn) {
		this.mano.añadirCarta(mazoIn.tomarCartaTope());
	}

	public void descartarCarta(int cartaATirar, PilaCartas pilaDescarte) { // añadir forma de recibir carta
		this.mano.transferirCarta(cartaATirar, pilaDescarte);
	}

	public void tomarCartaPilaDescarte(PilaCartas pilaDescarteIn) {
		Carta cartaRecienTomada = pilaDescarteIn.tomarCartaTope();
		this.mano.añadirCarta(cartaRecienTomada);
	}

	public Mano getMano() {
		return this.mano;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public int getId(){
		return this.id;
	}

	public void setListoParaJugar(boolean listo) {
		this.listoParaJugar = listo;
	}

	public boolean getListoParaJugar() {
		return this.listoParaJugar;
	}

	protected void resetMano() {
		this.mano = new Mano();
	}

	public int getPuntos() {
		return puntos;
	}

	public void añadirPuntosMano() {
		this.puntos = this.puntos + this.mano.calcularPuntajeRestante();
	}

	public static boolean validarNombre(String nombre){
		if (nombre==null || nombre.equals("")) return false;
		return true;
	}

	public static String generarNombreAleatorio() {
		String[] adjetivos = {
				"misterioso", "vengativo", "cansado", "anciano", "astuto", "feroz", "gentil",
				"silencioso", "ruidoso", "salvaje", "errante", "temible", "noble",
				"sombrío", "valiente", "enojado", "estoico", "sabio", "congelado", "llameante",
				"hastiado", "meloso", "amoroso", "dormilón", "gordo", "bebé", "poderoso", "veloz", "brillante",
				"divertido", "aburrido", "anarquista", "sagaz", "maníaco", "juguetón", "jocoso", "enérgico","perplejo","pedante"
		};

		String[] razasDeOso = {
				"Grizzly", "Kodiak", "Pardo", "Polar", "Negro", "Negro Asiático",
				"Himalayo", "Panda", "Bezudo", "Malayo", "Solar", "De Anteojos", "Andino",
				"Tibetano", "Del Gobi", "Siberiano", "Europeo", "Ussuri",
				"De Kamchatka", "Blanco Ártico Canadiense", "Alaska Peninsular", "Gigante de Qinling"
		};

		Random rand = new Random();
		String razaAleatoria = razasDeOso[rand.nextInt(razasDeOso.length)];
		String adjetivoAleatorio = adjetivos[rand.nextInt(adjetivos.length)];

		return razaAleatoria + " " + adjetivoAleatorio;
	}
}
