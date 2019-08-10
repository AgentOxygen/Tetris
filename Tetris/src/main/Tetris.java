package main;

import java.util.concurrent.atomic.AtomicBoolean;

public class Tetris implements Runnable{
	
	/**Creates BlockManager to be read for updating the visuals and game*/
	public BlockManager bm;
	/**Regulates game loop*/
	private final AtomicBoolean update = new AtomicBoolean(false);
	/**Sets rate, in updates per second, of update loop*/
	public double rate = 30;
	/**Creates visuals for game.*/
	public Visuals vis;
	/**Used for timing in seconds*/
	public static double game_time = 0.0;
	/**Used for debugging.*/
	public Visuals debug;
	/**Determines whether or not to display debugging window.*/
	private final boolean debugger = true;
	
	/**Creates thread for graphics processing.*/
	public Thread visuals;
	/**Creates thread for game core.*/
	public Thread game;
	/**Creates thread for debug window.*/
	public Thread debug_window;
	
	/**Creates game instance with grid size set by provided width and height variables. Use .run() to start update loop.*/
	public Tetris(int width, int height) {
		update.set(true);
		bm = new BlockManager(width, height);
		vis = new Visuals(30.0, width, height);
		game = new Thread(this);
		if(debugger) {
			debug = new Visuals(10, width, height);
			debug.debugger = true;
		}
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
		
		while(update.get()) {
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
		
		vis.stopThread();
		this.stopThread();
	}

	/**Stops thread from updating; breaks update loop.*/
	public void stopThread() {
		update.set(false);
	}
	
	@Override
	public void run() {
		visuals = new Thread(vis);
		visuals.start();
		if(debugger) {
			debug_window = new Thread(debug);
			debug_window.start();
		}
		loop();
	}
}
