package view;

import client.Client;

class Navigator 
{
	Client networkClient;
	View view;
	
	public Navigator()
	{
		view = new View(this);
		view.mainMenu();
	}
	
	public void endProgram()
	{
		view.sayGoodbye();
		System.exit(0);
	}
	
	public void openGame(Game game)
	{
		switch(game)
		{
			case CASCADIA: 
				view.openCascadiaView();
			case BLUFF:
				view.openBluffGameConfigurationView();
		}
	}
	
	public void toMainMenu()
	{
		view.mainMenu();
	}
}