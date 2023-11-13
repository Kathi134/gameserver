package view;

import java.util.Scanner;

import utils.ArrayHelper;

public class View
{
	Scanner scanner = new Scanner(System.in);
	Navigator navigator;
	
	public View(Navigator nav)
	{
		navigator = nav;
	}
	
	public void sayGoodbye()
	{
		System.out.println("See you soon!");
	}
	
	public void mainMenu()
	{
		System.out.println("Welcome to KP-Games!");
		System.out.println("Choose a game to play:");
		System.out.println("\t[c] Cascadia, [b] Bluff");
		System.out.println("[q] exit");
		
		char input = awaitUserInput('c', 'b', 'q');
		
		if(input == 'q')
			navigator.endProgram();
		else
			navigator.openGame(Game.get(input));
	}
	
	public void openCascadiaView()
	{
		System.out.println("Alert: Cascadia is not available yet.");
		navigator.toMainMenu();
	}
	
	public void openBluffGameConfigurationView()
	{
		System.out.println("BLUFF - Gut geblufft ist halb gewonnen!");
		System.out.println("Choose game mode:");
		System.out.println("\t[l] local, [o] online");
		System.out.println("[m] main menu");
		System.out.println("[q] exit");
				
		char input = awaitUserInput('l', 'o', 'm', 'q');
		if(input == 'q')
			navigator.endProgram();
		else if(input == 'm')
			navigator.toMainMenu();
		else if(input == 'l')
			navigator.openGameConfigScreen(Bluff);
			
	}
	
	public char awaitUserInput(char... options)
	{
		char input = scanner.next().charAt(0);
		while(!ArrayHelper.elementIn(input, options))
		{
			System.out.println("Select a valid option:");
			input = scanner.next().charAt(0);
		}
		return input;
	}
}


