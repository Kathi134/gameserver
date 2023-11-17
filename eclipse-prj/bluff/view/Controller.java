package view;

import model.Bet;
import model.BetResult;
import model.Dice;
import model.Round;
import observer.Observer;

public class Controller
{
	private Round round;
	private GUIRoot guiRoot;

	public Controller()
	{
		startRound(4);
	}
	
	public void closeApplication()
	{
		System.exit(0);
	}

	public void startRound(int numberOfPlayers)
	{
		round = new Round(numberOfPlayers);
		guiRoot = new GUIRoot(numberOfPlayers, this);
		round.enableNextRoll();
		updateGuiRootActivePlayer();
	}
	
	private void updateGuiRootActivePlayer()
	{
		guiRoot.setActivePlayer(round.getActivePlayer());
	}
	
	public void doubt()
	{
		round.activePlayerDoubt();
		guiRoot.showResult();
		if(round.isOver())
		{
			guiRoot.endGame(round.getGlobalWinner());
		}
		else
		{
			round.enableNextRoll();
			updateGuiRootActivePlayer();			
		}
	}
	
	public void sendBet(Bet bet)
	{
		if(!round.isNewBetHigher(bet))
		{
			return;
		}
		round.setCurrentBet(bet);
		updateGuiRootActivePlayer();
	}
	
	
	// Cascading Calls on Round
	
	public void subscribeObserverOnDice(Observer observer, int playerId)
	{
		if(round == null)
			return;
		round.subscribeObserverOnBoard(observer, playerId);
	}
	
	public boolean isValidBet(Bet bet)
	{
		if(round == null)
			return false;
		return round.isNewBetHigher(bet);
	}
	
	public BetResult getLastBetResult()
	{
		if(round == null)
			return new BetResult(null, 0, null, 0);
		return round.getLastBetResult();
	}
	
	public int getBettingPlayerId()
	{
		if(round == null)
			return -1;
		return round.getBettingPlayerId();
	}
	
	public int getLastPlayerId()
	{
		if(round == null)
			return -1;
		return round.getLastPlayerId();
	}
	
	public Dice[] getDiceOfPlayer(int id)
	{
		if(round == null)
			return new Dice[] {};
		return round.getDiceOfPlayer(id);
	}
	
	public boolean isPlayerEliminated(int id)
	{
		if(round == null)
			return true;
		return round.isPlayerEliminated(id);
	}
	
	public int getAmountOfTotalDice()
	{
		if(round == null)
			return 0;
		return round.getAmountOfTotalDice();
	}
}
