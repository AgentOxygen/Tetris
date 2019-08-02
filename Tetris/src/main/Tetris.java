package main;

public class Tetris implements Runnable{
	
	/**Creates grid to be read for updating the graphics.
	 * 'Empty' draws a blank square
	 * 'fill_black' draws a black-filled square
	 * */
	public String[][] grid;
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
	
	/**Creates game instance with grid size set by provided width and height variables. Use .run() to start update loop.*/
	public Tetris(int width, int height) {
		grid = new String[width][height];
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				grid[w][h] = "Empty";
			}
		}
		vis = new Visuals(30.0);
	}
	
	/**Called during each update.*/
	public void update() {
		
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
