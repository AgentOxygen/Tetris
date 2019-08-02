package main;

import java.util.Random;

public class Tetris implements Runnable{
	
	/**Creates grid to be read for updating the visuals.
	 * 0 draws a blank square
	 * 1 draws a black-filled square
	 * */
	public int[][] grid;
	/**Dictates width of grid*/
	public int width;
	/**Dictates height of grid*/
	public int height;
	/*Regulates game loop**/
	public boolean update = true;
	/**Sets rate, in updates per second, of update loop*/
	public double rate = 30;
	/**Creates visuals for game.*/
	public Visuals vis;
	/*Used for timing in seconds**/
	public double game_time = 0.0;
	/**Creates game instance with grid size set by provided width and height variables. Use .run() to start update loop.*/
	public Tetris(int width_, int height_) {
		width = width_;
		height = height_;
		grid = new int[width][height];
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				grid[w][h] = 0;
			}
		}
		vis = new Visuals(30.0, width, height);
		Thread game = new Thread(this);
		game.start();
	}
	
	private double time_switch = 0;
	
	/**Called during each update.*/
	public void update() {
		
		//Randomly selects cells to fill, just a proof of concept
		if(game_time - time_switch >= 0) {
			Random r = new Random();
			grid[r.nextInt(width)][r.nextInt(height)] = 1;
			time_switch = game_time;
		}
		
		//Update visuals
		vis.graph = grid;
	}
	
	private void loop() {
		double delay1 = 1000/rate;
		long standard_delay = Math.round(delay1);
		long delta = System.currentTimeMillis();
		
		while(update) {
			update();
			delta = System.currentTimeMillis() - delta;
			game_time = game_time + ((delay1 - delta)/1000.0);
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
		Thread visuals = new Thread(vis);
		visuals.start();
		loop();
	}
}
