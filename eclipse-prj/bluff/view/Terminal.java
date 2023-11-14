package view;

import java.util.Arrays;

import javax.swing.JTextArea;

import model.Dice;
import observer.Observer;
import observer.Subject;

public class Terminal extends Observer
{
	private Controller controller;

	private JTextArea guiElement;
	private Dice[] observedDiceState;
	private int id;

	public Terminal(JTextArea guiElement, Controller controller, int id)
	{
		this.guiElement = guiElement;
		guiElement.addKeyListener(new TerminalKeyListener(guiElement));
		this.controller = controller;
		this.id = id;
		this.controller.subscribeObserverOnDice(this, id);
	}

	public void printMessage(String message)
	{
		guiElement.append(message);
		guiElement.append("\n");
	}

	@Override
	public void update(Class<? extends Subject> subjectClass)
	{
		observedDiceState = controller.getRound().getDiceOfPlayer(id);
		showDice();
	}

	private void showDice()
	{
		String str = Arrays.stream(observedDiceState).filter(d -> !d.isLocked())
				.map(d -> String.valueOf(d.readCurrentDots())).reduce("", (a, b) -> a + " " + b);
		printMessage(str);
	}
}