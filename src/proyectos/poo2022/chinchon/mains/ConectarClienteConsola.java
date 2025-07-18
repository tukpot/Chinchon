package proyectos.poo2022.chinchon.mains;

import java.net.*;
import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.vista.IVista;
import proyectos.poo2022.chinchon.vista.pseudoconsola.PseudoConsola;

public class ConectarClienteConsola {

    public static void main(String[] args) {
        // Detectar la IP local del cliente
        String ipCliente = getLocalIPAddress();
        if (ipCliente == null) {
            System.err.println("No se pudo determinar la IP local del cliente. Abortando.");
            return;
        }

        // Solicitar puerto del cliente para escuchar callbacks
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente",
                "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999);

        // Solicitar IP del servidor
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que corre el servidor (si está corriendo en su misma PC, deje el default)",
                "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "127.0.0.1");

        String portServidor = "8888";

        // Establecer propiedad para que RMI use la IP externa del cliente si es
        // necesario
        System.setProperty("java.rmi.server.hostname", ipCliente);

        // Iniciar cliente
        Controlador controlador = new Controlador();
        IVista vista = new PseudoConsola(controlador);
        Cliente c = new Cliente(ipCliente, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        vista.iniciar();

        try {
            c.iniciar(controlador);
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
