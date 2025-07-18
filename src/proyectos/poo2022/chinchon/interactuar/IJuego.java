package proyectos.poo2022.chinchon.interactuar;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.Jugador;
import proyectos.poo2022.chinchon.modelo.Mano;

public interface IJuego extends IObservableRemoto {

    int conectarJugador(String nombre) throws RemoteException;

    void desconectarJugador(int usuarioId) throws RemoteException;

    void setListoParaJugar(int jugador, boolean estaListo) throws RemoteException;

    Carta getTopePila() throws RemoteException;

    Jugador getJugadorActual() throws RemoteException;

    Jugador getJugador(int id) throws RemoteException;

    Jugador[] getJugadores() throws RemoteException;

    int getCantidadJugadores() throws RemoteException;

    void tomarTopeMazo(int jugadorQueToma) throws RemoteException;

    void tomarTopePilaDescarte(int jugadorQueToma) throws RemoteException;

    void descartar(int cartaElegida, int jugadorQueDescarta) throws RemoteException;

    Mano getMano(int numJugador) throws RemoteException;

    void terminarRonda(int jugador) throws RemoteException;

    void empezarAJugar() throws RemoteException;

    void testearConectividad() throws RemoteException;

    String getJugadoresTopString() throws RemoteException;
}