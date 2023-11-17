package view;

import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Bet;
import model.BetResult;
import model.BetResultType;

public class GUIRoot
{
	private Controller controller;

	private JPanel mainPanel;
	private JFrame frame;

	private Terminal[] terminals;
	private Bet currentBet;

	private int activePlayer;

	public GUIRoot(int numberOfPlayers, Controller controller)
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
		Arrays.stream(terminals).forEach(Terminal::updateRemainingDice);
		this.activePlayer = playerId;
		for(int i=0; i<terminals.length; i++)
		{
			if(i == playerId)
			{
				terminals[i].enableBetInput();
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
			t.printlnMessage(activePlayer + " doubted " + currentBet);
		});
		controller.doubt();
	}
	
	public void showResult()
	{
		BetResult resultTriple = controller.getLastBetResult();
		int bettingPlayer = controller.getBettingPlayerId();
		String s1 = String.format("%d's bet was %s.\n", bettingPlayer, getResultText(resultTriple.result(), resultTriple.lostDice()));
		String s2 = String.format("%d won the bet. ", resultTriple.winningPlayer());
		String s3 = String.format("%s lost %d dice.", Arrays.toString(resultTriple.loosingPlayers()), resultTriple.lostDice());
		Arrays.stream(terminals).forEach(t -> t.printlnMessage(s1+s2+s3));
		
		currentBet = null;
		activePlayer = -1;
	}
	
	public String getResultText(BetResultType value, int lostDice)
	{
		return switch(value)
		{
			case BET_WON -> "correct";
			case BET_EXACT_MATCH -> "exactly correct";
			case BET_LOST -> "off by " + lostDice;
		};
	}
	
	public void sendBet()
	{
		if (controller.isValidBet(currentBet))
		{
			controller.sendBet(currentBet);			
		}
		else
		{
			terminals[activePlayer].processInvalidBet();
		}
	}
	
	public void endGame(int winnerId)
	{
		Arrays.stream(terminals).forEach(t -> 
		{
			t.printlnMessage(String.format("%d won the game!", winnerId));
			t.enableReturnInput(te -> backToLobby(te));
		});
	}
	
	private void backToLobby(Terminal terminal)
	{
		terminal.clearTerminal();
		terminal.printlnMessage("simulate being back in the lobby.");
		terminal.printlnMessage("bye.");
	}
	
	public void setCurrentBet(Bet bet)
	{
		currentBet = bet;
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

