package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

public class GUI extends JFrame {

	private static final long serialVersionUID = 4753952608180252847L;

	public GUI() {
		super("Tic Tac Toe");
		this.getContentPane();
		try {
			Image icon = ImageIO.read(getClass().getResource("/tictactoe.png"));
			this.setIconImage(icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Panel tPanel = new Panel();
		this.add(tPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // Ajusta el tamaño de la ventana
		this.setVisible(true);
	}

}

class Panel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6904185910328873802L;

	// Variables del juego
	private boolean hasUpdatedWinCount = false; 
	private int lastWinner = -1;
	boolean playerX; // Indica si es el turno del jugador X
	boolean gameDone = false; // Indica si el juego ha terminado
	int winner = -1; // Almacena el ganador (1 para X, 2 para O, 3 para empate)
	int player1wins = 0, player2wins = 0; // Contadores de victorias
	int[][] board = new int[3][3]; // Tablero del juego

	// Variables para pintar
	int offset = 95; // Ancho del cuadrado
	JButton boton; // Botón para reiniciar el juego

	// Colores
	Color azulMarino = new Color(0x0bdab);
	Color white = new Color(0xf7f7f7);
	Color darkGray = new Color(0x3f3f44);

	// Imágenes
	Image xImg; // Imagen para X
	Image oImg; // Imagen para O

	public Panel() {
		Dimension size = new Dimension(420, 300); // Tamaño del panel
		this.setPreferredSize(size);

		boton = new JButton("Play Again?"); // Botón para reiniciar el juego
		boton.addActionListener(this);
		boton.setBounds(318, 220, 50, 50);
		boton.setVisible(false); // Inicialmente oculto
		this.add(boton);

		this.addMouseListener(new XOListener()); // Añadir un listener para los clics del ratón

		// Cargar imágenes
		try {
			xImg = ImageIO.read(getClass().getResource("/xIcon.png")).getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			oImg = ImageIO.read(getClass().getResource("/oIcon.png"));
		} catch (IOException e) {
			System.out.println("Error loading images: " + e.getMessage());
		}

		resetGame(); // Inicializar el juego
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame(); // Reiniciar el juego al hacer clic en el botón
		repaint(); // Volver a pintar el tablero para reiniciar el juego
	}

	private void resetGame() {

		playerX = (lastWinner != 1); // que el jugador que pierde comience la siguiente partida

		winner = -1; // No hay ganador al inicio
		hasUpdatedWinCount = false; // Reiniciar el conteo de victorias
		gameDone = false; // El juego no ha terminado
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = 0; // Reiniciar el tablero
			}
		}
		boton.setVisible(false); // Ocultar el botón al jugar
		
	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		drawBoard(page); // Dibujar el tablero
		drawUI(page); // Dibujar la interfaz de usuario
		drawGame(page); // Dibujar el estado del juego
	}

