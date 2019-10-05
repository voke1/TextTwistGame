package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class LetterComp extends JComponent {
	private char letter;
	private boolean isInput;
	public JLabel label;

	public LetterComp(boolean x)
	{
		isInput = x;
		letter = ' ';
		this.setPreferredSize(new Dimension(80,80));
	}

	public void paintComponent(Graphics g)
	{
		//g.setColor(Color.blue);
		
		//g.fillRect(0, 0, getWidth(), getHeight());
		label = new JLabel();
		
		if(letter == ' ')
			g.setColor(Color.PINK);
		else
			g.setColor(Color.orange);

		//g.fillOval(5, 5, getWidth()-10, getHeight()-10);
		g.fillOval(0,0 , getWidth() -5, getHeight() - 5);

		g.setFont(new Font(null, Font.PLAIN, 60));

		FontMetrics fontMetrics = g.getFontMetrics();

		int x = 40 - fontMetrics.charWidth(letter)/2;

		int y = 40 + (fontMetrics.getAscent() - fontMetrics.getDescent())/2;

		g.setColor(Color.black);
		g.drawString(letter + "", x, y);
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
