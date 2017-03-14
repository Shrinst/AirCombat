package Juego;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


import Graficos.PanelJuego;

import Logica.Mando;


/**
 * Contiene la estructura de la rutina cuando se está jugando
 * 
 * @author Emmanuel
 * @version 1.1.0
 * @since Tron 1.1.0
 *
 */
public class Juego extends JFrame implements Runnable {
	// atributos.
	private static final int ANCHO = 900;
	private static final int ALTO = 600;

	private static volatile boolean enFuncionamiento = false;

	private static final String NOMBRE = "Tron";

	private static int aps = 0;
	private static int fps = 0;

	private static PanelJuego render;
	private static Thread thread;

	private static Mando teclas = new Mando();

	Random rnd = new Random();

	private ImageIcon icono = new ImageIcon(this.getClass().getResource("/Imagenes/icon.png"));

	/**
	 * Crea el constructor de la clase. Se definen tamaño, título, ubicaión e
	 * icono de la ventana
	 */
	public Juego() {
		this.setPreferredSize(new Dimension(ANCHO, ALTO));
		this.setResizable(false);
		this.setSize(320, 640);
		this.setBackground(Color.DARK_GRAY);
		this.setTitle("Tron");
		this.setIconImage(icono.getImage());
		this.setLocationRelativeTo(null);
		this.add(render = new PanelJuego());
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.addKeyListener(teclas);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.iniciar();
	}

	/**
	 * Inicia el thread del bucle principal y la variable que le indica al bucle
	 * cuando iniciar y cuando terminar
	 * 
	 */
	public synchronized void iniciar() {
		enFuncionamiento = true;

		thread = new Thread(this, "Gráficos");
		thread.start();
	}

	/**
	 * Detiene el thread del bucle principal y terminar el bucle principal
	 */
	public synchronized void detener() {
		enFuncionamiento = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que actualiza: el teclado, posición de la moto y posición de la
	 * estela.
	 */
	private void actualizar() {
		render.repaint();
		aps++;
	}

	/**
	 * Método que muestra en pantalla las actualizaciones hechas por el método
	 * actualizar
	 */
	private void mostrar() {
		render.validate();
		render.repaint();
		fps++;
	}

	@Override
	/**
	 * Método que contiene el bucle principal, el cual actualiza la pantalla de
	 * juego cada cierto tiempo
	 */
	public void run() {
		final int NS_POR_SEGUNDO = 1000000000;
		final byte APS_OBJETIVO = 60;
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				this.actualizar();
				delta--;
			}

			this.mostrar();

			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				this.setTitle(NOMBRE + " || APS: " + aps + " || FPS: " + fps);
				aps = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}
}
