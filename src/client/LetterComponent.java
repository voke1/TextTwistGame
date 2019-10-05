package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

public class LetterComponent extends JComponent
{
	private char letter;
	private boolean isInput;
	private Font fnt;

	public LetterComponent(boolean x)
	{
		isInput = x;
		letter = ' ';
		this.setPreferredSize(new Dimension(80,80));
		//setOpaque(false);
	}

	public void paintComponent(Graphics g)
	{
		
		setOpaque(false);
		//g.setColor(Color.blue);
		//g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		g.fillOval(0,0 , getWidth() + 1, getHeight() + 1);
		
		if(letter == ' ')
			g.setColor(Color.PINK);
		else
			g.setColor(Color.orange);

		//g.fillOval(5, 5, getWidth()-10, getHeight()-10);
	
		g.fillOval(0,0 , getWidth() -2, getHeight() - 2);

		g.setFont(new Font(null, Font.PLAIN, 50));

		FontMetrics fontMetrics = g.getFontMetrics();

		int x = 31 - fontMetrics.charWidth(letter)/2;

		int y = 35 + (fontMetrics.getAscent() - fontMetrics.getDescent())/2;

		g.setColor(new Color(127, 0, 255));
		fnt = new Font(null, Font.BOLD, 65);
		g.setFont(fnt);
		g.drawString(letter + "", x,y);
	}

	public void setLetter(char s)
	{
		letter = s;
		this.repaint();
	}

	public char getLetter()
	{
		return letter;
	}

}