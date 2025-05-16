package proyectos.poo2022.chinchon.mains;

import proyectos.poo2022.chinchon.clientes.clienteGrafico2d.ClienteGrafico2d;
import proyectos.poo2022.chinchon.clientes.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.ComprobadorMano;
import proyectos.poo2022.chinchon.principal.Juego;
import proyectos.poo2022.chinchon.utilidades.Tests;
import ar.edu.unlu.rmimvc.servidor.Servidor;

public class MultijugadorLocal {

	public static void main(String[] args) {
		// Tests.runAllTests();
		Juego modelo = new Juego();

		IVista vista1 = new PseudoConsola();
		Controlador controlador1 = new Controlador(modelo, vista1);

		IVista vista2 = new ClienteGrafico2d();
		Controlador controlador2 = new Controlador(modelo, vista2);

		vista1.iniciar();
		vista2.iniciar();
	}

}
