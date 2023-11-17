package model;

import java.util.EnumMap;

public class Bet implements Comparable<Bet>
{
	private int amount;
	private int value;
	
	public Bet(int amount, int value)
	{
		this.amount = amount;
		this.value = value;
	}
	
	public Bet(int[] values)
	{
		if(values.length != 2)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			this.amount = values[0];
			this.value = values[1];
			DiceValue.fromIntValue(values[1]);
		}
	}
	
	public BetResultType checkAgainstMap(EnumMap<DiceValue, Short> map)
	{
		DiceValue diceValue = DiceValue.fromIntValue(value);
		int actualAmount = map.get(diceValue);
		if(actualAmount == amount)
			return BetResultType.BET_EXACT_MATCH;
		else if(actualAmount > amount)
			return BetResultType.BET_WON;
		else
			return BetResultType.BET_LOST;
	}
	
	public int getDifferenceToMap(EnumMap<DiceValue, Short> map)
	{
		DiceValue diceValue = DiceValue.fromIntValue(value);
		int actualAmount = map.get(diceValue);
		if(value != 6)
			actualAmount += map.get(DiceValue.STAR);
		return Math.abs(actualAmount - amount);
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return String.format("At least %s times %s", amount, value);
	}

	@Override
	public int compareTo(Bet o)
	{
		if(this.amount == o.amount)
		{
			return this.value - o.value;
		}
		return this.amount - o.amount;
	}
}
