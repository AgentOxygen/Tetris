package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages  blocks and their position within the grid (blocks are clusters filled cells).
 * Contains methods that read and update grid.
 * */
public class BlockManager implements KeyListener{
	
	/**Grid is coded using '0' as empty and anything greater than 0 as filled.
	 * Positive numbers group units together into blocks.
	 * */
	private int[][] grid;
	/**Grid stored above the visible grid, where blocks are added and descend.*/
	private int[][] pregrid;
	/**Stores of if previous block landed and next block is needed.*/
	private boolean next_block = true;
	int y_count; //This variable follows block y position correctly.
	
	/**Keeps track of highest possible ID for blocks*/
	public int highest_id = 2;
	
	public BlockManager(int width, int height) {
		grid = new int[width][height];
		pregrid = new int[width][height];
		y_count = height;
		clearPreGrid();
	}
	
	/**Returns width of grid.*/
	public int getWidth() {
		return grid.length;
	}
	
	/**Returns height of grid*/
	public int getHeight() {
		return grid[0].length;
	}
	
	/**Reads grid and returns value of specified cell*/
	public int readGrid(int x, int y) {
		return grid[x][y];
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
	
	/**Sets value of every cell within the pre-grid equal to '0'*/
	public void clearPreGrid() {
		for(int i = 0; i < pregrid.length; i++) {
			for(int z = 0; z < pregrid[i].length; z++) {
				pregrid[i][z] = 0;
			}
		}
	}
	
	/**Shifts blocks down from pre-grid into grid and moves all blocks in grid down one unit.
	 * If a block collides with either the first layer or another block, then it stops and
	 * the ID is changed to '1'
	 * */
	public void descendGrid() {
		
		//Create new grid to eventually replace the old one.
		int[][] n_grid = grid;
		
		//List of ID's that need to be merged (block converted to '1').
		ArrayList<Integer> merge = new ArrayList<Integer>();
		
		//Descends blocks in grid
		for(int i = 1; i <= highest_id; i++) {
			for(int x = 0; x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					//Identify the correct cell, merge if necessary.
					if(!merge.contains(grid[x][y])) {
						if(grid[x][y] == i) {
							//Check cell below it, determine if free or non-existent or filled.
							try {
								// This if checks open space beneath block, and moves it down if needed.
								if((y_count>1) ) {
									if((grid[x][y-1] == 0) ) {
										n_grid[x][y-1] = i;
										grid[x][y] = 0;
										y_count--;
										//Falling
									}
								}else {
									next_block = true;
									y_count = this.getHeight();
									merge.add(i);
									//Landed
								}
							}catch(IndexOutOfBoundsException e){
								System.out.println(e.getLocalizedMessage());
							}
						}
					}else {
						//Merging into '1'
						n_grid[x][y] = 1;
					}
				}
			}
		}
		
		//Adds low-y position cells in pre-grid into top of grid
		for(int x = 0; x < pregrid.length; x++) {
			n_grid[x][n_grid[0].length - 1] = pregrid[x][0];
			pregrid[x][0] = 0;
		}
		
		//Shifts pre-grid blocks down
		int[][] np_grid = pregrid;
		for(int x = 0; x < pregrid.length; x++) {
			for(int y = 1; y < pregrid[0].length; y++) {
				pregrid[x][y] = np_grid[x][y - 1];
			}
		}
		
		grid = n_grid;
	}
	
	private double time_switch = -1;
	private double time_switch2 = -3;
	
	/**Should be called during each update cycle. Updates block manager's grid.*/
	public void update() {
		//Randomly selects cells to fill every 1 second, just a proof of concept
		if(Tetris.game_time - time_switch > 1) {
			descendGrid();
			time_switch = Tetris.game_time;
		}
		//Generates a new block every 3 seconds and current block has landed
		if(Tetris.game_time - time_switch2 > 3 && next_block == true) {
			generateBlock(1, 1);
			next_block = false;
			time_switch2 = Tetris.game_time;
		}
	}
	
	/**Creates block with specified number of cells. Scale adjusts space taken up by block.*/
	public void generateBlock(int n, int scale) {
		//Calculates new block ID
		int id = highest_id;
		highest_id++;
		
		//Creates block
		Block block = new Block(n, scale, id, grid);
		
		//Adjust x position relative to left (x = 0) by random.
		Random r = new Random();
		int margin = grid.length - block.getDimension();
		if(margin < 0) {
			margin = 0;
		}
		margin = r.nextInt(margin);
		
		//Adds generated block to pre-grid.
		for(int x = 0; x < block.getDimension(); x++) {
			for(int y = 0; y < block.getDimension(); y++) {
				pregrid[margin + x][y] = block.getBlock()[x][y];				
			}
		}
		System.out.println("Generated block.");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
