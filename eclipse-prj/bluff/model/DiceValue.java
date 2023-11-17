package model;

public enum DiceValue
{
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), STAR(6);

	private int intValue;

	DiceValue(int intValue)
	{
		this.intValue = intValue;
	}

	public int toIntValue()
	{
		return intValue;
	}
	
	public static DiceValue fromIntValue(int value)
	{
		switch(value)
		{
			case 1: return ONE;
			case 2: return TWO;
			case 3: return THREE;
			case 4: return FOUR;
			case 5: return FIVE;
			case 6: return STAR;
			default: throw new IllegalArgumentException();
		}
	}
}
