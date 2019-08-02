package main;

import java.util.Random;

/**
 * Manages  blocks and their position within the grid (blocks are clusters filled cells).
 * Contains methods that read and update grid.
 * */
public class BlockManager {
	
	private int[][] grid;
	
	public BlockManager(int width, int height) {
		grid = new int[width][height];
	}
	
	public int getWidth() {
		return grid.length;
	}
	
	public int getHeight() {
		return grid[0].length;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public void setGrid(int x, int y, int value) {
		grid[x][y] = value;
	}
	
	public void clearGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int z = 0; z < grid[i].length; z++) {
				grid[i][z] = 0;
			}
		}
	}
	
	private double time_switch = -1;
	
	/**Called during each update cycle.*/
	public void update() {
		//Randomly selects cells to fill, just a proof of concept
				if(Tetris.game_time - time_switch > 1) {
					this.clearGrid();
					Random r = new Random();
					this.setGrid(r.nextInt(this.getWidth()), r.nextInt(this.getHeight()), 1);
					time_switch = Tetris.game_time;
				}
	}
}
