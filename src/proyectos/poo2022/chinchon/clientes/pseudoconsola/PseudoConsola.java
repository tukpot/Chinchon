package proyectos.poo2022.chinchon.clientes.pseudoconsola;

import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;
import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.Jugador;
import proyectos.poo2022.chinchon.principal.Mano;
import proyectos.poo2022.chinchon.utilidades.MetodosUtiles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PseudoConsola extends JFrame implements IVista {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField edit_input;
    private JTextArea memo_display;
    private JButton butt_enter;
    private JTextArea display_mano_y_pila;

    private Controlador controlador;
    private EstadoPrograma estadoActual;

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
                enterPresionado();
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

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void iniciar() {
        this.inicioGrafico();
        this.setNombreJugador("Consola: " + Jugador.generarNombreAleatorio());
        this.controlador.setListoParaJugar(true);
    }

    @Override
    public void bloquear() {
        this.setEstadoActual(EstadoPrograma.ESPERANDO_TURNO);
        this.mostrarMenu(false);
    }

    @Override
    public void actualizarManoYPila() {
        this.mostrarManoYPila();
    }

    @Override
    public void tomarDeMazoOPila() {
        this.setEstadoActual(EstadoPrograma.ELIGIENDO_MAZO_O_PILA);
        this.mostrarMenu(false);
    }

    @Override
    public void descartarOCerrar() {
        this.setEstadoActual(EstadoPrograma.DESCARTAR_O_CERRAR);
        this.mostrarMenu(false);
    }

    @Override
    public void mostrarPuntos() {
        this.println("El jugador [" + this.controlador.getJugadorActual().getNombre() + "] ha cerrado la ronda.");
        this.println("Los puntos restantes son:");
        for (int i = 1; i <= this.controlador.getCantidadJugadores(); i++) {
            this.println("Jugador : [" + this.controlador.getJugador(i).getNombre() + "]");
            this.println("Puntos: " + this.controlador.getJugador(i).getPuntos());
        }
    }

    @Override
    public void perder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'perder'");
    }

    @Override
    public void ganar() {
        this.setEstadoActual(EstadoPrograma.GANO);
        this.mostrarMenu(false);
    }

    private void setEstadoActual(EstadoPrograma nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    private void solicitarNombre() {
        this.println("Escriba su nombre de jugador.");
        this.estadoActual = EstadoPrograma.REGISTRANDO_JUGADOR;
    }

    public void inicioGrafico() { // esto crea la ventana
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

    private void enterPresionado() {
        // si presionan enter, manejamos el input del usuario
        this.clear();
        switch ((EstadoPrograma) this.estadoActual) {
            default:
                break;

            case ESPERANDO_LISTO_PARA_JUGAR:
                this.cambiarListoParaJugar(inputConsola());
                break;

            case DESCARTAR_O_CERRAR:
                this.elegirDescarte(inputConsola());
                break;

            case REGISTRANDO_JUGADOR:
                this.setNombreJugador(inputConsola());
                break;

            case ELIGIENDO_MAZO_O_PILA:
                this.eligiendoMazoOPila(inputConsola());
                break;

            case ESPERANDO_TURNO:
                println("Está jugando el jugador [" + this.controlador.getJugadorActual().getNombre() + "].");
                println("Por favor, espere su turno.");
                // Esto se vuelve a escribir por el clear que se hace de forma incondicional
                break;
        }

    }

    private void elegirDescarte(String cartaDescartar) {
        if (!(MetodosUtiles.esInt(cartaDescartar))) {
            this.println("Posición inválida. Ingrese la posición de su carta a descartar.");
            return;
        }

        int nCartaDescartar = Integer.valueOf(cartaDescartar);

        if (nCartaDescartar == 0) {
            // si quiere descartar la carta 0, se interpreta como que quiere cerrar
            controlador.terminarRonda();
            return;
        }

        if (nCartaDescartar < 0 || nCartaDescartar > getCantidadCartas()) {
            this.println("Posición inválida. Ingrese la posición de su carta a descartar.");
            return;
        }

        controlador.descartar(nCartaDescartar);
    }

    private String inputConsola() {
        return this.edit_input.getText().trim();
    }

    private int getCantidadCartas() {
        return controlador.getJugador().getMano().getCantidadCartas();
    }

    private void cambiarListoParaJugar(String opcion) {
        this.clear();
        switch ((String) opcion) {
            case "1":
                this.println("Usted está listo para jugar. Para cancelar, ingrese 2.");
                this.controlador.setListoParaJugar(true);
                break;
            case "2":
                this.println("Usted no está listo para jugar. Para prepararse, ingrese 1.");
                this.controlador.setListoParaJugar(false);
                break;

            default:
                this.mostrarMenu(true);
                break;
        }
    }

    public void println(String textoAMostrar) {
        this.memo_display.append(textoAMostrar + "\n");
    }

    public void println(int numeroAMostrar) {
        this.println("" + numeroAMostrar);
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

    private void mostrarManoYPila() {
        this.display_mano_y_pila
                .setText("Pila: \n" + this.cartaAString(this.controlador.getTopePila()) + "\n" + "Su mano:");
        this.verMiMano();
    }

    private void verMiMano() {
        this.display_mano_y_pila.setText(
                this.display_mano_y_pila.getText() + "\n" + manoAString(this.controlador.getJugador().getMano()));
    }

    private String manoAString(Mano mano) {
        String textoSalida = "";
        for (int i = 1; i <= mano.getCantidadCartas(); i++) {
            textoSalida = textoSalida + String.valueOf(i) + ". ";
            textoSalida = textoSalida + cartaAString(mano.getCarta(i));
            textoSalida = textoSalida + "\n";
        }
        return textoSalida;
    }

    private String cartaAString(Carta carta) {
        if (carta == null) {
            return "[NO HAY CARTA/S]";
        }
        if (carta.getPalo() == Palo.COMODIN) {
            return "[COMODÍN]";
        }
        String textoSalida = "[" + String.valueOf(carta.getNumero()) + " de " + carta.getPalo() + "]";
        return textoSalida;
    }

    public void setNombreJugador(String nombre) {
        if (!(esNombreValido(nombre))) {
            println("Nombre inválido, pruebe con uno diferente.");
        } else {
            this.controlador.setJugador(nombre);
            this.println("Su nombre: [" + nombre + "] fue establecido con éxito.");
            this.setTitle("Chinchón. Jugador: " + nombre);
        }
    }

    private boolean esNombreValido(String nombre) {
        if ((nombre == null) || (nombre.equals(""))) {
            return false;
        } else {
            return controlador.validarNombre(nombre);
        }
    }

    private void clear() {
        this.memo_display.setText("");
    }

    private void limpiarBarraComandos() {
        this.edit_input.setText("");
    }

    private Mano getMano() {
        return this.controlador.getJugador().getMano();
    }

    private boolean puedeCerrarRonda() {
        return this.getMano().esCerrable();
    }

    private void mostrarMenu(boolean mostrarError) {
        this.clear();
        if (mostrarError)
            println("Hubo un problema. Sus opciones son:");

        switch (this.estadoActual) {
            case ESPERANDO_LISTO_PARA_JUGAR:
                this.println("Opciones:");
                this.println("1. Prepararse para jugar.");
                this.println("2. Salir del juego.");
                break;

            case ESPERANDO_TURNO:
                this.println("Está jugando el jugador [" + this.controlador.getJugadorActual().getNombre() + "].");
                this.println("Por favor, espere su turno.");
                break;

            case ELIGIENDO_MAZO_O_PILA:
                this.println("1. Tomar carta del mazo.");
                this.println("2. Tomar carta de la pila de descarte.");
                break;

            case DESCARTAR_O_CERRAR:
                this.println("Ingrese la posición de la carta que desea descartar.");
                if (puedeCerrarRonda())
                    this.println("También puede ingresar 0 si desea cerrar la ronda.");
                break;

            case REGISTRANDO_JUGADOR:
                this.println("Ingrese su nombre de jugador:");
                break;

            case GANO:
                this.println("¡GANASTE!");
                this.setEstadoActual(EstadoPrograma.GANO);
                break;

            default:
                this.println("Usted está en estado de: " + this.estadoActual);
                break;
        }
    }

    private void eligiendoMazoOPila(String opcion) {
        if (opcion.equals("1")) {
            this.controlador.tomarTopeMazo();
        } else if (opcion.equals("2")) {
            this.controlador.tomarTopePilaDescarte();
        } else {
            this.mostrarMenu(true);

        }
        this.mostrarManoYPila();
    }

}