	private void drawGame(Graphics page) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 1) {
					page.drawImage(xImg, 30 + offset * j, 30 + offset * i, null); // Dibujar X
				} else if (board[i][j] == 2) {
					page.drawImage(oImg, 30 + offset * j, 30 + offset * i, null); // Dibujar O
				}
			}
		}

		// Verificar si hay un ganador
		checkWinner();
	}

	private void checkWinner() {
	    winner = 0; // No hay ganador inicialmente

	    //Comprobar filas por si hay secuencia ganadora
	    for (int i = 0; i < 3; i++) {
	        if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
	            winner = board[i][0]; // Hay un ganador en filas
	            break; // Salir del bucle
	        }
	        if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
	            winner = board[0][i]; // Hay un ganador en columnas
	            break; // Salir del bucle
	        }
	    }

	    // Comprobar diagonales por si hay secuencia ganador
	    if (winner == 0) { // Solo comprobar si no hay ganador aún
	        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
	            winner = board[0][0]; // Hay un ganador en la diagonal
	        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
	            winner = board[0][2]; // Hay un ganador en la otra diagonal
	        }
	    }

	    // Verificar si hay un empate
	    boolean draw = true; // Inicializar como empate
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            if (board[i][j] == 0) {
	                draw = false; // No es un empate si hay al menos un espacio vacío
	            }
	        }
	    }

	    // Manejar el estado del juego usando switch
	    switch (winner) {
	    case 0: // No hay ganador aún
            // Verificar si hay un empate
            if (draw) {
                winner = 3; // Indicar un empate
                gameDone = true; // El juego ha terminado
                boton.setVisible(true); // Mostrar botón para reiniciar
            }
            break;
	        case 1: // Jugador X gana
	        case 2: // Jugador O gana
	            gameDone = true; // El juego ha terminado
	            lastWinner = winner; // Guardar el último ganador
	            // Solo actualizar si no se ha hecho antes
	            if (!hasUpdatedWinCount) { 
	                updateWinCount(); // Actualizar contador de victorias
	                hasUpdatedWinCount = true; // Marcar que se ha actualizado el conteo
	            }
	            boton.setVisible(true); // Mostrar botón para reiniciar
	            break;
	        
	       
	            
	    }}
	private void updateWinCount() {
		if (winner == 1) {
			player1wins++; // Incrementar victorias del jugador X
			lastWinner = 1; // actualizar ganador
		} else {
			player2wins++; // Incrementar victorias del jugador O
			lastWinner = 2; // actualizar ganador
		}
	}

	private void drawUI(Graphics page) {
		// Establecer color y fuente
		page.setColor(darkGray);
		page.fillRect(300, 0, 120, 300);
		Font font = new Font("Helvetica", Font.PLAIN, 20);
		page.setFont(font);

		// Establecer contador de victorias
		page.setColor(white);
		page.drawString("Win Count", 310, 30);
		page.drawString(": " + player1wins, 362, 70);
		page.drawString(": " + player2wins, 362, 105);

		// Dibujar puntaje X
		page.drawImage(xImg.getScaledInstance(27, 27, BufferedImage.SCALE_SMOOTH), 329, 47, null);
		// Dibujar puntaje O
		page.drawImage(oImg.getScaledInstance(27, 27, BufferedImage.SCALE_SMOOTH), 330, 85, null);

		// Dibujar turno del jugador
		page.setColor(white);
		Font font1 = new Font("Serif", Font.ITALIC, 18);
		page.setFont(font1);

		if (gameDone) {
			String winnerMessage = ""; // Mensaje del ganador
			String playerMessage = ""; // Mensaje del jugador

			if (winner == 1) {
				winnerMessage = "Winner:"; // Mensaje de ganador
				playerMessage = "Player X"; // Ganador es el jugador X
			} else if (winner == 2) {
				winnerMessage = "Winner:"; // Mensaje de ganador
				playerMessage = "Player O"; // Ganador es el jugador O
			} else if (winner == 3) {
				winnerMessage = "DRAW"; // Hay un empate
			}

			// Dibujar mensaje del ganador
			page.drawString(winnerMessage, 330, 150); // Mensaje del ganador
			if (winner != 3) { // Solo dibujar el mensaje del jugador si no hay empate
				page.drawString(playerMessage, 330, 180); // muestra el ganador

			}

			// Hacer visible el botón y posicionarlo
			boton.setVisible(true); // Mostrar botón para reiniciar
			boton.setBounds(310, 210, 100, 30); // Establecer posición y tamaño del botón

		} else {
			Font font2 = new Font("Serif", Font.ITALIC, 20);
			page.setFont(font2);
			page.drawString("Turn: ", 320, 200);
			if (playerX) {
				page.drawString("Player X", 318, 220); // Indicar que es el turno del jugador X
			} else {
				page.drawString("Player O", 318, 220); // Indicar que es el turno del jugador O
			}
		}
	}

	private void drawBoard(Graphics page) {
		this.setBackground(azulMarino); // Establecer color de fondo

		page.setColor(darkGray); // Establecer color para las líneas del tablero

		// Dibujar líneas horizontales
		page.fillRect(20, 100, 270, 5); // Primera línea horizontal
		page.fillRect(20, 195, 270, 5); // Segunda línea horizontal

		// Dibujar líneas verticales
		page.fillRect(110, 10, 5, 270); // Primera línea vertical
		page.fillRect(205, 10, 5, 270); // Segunda línea vertical
	}

	private class XOListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!gameDone) { // Solo permitir clics si el juego no ha terminado
				int x = e.getX() / offset; // Coordenada x del clic
				int y = e.getY() / offset; // Coordenada y del clic

				if (board[y][x] == 0) { // Verificar si la celda está vacía
					board[y][x] = playerX ? 1 : 2; // Asignar la marca del jugador actual
					playerX = !playerX; // Cambiar de jugador
					repaint(); // Actualizar la pantalla
				}
			}
		}
	}
}
