package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.clientes.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
public class Main {

	public static void main(String[] args) {
		
		Juego 		modelo 		= new Juego();
		IVista 		vista	 	= new PseudoConsola();
		Controlador controlador = new Controlador(modelo,vista);
		vista.iniciar();
	}
	

}
