package proyectos.poo2022.chinchon.clientes.clienteGrafico2d;

import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Carta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteGrafico2d extends JFrame implements IVista {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JButton[] botonesCarta = new JButton[8];
    private JTextArea textAreaDebug;  // Área de texto para debug

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

        // Panel para el mazo y la pila
        JPanel panelMazoYPila = new JPanel();
        panelMazoYPila.setLayout(new GridLayout(1, 2, 5, 0));
        panelMazoYPila.setPreferredSize(new Dimension(540, 50));
        panelMazoYPila.setOpaque(false);
        panelMazoYPila.setBackground(new Color(0, 0, 0, 0));
        contentPane.add(panelMazoYPila, BorderLayout.NORTH);

        JButton botonMazo = new JButton("Mazo");
        botonMazo.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonMazo.setContentAreaFilled(false);
        botonMazo.setBorderPainted(false);
        botonMazo.setFocusPainted(false);
        botonMazo.setOpaque(false);
        botonMazo.setBackground(new Color(0, 0, 0, 0));
        botonMazo.addActionListener(e -> {
            // lógica del mazo
        });
        panelMazoYPila.add(botonMazo);

        JButton botonPila = new JButton("Pila");
        botonPila.setFont(new Font("Monospaced", Font.BOLD, 14));
        botonPila.setContentAreaFilled(false);
        botonPila.setBorderPainted(false);
        botonPila.setFocusPainted(false);
        botonPila.setOpaque(false);
        botonPila.setBackground(new Color(0, 0, 0, 0));
        botonPila.addActionListener(e -> {
            // lógica de la pila
        });
        panelMazoYPila.add(botonPila);

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

        // Panel de botones de las cartas en el tercio inferior
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 8, 2, 0));  // solo 2px de espacio horizontal
        panelBotones.setPreferredSize(new Dimension(540, 200)); // más alto que antes
        panelBotones.setOpaque(false);
        panelBotones.setBackground(new Color(0, 0, 0, 0));
        contentPane.add(panelBotones, BorderLayout.SOUTH);


        for (int i = 0; i <= 7; i++) {
            JButton boton = new JButton();
            boton.setFont(new Font("Monospaced", Font.BOLD, 14));
            boton.setContentAreaFilled(false);
//            boton.setBorderPainted(false);
            boton.setFocusPainted(false);
//            boton.setOpaque(false);
            boton.setBackground(new Color(0, 0, 0, 0));
            boton.setText(null);
            final int numeroBoton = i;
            boton.addActionListener(e -> cartaManoPresionada(numeroBoton));
            botonesCarta[i] = boton;
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
    }

    @Override
    public void setControlador(Controlador controlador) {

    }



    @Override
    public void actualizarManoYPila() {

    }

    @Override
    public void descartar() {

    }

    @Override
    public void partidaIniciada() {

    }

    @Override
    public void bloquear() {

    }

    @Override
    public void jugarTurno() {

    }

    @Override
    public void mostrarPuntos() {

    }

    public void cartaManoPresionada(int numeroCarta){
        actualizarDebug("carta presionada: "+ String.valueOf(numeroCarta));
        Carta carta = new Carta(Palo.COMODIN,50);
        dibujarCarta(numeroCarta,carta);
    }
    // Método para actualizar el área de texto de debug
    public void actualizarDebug(String mensaje) {
        textAreaDebug.setText(mensaje); // Actualiza el contenido del área de texto
    }

    public JButton getBotonCarta(int indice) {
        if (indice >= 0 && indice <= 7) {
            return botonesCarta[indice];
        } else {
            throw new IllegalArgumentException("Índice de carta fuera de rango: " + indice);
        }
    }

    public void dibujarCarta(int indice, Carta carta) {
        String nombreArchivo = "./imagenes/cartas/" + carta.getPalo().name().toLowerCase() + "-" + carta.getNumero() + ".png";
        java.net.URL url = getClass().getResource(nombreArchivo);
        JButton boton = getBotonCarta(indice);
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
            actualizarDebug("No se encontró la imagen: " + nombreArchivo);
            boton.setText("X");
        }
    }


}
