package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MyGamePlay extends JFrame implements ActionListener {
	private JTextArea textArea;
	private GuessPanelN guessPanel;
	private StartPlayer network;
	private ScoresBoard score;
	private JLabel time, photoLabel, label1, label2;
	private JPanel rightSide;
	private JButton readyButton, button1, conn2Server, exit;
	private JTextArea systemInfo;
	public String tempName;
	public String name;
	public JFrame gameFrame;
	public JPanel topRight, buttonPanel, bottomRight, containerPanel, topPanel,
			panelN, MainPanel;
	public JScrollPane scrollPane1;
	public Font font;
	public Dimension dimTextArea, dimInPanel;

	public MyGamePlay() {
		// gameFrame = new JFrame();
		dimTextArea = new Dimension(400, 300);
		dimInPanel = new Dimension(600, 450);
		containerPanel = new JPanel();
		gameFrame = new JFrame();

		panelN = new JPanel();
		panelN.setSize(200, 600);
		panelN.setBackground(Color.RED);

		this.setTitle("Online Text Twist");
		// this.setPositionRelativeTo(null);
		// this.setMaximizedBounds(new Rectangle(1024, 768));
		MainPanel = new JPanel();
		MainPanel.setLayout(new BorderLayout());
		;
		// MainPanel.add(panelN, BorderLayout.CENTER);

		photoLabel = new JLabel();

		MainPanel.setBackground(Color.BLACK);

		// this.setSize(830, 900);
		this.setPreferredSize(new Dimension(960, 728));
		// this.setMaximumSize(new Dimension(960, 540));
		this.setResizable(true);
		// MainPanel.setMaximumSize(new Dimension(960, 540));
		// this.setLayout(null);

		// this.setLayout(new BorderLayout());
		MainPanel.setSize(1366, 768);
		this.add(MainPanel, BorderLayout.CENTER);
		// this.add(MainPanel);

		Insets insets = getInsets();
		MainPanel.setBounds(0 - insets.left, 0 - insets.top, 1366, 768);

		Image img1 = new ImageIcon(this.getClass().getResource("newfile.png"))
				.getImage();
		photoLabel.setIcon(new ImageIcon(img1));
		MainPanel.add(photoLabel);
		setLocation(200, 0);
		// this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),
		// BoxLayout.PAGE_AXIS));

		gameFrame.setSize(800, 600);
		gameFrame.setMaximizedBounds(new Rectangle(800, 700));
		gameFrame.getContentPane().setLayout(
				new BoxLayout(gameFrame, BoxLayout.PAGE_AXIS));
		// /this.add(gameFrame);

		font = new Font("Dialog input", Font.BOLD, 30);

		this.setFont(font);

		// //Time and ScoreBoard
		// topPanel = new JPanel();
		// topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		// this.getContentPane().add(topPanel);
		// //gameFrame.add(topPanel);

		time = new JLabel("0:00");
		time.setForeground(Color.ORANGE);
		time.setBounds(355 - insets.left, 10 - insets.top, 250, 80);
		// time.setAlignmentX((int)Component.CENTER_ALIGNMENT);
		// time.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
		time.setPreferredSize(new Dimension(800, 50));
		time.setFont(font);
		//
		// Font myFont = new Font("SansSerif", Font.PLAIN, 10);
		Color myColor = Color.ORANGE;
		TitledBorder titledBorder = BorderFactory.createTitledBorder(null,
				" TIME", TitledBorder.TOP, TitledBorder.DEFAULT_POSITION, font,
				myColor);
		time.setBorder(titledBorder);

		// time.setBorder(BorderFactory.createTitledBorder("Time Left"));
		// time.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		time.setHorizontalAlignment(SwingConstants.CENTER);
		photoLabel.add(time);

		topRight = new JPanel();
		topRight.setLayout(new BoxLayout(topRight, BoxLayout.PAGE_AXIS));
		this.getContentPane().add(topRight);
		// gameFrame.add(topRight);
		// MainPanel.setLayout(null);

		score = new ScoresBoard(this, 1);
		score.setFont(font);
		score.setBounds(180 - insets.left, 100 - insets.top, 295, 110);
		photoLabel.add(score);

		rightSide = new JPanel();
		rightSide.setBackground(Color.RED);
		rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.PAGE_AXIS));
		rightSide.setBounds(20 - insets.left, 400 - insets.top, 750, 100);
		// photoLabel.add(rightSide);
		// gameFrame.add(rightSide);

		// Correct Words

		textArea = new JTextArea();
		// textArea.setPreferredSize(dimTextArea);
		// textArea.setMaximumSize(dimTextArea);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.append("Correct Words: \n");
		textArea.setBackground(Color.ORANGE);
		// textArea.setOpaque(false);
		scrollPane.setBounds(485 - insets.left, 100 - insets.top, 295, 110);
		scrollPane.setOpaque(false);
		photoLabel.add(scrollPane);
		// scrollPane.setFocusable(false);

		systemInfo = new JTextArea();
		systemInfo.setPreferredSize(dimTextArea);
		systemInfo.setMaximumSize(dimTextArea);
		// systemInfo.setLayout(new BoxLayout(systemInfo, BoxLayout.PAGE_AXIS));
		scrollPane1 = new JScrollPane(systemInfo);
		systemInfo.setEditable(false);
		systemInfo.setFocusable(false);

		scrollPane1.setBounds(228 - insets.left, 535 - insets.top, 500, 30);
		photoLabel.add(scrollPane1);
		// gameFrame.add(scrollPane1);
		// scrollPane1.setFocusable(false);
		systemInfo.setForeground(Color.RED);
		systemInfo.setBackground(Color.ORANGE);
		systemInfo.setFont(new Font("DialogInput", Font.PLAIN, 12));
		systemInfo.setLineWrap(true);

		// systemInfo.append("HI");

		// GuessPanel
		//
		//
		bottomRight = new JPanel();
		bottomRight.setLayout(new BoxLayout(bottomRight, BoxLayout.LINE_AXIS));
		bottomRight.setBounds(20 - insets.left, 550 - insets.top, 750, 100);
		bottomRight.setBackground(Color.BLACK);
		rightSide.add(bottomRight);

		// Button Panel

		// buttonPanel = new JPanel();
		// buttonPanel.setLayout(new BoxLayout(buttonPanel,
		// BoxLayout.LINE_AXIS));
		// this.getContentPane().add(buttonPanel);
		// gameFrame.add(buttonPanel);

		// adding buttons
		conn2Server = new JButton("Connect");
		conn2Server.setFont(new Font(null, Font.BOLD, 23));
		conn2Server.setBackground(Color.ORANGE);
		conn2Server.setForeground(new Color(127, 0, 255));
		conn2Server.setBounds(120 - insets.left, 610 - insets.top, 150, 35);
		conn2Server.setAlignmentX(Component.CENTER_ALIGNMENT);
		conn2Server.setMnemonic(KeyEvent.VK_S);
		conn2Server.setActionCommand("connect2Server");
		conn2Server.addActionListener(this);
		photoLabel.add(conn2Server);
		readyButton = new JButton("Start");
		readyButton.setBounds(310 - insets.left, 610 - insets.top, 150, 35);
		readyButton.setFont(new Font(null, Font.BOLD, 23));
		readyButton.setForeground(new Color(127, 0, 255));
		readyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		readyButton.setMnemonic(KeyEvent.VK_R);
		readyButton.setActionCommand("ready");
		readyButton.addActionListener(this);
		photoLabel.add(readyButton);
		readyButton.setFocusable(false);
		readyButton.setBackground(Color.ORANGE);

		button1 = new JButton("Scramble");
		button1.setBackground(Color.ORANGE);
		button1.setForeground(new Color(127, 0, 255));
		button1.setFont(new Font(null, Font.BOLD, 23));
		button1.setAlignmentX(Component.LEFT_ALIGNMENT);
		button1.setBounds(500 - insets.left, 610 - insets.top, 150, 35);
		button1.setMnemonic(KeyEvent.VK_S);
		button1.setActionCommand("scramble");
		button1.addActionListener(this);
		photoLabel.add(button1);
		button1.setFocusable(false);

		exit = new JButton("Quit");
		exit.setBackground(Color.ORANGE);
		exit.setForeground(new Color(127, 0, 255));
		exit.setFont(new Font(null, Font.BOLD, 23));
		exit.setAlignmentX(Component.LEFT_ALIGNMENT);
		exit.setBounds(690 - insets.left, 610 - insets.top, 150, 35);
		exit.setMnemonic(KeyEvent.VK_Q);
		exit.setActionCommand("Quit");
		exit.addActionListener(this);
		photoLabel.add(exit);
		exit.setFocusable(false);

		this.setResizable(true);
		this.pack();

		this.setVisible(true);

		// try{Thread.sleep(50);}catch(Exception e){}

		name = JOptionPane.showInputDialog(this, "Enter your name:");

		tempName = ""; // converts spaces to underscores
		for (int i = 0; i < name.length(); i++) {
			if (name.substring(i, i + 1).equals(" ")) {
				tempName += "_";
			} else {
				tempName += name.substring(i, i + 1);
			}
		}

		System.out.println(tempName);
		this.setTitle("Online Text Twist: " + name);
	}

	private void setPositionRelativeTo(Object object) {
		// TODO Auto-generated method stub

	}

	public void connectToServer(StartPlayer c) {
		try {
			Insets insets = getInsets();
			network = c;
			network.setMyGamePlay(this);
			guessPanel = new GuessPanelN(network);
			guessPanel.setBounds(205 - insets.left, 345 - insets.top, 550, 160);
			photoLabel.add(guessPanel);
			network.sendName(tempName);

			// gets focus
			this.addWindowFocusListener(new WindowAdapter() {
				public void windowGainedFocus(WindowEvent e) {
					guessPanel.requestFocusInWindow();
				}
			});

			guessPanel.requestFocusInWindow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problem connecting to server");
		}

	}

	public GuessPanelN getGuessPanel() {
		return guessPanel;
	}

	public void setScoresBoard(ScoresBoard s) {
		score = s;
	}

	public ScoresBoard getScoresBoard() {
		return score;
	}

	public void actionPerformed(ActionEvent e) {
		final String action = e.getActionCommand();

		if (action.equals("connect2Server")) {
			try {

				if (network != null) {
					return;
				}
				network = new StartPlayer("localhost", 8889);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			connectToServer(network);
		}

		if (action.equals("Quit")) {

			int kk = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Confirm Exit Dialog", JOptionPane.YES_NO_OPTION);
			if (kk == JOptionPane.YES_OPTION) {
				this.dispose();
			}
			// String confirm = JOptionPane.showMessageDialog("Are you sure");;
			// System.exit(0);
		}

		if (action.equals("ready")) {
			network.isReady();
		} else if (action.equals("scramble")) {
			try {

				char[] tempArray = new char[GuessPanelN.WORD_LENGTH];

				for (int i = 0; i < tempArray.length; i++) {
					tempArray[i] = guessPanel.getTextArea().get(i).getLetter();
				}
				tempArray = guessPanel.scramble(tempArray);

				for (int i = 0; i < tempArray.length; i++) {
					guessPanel.getTextArea().get(i).setLetter(tempArray[i]);
				}
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(null,
						"There is no word to scramble");
			}

			// guessPanel.setWord(guessPanel.getWord());
		}
	}

	public void updateCorrect(String word, int type, String clientName,
			int clientNum, int scoret) {
		// 0 is good 1 guessed already 2 nonexistent

		if (type == 0) {
			textArea.append(word + " - Guessed by "
					+ clientName.replace('_', ' ') + "\n");
			textArea.setCaretPosition(textArea.getDocument().getLength());
			score.updateScore(clientName, clientNum, scoret);
		}
		if (clientNum == network.getClientNumber()) {
			if (type == 0) {
				systemInfo.append("Word is correct.\n");
			} else if (type == 1) {
				systemInfo.append("Word already guessed.\n");
			} else if (type == 2) {
				systemInfo.append("Word does not exist.\n");
			}

			systemInfo.setCaretPosition(systemInfo.getDocument().getLength());
		}
	}

	public void setTime(int min, int sec) {
		if (min >= 0 && sec >= 0) {
			if (min == 0 && sec == 0) {
				time.setText("Round's up!");
				guessPanel.setReady(false);
				guessPanel.reset();
			} else if (sec > 9)
				time.setText(min + ":" + sec);
			else
				time.setText(min + ":0" + sec);
		}
	}

	public JButton getButton() {
		return readyButton;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void alertOverflow() {
		systemInfo
				.append("All words have been guessed. Press ready to start a new round.\n");
		systemInfo.setCaretPosition(systemInfo.getDocument().getLength());
	}

}
