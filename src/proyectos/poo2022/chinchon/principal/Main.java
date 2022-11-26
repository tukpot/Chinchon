package proyectos.poo2022.chinchon.principal;

import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.clientes.PseudoConsola;
public class Main {

	public static void main(String[] args) {
		
		Juego 		modelo 		= new Juego();
		IVista 		vista	 	= new PseudoConsola();
		Controlador controlador = new Controlador(modelo,vista);
		vista.iniciar();
	}
	

}
