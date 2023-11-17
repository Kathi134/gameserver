package model;

public class Dice
{
	private boolean locked = false;
	private int currentDots = 0;
	
	public int roll()
	{
		currentDots = locked ? 0 : (int) Math.round(Math.random() * 5) + 1;
		return currentDots;
	}
	
	public void lock()
	{
		locked = true;
		currentDots = 0;
	}
	
	public boolean isLocked()
	{
		return locked;
	}
	
	public int readCurrentDots()
	{
		return currentDots;
	}
	
	public DiceValue getDiceValue()
	{
		return DiceValue.fromIntValue(currentDots);
	}
}
