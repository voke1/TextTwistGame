package server;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends Thread implements Runnable{
	private GameLoop gameLoop;
    private Socket socket;
    public PrintWriter outToPlayer;
    private BufferedReader inFromPlayer;
    private String playerName;
    public int playerID;
    public int interval = 30;
    public Font font;
    private boolean running = false;
    private Thread thread;
    public long waitTime = 10;
    private volatile boolean done = false;
        
    public Player(GameLoop gameLoop, Socket socket, int playerID, PrintWriter outToPlayer, BufferedReader inFromPlayer ){
    	this.gameLoop = gameLoop;
    	this.socket = socket;
    	this.playerID = playerID;
    	this.outToPlayer = outToPlayer;
    	this.inFromPlayer = inFromPlayer;
    	font = new Font("DialogInput", Font.BOLD, 30);
    	try {
    		if (playerName == null) {
    			playerName = inFromPlayer.readLine();
    			playerName = playerName.substring(5); //gets rid of "name ";
    		}
			
            inFromPlayer.readLine(); //waiting for client to say it's ready
            gameLoop.playersReady(playerID);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
     }
    
    public synchronized void startGame(){
    	if (!running) return;
    	running = true;
    	thread = new Thread(this);
    	thread.start();
    	
    }
    
    public synchronized void stopGame(){
    	if (running) return;
    	running = false;
    	try {
			thread.join();
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
    	
    }
    
    public void run(){
    	
    	while (!done){
    		while(!gameLoop.amIReady())
    	    {
    			try
    			{
    				Thread.sleep(50);
    			}
    			catch(Exception e)
    			{
    			}
    		}
    		System.out.print("Both clients SHOULD be ready now!");
    		outToPlayer.println("Starting Round : " + gameLoop.gameRound);
    		outToPlayer.println("numbe " + playerID); //number assigned to the client
    		outToPlayer.println("scram " + gameLoop.wordHandler()); //the scrambled answer
    		outToPlayer.println("clien " + gameLoop.numOfPlayers());
            ArrayList<Player> playerList = gameLoop.getPlayerList();
            String output = "List of Players: ";
            for(int i = 0; i < gameLoop.numOfPlayers(); i++) {
            	output += playerList.get(i).getPlayerName();
            	if(i < gameLoop.numOfPlayers()-1) {output += " ";}
            }
            outToPlayer.println(output);
            outToPlayer.println("Round Time : " + interval + " Seconds"); // Set the game round timer
                   
            alert(null, 5, playerID, 0); //you're telling people that you're ready. that is all.
    		
    		int delay = 1000;
            int period = 1000;
            Timer timer = new Timer();
             interval = 30;
            System.out.println(30);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
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
                    gameLoop.checkWord(guess, playerID);
                }
                catch(java.io.IOException e) {System.out.println("Problem in the thread;");}
            }
            
            gameLoop.resetPlayersReady();
            outToPlayer.println("resta ");
            done = true;
    		
    		
    	}
    	

		
    }
    
    
    
    public void alert(String answers, int type, int playerID, int score)
    {
		if(playerID < 999)
		{
        	if(answers == null) // you are alerting everyone that someone is READY
          	  	outToPlayer.println("Player - " + playerID + " is Ready.");
        	else
            	outToPlayer.println("corre " + answers + " " + type + " " + gameLoop.getPlayers().get(playerID).getPlayerName() + " " + playerID + " " + score);
		}
		else
		{
			outToPlayer.println(answers + " " + score); //this is actually the time :)
		}
    }
    
    
    
	public String getPlayerName()
    {
    	return playerName;
    }
 	
}
