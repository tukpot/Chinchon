package proyectos.poo2022.chinchon.vista.vista2D;

import proyectos.poo2022.chinchon.enumerados.EstadoPrograma;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.modelo.Carta;
import proyectos.poo2022.chinchon.modelo.Mano;
import proyectos.poo2022.chinchon.vista.common.VistaBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.RemoteException;

public class Vista2D extends VistaBase {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton[] botonesCartasMano = new JButton[8];
    private JButton botonPila;
    private JButton botonMazo;
    private JButton botonAccion;
    private JTextArea textAreaDebug;
    private JLabel labelJugadorActual;

    private EstadoPrograma estadoActual = EstadoPrograma.ESPERANDO_LISTO_PARA_JUGAR;

    public Vista2D(Controlador controlador) {
        super(controlador);
        setFont(new Font("Monospaced", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);

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
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // panel para el mazo y la pila en la parte superior
        JPanel panelMazoYPila = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        panelMazoYPila.setPreferredSize(new Dimension(540, 300));
        panelMazoYPila.setOpaque(false);
        contentPane.add(panelMazoYPila, BorderLayout.NORTH);

        botonMazo = new JButton("Mazo jijiji");
        botonMazo.setPreferredSize(new Dimension(200, 250));
        botonMazo.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonMazo.setContentAreaFilled(false);
        botonMazo.setFocusPainted(false);
        botonMazo.addActionListener(e -> {
            try {
                mazoPresionado();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        panelMazoYPila.add(botonMazo);

        botonPila = new JButton("Pila");
        botonPila.setPreferredSize(new Dimension(200, 250));
        botonPila.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonPila.setContentAreaFilled(false);
        botonPila.setFocusPainted(false);
        botonPila.addActionListener(e -> {
            try {
                pilaPresionada();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        panelMazoYPila.add(botonPila);

        // panel de acciones en el centro
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
        panelAcciones.setOpaque(false);
        panelAcciones.setPreferredSize(new Dimension(240, 100));
        contentPane.add(panelAcciones, BorderLayout.CENTER);

        labelJugadorActual = new JLabel("ESTÁ JUGANDO:");
        labelJugadorActual.setOpaque(true);
        panelAcciones.add(labelJugadorActual);

        botonAccion = new JButton("Listo para jugar");
        botonAccion.setPreferredSize(new Dimension(120, 60));
        botonAccion.addActionListener(e -> {
            try {
                botonAccionPresionado();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        panelAcciones.add(botonAccion);

        textAreaDebug = new JTextArea();
        textAreaDebug.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textAreaDebug.setEditable(false);
        textAreaDebug.setLineWrap(true);
        textAreaDebug.setWrapStyleWord(true);

        JScrollPane scrollPaneDebug = new JScrollPane(textAreaDebug);
        scrollPaneDebug.setBorder(null);
        scrollPaneDebug.getViewport().setOpaque(false);

        int debugWidth = getWidth() / 3;
        JPanel panelDebug = new JPanel(new BorderLayout());
        panelDebug.setBorder(new EmptyBorder(0, 0, 30, 0));
        panelDebug.setPreferredSize(new Dimension(debugWidth, 0));
        panelDebug.setOpaque(false);
        panelDebug.add(scrollPaneDebug, BorderLayout.CENTER);
        contentPane.add(panelDebug, BorderLayout.EAST);

        // Panel de botones de las cartas de la mano en la parte inferior
        JPanel panelBotones = new JPanel(new GridLayout(1, 8, 2, 0));
        panelBotones.setPreferredSize(new Dimension(540, 222));
        panelBotones.setOpaque(false);
        contentPane.add(panelBotones, BorderLayout.SOUTH);

        for (int i = 0; i < botonesCartasMano.length; i++) {
            JButton boton = new JButton();
            boton.setFont(new Font("Monospaced", Font.BOLD, 14));
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            final int numeroBoton = i;
            boton.addActionListener(e -> {
                try {
                    cartaManoPresionada(numeroBoton);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            botonesCartasMano[i] = boton;
            panelBotones.add(boton);
        }

        setLocationRelativeTo(null);
    }

    @Override
    public void actualizarManoYPila() throws RemoteException {
        Mano mano = this.getMano();
        for (int i = 0; i <= 7; i++) {
            JButton boton = this.getBotonCarta(i);
            Carta cartaMano = mano.getCarta(i + 1);
            this.dibujarCarta(boton, cartaMano);
        }
        printDebug("estado programa: " + this.estadoActual);
        if (this.estadoActual != EstadoPrograma.ESPERANDO_LISTO_PARA_JUGAR) {
            this.botonAccion.setText("Cerrar Ronda");
        }
        this.botonAccion
                .setEnabled(this.getMano().esCerrable() && this.estadoActual == EstadoPrograma.DESCARTAR_O_CERRAR);
        this.dibujarCarta(this.botonPila, this.getTopePila());
        this.dibujarCarta(this.botonMazo, null);
    }

    public void bloquear() throws RemoteException {
        this.labelJugadorActual.setText("Es turno del jugador: " + this.getJugadorActual().getNombre());
        printDebug("Es turno del jugador: " + this.getJugadorActual().getNombre());
    }

    @Override
    public void mostrarPuntos() throws RemoteException {
        this.printDebug("El jugador [" + this.getJugadorActual().getNombre() + "] ha cerrado la ronda.");
        for (int i = 1; i <= this.getCantidadJugadores(); i++) {
            this.printDebug("Jugador : [" + this.getJugador(i).getNombre() + "]");
            this.printDebug("Puntos: " + this.getJugador(i).getPuntos());
        }
    }

    private void cartaManoPresionada(int numeroCarta) throws RemoteException {
        if (this.estadoActual == EstadoPrograma.DESCARTAR_O_CERRAR) {
            this.descartar(numeroCarta + 1);
        }
    }

    private void botonAccionPresionado() throws RemoteException {
        // dependiendo de el estado actual del programa, hace cosas distintas
        switch ((EstadoPrograma) this.estadoActual) {
            default:

                break;

            case ESPERANDO_LISTO_PARA_JUGAR:
                this.setListoParaJugar(true);
                break;

            case DESCARTAR_O_CERRAR:
                this.terminarRonda();
                break;

        }
    }

    // Método para actualizar el área de texto de debug
    private void printDebug(String mensaje) {
        textAreaDebug.setText(textAreaDebug.getText() + mensaje + "\n");
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

    private void setEstadoActual(EstadoPrograma nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    private void mazoPresionado() throws RemoteException {
        if (this.estadoActual != EstadoPrograma.ELIGIENDO_MAZO_O_PILA)
            return;
        this.tomarTopeMazo();
    }

    private void pilaPresionada() throws RemoteException {
        if (this.estadoActual != EstadoPrograma.ELIGIENDO_MAZO_O_PILA)
            return;
        this.tomarTopePilaDescarte();
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
    public void ganar() throws RemoteException {
        this.clearDebug();
        this.printDebug("¡GANASTE!");
        this.printDebug("Estos son los puntajes finales:");
        this.mostrarPuntos();
        this.setEstadoActual(EstadoPrograma.GANO);
    }

    @Override
    public void perder() {
        this.printDebug("¡PERDISTE!");
        this.setEstadoActual(EstadoPrograma.PERDIO);
    }

    public void logicaInicioAdicional() {

    }

    public void sesionIniciada() throws RemoteException {
        setTitle("Chinchon 2D: " + this.getJugador().getNombre());
        setVisible(true);
    }
}
