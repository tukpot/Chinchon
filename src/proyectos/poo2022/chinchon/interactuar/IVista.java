package proyectos.poo2022.chinchon.interactuar;

public interface IVista {

	void setControlador(Controlador controlador);

	void iniciar();

	void bloquear();

	void actualizarManoYPila();

	void tomarDeMazoOPila();

	void descartarOCerrar();

	void mostrarPuntos();

	void perder();

    void ganar();

}
