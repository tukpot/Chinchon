package proyectos.poo2022.chinchon.clientes.clienteGrafico2d;

import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;
import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.Jugador;
import proyectos.poo2022.chinchon.principal.Mano;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteGrafico2d extends JFrame implements IVista {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton[] botonesCartasMano = new JButton[8];
    private JButton botonPila;
    private JButton botonMazo;
    private JButton botonCerrarRonda;
    private JTextArea textAreaDebug;
    private JLabel labelJugadorActual;

    private EstadoPrograma estadoActual;
    private Controlador controlador;

    public ClienteGrafico2d() {
        setFont(new Font("Monospaced", Font.PLAIN, 12));
        setTitle("Chinchón :-)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 733, 424);

        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = new ImageIcon(getClass().getResource("./imagenes/fondo.jpg")).getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        contentPane.setOpaque(false);
        contentPane.setBackground(new Color(0, 0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Panel para el mazo y la pila en la parte superior
        JPanel panelMazoYPila = new JPanel();
        panelMazoYPila.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20)); // Espaciado horizontal controlado
        panelMazoYPila.setPreferredSize(new Dimension(540, 300));
        panelMazoYPila.setOpaque(false);
        panelMazoYPila.setBackground(new Color(0, 0, 0, 0));
        contentPane.add(panelMazoYPila, BorderLayout.NORTH);

        // Botón Mazo
        JButton botonMazo = new JButton("Mazo jijiji");
        this.botonMazo = botonMazo;
        botonMazo.setPreferredSize(new Dimension(200, 250)); // Controlar tamaño sin estirar
        botonMazo.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonMazo.setContentAreaFilled(false);
        botonMazo.setFocusPainted(false);
        botonMazo.setBackground(new Color(0, 0, 0, 0));
        botonMazo.addActionListener(e -> {
            mazoPresionado();
        });
        panelMazoYPila.add(botonMazo);

        // Botón Pila
        JButton botonPila = new JButton("Pila");
        this.botonPila = botonPila;
        botonPila.setPreferredSize(new Dimension(200, 250)); // Misma lógica que el mazo
        botonPila.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonPila.setContentAreaFilled(false);
        botonPila.setFocusPainted(false);
        botonPila.setBackground(new Color(0, 0, 0, 0));
        botonPila.addActionListener(e -> {
            // lógica de la pila
            pilaPresionada();
        });
        panelMazoYPila.add(botonPila);

        // panel botoncitos cosillas
        JPanel panelAcciones = new JPanel();
        contentPane.add(panelAcciones, BorderLayout.CENTER);
        panelAcciones.setOpaque(false);
        panelAcciones.setBackground(new Color(0, 0, 0, 0));

        panelAcciones.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20)); // Espaciado horizontal controlado
        panelAcciones.setPreferredSize(new Dimension(240, 100));

        JButton botonCerrarRonda = new JButton("Cerrar Ronda");
        botonCerrarRonda.addActionListener(e -> this.terminarRonda());
        botonCerrarRonda.setPreferredSize(new Dimension(120, 60));
        this.botonCerrarRonda = botonCerrarRonda;

        JLabel labelJugadorActual = new JLabel("ESTÁ JUGANDO:");
        this.labelJugadorActual = labelJugadorActual;
        labelJugadorActual.setOpaque(true);
        panelAcciones.add(labelJugadorActual);
        panelAcciones.add(botonCerrarRonda);

        // Panel de texto de Debug
        JPanel panelDebug = new JPanel();
        panelDebug.setLayout(new BorderLayout());
        panelDebug.setPreferredSize(new Dimension(150, 50));
        panelDebug.setOpaque(false);
        panelDebug.setBackground(new Color(0, 0, 0, 0));
        contentPane.add(panelDebug, BorderLayout.EAST);

        textAreaDebug = new JTextArea();
        textAreaDebug.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textAreaDebug.setEditable(false);
        textAreaDebug.setLineWrap(true);
        textAreaDebug.setWrapStyleWord(true);

        JScrollPane scrollPaneDebug = new JScrollPane(textAreaDebug);
        scrollPaneDebug.setOpaque(false);
        scrollPaneDebug.getViewport().setOpaque(false);
        scrollPaneDebug.setBorder(null);
        panelDebug.add(scrollPaneDebug, BorderLayout.CENTER);

        // Panel de botones de las cartas de la mano
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 8, 2, 0));
        panelBotones.setPreferredSize(new Dimension(540, 200));
        panelBotones.setOpaque(false);
        panelBotones.setBackground(new Color(0, 0, 0, 0));
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        for (int i = 0; i <= 7; i++) {
            JButton boton = new JButton();
            boton.setFont(new Font("Monospaced", Font.BOLD, 14));
            boton.setContentAreaFilled(false);
            // boton.setBorderPainted(false);
            boton.setFocusPainted(false);
            // boton.setOpaque(false);
            boton.setBackground(new Color(0, 0, 0, 0));
            boton.setText(null);
            final int numeroBoton = i;
            boton.addActionListener(e -> cartaManoPresionada(numeroBoton));
            botonesCartasMano[i] = boton;
            panelBotones.add(boton);
        }

        setSize(new Dimension(800, 800));
        setLocationRelativeTo(null);
    }

    public void iniciar() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.setNombreJugador("2D: " + Jugador.generarNombreAleatorio());
        this.controlador.setListoParaJugar(true);
        this.controlador.empezarAJugar();
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void actualizarManoYPila() {
        Mano mano = this.getMano();
        for (int i = 0; i <= 7; i++) {
            JButton boton = this.getBotonCarta(i);
            Carta cartaMano = mano.getCarta(i + 1);
            this.dibujarCarta(boton, cartaMano);
        }
        printDebug("estado programa: " + this.estadoActual);
        this.botonCerrarRonda
                .setEnabled(this.getMano().esCerrable() && this.estadoActual == EstadoPrograma.DESCARTAR_O_CERRAR);
        this.dibujarCarta(this.botonPila, this.controlador.getTopePila());
        this.dibujarCarta(this.botonMazo, null);
    }


    public void bloquear() {
        this.labelJugadorActual.setText("Es turno del jugador: " + this.controlador.getJugadorActual().getNombre());
        printDebug("Es turno del jugador: " + this.controlador.getJugadorActual().getNombre());
    }

    @Override
    public void mostrarPuntos() {
        this.clearDebug();
        this.printDebug("El jugador [" + this.controlador.getJugadorActual().getNombre() + "] ha cerrado la ronda.");
        for (int i = 1; i <= this.controlador.getCantidadJugadores(); i++) {
            this.printDebug("Jugador : [" + this.controlador.getJugador(i).getNombre() + "]");
            this.printDebug("Puntos: " + this.controlador.getJugador(i).getPuntos());
        }
    }

    private void cartaManoPresionada(int numeroCarta) {
        if (this.estadoActual == EstadoPrograma.DESCARTAR_O_CERRAR) {
            controlador.descartar(numeroCarta + 1);
        }
    }

    // Método para actualizar el área de texto de debug
    private void printDebug(String mensaje) {
        textAreaDebug.setText(textAreaDebug.getText() + "\n" + mensaje);
    }

    private void clearDebug() {
        textAreaDebug.setText("");
    }

    private JButton getBotonCarta(int indice) {
        if (indice >= 0 && indice <= 7) {
            return botonesCartasMano[indice];
        } else {
            throw new IllegalArgumentException("Índice de carta fuera de rango: " + indice);
        }
    }

    private void dibujarCarta(JButton boton, Carta carta) {
        String nombreArchivo;
        if (carta == null) {
            nombreArchivo = "./imagenes/cartas/volteada.png";
        } else {
            nombreArchivo = "./imagenes/cartas/" + carta.getImageName();
        }

        java.net.URL url = getClass().getResource(nombreArchivo);
        int ancho = boton.getWidth();
        int alto = boton.getHeight();

        if (ancho <= 0 || alto <= 0) {
            ancho = 90;
            alto = 130;
        }

        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            boton.setText("");
            boton.setIcon(new ImageIcon(imagenEscalada));
        } else {
            printDebug("No se encontró la imagen: " + nombreArchivo);
            boton.setText("X");
        }
    }

    private void setNombreJugador(String nombre) {
        this.controlador.setJugador(nombre);
        this.setTitle(nombre);
    }

    private void setEstadoActual(EstadoPrograma nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    private void mazoPresionado() {
        printDebug("mazo presionado");
        if (this.estadoActual != EstadoPrograma.ELIGIENDO_MAZO_O_PILA)
            return;
        this.controlador.tomarTopeMazo();
    }

    private void pilaPresionada() {
        printDebug("pila presionada");
        if (this.estadoActual != EstadoPrograma.ELIGIENDO_MAZO_O_PILA)
            return;
        this.controlador.tomarTopePilaDescarte();
    }

    private void terminarRonda() {
        printDebug("terminar ronda presionado");
        if (this.estadoActual == EstadoPrograma.DESCARTAR_O_CERRAR) {
            controlador.terminarRonda();
        }
    }

    private Mano getMano() {
        return this.controlador.getJugador().getMano();
    }

    @Override
    public void tomarDeMazoOPila() {
        printDebug("TU TURNO");
        this.labelJugadorActual.setText("¡ES TU TURNO!");
        this.setEstadoActual(EstadoPrograma.ELIGIENDO_MAZO_O_PILA);
    }

    @Override
    public void descartarOCerrar() {
        clearDebug();
        printDebug("Seleccione la carta que desea descartar o presione el botón para cerrar la ronda");
        this.estadoActual = EstadoPrograma.DESCARTAR_O_CERRAR;
    }

    @Override
    public void perder() {
        this.printDebug("PERDISTE!!!");
        this.setEstadoActual(EstadoPrograma.PERDIO);
        // PRINTEAR LISTA DE GANADORES DE OTRAS PARTIDAS
        this.controlador = null; // esto actúa como kick

    }

}
