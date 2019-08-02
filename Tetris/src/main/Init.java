package main;

public class Init {
	public static void main(String args[]) {
		System.out.println("Game of Tetris Starting...");
		Tetris game = new Tetris(10, 20);
		game.run();
	}
}
