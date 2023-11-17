package model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.IntStream;

import observer.Observer;
import view.BetResultTriple;

public class Round
{
	public final int NUMBER_OF_PLAYERS;
	private Board[] playerBoards;
	private int activePlayer;
	
	private BetResultTriple lastBetResult;
	private Bet currentBet;
	private EnumMap<DiceValue, Short> wholePot;
	
	public Round(int numberOfPlayers)
	{
		this.NUMBER_OF_PLAYERS = numberOfPlayers;
		playerBoards = new Board[numberOfPlayers];
		Arrays.setAll(playerBoards, i->new Board());
		activePlayer = 0;
		wholePot = new EnumMap<>(DiceValue.class);
	}
	
	public void enableNextRoll()
	{
		Arrays.stream(playerBoards).forEach(Board::rollAvailable);
		for(DiceValue value: DiceValue.values())
		{
			wholePot.put(value, countOccurrencesOfDiceValue(value));
		}
	}
	
	private short countOccurrencesOfDiceValue(DiceValue value)
	{			
		return (short) Arrays.stream(playerBoards)
			.flatMap(b -> Arrays.stream(b.getAvailableDice()))
			.filter(d -> d.getDiceValue() == value)
			.count();
	}
	
	public void subscribeObserverOnBoard(Observer observer, int playerId)	
	{
		playerBoards[playerId].subscribe(observer);
	}
	
	public Dice[] getDiceOfPlayer(int playerId)
	{
		return playerBoards[playerId].getDice();
	}
	
	public int getActivePlayer()
	{
		return activePlayer;
	}
	
	public void setCurrentBet(Bet bet)
	{
		this.currentBet = bet;
		this.activePlayer++;
	}
	
	public void activePlayerDoubt()
	{
		BetResult result = currentBet.checkAgainstMap(wholePot);
		int[] loosingPlayers;
		int lostDice = -1;
		if(result == BetResult.BET_EXACT_MATCH)
		{
			lostDice = 1;
			IntStream.range(0, NUMBER_OF_PLAYERS)
				.filter(i -> i!=getPreviousPlayerId())
				.mapToObj(i -> playerBoards[i])
				.forEach(b -> b.loseDice(1));
			loosingPlayers = IntStream.range(0, NUMBER_OF_PLAYERS)
				.filter(i -> i!=getPreviousPlayerId())
				.toArray();
		}
		else
		{
			lostDice = currentBet.getDifferenceToMap(wholePot);
			int loosingPlayer = result == BetResult.BET_WON ? activePlayer : getPreviousPlayerId();
			playerBoards[loosingPlayer].loseDice(lostDice);
			loosingPlayers = new int[] {loosingPlayer};
		}
		lastBetResult = new BetResultTriple(result, loosingPlayers, lostDice);
	}
	
	public BetResultTriple getLastBetResult()
	{
		return lastBetResult;
	}
	
	public int getPreviousPlayerId()
	{
		int id = activePlayer-1;
		if (id == -1)
			id = NUMBER_OF_PLAYERS;
		return id;
	}
}
