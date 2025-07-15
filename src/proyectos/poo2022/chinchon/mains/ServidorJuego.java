package proyectos.poo2022.chinchon.mains;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import proyectos.poo2022.chinchon.modelo.Juego;

public class ServidorJuego {

	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione la IP en la que escuchar� peticiones el servidor", "IP del servidor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				ips.toArray(),
				null);
		String port = (String) JOptionPane.showInputDialog(
				null,
				"Seleccione el puerto en el que escuchar� peticiones el servidor", "Puerto del servidor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888);
		Juego modelo = new Juego();
		Servidor servidor = new Servidor(ip, Integer.parseInt(port));
		try {
			servidor.iniciar(modelo);
			System.out.println("Servidor corriendo en: " + ip + ":" + port);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
