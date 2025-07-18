package proyectos.poo2022.chinchon.modelo;

import java.io.Serializable;

public class JugadorTop implements Serializable {
    private String nombre;
    private int rondasGanadas;

    public JugadorTop(String nombre, int rondasGanadas) {
        this.nombre = nombre;
        this.rondasGanadas = rondasGanadas;
    }

    public int getRondasGanadas() {
        return this.rondasGanadas;
    }

    public void aumentarRondasGanadas(){
        this.rondasGanadas++;
    }

    public String getNombre() {
        return this.nombre;
    }
}
