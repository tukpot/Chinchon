package proyectos.poo2022.chinchon.jugar;

import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.clientes.PseudoConsola;
import proyectos.poo2022.chinchon.principal.Juego;

public class MultijugadorLocal {

	public static void main(String[] args) {
		Juego 		modelo 			= new Juego();
		
		IVista 		vista1			= new PseudoConsola();
		Controlador 	controlador1 		= new Controlador(modelo, vista1);
		
		
		IVista 		vista2			= new PseudoConsola();
		Controlador 	controlador12 		= new Controlador(modelo, vista2);
		
		vista1.iniciar();
		vista2.iniciar();
	}

}
