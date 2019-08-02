package main;

public class Tetris implements Runnable{
	
	/**Creates BlockManager to be read for updating the visuals and game*/
	public BlockManager bm;
	/**Regulates game loop*/
	public boolean update = true;
	/**Sets rate, in updates per second, of update loop*/
	public double rate = 30;
	/**Creates visuals for game.*/
	public Visuals vis;
	/*Used for timing in seconds**/
	public static double game_time = 0.0;
	/**Creates game instance with grid size set by provided width and height variables. Use .run() to start update loop.*/
	public Tetris(int width, int height) {
		bm = new BlockManager(width, height);
		vis = new Visuals(30.0, width, height);
		Thread game = new Thread(this);
		game.start();
	}
	
	/**Called during each update.*/
	public void update() {
		
		//Update Block Manager
		bm.update();
		
		//Update visuals
		vis.graph = bm.getGrid();
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
