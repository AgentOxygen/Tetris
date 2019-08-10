package main;

import java.util.ArrayList;
import java.util.Random;
/**
 * Handles generation of blocks using a chance-based algorithm.
 * Since the generation of blocks is somewhat complex, I decided to keep it separate from the block manager
 * to improve development over github.
 * */
public class Block {
	
	private int min_dimension;
	private int[][] block;
	
	@Deprecated
	/**Creates block by random generation.*/
	public Block(int n, int scale, int id, int grid[][]) {
		buildBlock(n, scale, id, grid);
	}
	
	/**Creates block by randomly selecting from the block templates.*/
	public Block() {
		block = BlockTemplates.randomlyGetTemplate();
		min_dimension = block.length;
	}
	
	@Deprecated
	public void buildBlock(int n, int scale, int id, int grid[][]) {
		//Creates blank array for block in a small grid; acting as a blueprint.
				min_dimension = (int)Math.round(Math.sqrt(n));
				if(scale > 1) {
					min_dimension = min_dimension*scale;
				}
				block = new int[min_dimension][min_dimension];
				
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
				 * 
				 * The algorithm decides which cell will act as a seed for which it can branch off of, and repeats
				 * this process step-by-step until the correct number of cells are filled.
				 * It then determines how many blocks are touching the seed cell and picks a side, if possible.
				 * 
				 * # of Cells Touching		# of possible faces				It then randomly picks a side to
				 * 			0						4						branch off if.
				 * 			1						3
				 * 			2						2
				 * 			3						1
				 * 			4						0
				 * 
				 */
				
				Random rand = new Random();
				
				//Creates first "seed" cell for block.
				block[min_dimension/2][min_dimension/2] = id;
				
				//Index of filled cells --> [in - fn][x + y]
				int[][] index = new int[n][2];
				
				//Index first seed cell
				index[0][0] = min_dimension/2;
				index[0][1] = min_dimension/2;
				
				//Step-by-step generation
				for(int i = 1; i <= n; i++) {
					//Select one of the indexed-filled cells randomly
					int sel = rand.nextInt(i);
					int x = index[sel][0];
					int y = index[sel][1];
					
					//Array used to determine if North-South-West-East faces are available
					boolean[] faces = new boolean[4];
					int n_faces = 0;
					for(int f = 0; f < 4; f++) {
						switch(f) {
						case 0:
							//Check North
							try {
								if(block[x][y + 1] == 0) {
									faces[0] = true;
									n_faces++;
								}else {
									faces[0] = false;
								}
							}catch(IndexOutOfBoundsException e) {
								System.out.println("Branch error. Decrease 'n' or increase 'scale'.");
							}
							break;
						case 1:
							//Check South
							try {
								if(block[x][y - 1] == 0) {
									faces[1] = true;
									n_faces++;
								}else {
									faces[1] = false;
								}
							}catch(IndexOutOfBoundsException e) {
								System.out.println("Branch error. Decrease 'n' or increase 'scale'.");
							}
							break;
						case 2:
							//Check East
							try {
								if(block[x + 1][y] == 0) {
									faces[2] = true;
									n_faces++;
								}else {
									faces[2] = false;
								}
							}catch(IndexOutOfBoundsException e) {
								System.out.println("Branch error. Decrease 'n' or increase 'scale'.");
							}
							break;
						case 3:
							//Check West
							try {
								if(block[x - 1][y] == 0) {
									faces[3] = true;
									n_faces++;
								}else {
									faces[3] = false;
								}
							}catch(IndexOutOfBoundsException e) {
								System.out.println("Branch error. Decrease 'n' or increase 'scale'.");
							}
							break;
						}
					}
					
					int direction = -1;
					
					if(n_faces > 0) {
						direction = pickRandomSide(rand, faces);
						
					}else {
						System.out.println("Failed to generate " + i + "th cell in block.");
					}
					
					if(direction != -1) {
						switch(direction) {
						case 1:
							index[i - 1][0] = x;
							index[i - 1][1] = y + 1;
							block[x][y + 1] = id;
							break;
						case 2:
							index[i - 1][0] = x;
							index[i - 1][1] = y - 1;
							block[x][y - 1] = id;
							break;
						case 3:
							index[i - 1][0] = x + 1;
							index[i - 1][1] = y;
							block[x + 1][y] = id;
							break;
						case 4:
							index[i - 1][0] = x - 1;
							index[i - 1][1] = y;
							block[x][y] = id;
							break;
						}
					}
					
				}
	}
	
	/**
	 * Picks random available side:
	 * 1 - North
	 * 2 - South
	 * 3 - East
	 * 4 - West
	 * */
	private int pickRandomSide(Random r, boolean[] sides) {
		
		ArrayList<Integer> directions = new ArrayList<Integer>();
		
		if(sides[0] == true) {
			directions.add(1);
		} else if (sides[1] == true) {
			directions.add(2);
		} else if (sides[2] == true) {
			directions.add(3);
		} else if (sides[3] == true) {
			directions.add(4);
		}
		
		return directions.get(r.nextInt(directions.size()));
	}
	
	public int getDimension() {
		return min_dimension;
	}
	
	public int[][] getBlock(){
		return block;
	}
}
