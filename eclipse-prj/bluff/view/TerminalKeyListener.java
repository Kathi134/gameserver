package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

public class TerminalKeyListener implements KeyListener
{
	private JTextArea terminal;
	StringBuffer buffer = new StringBuffer();

	public TerminalKeyListener(JTextArea terminal)
	{
		this.terminal = terminal;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			
		}
		
		char inputChar = e.getKeyChar();
		terminal.append(String.valueOf(inputChar));
		buffer.append(inputChar);
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