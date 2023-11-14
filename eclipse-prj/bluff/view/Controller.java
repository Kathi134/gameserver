package view;

import model.Round;
import observer.Observer;

public class Controller
{
	// todo: enable terminal on turn only
	
	
	private Round round;
	private MultiGUIApp screens;

	public Controller()
	{
		startRound(4);
	}

	public void startRound(int numberOfPlayers)
	{
		round = new Round(numberOfPlayers);
		screens = new MultiGUIApp(numberOfPlayers, this);
		round.enableNextRoll();
	}

	public void subscribeObserverOnDice(Observer observer, int playerId)
	{
		if(round == null)
			return;
		round.subscribeObserverOnBoard(observer, playerId);
	}

	public Round getRound()
	{
		return round;
	}
}
