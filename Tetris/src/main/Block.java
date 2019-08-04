package main;

import java.util.Random;
/**
 * Handles generation of blocks using a chance-based algorithm.
 * Since the generation of blocks is somewhat complex, I decided to keep it separate from the block manager
 * to improve development over git-hub.
 * */
public class Block {
	
	private int min_dimension;
	private int[][] block;
	
	public Block(int n, int scale, int id, int grid[][]) {
		//Creates blank array for block in a small grid; acting as a blueprint.
		min_dimension = (int)Math.round(Math.sqrt(n));
		if(scale > 1) {
			min_dimension = min_dimension*scale;
		}
		block = new int[min_dimension][min_dimension];
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
		
		Random rand = new Random();
		
	}
	
	public int getDimension() {
		return min_dimension;
	}
	
	public int[][] getBlock(){
		return block;
	}
}
