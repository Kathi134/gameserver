package view;

import java.util.Arrays;

import javax.swing.JTextArea;

import model.Dice;
import observer.Observer;
import observer.Subject;

public class Terminal extends Observer
{
	private MultiGUIApp guiRoot;

	private TerminalKeyListener listener;
	private JTextArea guiElement;
	private Dice[] observedDiceState;
	private int id;

	public Terminal(JTextArea guiElement, MultiGUIApp guiRoot, int id)
	{
		this.id = id;
		this.guiElement = guiElement;
		this.listener = new TerminalKeyListener(guiElement, guiRoot);
		this.guiRoot = guiRoot;
		this.guiRoot.getController().subscribeObserverOnDice(this, id);
	}
	
	public void printMessage(String message)
	{
		guiElement.append(message);
	}

	public void printlnMessage(String message)
	{
		guiElement.append(message);
		guiElement.append("\n");
	}

	@Override
	public void update(Class<? extends Subject> subjectClass)
	{
		observedDiceState = guiRoot.getController().getRound().getDiceOfPlayer(id);
		showDice();
	}

	private void showDice()
	{
		String str = Arrays.stream(observedDiceState).filter(d -> !d.isLocked())
				.map(d -> String.valueOf(d.readCurrentDots())).reduce("", (a, b) -> a + " " + b);
		printlnMessage(str);
	}
	
	public void disableInput()
	{
		guiElement.removeKeyListener(listener);
	}
	
	public void enableInput()
	{
		if (guiRoot.getCurrentBet() != null)
		{	
			printlnMessage("[doubt] " + guiRoot.getController().getRound().getPreviousPlayerId() + "'s debt: " + guiRoot.getCurrentBet());
			printMessage("or ");
		}
		printlnMessage("put your bet: At least [amount] times [value]");
		guiElement.addKeyListener(listener);
	}
	
	
}