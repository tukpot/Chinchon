package proyectos.poo2022.chinchon.mains;

import java.net.*;
import java.rmi.RemoteException;
import java.util.Enumeration;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import proyectos.poo2022.chinchon.modelo.Juego;

public class ServidorJuego {

	public static void main(String[] args) {
		// Escuchar en todas las interfaces
		String listenIP = "0.0.0.0";
		String port = "8888";

		// Detectar la IP local válida (no loopback)
		String publicIP = getLocalIPAddress();
		if (publicIP == null) {
			System.err.println("No se pudo determinar la IP local. Abortando.");
			return;
		}

		// Configurar la IP pública para RMI
		System.setProperty("java.rmi.server.hostname", publicIP);

		Juego modelo = new Juego();
		Servidor servidor = new Servidor(listenIP, Integer.parseInt(port));

		try {
			servidor.iniciar(modelo);
			System.out.println("Servidor RMI corriendo en: " + publicIP + ":" + port);
		} catch (RemoteException | RMIMVCException e) {
			e.printStackTrace();
		}
	}

	// Método para obtener automáticamente la IP local válida (IPv4)
	private static String getLocalIPAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				if (!ni.isUp() || ni.isLoopback() || ni.isVirtual())
					continue;

				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
						return address.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
}
