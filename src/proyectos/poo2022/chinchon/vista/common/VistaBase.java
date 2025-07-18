package proyectos.poo2022.chinchon.vista.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.Jugador;
import proyectos.poo2022.chinchon.modelo.Mano;
import proyectos.poo2022.chinchon.vista.IVista;

public abstract class VistaBase extends JFrame implements IVista {

    private VentanaInicioSesion vInicioSesion;
    private Controlador controlador;

    public abstract void logicaInicioAdicional();

    public abstract void sesionIniciada() throws RemoteException;

    public VistaBase(Controlador controlador) {
        super();
        this.vInicioSesion = new VentanaInicioSesion();
        this.controlador = controlador;
        this.controlador.setVista(this);

        this.vInicioSesion.onClickIniciar(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                controlador.conectarJugador(vInicioSesion.getGetNombreUsuario());
                try {
                    sesionIniciada();
                    vInicioSesion.setVisible(false);
                } catch (RemoteException e) {
                    System.out.println("fallo al testear la conectividad");
                }
            }
        });
    }

    public final void iniciar() {
        // Patrón Template
        this.mostrarInicioSesion();
        this.logicaInicioAdicional();
    }

    private final void mostrarInicioSesion() {
        this.vInicioSesion.setVisible(true);
    }

    public final void setListoParaJugar(boolean listo) throws RemoteException {
        this.controlador.setListoParaJugar(listo);
    }

    public final Carta getTopePila() throws RemoteException {
        return this.controlador.getTopePila();
    }

    public final Mano getMano() throws RemoteException {
        return this.controlador.getJugador().getMano();
    }

    public final void tomarTopeMazo() throws RemoteException {
        this.controlador.tomarTopeMazo();
    }

    public final void terminarRonda() throws RemoteException {
        this.controlador.terminarRonda();
    }

    public final void tomarTopePilaDescarte() throws RemoteException {
        this.controlador.tomarTopePilaDescarte();
    }

    public final void descartar(int cartaElegida) throws RemoteException {
        this.controlador.descartar(cartaElegida);
    }

    public final Jugador getJugadorActual() throws RemoteException {
        return this.controlador.getJugadorActual();
    }

    public final Jugador getJugador(int id) throws RemoteException {
        return this.controlador.getJugador(id);
    }

    public final Jugador getJugador() throws RemoteException {
        return this.controlador.getJugador();
    }

    public final int getCantidadJugadores() throws RemoteException {
        return this.controlador.getCantidadJugadores();
    }

    public final void testConectado() throws RemoteException {
        this.controlador.testConectado();
    }
}
