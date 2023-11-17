package view;

import model.Bet;
import model.Round;
import observer.Observer;

public class Controller
{
	private Round round;
	private MultiGUIApp guiRoot;

	public Controller()
	{
		startRound(4);
	}

	public void startRound(int numberOfPlayers)
	{
		round = new Round(numberOfPlayers);
		guiRoot = new MultiGUIApp(numberOfPlayers, this);
		round.enableNextRoll();
		updateGuiRootActivePlayer();
	}
	
	private void updateGuiRootActivePlayer()
	{
		guiRoot.setActivePlayer(round.getActivePlayer());
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
	
	public void doubt()
	{
		round.activePlayerDoubt();
		guiRoot.showResult();
		round.enableNextRoll();
	}
	
	public void sendBet(Bet bet)
	{
		round.setCurrentBet(bet);
		updateGuiRootActivePlayer();
	}
}
