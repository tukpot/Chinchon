package proyectos.poo2022.chinchon.interactuar;

public interface IVista {

	void setControlador(Controlador controlador);
	void iniciar();
	void actualizarManoYPila();
	void descartar();
	void partidaIniciada();
	void bloquear();
	void jugarTurno();
	void mostrarPuntos();

}
