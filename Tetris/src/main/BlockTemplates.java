package main;

import java.util.Random;

public class BlockTemplates {
	
	private static Random r = new Random();
	
	/**Randomly selects and creates a two dimensional integer array from templates for generating a block.*/
	public static int[][] randomlyGetTemplate(){
		int selection = r.nextInt(10);
		int[][] bp = new int[7][7];
		
		for(int x = 0; x < bp.length; x++) {
			for(int y = 0; y < bp[0].length; y++) {
				bp[x][y] = 0;
			}
		}
		
		System.out.println(selection);
		
		switch(selection) {
		case 0:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			bp[4][3] = 1;
			bp[4][4] = 1;
			break;
		case 1:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			bp[3][6] = 1;
			bp[4][3] = 1;
			break;
		case 2:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			bp[4][3] = 1;
			bp[4][4] = 1;
			bp[5][3] = 1;
			bp[5][4] = 1;
			break;
		case 3:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			break;
		case 4:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			bp[3][6] = 1;
			break;
		case 5:
			bp[3][2] = 1;
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			bp[3][6] = 1;
			break;
		case 6:
			bp[3][0] = 1;
			bp[4][0] = 1;
			bp[5][0] = 1;
			bp[6][0] = 1;
			break;
		case 7:
			bp[3][3] = 1;
			bp[3][4] = 1;
			break;
		case 8:
			bp[3][3] = 1;
			bp[3][4] = 1;
			bp[3][5] = 1;
			break;
		case 9:
			bp[3][0] = 1;
			bp[4][0] = 1;
			bp[5][0] = 1;
			break;
		default:
			bp[3][0] = 1;
			bp[4][0] = 1;
			bp[5][0] = 1;
			bp[4][1] = 1;
			break;
		}
		
		return bp;
	}
}
