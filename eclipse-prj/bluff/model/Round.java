package model;

import java.util.Arrays;

import observer.Observer;

public class Round
{
	private int numberOfPlayers;
	private Board[] playerBoards;
	private int activePlayer;
	
	public Round(int numberOfPlayers)
	{
		this.numberOfPlayers = numberOfPlayers;
		playerBoards = new Board[numberOfPlayers];
		Arrays.setAll(playerBoards, i->new Board());
		activePlayer = 0;
	}
	
	public void enableNextRoll()
	{
		Arrays.stream(playerBoards).forEach(Board::rollAvailable);
	}
	
	public void subscribeObserverOnBoard(Observer observer, int playerId)	
	{
		playerBoards[playerId].subscribe(observer);
	}
	
	public Dice[] getDiceOfPlayer(int playerId)
	{
		return playerBoards[playerId].getDice();
	}
}
