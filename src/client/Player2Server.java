package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player2Server extends Thread {
	public Socket socket;
	public PrintWriter out;
	public BufferedReader in;
	private StartPlayer client;
	private boolean setToTrue;

	public Player2Server(String ip, int port, StartPlayer sp) throws IOException
	{
		socket = new Socket(ip, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		client = sp;
	}

	public void run()
	{
		System.out.println("client loop running");

		while(client.getMyGamePlay() == null)
		{
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e){}
		}

		setToTrue = true;
		while(setToTrue)
		{
			
			//System.out.println("in while loop");
			try
			{
				this.process(in.readLine());
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		try
		{
			out.close();
			in.close();
			socket.close();
		}
		catch(IOException e)
		{}
	}

	public void process(String s)
	{
		System.out.println("s = " + s);

		if(s.startsWith("numbe ")) //which client number
		{
			//out.println("ready");
			client.setClientNumber(Integer.parseInt(s.substring(6)));
		}

		if(s.startsWith("scram ")) //gets a new word
		{
			client.setScrambledWord(s.substring(6));

		}

		if(s.startsWith("corre ")) //updates score
		{
			//format: corre word type clientname clientnumber scoreupdate

			String[] stringarray = s.substring(6).split(" ");

			//add last part 0,1,2
			client.updateCorrect(stringarray[0], Integer.parseInt(stringarray[1]), stringarray[2], Integer.parseInt(stringarray[3]), Integer.parseInt(stringarray[4]));
		}

		if(s.startsWith("curre ")) //current time
		{
			client.setTime(Integer.parseInt(s.substring(6)));
			//client.setStopTime(Integer.parseInt(s.substring(6)));
			//if (client.setStopTime(Integer.parseInt(s.substring(6)))==0){
			//	setToTrue = false;
				//System.out.println(setToTrue);
			//}
			//client.setTime(time);
		}
		
		if(s.startsWith("resta ")) //start a new round
		{
			client.alertRestart();
		}

		if(s.startsWith("total ")) //starting time
		{
			client.setTime(Integer.parseInt(s.substring(6)));
		}

		if(s.startsWith("List of Players: ")) //array of names
		{
			System.out.println("names = " + s);

			String[] stringarray = s.substring(17).split(" ");

			client.setScoreboard(stringarray.length, stringarray);
		}
		if(s.startsWith("overf ")) //all words guessed
		{
			client.alertRestart();
			client.alertOverflow();
		}



	}

	public void send(String s)
	{
		out.println(s);
	}


}
