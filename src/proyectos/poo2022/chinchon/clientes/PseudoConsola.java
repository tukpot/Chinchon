package proyectos.poo2022.chinchon.clientes;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyectos.poo2022.chinchon.enumerados.EstadosPrograma;
import proyectos.poo2022.chinchon.enumerados.Palo;
import proyectos.poo2022.chinchon.interactuar.Controlador;
import proyectos.poo2022.chinchon.interactuar.IVista;
import proyectos.poo2022.chinchon.principal.Carta;
import proyectos.poo2022.chinchon.principal.Mano;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class PseudoConsola extends JFrame implements IVista {

	private JPanel 		contentPane;
	private JTextField 	edit_input;
	private JTextArea 	memo_display;
	private JButton 	butt_enter;
	private JTextArea 	display_mano_y_pila;
	
	
	private Controlador controlador;
	private EstadosPrograma estadoActual;// = EstadosPrograma.REGISTRANDO_JUGADOR;
	

	public void iniciar() {
		this.inicioGrafico();
		this.solicitarNombre();
	}
	
	private void solicitarNombre() {
		this.println("Escriba su nombre de jugador.");
		this.estadoActual= EstadosPrograma.REGISTRANDO_JUGADOR;
	}

	public void inicioGrafico() { //esto era main
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//PseudoConsola frame = new PseudoConsola();
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
		setBounds(100, 100, 650, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 475, 269);
		contentPane.add(scrollPane);
		
		this.memo_display = new JTextArea();
		scrollPane.setViewportView(memo_display);
		memo_display.setEditable(false);
		memo_display.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		this.edit_input = new JTextField();
		edit_input.setFont(new Font("Monospaced", Font.PLAIN, 10));
		edit_input.setBounds(10, 291, 475, 21);
		contentPane.add(edit_input);
		edit_input.setColumns(10);
		
		this.butt_enter = new JButton("Enter");
		butt_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    manejadorDeComandos();
			    limpiarBarraComandos();
			}
		});
		butt_enter.setBounds(495, 291, 134, 21);
		contentPane.add(butt_enter);
		
		display_mano_y_pila = new JTextArea();
		display_mano_y_pila.setEditable(false);
		display_mano_y_pila.setLineWrap(true);
		display_mano_y_pila.setBounds(495, 10, 131, 269);
		contentPane.add(display_mano_y_pila);
	}
	
	private void manejadorDeComandos() {
		switch ((EstadosPrograma) this.estadoActual) {
			default:
				break;
				
			case MENU:
				this.inputMenuPrincipal(inputConsola());
				break;
		
			case DESCARTAR:
				this.elegirDescarte(Integer.parseInt(inputConsola().trim()));
				break;
				
			case REGISTRANDO_JUGADOR:
				this.setNombreJugador(inputConsola().trim());
				break;
			
			case RECIBIENDO_JUGADA_TURNO:
			    this.eligiendoMazoOPila(inputConsola().trim());
			    break;
			    
			case ESPERANDO_TURNO:
			    println("Por favor espere su turno.");
			    break;
			    
			case FINALIZANDO_TURNO:
			    this.eligiendoDescartarOCerrar(inputConsola().trim());
			    break;
		
		}
		
	}
	
	private void eligiendoDescartarOCerrar(String opcion) {
	    if (opcion.equals("1")) {
		this.descartar();
	    }
	    else if (opcion.equals("2")) {
		print("hacer opcion cerrar juego");
		this.cerrarJuego();
	    }
	    else {
		this.println("Opción inválida.");
		this.eligirFinTurno();
	    }
	}

	private void cerrarJuego() {
	    // TODO Auto-generated method stub
	}

	private void elegirDescarte(int nCartaDescartar) {
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

	private void println(String textoAMostrar){
	    this.memo_display.append(textoAMostrar + "\n");
	}
	
	private void println() {
	    this.println("");
	}
	
	private void print(String textoAMostrar) {
	    this.memo_display.setText(this.memo_display.getText() + textoAMostrar);
	}
	
	private void mostrarCarta(Carta carta) {
	    this.print(this.cartaAString(carta));
	}
	
	public void mostrarMenuPrincipal() {
		this.estadoActual = EstadosPrograma.MENU;
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
	    this.display_mano_y_pila.setText("Pila: "+ this.cartaAString(this.controlador.getTopePila()) + "\n" + "Su mano:");
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
	    this.estadoActual = EstadosPrograma.DESCARTAR;		
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
	    this.estadoActual = EstadosPrograma.ESPERANDO_TURNO;
	}

	public void jugarTurno() {
	    clear();
	    println("¡Es su turno de jugar!");
	    println("1. Para tomar una carta del mazo.");
	    println("2. Para tomar una carta de la pila de descarte.");
	    this.estadoActual = EstadosPrograma.RECIBIENDO_JUGADA_TURNO;
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
	    this.clear();
	    this.println("Para finalizar su turno, puede: ");
	    this.println("1. Elegir una carta a descartar.");
	    this.println("2. Intentar armar sus jugadas y finalizar la mano.");
	    this.estadoActual= EstadosPrograma.FINALIZANDO_TURNO;
	}
}
