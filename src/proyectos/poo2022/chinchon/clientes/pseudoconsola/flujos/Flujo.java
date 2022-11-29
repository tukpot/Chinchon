package proyectos.poo2022.chinchon.clientes.pseudoconsola.flujos;

import proyectos.poo2022.chinchon.clientes.pseudoconsola.PseudoConsola;
import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;

public abstract class Flujo {
    private EstadoPrograma estadoActual;
    private PseudoConsola vista;
    
    
    public Flujo(PseudoConsola vista) {
	this.vista = vista;
    };
    
    
    protected void setEstadoActual(EstadoPrograma nuevoEstado) {
	this.estadoActual= nuevoEstado;
    };
    
    public EstadoPrograma getEstadoActual() {
	return this.estadoActual;
    };
    
    public abstract void recibirInput(String texto);
    
    public void println(String texto) {
	this.vista.println(texto);
    }
    
    public void println() {
	this.vista.println();
    }
    
    public void clear() {
	this.vista.clear();
    }
    
    protected void cancelarFlujo() {
	this.vista.volverAEstadoAnterior();
    }
    
    protected abstract void terminarFlujo();
    
    public PseudoConsola getVista() {
	return this.vista;
    }
    
    
    
}