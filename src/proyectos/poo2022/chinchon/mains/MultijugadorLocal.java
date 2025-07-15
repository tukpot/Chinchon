package proyectos.poo2022.chinchon.mains;

import java.rmi.RemoteException;

import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.modelo.Juego;
import proyectos.poo2022.chinchon.vista.IVista;
import proyectos.poo2022.chinchon.vista.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.vista.vista2D.Vista2D;

public class MultijugadorLocal {

	public static void main(String[] args) throws RemoteException {
		// Tests.runAllTests();
		Juego modelo = new Juego();

		IVista vista1 = new PseudoConsola();
		Controlador controlador1 = new Controlador(modelo, vista1);

		IVista vista2 = new Vista2D();
		Controlador controlador2 = new Controlador(modelo, vista2);

		vista1.iniciar();
		vista2.iniciar();
	}

}
