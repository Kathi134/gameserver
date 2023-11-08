package main;

public class Main
{
	public static final int GLOBAL_PORT = 51317;
	
	public static void main(String[] args)
	{
		new GlobalServer(GLOBAL_PORT).run();
	}
}

