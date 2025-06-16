package proyectos.poo2022.chinchon.mains;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.modelo.Juego;
import proyectos.poo2022.chinchon.vista.IVista;
import proyectos.poo2022.chinchon.vista.clienteGrafico2d.ClienteGrafico2d;

public class ConectarClienteGrafico {

    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null);
        String port = "9999";
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        String portServidor = "8888";
        Controlador controlador = new Controlador();
        IVista vista = new ClienteGrafico2d(controlador);
        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        vista.iniciar();
        try {
            c.iniciar(controlador);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
