package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoop  implements Runnable{
	
	
		private ArrayList<Player> playerList;
	   	private ArrayList<Socket> socketList;
	   	private Player player;
	    private WordHand wordHandler;
	    private ArrayList<String> correctWords;
	    private ArrayList<Integer> scores;
	    private int numOfPlayers; //total number of players
	    private int numReady; //number of players ready
	    private int port;
	    private int playerID;
	    public int interval;
	    private String playerName;
	    public int gameRound;
	    
	    public ServerSocket serverSocket;
	    public Socket socket;
	    public Thread thread;
	    public PrintWriter outToPlayer;
	    public BufferedReader inFromPlayer;
	    
	    private boolean running = false;
	    
	    public static final int ROUND_TIME = 30; // expressed in seconds
	    
		/* types of guesses */
	    private static final int GOOD_GUESS = 0;
	    private static final int GUESSED_ALREADY = 1;
	    private static final int INCORRECT = 2;
	    
	    public GameLoop(int port, int numOfPlayers ){
	    	this.port = port;
	    	this.numOfPlayers = numOfPlayers;
	    	socketList = new ArrayList<Socket>();
	    	playerList = new ArrayList<Player>();
	    	wordHandler = new WordHand();
	    	correctWords = new ArrayList<String>();
	    	scores = new ArrayList<Integer>();
	    	numReady = 0;
	    	playerID = 0;
	    	
	    }
	    
	    public void gameLoop(){
	    	startServer();
	    	for (gameRound = 1; gameRound <= numOfPlayers; gameRound++){
	    		handleWords();
	    		System.out.println("This is round : " + gameRound);
	    		startPlayerConnection();
	    		continue;
	    	}
	    	
	    }
	    
	    public void handleWords(){
	    	wordHandler.chosenWordsList();
	    	wordHandler.chooseWord();
	    	wordHandler.potentialAnswers();
	    	System.out.println("The Total Number of Words that can be formed from " + wordHandler.getRandomWord() + " is : " + wordHandler.potentialAnswers());
	        wordHandler.printPotentialAns();
	    	System.out.println("Again, this is the chosen random word : " + wordHandler.getRandomWord());
	    }
	    public void playersReady(int num)
	    {
			numReady++;
			System.out.println("The number ready - " + numReady);
			System.out.println("The overall number - " + numOfPlayers);
		}
	    
	    public int resetPlayersReady() {
			return numReady = 0;
			
		}
	    
	    public ArrayList<Player> getPlayers(){
	    	return playerList;
	    }
	    
	    public void startServer(){
	    	try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("Attempt to start server failed. Check that the port is available");
			}
	    }
	    
	    public void startPlayerConnection(){
	    	
	    	
	    	while (true){
	    		try 
	    		{
					socket = serverSocket.accept();
					socketList.add(socket);
					
					outToPlayer = new PrintWriter(socket.getOutputStream(), true);
					inFromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					player = new Player(this, socket, playerID, outToPlayer, inFromPlayer);
					playerList.add(player);
					playerList.get(playerList.size() - 1).start();
					System.out.println("Player "+ playerID + " has connected");
					scores.add(new Integer(0), playerID);
					playerID++;
					/*
					if (player.interval == 0)
					{
						resetPlayersReady();
						System.out.println("Num of Players now Ready : "+ resetPlayersReady());
					}
					 * 
					 */
					
			    } catch (IOException e) {
						e.printStackTrace();
				}
	    	}
	    }
	    
	    public void startGameLoop(){
	    	if (running) return;
	    	running = true;
	    	thread = new Thread(this);
	    	thread.start();
	    }
	    
	    public void stopGameLoop(){
	    	if (!running) return;
	    	running = false;
	    	try {
				thread.join();
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("This stopGameLoop failed");
			}
	    }
	    
	    public void tick(){
	    	
	    	System.out.println("game loop starts here");
			while(!amIReady())
		    {
				try
				{
					Thread.sleep(50);
				}
				catch(Exception e)
				{
				}
			}
			
			System.out.println("Does this print after first loop?");
			
			System.out.print("Both clients SHOULD be ready now!");
			outToPlayer.println("numbe " + playerID); //number assigned to the client
			outToPlayer.println("scram " + wordHandler.getRandomWord()); //the scrambled answer
			outToPlayer.println("clien " + numOfPlayers());
	        ArrayList<Player> playerList = getPlayerList();
	        String output = "names ";
	        for(int i = 0; i < numOfPlayers(); i++) {
	        	output += playerList.get(i).getPlayerName();
	        	if(i < numOfPlayers()-1) {output += " ";}
	        }
	        outToPlayer.println(output);
	        outToPlayer.println("total " + GameLoop.ROUND_TIME); // Set the game round timer
	               
	        alert(null, 5, playerID, 0); //you're telling people that you're ready. that is all.
			
			int delay = 1000;
	        int period = 1000;
	        Timer timer = new Timer();
	         interval = 30;
	        System.out.println(30);
	        timer.scheduleAtFixedRate(new TimerTask() {
	            @Override
	            public void run() {
	                //setInterval();
	                if (interval == 1)
	                timer.cancel();
	                	--interval;
	                        
	               outToPlayer.println("curre " + interval);
	            }
	        }, delay, period);
	        
	        while(interval>=1)
	        {
	            try
	            {
	            	System.out.println("Waiting for something from client");
	                String guess = inFromPlayer.readLine();
	                if(guess.startsWith("guess"))
	                {
	                    guess = guess.substring(6, guess.length());
	                    System.out.println("here's the guess - " + guess);
	                }
	                checkWord(guess, playerID);
	            }
	            catch(java.io.IOException e) {System.out.println("Problem in the thread;");}
	        }
	        
	        outToPlayer.println("resta ");
	    	
	    }
	    
	    public String wordHandler(){
	    	return wordHandler.getRandomWord();
	    }
	    
	    public boolean amIReady()
	    {
			return numReady == numOfPlayers;
		}
	    
	    public String getPlayerName()
	    {
	    	return playerName;
	    }
	    
	    public ArrayList<Player> getPlayerList()
		{
			return playerList;
		}
	    
	    public boolean checkWord(String guess, int playerID) //playerID is the number of the client
	    {
	    	//type 0 good, 1 guessed already, 2 incorrect
	    	int type = GOOD_GUESS;
	    	
	    	if (!wordHandler.checkWord(guess)) { type = INCORRECT; }
	    	else if (correctWords.contains(guess)) { type = GUESSED_ALREADY; }
	    	
	        if (correctWords.contains(guess) == false)
	        {
	            if (wordHandler.checkWord(guess))
	            {
	                correctWords.add(guess);
	                scores.set(playerID, scores.get(playerID) + (guess.length() * 100));
	                alert(guess, type, playerID, scores.get(playerID));

	                return true;
	            }
	            return false;
	        }
	        return false;
	    }
	    
	    public void alert(String answer, int type, int playerID, int score) //main use is to alert on guesses, but does other stuff too
	    {
	        for(int i = 0; i < playerList.size(); i++)
	        {
	            playerList.get(i).alert(answer, type, playerID, score);
	        }
	    }
	    
	    public int numOfPlayers()
		{
			return playerList.size();
		}

		public void run() {
			
	    	long lastTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			double delta = 0.0;
			double ns = 1000000000.0/60.0;
			int frames = 0;
			int ticks = 0;
			
			while (running){
				long now = System.nanoTime();
				delta += (now - lastTime)/ns;
				lastTime = now;
				while (delta >= 1){
					tick();
					ticks++;
					delta--;
				}
				//render();
				frames++;
				if (System.currentTimeMillis() - timer > 1000){
					timer+=1000;
					//System.out.println(frames + "  Frames Per Second  " + ticks + "  Updates Per Second");
					frames = 0;
					ticks = 0;
				}
			}
			//stopGameLoop();
		}
}
