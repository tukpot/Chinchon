package proyectos.poo2022.chinchon.mains;

import proyectos.poo2022.chinchon.clientes.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Juego;

public class MultijugadorLocal {

	public static void main(String[] args) {
		Juego 			modelo 		= new Juego();

		IVista 			vista1		= new PseudoConsola();
		Controlador 		controlador1 	= new Controlador(modelo, vista1);
		
		IVista 			vista2		= new PseudoConsola();
		Controlador 		controlador2 	= new Controlador(modelo, vista2);
		
		vista1.iniciar();
		vista2.iniciar();
	}

}
