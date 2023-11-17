package model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.IntStream;

import observer.Observer;

// TODO: fix count logic -> start counts for everything

public class Round
{
	private static final int NO_WINNER_ID = -1;
	
	public final int NUMBER_OF_PLAYERS;
	private Board[] playerBoards;
	private int activePlayer;
	
	private int cachedBettingPlayer;
	private BetResult lastBetResult;
	private Bet currentBet;
	private EnumMap<DiceValue, Short> wholePot;
	
	private int globalWinner = NO_WINNER_ID;
	
	public Round(int numberOfPlayers)
	{
		this.NUMBER_OF_PLAYERS = numberOfPlayers;
		playerBoards = new Board[numberOfPlayers];
		Arrays.setAll(playerBoards, i->new Board());
		activePlayer = 0;
		cachedBettingPlayer = -1;
		wholePot = new EnumMap<>(DiceValue.class);
	}
	
	public void enableNextRoll()
	{
		Arrays.stream(playerBoards).forEach(Board::rollAvailable);
		for(DiceValue value: DiceValue.values())
		{
			wholePot.put(value, countOccurrencesOfDiceValue(value));
			System.out.print(value + ":" + countOccurrencesOfDiceValue(value) + "\t");
		}
		System.out.println(getAmountOfTotalDice());
	}
	
	private short countOccurrencesOfDiceValue(DiceValue value)
	{			
		return (short) Arrays.stream(playerBoards)
			.flatMap(b -> Arrays.stream(b.getAvailableDice()))
			.filter(d -> d.getDiceValue() == value)
			.count();
	}
	
	
	public void setCurrentBet(Bet bet)
	{
		if(!isNewBetHigher(bet))
			return;
		this.currentBet = bet;
		nextPlayer();
	}
	
	private void nextPlayer()
	{
		do
		{
			activePlayer = (activePlayer+1) % NUMBER_OF_PLAYERS;
		}
		while(playerBoards[activePlayer].isEliminated());
	}
	
	public void activePlayerDoubt()
	{
		cachedBettingPlayer = getLastPlayerId();
		
		BetResultType result = currentBet.checkAgainstMap(wholePot);
		int[] loosingPlayers;
		int lostDice = -1;
		int betWinner = getLastPlayerId();
		
		if(result == BetResultType.BET_EXACT_MATCH)
		{
			lostDice = 1;
			loosingPlayers = IntStream.range(0, NUMBER_OF_PLAYERS)
				.filter(i -> i!=getLastPlayerId())
				.toArray();
			Arrays.stream(loosingPlayers)
				.mapToObj(i -> playerBoards[i])
				.forEach(b -> b.loseDice(1));
		}
		else
		{
			lostDice = currentBet.getDifferenceToMap(wholePot);
			int loosingPlayer = activePlayer;
			if(result == BetResultType.BET_LOST)
			{
				loosingPlayer = getLastPlayerId();
				betWinner = activePlayer;
			}
			
			playerBoards[loosingPlayer].loseDice(lostDice);
			loosingPlayers = new int[] {loosingPlayer};
		}
		
		lastBetResult = new BetResult(result, betWinner, loosingPlayers, lostDice);
		currentBet = null;
		setBetWinnerToActivePlayer();
		
		var livingPlayers = IntStream.range(0, NUMBER_OF_PLAYERS)
				.filter(i -> !playerBoards[i].isEliminated()).toArray();
		if(livingPlayers.length == 1)
		{
			endGameAndSetWinner(livingPlayers[0]);
		}
	}
	
	public int getLastPlayerId()
	{
		int i=1;
		int bettingPlayerId = (activePlayer - i + NUMBER_OF_PLAYERS) % NUMBER_OF_PLAYERS;
		while(playerBoards[bettingPlayerId].isEliminated())
		{
			bettingPlayerId = (activePlayer - ++i + NUMBER_OF_PLAYERS) % NUMBER_OF_PLAYERS;			
		}
		return bettingPlayerId;
	}
	
	public int getAmountOfTotalDice()
	{
		// TODO: map is not in current state -> always 20 i geuss
		return wholePot.values().stream().mapToInt(i -> i).sum();
	}

	public void subscribeObserverOnBoard(Observer observer, int playerId)	
	{
		playerBoards[playerId].subscribe(observer);
	}	
	
	private void endGameAndSetWinner(int winnerId)
	{
		globalWinner = winnerId;
	}

	public BetResult getLastBetResult()
	{
		return lastBetResult;
	}
	
	public Dice[] getDiceOfPlayer(int playerId)
	{
		return playerBoards[playerId].getDice();
	}
	
	public int getActivePlayer()
	{
		return activePlayer;
	}
	
	private void setBetWinnerToActivePlayer()
	{
		activePlayer = lastBetResult.winningPlayer();	
	}
	
	public int getBettingPlayerId()
	{
		return cachedBettingPlayer;
	}
	
	public boolean isNewBetHigher(Bet newBet)
	{
		if(currentBet == null)
			return true;
		return newBet.compareTo(currentBet) > 0;
	}
	
	public boolean isPlayerEliminated(int id)
	{
		return playerBoards[id].isEliminated();
	}
	
	public boolean isOver()
	{
		return globalWinner != NO_WINNER_ID;
	}
	
	public int getGlobalWinner()
	{
		return globalWinner;
	}
	
	public void reset()
	{
		playerBoards = new Board[NUMBER_OF_PLAYERS];
		Arrays.setAll(playerBoards, i->new Board());
		activePlayer = 0;
		cachedBettingPlayer = -1;
		wholePot = new EnumMap<>(DiceValue.class);
		globalWinner = NO_WINNER_ID;
		lastBetResult = null;
	}
}
