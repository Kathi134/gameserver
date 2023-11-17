package view;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JTextArea;

import model.Dice;
import observer.Observer;
import observer.Subject;

// TODO: show how many dice are locked
// TODO: notify others if a player was eliminated

public class Terminal extends Observer
{
	private static final String BEGIN_NEW_ROUND_IDENTIFIER = "remaining dice:";
	
	private GUIRoot guiRoot;

	private TerminalBetKeyListener betListener;
	private JTextArea guiElement;
	private Dice[] observedDiceState;
	boolean dead;
	private int id;

	public Terminal(JTextArea guiElement, GUIRoot guiRoot, int id)
	{
		this.id = id;
		this.guiElement = guiElement;
		this.betListener = new TerminalBetKeyListener(guiElement, guiRoot);
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
		if(dead)
		{
			System.out.println("already dead");
			return;
		}
		
		observedDiceState = guiRoot.getController().getDiceOfPlayer(id);
		if(guiRoot.getController().isPlayerEliminated(id))
		{
			dead = true;
			printlnMessage("You are eliminated.");
		}
		else
		{
			showDice();			
		}
	}

	private void showDice()
	{
		printlnMessage(String.format("%s %d", BEGIN_NEW_ROUND_IDENTIFIER, guiRoot.getController().getAmountOfTotalDice()));
		String str = Arrays.stream(observedDiceState)
				.filter(d -> !d.isLocked())
				.map(d -> String.valueOf(d.readCurrentDots()))
				.collect(Collectors.joining(" "));
		printlnMessage(str);
	}
	
	public void disableInput()
	{
		betListener.clear();
		guiElement.removeKeyListener(betListener);
	}
	
	public void enableBetInput()
	{
		if (guiRoot.getCurrentBet() != null)
		{	
			printlnMessage("[doubt] " + guiRoot.getController().getLastPlayerId() + "'s bet: " + guiRoot.getCurrentBet());
			printMessage("or ");
		}
		printlnMessage("put your bet: At least [amount] times [value]");
		guiElement.addKeyListener(betListener);
	}
	
	public void enableReturnInput(Consumer<Terminal> callback) 
	{
		guiElement.removeKeyListener(betListener);
		guiElement.addKeyListener(new TerminalReturnKeyListener(callback, this));
	}
	
	public void processInvalidBet()
	{
		printlnMessage("Your bet did not outbid.");
		betListener.clear();
	}
	
	public void setTerminalTextToLastBet()
	{
		String currentText = guiElement.getText();
		guiElement.setText(currentText.substring(currentText.lastIndexOf(BEGIN_NEW_ROUND_IDENTIFIER)));
	}
	
	public void clearTerminal()
	{
		guiElement.setText("");
	}
	
	public void updateRemainingDice()
	{
		String current = guiElement.getText();
		int indexOfNewRoundIdentifier = current.indexOf(BEGIN_NEW_ROUND_IDENTIFIER);
		int indexOfNewLineAfterNewRoundIdentifier = current.substring(indexOfNewRoundIdentifier).indexOf("\n");
		String remaining = BEGIN_NEW_ROUND_IDENTIFIER + " " + guiRoot.getController().getAmountOfTotalDice();
		StringBuilder updated = new StringBuilder(current);
		updated.replace(indexOfNewRoundIdentifier, indexOfNewLineAfterNewRoundIdentifier, remaining);
		guiElement.setText(updated.toString());
	}
}