package view;

import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Bet;

public class MultiGUIApp
{
	private Controller controller;

	private JPanel mainPanel;
	private JFrame frame;

	private Terminal[] terminals;
	private Bet currentBet;

	private int activePlayer;

	public MultiGUIApp(int numberOfPlayers, Controller controller)
	{
		this.controller = controller;
		terminals = new Terminal[numberOfPlayers];
		initializeWindow();
		initializeTerminals();
	}

	private void initializeWindow()
	{
		frame = new JFrame("Multi-Terminal GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		mainPanel = new JPanel(new GridLayout(2, 2));
	}

	private void initializeTerminals()
	{
		for(int i = 0; i < terminals.length; i++)
		{
			JTextArea terminal = new JTextArea();
			terminal.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(terminal);
			mainPanel.add(scrollPane);

			terminals[i] = new Terminal(terminal, this, i);
		}

		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
	}

	public void setActivePlayer(int playerId)
	{
		this.activePlayer = playerId;
		for(int i=0; i<terminals.length; i++)
		{
			if(i == playerId)
			{
				terminals[i].enableInput();
			}
			else
			{
				terminals[i].disableInput();
			}
		}
	}
	
	public void doubt()
	{
		Arrays.stream(terminals).forEach(t -> 
		{
			t.disableInput();
//			t.printNewLineIfNot();
			t.printlnMessage(activePlayer + " doubted " + currentBet);
		});
		controller.doubt();
	}
	
	public void showResult()
	{
		
	}
	
	public void setCurrentBet(Bet bet)
	{
		currentBet = bet;
	}
	
	public void sendBet()
	{
		controller.sendBet(currentBet);
	}
	
	public Controller getController()
	{
		return controller;
	}
	
	public int getCurrentPlayer()
	{
		return activePlayer;
	}
	
	public Bet getCurrentBet()
	{
		return currentBet;
	}
}

