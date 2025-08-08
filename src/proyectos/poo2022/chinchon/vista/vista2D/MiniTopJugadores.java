package proyectos.poo2022.chinchon.vista.vista2D;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MiniTopJugadores extends JFrame {
	private JPanel contentPane;
	private String topString;

	public MiniTopJugadores(String topString) {
		super("Top de Jugadores");
		// Convertir saltos de línea en <br>
		String topHtml = "<html>" + topString.replace("\n", "<br>") + "</html>";
		this.topString = topHtml;
		prepararPantalla();
	}

	public void prepararPantalla() {
		// pantalla principal y su fondo
		this.contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					Image backgroundImage = new ImageIcon(getClass().getResource("./imagenes/fondo-top-ganadores.jpg"))
							.getImage();
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

		// label transparente para mostrar el top
		JLabel labelTop = new JLabel(this.topString, JLabel.CENTER); // setear texto del constructor
		labelTop.setOpaque(false); // Fondo transparente
		labelTop.setFont(new Font("SansSerif", Font.BOLD, 40));
		labelTop.setForeground(java.awt.Color.WHITE); // texto visible sobre fondo
		contentPane.add(labelTop, BorderLayout.CENTER);

		setContentPane(contentPane);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
