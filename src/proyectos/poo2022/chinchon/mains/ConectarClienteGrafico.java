package proyectos.poo2022.chinchon.mains;

import proyectos.poo2022.chinchon.clientes.clienteGrafico2d.ClienteGrafico2d;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Juego;

public class ConectarClienteGrafico {

    public static void main(String[] args) {

        IVista 	vista = new ClienteGrafico2d();
//        Controlador 		controlador 	= new Controlador(modelo, vista1);
        vista.iniciar();
    }

}
