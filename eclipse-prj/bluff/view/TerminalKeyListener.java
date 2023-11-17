package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JTextArea;

import model.Bet;

public class TerminalKeyListener implements KeyListener
{
	private JTextArea terminal;
	private int charsEnteredByUser = 0;
	
	private MultiGUIApp guiRoot;
	
	public TerminalKeyListener(JTextArea terminal, MultiGUIApp guiRoot)
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
			String text = terminal.getText().trim();
			text = text.substring(text.length() - charsEnteredByUser);
			if("doubt".equals(text.toLowerCase()))
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