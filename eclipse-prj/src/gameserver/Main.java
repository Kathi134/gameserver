package gameserver;

public class Main
{
	public static final int GAME_PORT = 51317;
	
	public static void main(String[] args)
	{
		new GlobalServer(GAME_PORT).run();
	}
}

