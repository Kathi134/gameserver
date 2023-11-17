package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class TerminalReturnKeyListener implements KeyListener
{
	private Consumer<Terminal> callback;
	private Terminal target;
	
	public TerminalReturnKeyListener(Consumer<Terminal> callback, Terminal target)
	{
		this.callback = callback;
		this.target = target;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		char inputChar = e.getKeyChar();
		if(inputChar == '\n' || e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			callback.accept(target);
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{}

	@Override
	public void keyReleased(KeyEvent e)
	{}
}
