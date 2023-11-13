package view;

public enum Game
{
	CASCADIA, BLUFF;

	static Game get(char identifier)
	{
		return switch (identifier)
		{
			case 'c' -> CASCADIA;
			case 'b' -> BLUFF;
			default -> BLUFF;
		};
	}
}
