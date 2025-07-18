package proyectos.poo2022.chinchon.modelo;

import java.io.*;
import java.util.*;

public class TopJugadores {
    private static TopJugadores instancia;

    private List<JugadorTop> listaJugadoresTop;
    private final String nombreArchivoTopJugadores = "top_jugadores.ser";
    private final int cantidadMaximaTop = 10;

    private TopJugadores() {
        this.listaJugadoresTop = this.cargarJugadoresDesdeArchivo();
    }

    public static TopJugadores getInstancia() {
        if (instancia == null) {
            instancia = new TopJugadores();
        }
        return instancia;
    }

    public void agregarJugador(Jugador jugadorNuevo) {
        for (JugadorTop jugadorTop : this.listaJugadoresTop) {
            if (jugadorTop.getNombre().equalsIgnoreCase(jugadorNuevo.getNombre())) {
                jugadorTop.aumentarRondasGanadas();
                this.ordenarYLimitarLista();
                this.guardarJugadoresEnArchivo();
                return;
            }
        }

        JugadorTop nuevoJugadorTop = new JugadorTop(jugadorNuevo.getNombre(), 1);
        this.listaJugadoresTop.add(nuevoJugadorTop);
        this.ordenarYLimitarLista();
        this.guardarJugadoresEnArchivo();
    }

    public List<JugadorTop> getJugadoresTop() {
        return this.listaJugadoresTop;
    }

    private void ordenarYLimitarLista() {
        this.listaJugadoresTop.sort((a, b) -> Integer.compare(b.getRondasGanadas(), a.getRondasGanadas()));
        if (this.listaJugadoresTop.size() > this.cantidadMaximaTop) {
            this.listaJugadoresTop = new ArrayList<>(this.listaJugadoresTop.subList(0, this.cantidadMaximaTop));
        }
    }

    private void guardarJugadoresEnArchivo() {
        try (ObjectOutputStream flujoSalida = new ObjectOutputStream(
                new FileOutputStream(this.nombreArchivoTopJugadores))) {
            flujoSalida.writeObject(this.listaJugadoresTop);
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }

    private List<JugadorTop> cargarJugadoresDesdeArchivo() {
        File archivo = new File(this.nombreArchivoTopJugadores);
        if (!archivo.exists())
            return new ArrayList<>();

        try (ObjectInputStream flujoEntrada = new ObjectInputStream(
                new FileInputStream(this.nombreArchivoTopJugadores))) {
            return (List<JugadorTop>) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException excepcion) {
            excepcion.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getJugadoresTopString() {
        return formatearJugadoresTop(this.getJugadoresTop());
    }

    private static String formatearJugadoresTop(List<JugadorTop> jugadoresTop) {
        StringBuilder resultado = new StringBuilder();
        int posicion = 1;

        for (JugadorTop jugador : jugadoresTop) {
            resultado.append(posicion++)
                    .append(". ")
                    .append(jugador.getNombre())
                    .append(" - ")
                    .append(jugador.getRondasGanadas())
                    .append(" rondas ganadas\n");
        }

        return resultado.toString();
    }

}
