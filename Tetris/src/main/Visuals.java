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
	private int graph_width;
	private int graph_height;
	
	/**Provide refresh rate (redraws per second)*/
	public Visuals(double refresh_rate, int grid_width, int grid_height) {
		rate = refresh_rate;
		graph_width = grid_width;
		graph_height = grid_height;
		graph = new int[grid_width][grid_height];
		this.setSize(new Dimension(300, 600));
		this.setTitle("Tetris");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	/**Draws designs, outlines, and things that don't move or change.*/
	private void drawStatics(Graphics g) {
		int scale_graph = this.getWidth()/(graph_width + 2);
		int x_graph = (this.getWidth() - (graph_width*scale_graph))/2;
		int y_graph = 50;
		
		for(int w = 0; w < graph_width; w++) {
			for(int h = 0; h < graph_height; h++) {
				g.drawRect(w*scale_graph + x_graph, h*scale_graph + y_graph, scale_graph, scale_graph);
			}
		}
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
