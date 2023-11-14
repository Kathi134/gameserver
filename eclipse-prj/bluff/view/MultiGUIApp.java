package view;

import javax.swing.*;

import model.Board;

import java.awt.*;

public class MultiGUIApp
{
	private Controller controller;

	private JPanel mainPanel;
	private JFrame frame;

	private Terminal[] terminals;

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

			terminals[i] = new Terminal(terminal, controller, i);
		}

		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
	}

	private void printMessageOnTerminal(String message, int terminalId)
	{
		SwingUtilities.invokeLater(() -> terminals[terminalId].printMessage(message));
	}

}

