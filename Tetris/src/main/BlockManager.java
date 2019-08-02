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
	
	/**Returns width of grid.*/
	public int getWidth() {
		return grid.length;
	}
	
	/**Returns height of grid*/
	public int getHeight() {
		return grid[0].length;
	}
	
	/**Returns grid*/
	public int[][] getGrid() {
		return grid;
	}
	
	/**Sets value of specified cell within grid*/
	public void setGrid(int x, int y, int value) {
		grid[x][y] = value;
	}
	
	/**Sets value of every cell within the grid equal to '0'*/
	public void clearGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int z = 0; z < grid[i].length; z++) {
				grid[i][z] = 0;
			}
		}
	}
	
	private double time_switch = -1;
	
	/**Should be called during each update cycle. Updates block manager's grid.*/
	public void update() {
		//Randomly selects cells to fill every 1 second, just a proof of concept
				if(Tetris.game_time - time_switch > 1) {
					this.clearGrid();
					Random r = new Random();
					this.setGrid(r.nextInt(this.getWidth()), r.nextInt(this.getHeight()), 1);
					time_switch = Tetris.game_time;
				}
	}
}
