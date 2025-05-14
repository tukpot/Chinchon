package proyectos.poo2022.chinchon.interactuar;

public interface Observable {
	public void notificar(Object evento);
	public void agregarObservador(Observador observador);
}
