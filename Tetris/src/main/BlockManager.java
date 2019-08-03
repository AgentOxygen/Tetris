package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Manages  blocks and their position within the grid (blocks are clusters filled cells).
 * Contains methods that read and update grid.
 * */
public class BlockManager {
	
	/**Grid is coded using '0' as empty and anything greater than 0 as filled.
	 * Positive numbers group units together into blocks.
	 * */
	private int[][] grid;
	/**Grid stored above the visible grid, where blocks are added and descend.*/
	private int[][] pregrid;
	
	/**Keeps track of highest possible ID for blocks*/
	public int highest_id = 1;
	
	public BlockManager(int width, int height) {
		grid = new int[width][height];
		pregrid = new int[width][height];
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
								if(grid[x][y-1] == 0) {
									n_grid[x][y-1] = i;
								}else if(grid[x][y-1] == 1) {
									merge.add(i);
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
		
		//Adds low-y position cells in pre-grid into top of grid, and shifts entire pre-grid down one.
		for(int x = 0; x < pregrid.length; x++) {
			n_grid[x][n_grid[0].length - 1] = pregrid[x][0];
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
		//Generates a new block every 3 seconds
		if(Tetris.game_time - time_switch2 > 3) {
			generateBlock(1, 1);
			System.out.println("Generated block.");
			time_switch2 = Tetris.game_time;
		}
	}
	
	/**Creates block with specified number of cells. Scale adjusts space taken up by block.*/
	public void generateBlock(int n, int scale) {
		//Assigns block ID
		int id = highest_id;
		highest_id++;
		
		//Creates blank array for block in a small grid; acting as a blueprint.
		int min_dimension = (int)Math.round(Math.sqrt(n));
		if(scale > 1) {
			min_dimension = min_dimension*scale;
		}
		int[][] block = new int[min_dimension][min_dimension];
		//Creates center "seed" cell for block.
		block[min_dimension/2][min_dimension/2] = id;
		
		/*
		 * Generates block using random growth pattern that fills cells next to faces. 
		 * At the moment, it considers every available face when filling more cells.
		 * 
		 * 		⬛ 	<-- Has 4 possible faces		⬛⬛⬛	<-- Has 8 possible faces 
		 * 
		 * 		⬛⬛⬛	Has 10 possible faces		⬛⬛⬛	<-- Has 10 possible faces
		 * 		⬛	<-- 2 result in same cell		   ⬛			4 result in same cell
		 * 
		 * Each filled cell must touch another filled cell. Therefore, to calculate the number
		 * of possible faces is simple: (n*4)-(2*(n-1))
		 * 'n' refers to the number of to-be-filled cells, each cell has four faces, minus the
		 * number of faces already touching.
		 * 		⬛⬛⬛ 	<-- has three cells (n=3) and four of the faces are touching. For every two
		 * 					touching filled cells, there are two faces that mustn't be considered.
		 * 					The number of touching cells is equal to one less than the total number
		 * 					of filled cells. Each individual cell is has four faces.
		 */
		
		Random r = new Random();
		
		int margin = grid.length - min_dimension;
		if(margin < 0) {
			margin = 0;
		}
		//Adjust x position relative to left (x = 0) by random.
		margin = r.nextInt(margin);
		
		//Adds generated block to pre-grid.
		for(int x = 0; x < min_dimension; x++) {
			for(int y = 0; y < min_dimension; y++) {
				pregrid[margin + x][y] = block[x][y];
			}
		}
		
		
	}
}
