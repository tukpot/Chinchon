package proyectos.poo2022.chinchon.mains;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import proyectos.poo2022.chinchon.modelo.Juego;

public class ServidorJuego {

	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = "127.0.0.1";
		String interfazUsada = "loopback";

		// Esto lo único que hace es seleccionar una IP y puerto autómaticamente
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
					continue;
				}

				Enumeration<InetAddress> direcciones = ni.getInetAddresses();
				while (direcciones.hasMoreElements()) {
					InetAddress addr = direcciones.nextElement();
					if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
						ip = addr.getHostAddress();
						interfazUsada = ni.getDisplayName();
						break;
					}
				}
				if (!ip.equals("127.0.0.1"))
					break;
			}
		} catch (SocketException e) {
			System.err.println("Error al obtener la IP de la red local.");
			e.printStackTrace();
		}
		System.out.println("IP seleccionada: " + ip + " (Interfaz: " + interfazUsada + ")");
		int port = 8888;
		// Fin de selección de IP y puerto automática
		Juego modelo = new Juego();
		Servidor servidor = new Servidor(ip, port);

		try {
			servidor.iniciar(modelo);
		} catch (RemoteException | RMIMVCException e) {
			System.out.println("Hubo un problema en ServidorJuego");
			e.printStackTrace();
		}

		System.out.println("Servidor corriendo en: " + ip + ":" + port);
	}
}
