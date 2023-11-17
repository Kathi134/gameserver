package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JTextArea;

import model.Bet;

public class TerminalBetKeyListener implements KeyListener
{
	private JTextArea terminal;
	private int charsEnteredByUser = 0;
	
	private GUIRoot guiRoot;
	
	public TerminalBetKeyListener(JTextArea terminal, GUIRoot guiRoot)
	{
		this.guiRoot = guiRoot;
		this.terminal = terminal;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		char inputChar = e.getKeyChar();
		if(inputChar == '\b' || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			if(charsEnteredByUser == 0)
				return;
			StringBuilder text = new StringBuilder(terminal.getText());
			text.deleteCharAt(text.length()-1);
			terminal.setText(text.toString());
			charsEnteredByUser--;
		}
		else if(inputChar == '\n' || e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			terminal.append(String.valueOf(inputChar));
			charsEnteredByUser++;
			String text = terminal.getText();
			text = text.substring(text.length() - charsEnteredByUser).trim();
			if(text.toLowerCase().contains("doubt"))
			{
				guiRoot.doubt();
			}
			else
			{
				int[] vals = Arrays.stream(text.toString().split(" ")).mapToInt(Integer::valueOf).toArray();
				guiRoot.setCurrentBet(new Bet(vals));
				guiRoot.sendBet();
			}
		}
		else
		{
			terminal.append(String.valueOf(inputChar));
			charsEnteredByUser++;
		}	
	}
	
	public void clear()
	{
		charsEnteredByUser = 0;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// Handle key pressed event if needed
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// Handle key released event if needed
	}
}