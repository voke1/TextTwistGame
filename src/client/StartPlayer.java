package client;

import java.awt.Color;
import java.io.IOException;

public class StartPlayer{
	private Player2Server player2Server;
	private GuessPanelN guessPanel;
	//private MyGamePlay f;
	private MyGamePlay mgp;
	private int max_time; // expressed in seconds
	private int clientNumber;

	public StartPlayer(String ip, int port) throws IOException
	{
		player2Server = new Player2Server(ip, port, this);


		player2Server.start();
	}

	public void receive(String s)
	{
		System.out.println(s);
		
		
	}

	public void sendName(String name)
	{
		player2Server.send("name " + name);
	}

	public void sendWord(String s)
	{
		player2Server.send("guess " + s);
	}

	public void isReady()
	{
		player2Server.send("ready");
	}

	public void setGuessPanel(GuessPanelN p)
	{
		guessPanel = p;
	}

	

	public void setClientNumber(int x)
	{
		clientNumber = x;
	}

	public int getClientNumber()
	{
		return clientNumber;
	}

	public void setScrambledWord(String s)
	{
		while(guessPanel == null)
		{
			try
				{Thread.sleep(1);}
			catch(Exception e)
			{}
		}
		guessPanel.setWord(s);

		mgp.getTextArea().removeAll();
		mgp.getButton().setBackground(null);
		guessPanel.setReady(true);
	}

	public void updateCorrect(String word, int type, String clientName, int clientNum, int score)
	{
		mgp.updateCorrect(word, type, clientName, clientNum, score);
	}

	public void setScoreboard(int size, String[] stringarray)
	{
		/*String[] nameArray = new String[(stringarray.length-1)/2];

		for(int i = 1; i < stringarray.length; i = i + 2)
		{
			nameArray[Integer.parseInt(stringarray[i])] = stringarray[i+1];
		}*/

		mgp.getScoresBoard().setBoard(size, stringarray);

		mgp.pack();

		//f.setScoreboard(temp);
	}

	
	
	public void setMyGamePlay(MyGamePlay mgp)
	{
		this.mgp = mgp;
		//System.out.println("setTextTwistFrame");
	}
	
	public MyGamePlay getMyGamePlay()
	{
		return mgp;
	}

	public void setTime(int time)
	{
		int min = time/60;
		int sec = time - (min*60);

		mgp.setTime(min, sec);

	}
	
	public int setStopTime(int time)
	{
		int min = time/60;
		int sec = time - (min*60);
	return time;

	}

	public void alertRestart()
	{
		mgp.getButton().setBackground(Color.YELLOW);
	}

	public void alertOverflow()
	{
		mgp.alertOverflow();
	}

}
