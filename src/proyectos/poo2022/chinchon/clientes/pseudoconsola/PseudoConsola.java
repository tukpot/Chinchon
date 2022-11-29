package proyectos.poo2022.chinchon.clientes.pseudoconsola;

import proyectos.poo2022.chinchon.clientes.pseudoconsola.flujos.Flujo;
import proyectos.poo2022.chinchon.clientes.pseudoconsola.flujos.FlujoCerrarRonda;
import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;
import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.ConjuntoCartas;
import proyectos.poo2022.chinchon.principal.Mano;
import proyectos.poo2022.chinchon.utilidades.MetodosUtiles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class PseudoConsola extends JFrame implements IVista {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel 	contentPane;
    private JTextField 	edit_input;
    private JTextArea 	memo_display;
    private JButton 	butt_enter;
    private JTextArea 	display_mano_y_pila;


    private Controlador 	controlador;
    private EstadoPrograma 	estadoActual;
    private EstadoPrograma	estadoAnterior;
    private Flujo 		flujoActual;
    private Flujo		flujoAnterior;

    public void iniciar() {
        this.inicioGrafico();
        this.solicitarNombre();
    }

    private void solicitarNombre() {
        this.println("Escriba su nombre de jugador.");
        this.estadoActual= EstadoPrograma.REGISTRANDO_JUGADOR;
    }

    public void inicioGrafico() { //esto crea la ventana
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PseudoConsola() {
        setFont(new Font("Monospaced", Font.PLAIN, 12));
        setTitle("Chinchón :-)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 733, 424);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 540, 319);
        contentPane.add(scrollPane);
        
                this.memo_display = new JTextArea();
                scrollPane.setViewportView(memo_display);
                memo_display.setEditable(false);
                memo_display.setFont(new Font("Monospaced", Font.PLAIN, 16));

        this.edit_input = new JTextField();
        edit_input.setFont(new Font("Monospaced", Font.PLAIN, 16));
        edit_input.setBounds(10, 339, 540, 38);
        contentPane.add(edit_input);
        edit_input.setColumns(10);

        this.butt_enter = new JButton("Enter");
        butt_enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejadorDeComandos();
                limpiarBarraComandos();
            }
        });
        butt_enter.setBounds(560, 339, 149, 38);
        contentPane.add(butt_enter);
                
                        display_mano_y_pila = new JTextArea();
                        display_mano_y_pila.setFont(new Font("Monospaced", Font.PLAIN, 14));
                        display_mano_y_pila.setBounds(560, 10, 149, 319);
                        contentPane.add(display_mano_y_pila);
                        display_mano_y_pila.setEditable(false);
                        display_mano_y_pila.setLineWrap(true);
    }

    private void manejadorDeComandos() {
	this.clear();
        switch ((EstadoPrograma) this.estadoActual) {
            default:
                break;

            case MENU:
                this.inputMenuPrincipal(inputConsola());
                break;

            case DESCARTAR:

                this.elegirDescarte(inputConsola());
                break;

            case REGISTRANDO_JUGADOR:
                this.setNombreJugador(inputConsola());
                break;

            case RECIBIENDO_JUGADA_TURNO:
                this.eligiendoMazoOPila(inputConsola());
                break;

            case ESPERANDO_TURNO:
        	println("Está jugando el jugador ["+this.controlador.getJugadorActual().getNombre()+"].");
                println("Por favor, espere su turno.");
                //Esto se vuelve a escribir por el clear que se hace de forma incondicional
                break;

            case FINALIZANDO_TURNO:
                this.eligiendoDescartarOCerrar(inputConsola());
                break;
               
            case CERRANDO_RONDA:
        	this.flujoActual.recibirInput(inputConsola());
        	break;

        }

}

    private void eligiendoDescartarOCerrar(String opcion) {
        if (opcion.equals("1")) {
            this.descartar();
        }
        else if (opcion.equals("2")) {
            print("hacer opcion cerrar juego");
            this.empezarFlujoCerrarRonda();
        }
        else {
            this.clear();
            this.println("Opción inválida.");
            this.eligirFinTurno();
        }
    }

    private void empezarFlujoCerrarRonda() {
        this.setEstadoActual(EstadoPrograma.CERRANDO_RONDA);
        this.setFlujoActual(new FlujoCerrarRonda(this));
    }

    private void elegirDescarte(String cartaDescartar) {
	if ( !(MetodosUtiles.esInt(cartaDescartar)) ){
	    this.println("Posición inválida. Ingrese la posición de su carta a descartar.");
	    return;
	}
	int nCartaDescartar = Integer.valueOf(cartaDescartar);
	
        if (nCartaDescartar==0) {
            this.eligirFinTurno();
        }
        else if ( (nCartaDescartar < 1) || (nCartaDescartar > controlador.getJugador().getMano().getCantidadCartas()) ){
            this.println("Posición inválida. Ingrese la posición de su carta a descartar.");
        }
        else {
            controlador.descartar(nCartaDescartar);
            controlador.terminarTurno();
        }

    }

    private String inputConsola() {
        return this.edit_input.getText().trim();
    }

    private void inputMenuPrincipal(String opcion) {
        if (opcion.equals("2")) {
            this.controlador.setListoParaJugar(false);
            this.println("Usted no está listo para jugar. Para prepararse, ingrese 1.");
        }
        else if (opcion.equals("1")) {
            this.controlador.setListoParaJugar(true);
            this.println("Usted está listo para jugar. Para cancelar, ingrese 2.");
            this.controlador.empezarAJugar();
        }
        else {
            this.println("Opción inválida.");
            this.mostrarMenuPrincipal();
        }
    }

    public void println(String textoAMostrar){
        this.memo_display.append(textoAMostrar + "\n");
    }
    
    public void println(int numeroAMostrar) {
	this.println(""+numeroAMostrar);
    }

    public void println() {
        this.println("");
    }

    private void print(String textoAMostrar) {
        this.memo_display.setText(this.memo_display.getText() + textoAMostrar);
    }

    private void mostrarCarta(Carta carta) {
        this.print(this.cartaAString(carta));
    }

    public void mostrarMenuPrincipal() {
        this.setEstadoActual(EstadoPrograma.MENU);
        this.println("Opciones:");
        this.println("1. Prepararse para jugar.");
        this.println("2. Salir del juego.");
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }



    public void actualizarManoYPila() {
        this.mostrarManoYPila();
    }

    private void mostrarManoYPila() {
        this.display_mano_y_pila.setText("Pila: \n"+ this.cartaAString(this.controlador.getTopePila()) + "\n" + "Su mano:");
        this.verMiMano();
    }
    private void mostrarMano(Mano manoAMostrar) {
        for (int i=1; i<=manoAMostrar.getCantidadCartas();i++) {
            print("Posición: "+String.valueOf(i)+". Carta: ");
            this.mostrarCarta(manoAMostrar.getCarta(i));
            println();
        }
    }

    private void verMiMano() {
        this.display_mano_y_pila.setText(this.display_mano_y_pila.getText() + "\n" + manoAString(this.controlador.getJugador().getMano()));
    }

    private String manoAString(Mano mano) {
        String textoSalida ="";
        for (int i=1; i<=mano.getCantidadCartas();i++) {
            textoSalida = textoSalida + String.valueOf(i) +". ";
            textoSalida = textoSalida + cartaAString(mano.getCarta(i));
            textoSalida = textoSalida + "\n";
        }
        return textoSalida;
    }

    private String cartaAString(Carta carta) {
        if (carta==null) {
            return "[NO HAY CARTA/S]";
        }
        if (carta.getPalo()==Palo.COMODIN) {
            return "[COMODÍN]";
        }
        String textoSalida= "["+String.valueOf(carta.getNumero()) +" de "+ carta.getPalo()+"]";
        return textoSalida;
    }

    public void descartar() {
        this.println("Indique la posición de su carta a descartar. 0 para volver atrás.");
        this.setEstadoActual(EstadoPrograma.DESCARTAR);
    }

    public void setNombreJugador(String nombre) {
        if ( !( esNombreValido(nombre) ) )  {
            println("Nombre inválido, pruebe con uno diferente.");
        }
        else {
            this.controlador.setJugador(nombre);
            this.println("Su nombre: ["+nombre +"] fue establecido con éxito.");
            this.setTitle("Chinchón. Jugador: "+nombre);
            this.mostrarMenuPrincipal();
        }
    }

    private boolean esNombreValido(String nombre) {
        if ((nombre==null)||(nombre.equals(""))) {
            return false;
        }
        else {
            return controlador.validarNombre(nombre);
        }
    }

    public void clear() {
        this.memo_display.setText("");
    }

    public void partidaIniciada() {
        this.clear();
        this.println("Todos los jugadores están listos para jugar.");
        this.println("Comienza la partida. Buena suerte.");
    }

    public void limpiarBarraComandos() {
        this.edit_input.setText("");
    }

    public void bloquear() {
        this.clear();
        println("Está jugando el jugador ["+this.controlador.getJugadorActual().getNombre()+"].");
        println("Por favor, espere su turno.");
        this.setEstadoActual(EstadoPrograma.ESPERANDO_TURNO);
    }

    public void jugarTurno() {
        clear();
        println("¡Es su turno de jugar!");
        println("1. Para tomar una carta del mazo.");
        println("2. Para tomar una carta de la pila de descarte.");
        this.setEstadoActual(EstadoPrograma.RECIBIENDO_JUGADA_TURNO);
    }

    private void eligiendoMazoOPila(String opcion) {
        if (opcion.equals("1")) {
            this.controlador.tomarTopeMazo();
            this.eligirFinTurno();
        }
        else if (opcion.equals("2")){
            this.controlador.tomarTopePilaDescarte();
            this.eligirFinTurno();
        }
        else {
            this.clear();
            this.println("Opción inválida. Sus opciones son:");
            this.println("1. Tomar carta del mazo.");
            this.println("2. Tomar carta de la pila de descarte.");
        }
        this.mostrarManoYPila();
    }

    private void eligirFinTurno() {
        this.println("Para finalizar su turno, puede: ");
        this.println("1. Elegir una carta a descartar.");
        this.println("2. Intentar armar sus jugadas y finalizar la mano.");
        this.setEstadoActual(EstadoPrograma.FINALIZANDO_TURNO);
    }
    
    public void setEstadoActual(EstadoPrograma nuevoEstado) {
	this.estadoAnterior	= this.estadoActual;
	this.estadoActual	= nuevoEstado;
    }

    public void volverAEstadoAnterior() {
	this.estadoActual = this.estadoAnterior;
    }
    
    public void setFlujoActual(Flujo nuevoFlujo) {
	this.flujoAnterior	= this.flujoActual;
	this.flujoActual 	= nuevoFlujo;
    }
    
    public Controlador getControlador() {
	return this.controlador;
    }
    

    public void terminarRonda() {
	this.controlador.terminarRonda();
    }

    public void mostrarPuntos() {
	this.println("El jugador ["+this.controlador.getJugadorActual().getNombre()+"] ha cerrado la ronda.");
	this.println("Los puntos restantes son:");
	for (int i=1; i<=this.controlador.getCantidadJugadores();i++) {
	    this.println("Jugador : ["+ this.controlador.getJugador(i).getNombre()+"]");
	    this.println("Puntos: "+ this.controlador.getJugador(i).getPuntos());
	}
	
    }
}
