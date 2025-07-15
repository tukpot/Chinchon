package proyectos.poo2022.chinchon.vista;

import java.rmi.RemoteException;

import proyectos.poo2022.chinchon.interactuar.Controlador;

public interface IVista {

	void iniciar();

	void bloquear() throws RemoteException;

	void actualizarManoYPila() throws RemoteException;

	void tomarDeMazoOPila();

	void descartarOCerrar();

	void mostrarPuntos() throws RemoteException;

	void perder();

    void ganar() throws RemoteException;

}
