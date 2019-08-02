package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Handles all graphical stuff. Creates its own thread for processing, therefore it should be created
 * and initialized before the game loop is started.
 * */
public class Visuals extends JFrame implements Runnable{
	private static final long serialVersionUID = 312566061411303349L;
	public boolean update = true;
	private double rate;
	/**Array used for checking position of blocks.*/
	public int[][] graph;
	
	/**Provide refresh rate (redraws per second)*/
	public Visuals(double refresh_rate) {
		rate = refresh_rate;
		this.setSize(new Dimension(300, 450));
		this.setTitle("Tetris");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	/**Draws designs, outlines, and things that don't move or change.*/
	private void drawStatics(Graphics g) {
		
	}
	
	@Override
	public void paint(Graphics g) {
		drawStatics(g);
	}
	
	private void update() {
		this.repaint();
	}
	
	private void loop() {
		
		double delay1 = 1000/rate;
		long standard_delay = Math.round(delay1);
		long delta = System.currentTimeMillis();
		
		while(update) {
			update();
			delta = System.currentTimeMillis() - delta;
			if(delta < 0) {
				delta = 0;
			}
			if(delta > standard_delay) {
				delta = standard_delay;
			}
			try {
				Thread.sleep(standard_delay - delta);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			delta = System.currentTimeMillis();
		}
	}
	
	
	@Override
	public void run() {
		loop();
	}
}
