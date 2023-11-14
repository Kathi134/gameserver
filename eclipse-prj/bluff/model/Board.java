package model;

import java.util.Arrays;

import observer.Subject;

public class Board extends Subject
{
	public static final int CUP_SIZE = 5;
	
	private Dice[] dice = new Dice[CUP_SIZE];
	private int lockedDice = 0;
	
	public Board()
	{
		Arrays.setAll(dice, i->new Dice());
	}
	
	public void rollAvailable()
	{
		Arrays.stream(dice).filter(d->!d.isLocked()).forEach(Dice::roll);
		notifyObservers();
	}
	
	public void loseDice(int amount)
	{
		if(amount+lockedDice >= CUP_SIZE)
		{
			Arrays.stream(dice).forEach(Dice::lock);
		}
		else
		{
			for(int i=0; i<amount; i++)
			{
				dice[lockedDice++].lock();
			}
		}
	}
	
	public Dice[] getDice()
	{
		return Arrays.copyOf(dice, dice.length);
	}
}
