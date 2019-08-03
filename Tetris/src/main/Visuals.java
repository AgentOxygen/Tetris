package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Handles all graphical stuff. Creates its own thread for processing, therefore it should be created
 * and initialized before the game loop is started.
 * */
public class Visuals extends JFrame implements Runnable{
	private static final long serialVersionUID = 312566061411303349L;
	
	/**Regulates visuals loop*/
	private final AtomicBoolean update = new AtomicBoolean(false);
	
	/**Controls whether or not to display ID numbers of each block*/
	public boolean show_numbers = true;
	
	private double rate;
	/**Array used for checking position of blocks.*/
	public int[][] graph;
	private int graph_width;
	private int graph_height;
	
	private JPanel panel = new JPanel() {;
		private static final long serialVersionUID = -1218536342922173362L;

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		drawStatics(g);
	}
	};
	
	/**Provide refresh rate (redraws per second)*/
	public Visuals(double refresh_rate, int grid_width, int grid_height) {
		rate = refresh_rate;
		graph_width = grid_width;
		graph_height = grid_height;
		graph = new int[grid_width][grid_height];
		update.set(true);
		
		this.setContentPane(panel);
		this.setSize(new Dimension(300, 600));
		this.setTitle("Tetris");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	/**Draws designs, outlines, and things that don't move within visuals thread.*/
	private void drawStatics(Graphics g) {
		int scale_graph = this.getWidth()/(graph_width + 2);
		int x_graph = (this.getWidth() - (graph_width*scale_graph))/2;
		int y_graph = 15;
		
		for(int w = 0; w < graph_width; w++) {
			for(int h = 0; h < graph_height; h++) {
				g.setColor(Color.black);
				if(graph[w][h] == 0) {
					g.drawRect((graph.length - w - 1)*scale_graph + x_graph, (graph[0].length - h)*scale_graph + y_graph, scale_graph, scale_graph);
				}else {
					g.fillRect((graph.length - w - 1)*scale_graph + x_graph, (graph[0].length - h)*scale_graph + y_graph, scale_graph, scale_graph);
					//Writes ID numbers to cells
					if(show_numbers) {
						g.setColor(Color.red);
						g.drawString(Integer.toString(graph[w][h]), (graph.length - w - 1)*scale_graph + x_graph + scale_graph/2, 
								(graph[0].length - h)*scale_graph + y_graph + scale_graph/2);
					}
				}
			}
		}
	}
	
	private void update() {
		this.repaint();
	}
	
	private void loop() {
		
		double delay1 = 1000/rate;
		long standard_delay = Math.round(delay1);
		long delta = System.currentTimeMillis();
		
		while(update.get()) {
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
	
	/**Stops thread from updating; breaks update loop.*/
	public void stopThread() {
		update.set(false);
	}
	
	@Override
	public void run() {
		loop();
	}
}
