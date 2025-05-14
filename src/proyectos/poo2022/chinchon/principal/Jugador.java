package proyectos.poo2022.chinchon.principal;

import java.util.Random;
import java.util.ResourceBundle.Control;

import proyectos.poo2022.chinchon.enumerados.Evento;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.Observable;
import proyectos.poo2022.chinchon.interactuar.Observador;

public class Jugador implements Observable {
	Controlador controlador;
	private Observador observador;
	private Mano mano = new Mano();
	private int puntos = 0;
	private String nombre;
	private boolean listoParaJugar = false;

	public Jugador(String nombre) {
		this.nombre = nombre;
	}

	public void notificar(Object evento) {
		this.observador.actualizar(evento, this);
	}

	public void tomarCartaMazo(Mazo mazoIn) {
		this.mano.añadirCarta(mazoIn.tomarCartaTope());
	}

	public void descartarCarta(int cartaATirar, PilaDescarte pilaDescarte) { // añadir forma de recibir carta
		this.mano.transferirCarta(cartaATirar, pilaDescarte);
	}

	public void tomarCartaPilaDescarte(PilaDescarte pilaDescarteIn) {
		Carta cartaRecienTomada = pilaDescarteIn.tomarCartaTope();
		this.mano.añadirCarta(cartaRecienTomada);
	}

	public Mano getMano() {
		return this.mano;
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

	protected void resetMano() {
		this.mano = new Mano();
	}

	public int getPuntos() {
		return puntos;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
		this.agregarObservador(controlador);
	}

	public void añadirPuntosMano() {
		this.puntos = this.mano.calcularPuntajeRestante();
		this.puntos = this.puntos + 100;
	}

	public static String generarNombreAleatorio() {
		String[] adjetivos = {
				"misterioso", "vengativo", "cansado", "anciano", "astuto", "feroz", "gentil",
				"silencioso", "ruidoso", "salvaje", "errante", "temible", "noble",
				"sombrío", "valiente", "enojado", "estoico", "sabio", "congelado", "llameante",
				"hastiado", "meloso", "amoroso", "dormilón", "gordo", "bebé", "poderoso", "veloz", "brillante",
				"divertido", "aburrido", "anarquista", "sagaz", "maníaco", "juguetón", "jocoso", "enérgico"
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

	public void agregarObservador(Observador observador) {
		if (this.observador == null) {
			this.observador = observador;
		} else {
			throw new Error("Observador ya seteado para el jugador [" + this.getNombre() + "]");
		}
	}
}
