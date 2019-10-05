package server;

public class Game {

	public static void main(String[] args) {
		GameLoop gameLoop = new GameLoop(8889, 1);
		gameLoop.gameLoop();
		
	}

}
