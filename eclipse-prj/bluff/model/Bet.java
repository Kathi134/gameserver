package model;

import java.util.EnumMap;

public class Bet
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
	
	public BetResult checkAgainstMap(EnumMap<DiceValue, Short> map)
	{
		DiceValue diceValue = DiceValue.fromIntValue(value);
		int actualAmount = map.get(diceValue);
		if(actualAmount == amount)
			return BetResult.BET_EXACT_MATCH;
		else if(actualAmount > amount)
			return BetResult.BET_WON;
		else
			return BetResult.BET_LOST;
	}
	
	public int getDifferenceToMap(EnumMap<DiceValue, Short> map)
	{
		DiceValue diceValue = DiceValue.fromIntValue(value);
		int actualAmount = map.get(diceValue);
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
}
